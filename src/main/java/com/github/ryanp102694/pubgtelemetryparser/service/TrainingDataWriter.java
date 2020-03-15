package com.github.ryanp102694.pubgtelemetryparser.service;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

@Component
public class TrainingDataWriter {

    public List<String> getTrainingDataLines(List<SortedMap<String, String>> dataPointsList){

        List<String> trainingDataLines = new ArrayList<>();

        for(int i = 0; i < dataPointsList.size(); i++){
            StringBuilder trainingLineBuilder = new StringBuilder();
            SortedMap<String, String> dataPoints = dataPointsList.get(i);

            for(String key : dataPoints.keySet()){
                if(!"alive".equals(key)){
                    trainingLineBuilder.append(dataPoints.get(key)).append(",");
                }
            }
            trainingLineBuilder.setLength(trainingLineBuilder.length() - 1);
            trainingDataLines.add(trainingLineBuilder.toString());
        }
        return trainingDataLines;
    }

    public List<String> getLabelLines(List<SortedMap<String, String>> dataPointsList){
        List<String> trainingDataLines = new ArrayList<>();

        for(int i = 0; i < dataPointsList.size(); i++){
            StringBuilder labelBuilder = new StringBuilder();

            SortedMap<String, String> dataPoints = dataPointsList.get(i);

            for(String key : dataPoints.keySet()){
                if("alive".equals(key)){
                    String otherString = "";
                    if("1".equals(dataPoints.get(key))){
                        otherString = "0";
                    }else{
                        otherString = "1";
                    }
                    labelBuilder.append(dataPoints.get(key)).append(",").append(otherString);
                }
            }
            trainingDataLines.add(labelBuilder.toString());
        }
        return trainingDataLines;
    }

}
