package com.github.ryanp102694.lambda;

import com.amazonaws.services.lambda.invoke.LambdaFunction;
import com.github.ryanp102694.model.PubgTelemetryRequest;
import com.github.ryanp102694.model.PubgTelemetryResponse;

public interface PubgTelemetryParserService {

    @LambdaFunction(functionName="arn:aws:lambda:us-east-1:025878132360:function:lambda-pubg-telemetry-parser")
    PubgTelemetryResponse parseTelemetry(PubgTelemetryRequest pubgTelemetryRequest);
}
