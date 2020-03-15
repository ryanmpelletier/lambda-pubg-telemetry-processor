package com.github.ryanp102694.pubgtelemetryparser.service;

import com.github.ryanp102694.model.TrainingItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

@Component
public class TrainingItemBuilder {

    public List<TrainingItem> buildTrainingItems(List<SortedMap<String, String>> dataPointsList){
        List<TrainingItem> trainingItems = new ArrayList<>();

        for(int i = 0; i < dataPointsList.size(); i++){
            StringBuilder trainingLineBuilder = new StringBuilder();
            StringBuilder labelBuilder = new StringBuilder();

            SortedMap<String, String> dataPoints = dataPointsList.get(i);


            for(String key : dataPoints.keySet()){
                if(!"alive".equals(key)){
                    trainingLineBuilder.append(dataPoints.get(key)).append(",");
                }else{
                    String otherString = "";
                    if("1".equals(dataPoints.get(key))){
                        otherString = "0";
                    }else{
                        otherString = "1";
                    }
                    labelBuilder.append(dataPoints.get(key)).append(",").append(otherString);
                }
            }
            trainingLineBuilder.setLength(trainingLineBuilder.length() - 1);

            trainingItems.add(new TrainingItem(trainingLineBuilder.toString(), labelBuilder.toString()));
        }
        return trainingItems;
    }

}
