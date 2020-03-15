package com.github.ryanp102694.model;

import java.util.List;

public class PubgTelemetryResponse {

    private List<String> trainingLines;
    private List<String> labelLines;

    public PubgTelemetryResponse() {}

    public PubgTelemetryResponse(List<String> trainingLines, List<String> labelLines) {
        this.trainingLines = trainingLines;
        this.labelLines = labelLines;
    }

    public List<String> getTrainingLines() {
        return trainingLines;
    }

    public void setTrainingLines(List<String> trainingLines) {
        this.trainingLines = trainingLines;
    }

    public List<String> getLabelLines() {
        return labelLines;
    }

    public void setLabelLines(List<String> labelLines) {
        this.labelLines = labelLines;
    }
}
