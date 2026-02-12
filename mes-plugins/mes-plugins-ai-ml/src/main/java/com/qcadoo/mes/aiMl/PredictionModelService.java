package com.qcadoo.mes.aiMl;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;

import java.util.List;

public class PredictionModelService {

    private DataDefinitionService dataDefinitionService;

    public Entity getPredictionModel(final Long id) {
        DataDefinition predictionModelDD = dataDefinitionService.get("aiMl", "predictionModel");
        return predictionModelDD.get(id);
    }

    public List<Entity> getAllPredictionModels() {
        DataDefinition predictionModelDD = dataDefinitionService.get("aiMl", "predictionModel");
        return predictionModelDD.find().list().getEntities();
    }

    public Entity createPredictionModel(final Entity predictionModel) {
        DataDefinition predictionModelDD = dataDefinitionService.get("aiMl", "predictionModel");
        return predictionModelDD.save(predictionModel);
    }

    public void trainModel(final Long id) {
        Entity model = getPredictionModel(id);
        // Implement model training logic
    }

    public void predict(final Long id, final Entity inputData) {
        Entity model = getPredictionModel(id);
        // Implement prediction logic
    }

    public void setDataDefinitionService(DataDefinitionService dataDefinitionService) {
        this.dataDefinitionService = dataDefinitionService;
    }

}
