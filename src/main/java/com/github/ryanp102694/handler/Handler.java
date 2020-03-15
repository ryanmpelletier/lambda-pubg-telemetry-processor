package com.github.ryanp102694.handler;

import com.github.ryanp102694.model.PubgTelemetryResponse;
import com.github.ryanp102694.model.PubgTelemetryRequest;
import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;

public class Handler extends SpringBootRequestHandler<PubgTelemetryRequest, PubgTelemetryResponse> {

}
