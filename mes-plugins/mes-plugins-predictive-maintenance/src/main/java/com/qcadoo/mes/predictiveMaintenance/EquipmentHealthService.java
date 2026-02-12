package com.qcadoo.mes.predictiveMaintenance;

import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.SearchCriteriaBuilder;
import com.qcadoo.model.api.search.SearchRestrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EquipmentHealthService {

    @Autowired
    private com.qcadoo.model.api.DataDefinitionService dataDefinitionService;

    private static final String MODEL_NAME = "equipmentHealth";
    private static final String PLUGIN_IDENTIFIER = "predictiveMaintenance";

    public Entity createEquipmentHealth() {
        return getDataDefinition().create();
    }

    public Entity getEquipmentHealth(final Long id) {
        return getDataDefinition().get(id);
    }

    public List<Entity> getEquipmentHealths() {
        return getDataDefinition().find().list().getEntities();
    }

    public List<Entity> getEquipmentHealthsByStatus(final String status) {
        SearchCriteriaBuilder scb = getDataDefinition().find();
        scb.add(SearchRestrictions.eq("status", status));
        return scb.list().getEntities();
    }

    public List<Entity> getEquipmentHealthsByPriority(final Integer priority) {
        SearchCriteriaBuilder scb = getDataDefinition().find();
        scb.add(SearchRestrictions.eq("maintenancePriority", priority));
        return scb.list().getEntities();
    }

    public Entity saveEquipmentHealth(final Entity equipmentHealth) {
        return getDataDefinition().save(equipmentHealth);
    }

    public void deleteEquipmentHealth(final Long id) {
        getDataDefinition().delete(id);
    }

    public Entity createEquipmentMeasurement() {
        return getDataDefinition("equipmentMeasurement").create();
    }

    public Entity getEquipmentMeasurement(final Long id) {
        return getDataDefinition("equipmentMeasurement").get(id);
    }

    public List<Entity> getEquipmentMeasurementsByEquipmentHealth(final Long equipmentHealthId) {
        SearchCriteriaBuilder scb = getDataDefinition("equipmentMeasurement").find();
        scb.add(SearchRestrictions.eq("equipmentHealth.id", equipmentHealthId));
        return scb.list().getEntities();
    }

    public Entity saveEquipmentMeasurement(final Entity equipmentMeasurement) {
        return getDataDefinition("equipmentMeasurement").save(equipmentMeasurement);
    }

    public void calculateHealthScore(final Entity equipmentHealth) {
        // Implementation of health score calculation
    }

    public void predictFailure(final Entity equipmentHealth) {
        // Implementation of failure prediction
    }

    private com.qcadoo.model.api.DataDefinition getDataDefinition() {
        return dataDefinitionService.get(PLUGIN_IDENTIFIER, MODEL_NAME);
    }

    private com.qcadoo.model.api.DataDefinition getDataDefinition(final String modelName) {
        return dataDefinitionService.get(PLUGIN_IDENTIFIER, modelName);
    }

}
