package com.github.ryanp102694.model;

import java.util.List;

public class PubgTelemetryS3KeyFinderRequest {
    private List<String> folderNames;

    public List<String> getFolderNames() {
        return folderNames;
    }

    public void setFolderNames(List<String> folderNames) {
        this.folderNames = folderNames;
    }
}
