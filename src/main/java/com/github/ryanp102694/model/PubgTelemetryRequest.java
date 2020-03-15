package com.github.ryanp102694.model;

public class PubgTelemetryRequest {
    private String s3TelemetryJsonKey;

    public PubgTelemetryRequest(){}

    public PubgTelemetryRequest(String s3TelemetryJsonKey) {
        this.s3TelemetryJsonKey = s3TelemetryJsonKey;
    }

    public String getS3TelemetryJsonKey() {
        return s3TelemetryJsonKey;
    }

    public void setS3TelemetryJsonKey(String s3TelemetryJsonKey) {
        this.s3TelemetryJsonKey = s3TelemetryJsonKey;
    }
}
