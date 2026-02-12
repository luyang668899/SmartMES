package com.qcadoo.mes.predictiveMaintenance;

import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.SearchCriteriaBuilder;
import com.qcadoo.model.api.search.SearchRestrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MaintenanceScheduleService {

    @Autowired
    private com.qcadoo.model.api.DataDefinitionService dataDefinitionService;

    private static final String MODEL_NAME = "maintenanceSchedule";
    private static final String PLUGIN_IDENTIFIER = "predictiveMaintenance";

    public Entity createMaintenanceSchedule() {
        return getDataDefinition().create();
    }

    public Entity getMaintenanceSchedule(final Long id) {
        return getDataDefinition().get(id);
    }

    public List<Entity> getMaintenanceSchedules() {
        return getDataDefinition().find().list().getEntities();
    }

    public List<Entity> getMaintenanceSchedulesByStatus(final String status) {
        SearchCriteriaBuilder scb = getDataDefinition().find();
        scb.add(SearchRestrictions.eq("status", status));
        return scb.list().getEntities();
    }

    public List<Entity> getMaintenanceSchedulesByType(final String scheduleType) {
        SearchCriteriaBuilder scb = getDataDefinition().find();
        scb.add(SearchRestrictions.eq("scheduleType", scheduleType));
        return scb.list().getEntities();
    }

    public List<Entity> getMaintenanceSchedulesByDateRange(final Date startDate, final Date endDate) {
        SearchCriteriaBuilder scb = getDataDefinition().find();
        scb.add(SearchRestrictions.between("scheduledDate", startDate, endDate));
        return scb.list().getEntities();
    }

    public Entity saveMaintenanceSchedule(final Entity maintenanceSchedule) {
        return getDataDefinition().save(maintenanceSchedule);
    }

    public void deleteMaintenanceSchedule(final Long id) {
        getDataDefinition().delete(id);
    }

    public Entity createMaintenanceTask() {
        return getDataDefinition("maintenanceTask").create();
    }

    public Entity getMaintenanceTask(final Long id) {
        return getDataDefinition("maintenanceTask").get(id);
    }

    public List<Entity> getMaintenanceTasksByMaintenanceSchedule(final Long maintenanceScheduleId) {
        SearchCriteriaBuilder scb = getDataDefinition("maintenanceTask").find();
        scb.add(SearchRestrictions.eq("maintenanceSchedule.id", maintenanceScheduleId));
        return scb.list().getEntities();
    }

    public List<Entity> getMaintenanceTasksByStatus(final String status) {
        SearchCriteriaBuilder scb = getDataDefinition("maintenanceTask").find();
        scb.add(SearchRestrictions.eq("status", status));
        return scb.list().getEntities();
    }

    public Entity saveMaintenanceTask(final Entity maintenanceTask) {
        return getDataDefinition("maintenanceTask").save(maintenanceTask);
    }

    public void generatePreventiveMaintenanceSchedule() {
        // Implementation of preventive maintenance schedule generation
    }

    public void generatePredictiveMaintenanceSchedule() {
        // Implementation of predictive maintenance schedule generation
    }

    private com.qcadoo.model.api.DataDefinition getDataDefinition() {
        return dataDefinitionService.get(PLUGIN_IDENTIFIER, MODEL_NAME);
    }

    private com.qcadoo.model.api.DataDefinition getDataDefinition(final String modelName) {
        return dataDefinitionService.get(PLUGIN_IDENTIFIER, modelName);
    }

}
