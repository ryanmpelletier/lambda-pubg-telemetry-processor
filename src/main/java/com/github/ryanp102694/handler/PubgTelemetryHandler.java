package com.github.ryanp102694.handler;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.github.ryanp102694.model.PubgTelemetryResponse;
import com.github.ryanp102694.model.PubgTelemetryRequest;
import com.github.ryanp102694.pubgtelemetryparser.TelemetryProcessor;
import com.github.ryanp102694.pubgtelemetryparser.data.GameData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Function;

//set a FUNCTION_NAME environment variable to pubgTelemetryHandler to trigger this
@Component("pubgTelemetryHandler")
public class PubgTelemetryHandler implements Function<PubgTelemetryRequest, PubgTelemetryResponse> {

    private final static Logger log = LoggerFactory.getLogger(PubgTelemetryHandler.class);

    @Value("${aws.bucket}")
    String awsBucket;


    @Override
    public PubgTelemetryResponse apply(PubgTelemetryRequest pubgTelemetryRequest) {

        log.info("Creating Amazon S3 client for bucket {} in region {}",
                awsBucket, Regions.US_EAST_1);

        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1)
                .build();

        log.info("Getting S3 Object at {}", pubgTelemetryRequest.getS3ObjectUrl());

        S3Object s3Object = s3Client.getObject(awsBucket, pubgTelemetryRequest.getS3ObjectUrl());
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        TelemetryProcessor telemetryProcessor = new TelemetryProcessor();
        try{
            GameData gameData = telemetryProcessor.processTelemetry(inputStream);
        }catch(IOException ioException){
            log.error("There was a problem processing telemetry. {}", ioException.getMessage());
        }

        return new PubgTelemetryResponse("Hello World! " + pubgTelemetryRequest.getS3ObjectUrl());
    }
}
