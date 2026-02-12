package com.qcadoo.mes.advancedPlanning;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;

import java.util.List;

public class ProductionPlanService {

    private DataDefinitionService dataDefinitionService;

    public Entity getProductionPlan(final Long id) {
        DataDefinition productionPlanDD = dataDefinitionService.get("advancedPlanning", "productionPlan");
        return productionPlanDD.get(id);
    }

    public List<Entity> getAllProductionPlans() {
        DataDefinition productionPlanDD = dataDefinitionService.get("advancedPlanning", "productionPlan");
        return productionPlanDD.find().list().getEntities();
    }

    public Entity createProductionPlan(final Entity productionPlan) {
        DataDefinition productionPlanDD = dataDefinitionService.get("advancedPlanning", "productionPlan");
        return productionPlanDD.save(productionPlan);
    }

    public void optimizeProductionPlan(final Long id) {
        Entity productionPlan = getProductionPlan(id);
        // Implement optimization logic using genetic algorithms
    }

    public void setDataDefinitionService(DataDefinitionService dataDefinitionService) {
        this.dataDefinitionService = dataDefinitionService;
    }

}
