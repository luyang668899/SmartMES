package com.qcadoo.mes.systemIntegration;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;

import java.util.List;

public class IntegrationConnectionService {

    private DataDefinitionService dataDefinitionService;

    public Entity getConnection(final Long id) {
        DataDefinition connectionDD = dataDefinitionService.get("systemIntegration", "integrationConnection");
        return connectionDD.get(id);
    }

    public List<Entity> getAllConnections() {
        DataDefinition connectionDD = dataDefinitionService.get("systemIntegration", "integrationConnection");
        return connectionDD.find().list().getEntities();
    }

    public Entity createConnection(final Entity connection) {
        DataDefinition connectionDD = dataDefinitionService.get("systemIntegration", "integrationConnection");
        return connectionDD.save(connection);
    }

    public void testConnection(final Long id) {
        Entity connection = getConnection(id);
        // Implement connection testing logic
    }

    public void setDataDefinitionService(DataDefinitionService dataDefinitionService) {
        this.dataDefinitionService = dataDefinitionService;
    }

}
