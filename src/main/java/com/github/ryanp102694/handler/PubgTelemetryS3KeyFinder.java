package com.github.ryanp102694.handler;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.github.ryanp102694.lambda.PubgTelemetryParserService;
import com.github.ryanp102694.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

//set a FUNCTION_NAME environment variable to pubgTelemetryS3KeyFinder to trigger this
@Component("pubgTelemetryS3KeyFinder")
public class PubgTelemetryS3KeyFinder implements Function<PubgTelemetryS3KeyFinderRequest, String> {

    private final static Logger log = LoggerFactory.getLogger(PubgTelemetryS3KeyFinder.class);

    @Value("${aws.bucket}")
    String awsBucket;

    @Value("${aws.lambda.concurrency}")
    Integer concurrency;

    /**
     * Find S3 keys of all the telemetry files.
     * Invoke lots of Lambdas in parallel.
     * Wait for responses.
     * Join results.
     * Write write results to an S3 object.
     *
     * @param pubgTelemetryS3KeyFinderRequest
     * @return
     */
    @Override
    public String apply(PubgTelemetryS3KeyFinderRequest pubgTelemetryS3KeyFinderRequest) {


        log.info("Creating Amazon S3 client for bucket {} in region {}",
                awsBucket, Regions.US_EAST_1);

        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1)
                .build();

        log.info("Getting keys from bucket {} and folders {}", awsBucket, pubgTelemetryS3KeyFinderRequest.getFolderNames());

        Set<String> s3Keys = new HashSet<>();

        for(String folderName : pubgTelemetryS3KeyFinderRequest.getFolderNames()){
            Set<String> keys = s3Client.listObjectsV2(awsBucket, folderName).getObjectSummaries()
                    .stream()
                    .map(s3ObjectSummary -> s3ObjectSummary.getKey())
                    .collect(Collectors.toSet());
            keys.remove(folderName + "/");
            log.info("Found {} keys from folder {}. The keys are {}", keys.size(), folderName, keys);
            s3Keys.addAll(keys);
        }


        log.info("Creating ExecutorService with {} threads.", concurrency);
        ExecutorService executorService = Executors.newFixedThreadPool(concurrency);

        List<Callable<PubgTelemetryResponse>> lambdaCallables = new ArrayList<>();

        log.info("Building Callables for each telemetry file.");

        for(String s3Key : s3Keys){
            lambdaCallables.add(new Callable<PubgTelemetryResponse>() {
                @Override
                public PubgTelemetryResponse call() throws Exception {
                    log.info("Building Lambda Client for telemetry parser lambda at {}", Instant.now());

                    final PubgTelemetryParserService pubgTelemetryParserService = LambdaInvokerFactory.builder()
                            .lambdaClient(AWSLambdaClientBuilder.defaultClient())
                            .build(PubgTelemetryParserService.class);

                    log.info("Invoking telemetry parser lambda at {}", Instant.now());
                    return pubgTelemetryParserService.parseTelemetry(new PubgTelemetryRequest(s3Key));
                }
            });
        }

        List<PubgTelemetryResponse> lambdaResponses = lambdaCallables.stream()
                .map(callable -> executorService.submit(callable))
                .collect(Collectors.toList())
                .stream().map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        log.error("There was a problem processing a telemetry file.");
                        return null;
                    }
                })
                .collect(Collectors.toList());

        List<TrainingItem> trainingItems = new ArrayList<>();

        lambdaResponses.stream()
                .filter(Objects::nonNull)
                .map(PubgTelemetryResponse::getTrainingItems)
                .forEach(trainingItems::addAll);

        StringBuilder trainingDataBuilder = new StringBuilder();
        StringBuilder labelDataBuilder = new StringBuilder();

        for(TrainingItem trainingItem : trainingItems){
            trainingDataBuilder.append(trainingItem.getTrainingData()).append('\n');
            labelDataBuilder.append(trainingItem.getLabel()).append('\n');
        }

        trainingDataBuilder.setLength(trainingDataBuilder.length() - 1);
        labelDataBuilder.setLength(trainingDataBuilder.length() - 1);

        s3Client.putObject(awsBucket, "training.csv", trainingDataBuilder.toString());
        s3Client.putObject(awsBucket, "labels.csv", labelDataBuilder.toString());

        log.info("Collected {} training items from {} lambda calls.", trainingItems.size(), lambdaCallables.size());


        return "Collected " + trainingItems.size() + " training items from " + lambdaCallables.size() + " lambda calls.";
    }
}
