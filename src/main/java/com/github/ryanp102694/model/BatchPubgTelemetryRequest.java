package com.github.ryanp102694.model;

import java.util.List;

public class BatchPubgTelemetryRequest {

    private List<PubgTelemetryRequest> pubgTelemetryRequestList;


    public BatchPubgTelemetryRequest(List<PubgTelemetryRequest> pubgTelemetryRequestList) {
        this.pubgTelemetryRequestList = pubgTelemetryRequestList;
    }

    public List<PubgTelemetryRequest> getPubgTelemetryRequestList() {
        return pubgTelemetryRequestList;
    }

    public void setPubgTelemetryRequestList(List<PubgTelemetryRequest> pubgTelemetryRequestList) {
        this.pubgTelemetryRequestList = pubgTelemetryRequestList;
    }
}
