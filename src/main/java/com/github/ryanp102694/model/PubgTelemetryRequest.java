package com.github.ryanp102694.model;

public class PubgTelemetryRequest {
    private String s3ObjectUrl;

    public PubgTelemetryRequest(){}

    public PubgTelemetryRequest(String s3ObjectUrl) {
        this.s3ObjectUrl = s3ObjectUrl;
    }

    public String getS3ObjectUrl() {
        return s3ObjectUrl;
    }

    public void setS3ObjectUrl(String s3ObjectUrl) {
        this.s3ObjectUrl = s3ObjectUrl;
    }
}
