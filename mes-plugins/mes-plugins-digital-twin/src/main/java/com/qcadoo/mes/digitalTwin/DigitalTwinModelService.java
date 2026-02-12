package com.qcadoo.mes.digitalTwin;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;

import java.util.List;

public class DigitalTwinModelService {

    private DataDefinitionService dataDefinitionService;

    public Entity getDigitalTwinModel(final Long id) {
        DataDefinition digitalTwinModelDD = dataDefinitionService.get("digitalTwin", "digitalTwinModel");
        return digitalTwinModelDD.get(id);
    }

    public List<Entity> getAllDigitalTwinModels() {
        DataDefinition digitalTwinModelDD = dataDefinitionService.get("digitalTwin", "digitalTwinModel");
        return digitalTwinModelDD.find().list().getEntities();
    }

    public Entity createDigitalTwinModel(final Entity digitalTwinModel) {
        DataDefinition digitalTwinModelDD = dataDefinitionService.get("digitalTwin", "digitalTwinModel");
        return digitalTwinModelDD.save(digitalTwinModel);
    }

    public void updateDigitalTwinModel(final Long id, final Entity updatedModel) {
        DataDefinition digitalTwinModelDD = dataDefinitionService.get("digitalTwin", "digitalTwinModel");
        Entity existingModel = digitalTwinModelDD.get(id);
        if (existingModel != null) {
            existingModel.data().putAll(updatedModel.data());
            digitalTwinModelDD.save(existingModel);
        }
    }

    public void setDataDefinitionService(DataDefinitionService dataDefinitionService) {
        this.dataDefinitionService = dataDefinitionService;
    }

}
