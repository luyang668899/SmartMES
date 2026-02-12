package com.qcadoo.mes.energyManagement;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;

import java.util.List;

public class EnergyMeterService {

    private DataDefinitionService dataDefinitionService;

    public Entity getEnergyMeter(final Long id) {
        DataDefinition energyMeterDD = dataDefinitionService.get("energyManagement", "energyMeter");
        return energyMeterDD.get(id);
    }

    public List<Entity> getAllEnergyMeters() {
        DataDefinition energyMeterDD = dataDefinitionService.get("energyManagement", "energyMeter");
        return energyMeterDD.find().list().getEntities();
    }

    public Entity createEnergyMeter(final Entity energyMeter) {
        DataDefinition energyMeterDD = dataDefinitionService.get("energyManagement", "energyMeter");
        return energyMeterDD.save(energyMeter);
    }

    public List<Entity> getEnergyMetersByType(final String meterType) {
        DataDefinition energyMeterDD = dataDefinitionService.get("energyManagement", "energyMeter");
        return energyMeterDD.find().add(com.qcadoo.model.api.search.SearchRestrictions.eq("meterType", meterType)).list().getEntities();
    }

    public List<Entity> getEnergyMetersByLocation(final String location) {
        DataDefinition energyMeterDD = dataDefinitionService.get("energyManagement", "energyMeter");
        return energyMeterDD.find().add(com.qcadoo.model.api.search.SearchRestrictions.eq("location", location)).list().getEntities();
    }

    public List<Entity> getEnergyMetersByStatus(final String status) {
        DataDefinition energyMeterDD = dataDefinitionService.get("energyManagement", "energyMeter");
        return energyMeterDD.find().add(com.qcadoo.model.api.search.SearchRestrictions.eq("status", status)).list().getEntities();
    }

    public void updateMeterReading(final String meterId, final double reading) {
        DataDefinition energyMeterDD = dataDefinitionService.get("energyManagement", "energyMeter");
        List<Entity> meters = energyMeterDD.find().add(com.qcadoo.model.api.search.SearchRestrictions.eq("meterId", meterId)).list().getEntities();
        if (!meters.isEmpty()) {
            Entity meter = meters.get(0);
            meter.setField("lastReading", reading);
            meter.setField("lastReadingDate", new java.util.Date());
            energyMeterDD.save(meter);
        }
    }

    public void updateMeterStatus(final String meterId, final String status) {
        DataDefinition energyMeterDD = dataDefinitionService.get("energyManagement", "energyMeter");
        List<Entity> meters = energyMeterDD.find().add(com.qcadoo.model.api.search.SearchRestrictions.eq("meterId", meterId)).list().getEntities();
        if (!meters.isEmpty()) {
            Entity meter = meters.get(0);
            meter.setField("status", status);
            energyMeterDD.save(meter);
        }
    }

    public void setDataDefinitionService(DataDefinitionService dataDefinitionService) {
        this.dataDefinitionService = dataDefinitionService;
    }

}