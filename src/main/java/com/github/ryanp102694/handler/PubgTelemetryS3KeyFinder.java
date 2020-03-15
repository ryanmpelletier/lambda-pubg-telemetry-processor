package com.github.ryanp102694.handler;

import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;
import com.github.ryanp102694.lambda.PubgTelemetryParserService;
import com.github.ryanp102694.model.BatchPubgTelemetryRequest;
import com.github.ryanp102694.model.PubgTelemetryRequest;
import com.github.ryanp102694.model.PubgTelemetryResponse;
import com.github.ryanp102694.model.PubgTelemetryS3KeyFinderRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.Function;

//set a FUNCTION_NAME environment variable to pubgTelemetryS3KeyFinder to trigger this
@Component("pubgTelemetryS3KeyFinder")
public class PubgTelemetryS3KeyFinder implements Function<PubgTelemetryS3KeyFinderRequest, BatchPubgTelemetryRequest> {


    @Override
    public BatchPubgTelemetryRequest apply(PubgTelemetryS3KeyFinderRequest pubgTelemetryS3KeyFinderRequest) {


        final PubgTelemetryParserService pubgTelemetryParserService = LambdaInvokerFactory.builder()
                .lambdaClient(AWSLambdaClientBuilder.defaultClient())
                .build(PubgTelemetryParserService.class);

        PubgTelemetryResponse pubgTelemetryResponse =
                pubgTelemetryParserService.parseTelemetry(new PubgTelemetryRequest("Erangel_Main/squad-fpp/0132e4f0-2baf-459f-86e7-26732315930e"));


        return new BatchPubgTelemetryRequest(Arrays.asList());
    }
}
