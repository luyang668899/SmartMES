package com.qcadoo.mes.energyManagement;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;

import java.util.List;

public class EnergyConsumptionService {

    private DataDefinitionService dataDefinitionService;

    public Entity getEnergyConsumption(final Long id) {
        DataDefinition energyConsumptionDD = dataDefinitionService.get("energyManagement", "energyConsumption");
        return energyConsumptionDD.get(id);
    }

    public List<Entity> getAllEnergyConsumption() {
        DataDefinition energyConsumptionDD = dataDefinitionService.get("energyManagement", "energyConsumption");
        return energyConsumptionDD.find().list().getEntities();
    }

    public Entity createEnergyConsumption(final Entity energyConsumption) {
        DataDefinition energyConsumptionDD = dataDefinitionService.get("energyManagement", "energyConsumption");
        return energyConsumptionDD.save(energyConsumption);
    }

    public List<Entity> getEnergyConsumptionByMeter(final String energyMeter) {
        DataDefinition energyConsumptionDD = dataDefinitionService.get("energyManagement", "energyConsumption");
        return energyConsumptionDD.find().add(com.qcadoo.model.api.search.SearchRestrictions.eq("energyMeter", energyMeter)).list().getEntities();
    }

    public List<Entity> getEnergyConsumptionByType(final String meterType) {
        DataDefinition energyConsumptionDD = dataDefinitionService.get("energyManagement", "energyConsumption");
        return energyConsumptionDD.find().add(com.qcadoo.model.api.search.SearchRestrictions.eq("meterType", meterType)).list().getEntities();
    }

    public List<Entity> getEnergyConsumptionByDateRange(final java.util.Date startDate, final java.util.Date endDate) {
        DataDefinition energyConsumptionDD = dataDefinitionService.get("energyManagement", "energyConsumption");
        return energyConsumptionDD.find()
                .add(com.qcadoo.model.api.search.SearchRestrictions.ge("startDate", startDate))
                .add(com.qcadoo.model.api.search.SearchRestrictions.le("endDate", endDate))
                .list().getEntities();
    }

    public List<Entity> getEnergyConsumptionByLocation(final String location) {
        DataDefinition energyConsumptionDD = dataDefinitionService.get("energyManagement", "energyConsumption");
        return energyConsumptionDD.find().add(com.qcadoo.model.api.search.SearchRestrictions.eq("location", location)).list().getEntities();
    }

    public List<Entity> getEnergyConsumptionByCostCenter(final String costCenter) {
        DataDefinition energyConsumptionDD = dataDefinitionService.get("energyManagement", "energyConsumption");
        return energyConsumptionDD.find().add(com.qcadoo.model.api.search.SearchRestrictions.eq("costCenter", costCenter)).list().getEntities();
    }

    public List<Entity> getEnergyConsumptionByProductionOrder(final String productionOrder) {
        DataDefinition energyConsumptionDD = dataDefinitionService.get("energyManagement", "energyConsumption");
        return energyConsumptionDD.find().add(com.qcadoo.model.api.search.SearchRestrictions.eq("productionOrder", productionOrder)).list().getEntities();
    }

    public double calculateTotalConsumption(final String meterType, final java.util.Date startDate, final java.util.Date endDate) {
        List<Entity> consumptions = getEnergyConsumptionByType(meterType);
        double totalConsumption = 0.0;
        for (Entity consumption : consumptions) {
            java.util.Date consumptionStartDate = (java.util.Date) consumption.getField("startDate");
            java.util.Date consumptionEndDate = (java.util.Date) consumption.getField("endDate");
            if (consumptionStartDate.after(startDate) && consumptionEndDate.before(endDate)) {
                totalConsumption += (Double) consumption.getField("consumption");
            }
        }
        return totalConsumption;
    }

    public double calculateTotalCost(final String meterType, final java.util.Date startDate, final java.util.Date endDate) {
        List<Entity> consumptions = getEnergyConsumptionByType(meterType);
        double totalCost = 0.0;
        for (Entity consumption : consumptions) {
            java.util.Date consumptionStartDate = (java.util.Date) consumption.getField("startDate");
            java.util.Date consumptionEndDate = (java.util.Date) consumption.getField("endDate");
            if (consumptionStartDate.after(startDate) && consumptionEndDate.before(endDate)) {
                totalCost += (Double) consumption.getField("cost");
            }
        }
        return totalCost;
    }

    public void validateEnergyConsumption(final Long id) {
        DataDefinition energyConsumptionDD = dataDefinitionService.get("energyManagement", "energyConsumption");
        Entity energyConsumption = energyConsumptionDD.get(id);
        if (energyConsumption != null) {
            energyConsumption.setField("status", "Validated");
            energyConsumptionDD.save(energyConsumption);
        }
    }

    public void rejectEnergyConsumption(final Long id) {
        DataDefinition energyConsumptionDD = dataDefinitionService.get("energyManagement", "energyConsumption");
        Entity energyConsumption = energyConsumptionDD.get(id);
        if (energyConsumption != null) {
            energyConsumption.setField("status", "Rejected");
            energyConsumptionDD.save(energyConsumption);
        }
    }

    public void setDataDefinitionService(DataDefinitionService dataDefinitionService) {
        this.dataDefinitionService = dataDefinitionService;
    }

}