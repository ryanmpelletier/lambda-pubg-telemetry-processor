package com.github.ryanp102694.model;

import java.util.List;

public class PubgTelemetryResponse {

    private List<TrainingItem> trainingItems;

    public PubgTelemetryResponse() {}

    public PubgTelemetryResponse(List<TrainingItem> trainingItems) {
        this.trainingItems = trainingItems;
    }

    public List<TrainingItem> getTrainingItems() {
        return trainingItems;
    }

    public void setTrainingItems(List<TrainingItem> trainingItems) {
        this.trainingItems = trainingItems;
    }
}
