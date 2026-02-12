package com.qcadoo.mes.energyManagement;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;

import java.util.List;

public class EnergyTariffService {

    private DataDefinitionService dataDefinitionService;

    public Entity getEnergyTariff(final Long id) {
        DataDefinition energyTariffDD = dataDefinitionService.get("energyManagement", "energyTariff");
        return energyTariffDD.get(id);
    }

    public List<Entity> getAllEnergyTariffs() {
        DataDefinition energyTariffDD = dataDefinitionService.get("energyManagement", "energyTariff");
        return energyTariffDD.find().list().getEntities();
    }

    public Entity createEnergyTariff(final Entity energyTariff) {
        DataDefinition energyTariffDD = dataDefinitionService.get("energyManagement", "energyTariff");
        return energyTariffDD.save(energyTariff);
    }

    public List<Entity> getEnergyTariffsByType(final String energyType) {
        DataDefinition energyTariffDD = dataDefinitionService.get("energyManagement", "energyTariff");
        return energyTariffDD.find().add(com.qcadoo.model.api.search.SearchRestrictions.eq("energyType", energyType)).list().getEntities();
    }

    public List<Entity> getEnergyTariffsByDateRange(final java.util.Date startDate, final java.util.Date endDate) {
        DataDefinition energyTariffDD = dataDefinitionService.get("energyManagement", "energyTariff");
        return energyTariffDD.find()
                .add(com.qcadoo.model.api.search.SearchRestrictions.ge("startDate", startDate))
                .add(com.qcadoo.model.api.search.SearchRestrictions.le("endDate", endDate))
                .list().getEntities();
    }

    public List<Entity> getEnergyTariffsByStatus(final String status) {
        DataDefinition energyTariffDD = dataDefinitionService.get("energyManagement", "energyTariff");
        return energyTariffDD.find().add(com.qcadoo.model.api.search.SearchRestrictions.eq("status", status)).list().getEntities();
    }

    public Entity getCurrentTariff(final String energyType) {
        DataDefinition energyTariffDD = dataDefinitionService.get("energyManagement", "energyTariff");
        java.util.Date today = new java.util.Date();
        List<Entity> tariffs = energyTariffDD.find()
                .add(com.qcadoo.model.api.search.SearchRestrictions.eq("energyType", energyType))
                .add(com.qcadoo.model.api.search.SearchRestrictions.le("startDate", today))
                .add(com.qcadoo.model.api.search.SearchRestrictions.or(
                        com.qcadoo.model.api.search.SearchRestrictions.isNull("endDate"),
                        com.qcadoo.model.api.search.SearchRestrictions.ge("endDate", today)
                ))
                .list().getEntities();
        return tariffs.isEmpty() ? null : tariffs.get(0);
    }

    public double getPriceForTime(final String energyType, final java.util.Date date, final java.util.Date time) {
        Entity tariff = getCurrentTariff(energyType);
        if (tariff == null) {
            return 0.0;
        }
        String tariffType = (String) tariff.getField("tariffType");
        if ("Time-of-Use".equals(tariffType)) {
            // Implement time-of-use pricing logic
            java.sql.Time sqlTime = new java.sql.Time(time.getTime());
            java.sql.Time peakStart = (java.sql.Time) tariff.getField("peakStartTime");
            java.sql.Time peakEnd = (java.sql.Time) tariff.getField("peakEndTime");
            java.sql.Time offPeakStart = (java.sql.Time) tariff.getField("offPeakStartTime");
            java.sql.Time offPeakEnd = (java.sql.Time) tariff.getField("offPeakEndTime");
            java.sql.Time shoulderStart = (java.sql.Time) tariff.getField("shoulderStartTime");
            java.sql.Time shoulderEnd = (java.sql.Time) tariff.getField("shoulderEndTime");
            if (sqlTime.after(peakStart) && sqlTime.before(peakEnd)) {
                return (Double) tariff.getField("peakPrice");
            } else if (sqlTime.after(offPeakStart) && sqlTime.before(offPeakEnd)) {
                return (Double) tariff.getField("offPeakPrice");
            } else if (shoulderStart != null && shoulderEnd != null && sqlTime.after(shoulderStart) && sqlTime.before(shoulderEnd)) {
                return (Double) tariff.getField("shoulderPrice");
            } else {
                return (Double) tariff.getField("unitPrice");
            }
        } else {
            return (Double) tariff.getField("unitPrice");
        }
    }

    public void activateTariff(final Long id) {
        DataDefinition energyTariffDD = dataDefinitionService.get("energyManagement", "energyTariff");
        Entity tariff = energyTariffDD.get(id);
        if (tariff != null) {
            tariff.setField("status", "Active");
            energyTariffDD.save(tariff);
        }
    }

    public void deactivateTariff(final Long id) {
        DataDefinition energyTariffDD = dataDefinitionService.get("energyManagement", "energyTariff");
        Entity tariff = energyTariffDD.get(id);
        if (tariff != null) {
            tariff.setField("status", "Inactive");
            energyTariffDD.save(tariff);
        }
    }

    public void setDataDefinitionService(DataDefinitionService dataDefinitionService) {
        this.dataDefinitionService = dataDefinitionService;
    }

}