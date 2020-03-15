package com.github.ryanp102694.model;

public class PubgTelemetryResponse {

    private String value;

    public PubgTelemetryResponse() {}

    public PubgTelemetryResponse(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
