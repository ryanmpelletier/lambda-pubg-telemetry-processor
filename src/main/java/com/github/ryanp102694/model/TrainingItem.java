package com.github.ryanp102694.model;

public class TrainingItem {

    private String trainingData;
    private String label;

    public TrainingItem(String trainingData, String label) {
        this.trainingData = trainingData;
        this.label = label;
    }

    public String getTrainingData() {
        return trainingData;
    }

    public void setTrainingData(String trainingData) {
        this.trainingData = trainingData;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
