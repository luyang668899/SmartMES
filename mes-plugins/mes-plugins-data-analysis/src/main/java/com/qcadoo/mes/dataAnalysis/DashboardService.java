package com.qcadoo.mes.dataAnalysis;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;

import java.util.List;

public class DashboardService {

    private DataDefinitionService dataDefinitionService;

    public Entity getDashboard(final Long id) {
        DataDefinition dashboardDD = dataDefinitionService.get("dataAnalysis", "dashboard");
        return dashboardDD.get(id);
    }

    public List<Entity> getAllDashboards() {
        DataDefinition dashboardDD = dataDefinitionService.get("dataAnalysis", "dashboard");
        return dashboardDD.find().list().getEntities();
    }

    public Entity createDashboard(final Entity dashboard) {
        DataDefinition dashboardDD = dataDefinitionService.get("dataAnalysis", "dashboard");
        return dashboardDD.save(dashboard);
    }

    public void setDataDefinitionService(DataDefinitionService dataDefinitionService) {
        this.dataDefinitionService = dataDefinitionService;
    }

}
