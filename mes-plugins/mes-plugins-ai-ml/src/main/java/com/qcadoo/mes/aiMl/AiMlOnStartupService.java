package com.qcadoo.mes.aiMl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;

@Service
public class AiMlOnStartupService {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Transactional
    public void onStartup() {
        // Initialize AI and ML parameters
        initAiMlParameters();
    }

    private void initAiMlParameters() {
        DataDefinition aiMlParameterDD = dataDefinitionService.get("aiMl", "aiMlParameter");
        Entity aiMlParameter = aiMlParameterDD.find().setMaxResults(1).uniqueResult();
        if (aiMlParameter == null) {
            aiMlParameter = aiMlParameterDD.create();
            aiMlParameter.setField("enablePredictionModels", true);
            aiMlParameter.setField("enableAnomalyDetection", true);
            aiMlParameter.setField("enableQualityPrediction", true);
            aiMlParameter.setField("predictionModelRefreshInterval", 60);
            aiMlParameter.setField("anomalyDetectionThreshold", 0.95);
            aiMlParameter.setField("qualityPredictionConfidence", 0.9);
            aiMlParameter.setField("maxTrainingDataSize", 100000);
            aiMlParameter.setField("modelTrainingInterval", 24);
            aiMlParameter.setField("enableRealTimeProcessing", false);
            aiMlParameter.setField("maxConcurrentPredictions", 10);
            aiMlParameterDD.save(aiMlParameter);
        }
    }

}
