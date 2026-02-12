package com.qcadoo.mes.systemIntegration;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;

import java.util.List;

public class IntegrationMappingService {

    private DataDefinitionService dataDefinitionService;

    public Entity getMapping(final Long id) {
        DataDefinition mappingDD = dataDefinitionService.get("systemIntegration", "integrationMapping");
        return mappingDD.get(id);
    }

    public List<Entity> getAllMappings() {
        DataDefinition mappingDD = dataDefinitionService.get("systemIntegration", "integrationMapping");
        return mappingDD.find().list().getEntities();
    }

    public Entity createMapping(final Entity mapping) {
        DataDefinition mappingDD = dataDefinitionService.get("systemIntegration", "integrationMapping");
        return mappingDD.save(mapping);
    }

    public void setDataDefinitionService(DataDefinitionService dataDefinitionService) {
        this.dataDefinitionService = dataDefinitionService;
    }

}
