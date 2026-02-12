package com.qcadoo.mes.aiMl;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;

import java.util.List;

public class AnomalyDetectionService {

    private DataDefinitionService dataDefinitionService;

    public Entity getAnomalyDetection(final Long id) {
        DataDefinition anomalyDetectionDD = dataDefinitionService.get("aiMl", "anomalyDetection");
        return anomalyDetectionDD.get(id);
    }

    public List<Entity> getAllAnomalyDetections() {
        DataDefinition anomalyDetectionDD = dataDefinitionService.get("aiMl", "anomalyDetection");
        return anomalyDetectionDD.find().list().getEntities();
    }

    public Entity createAnomalyDetection(final Entity anomalyDetection) {
        DataDefinition anomalyDetectionDD = dataDefinitionService.get("aiMl", "anomalyDetection");
        return anomalyDetectionDD.save(anomalyDetection);
    }

    public void runAnomalyDetection(final Long id) {
        Entity detection = getAnomalyDetection(id);
        // Implement anomaly detection logic
    }

    public void analyzeAnomaly(final Long id, final Entity anomalyData) {
        Entity detection = getAnomalyDetection(id);
        // Implement anomaly analysis logic
    }

    public void setDataDefinitionService(DataDefinitionService dataDefinitionService) {
        this.dataDefinitionService = dataDefinitionService;
    }

}
