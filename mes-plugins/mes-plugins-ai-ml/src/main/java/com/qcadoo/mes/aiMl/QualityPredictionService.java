package com.qcadoo.mes.aiMl;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;

import java.util.List;

public class QualityPredictionService {

    private DataDefinitionService dataDefinitionService;

    public Entity getQualityPrediction(final Long id) {
        DataDefinition qualityPredictionDD = dataDefinitionService.get("aiMl", "qualityPrediction");
        return qualityPredictionDD.get(id);
    }

    public List<Entity> getAllQualityPredictions() {
        DataDefinition qualityPredictionDD = dataDefinitionService.get("aiMl", "qualityPrediction");
        return qualityPredictionDD.find().list().getEntities();
    }

    public Entity createQualityPrediction(final Entity qualityPrediction) {
        DataDefinition qualityPredictionDD = dataDefinitionService.get("aiMl", "qualityPrediction");
        return qualityPredictionDD.save(qualityPrediction);
    }

    public void predictQuality(final Long productId, final Entity productionData) {
        // Implement quality prediction logic
    }

    public void validatePrediction(final Long id, final String actualQuality) {
        Entity prediction = getQualityPrediction(id);
        // Implement prediction validation logic
    }

    public void setDataDefinitionService(DataDefinitionService dataDefinitionService) {
        this.dataDefinitionService = dataDefinitionService;
    }

}
