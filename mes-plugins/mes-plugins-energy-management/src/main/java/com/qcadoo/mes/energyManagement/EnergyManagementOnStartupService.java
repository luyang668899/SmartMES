package com.qcadoo.mes.energyManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;

@Service
public class EnergyManagementOnStartupService {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Transactional
    public void onStartup() {
        // Initialize energy management parameters
        initEnergyManagementParameters();
    }

    private void initEnergyManagementParameters() {
        DataDefinition energyManagementParameterDD = dataDefinitionService.get("energyManagement", "energyManagementParameter");
        Entity energyManagementParameter = energyManagementParameterDD.find().setMaxResults(1).uniqueResult();
        if (energyManagementParameter == null) {
            energyManagementParameter = energyManagementParameterDD.create();
            energyManagementParameter.setField("enableEnergyManagement", true);
            energyManagementParameter.setField("enableEnergyMeters", true);
            energyManagementParameter.setField("enableEnergyConsumptionTracking", true);
            energyManagementParameter.setField("enableEnergyTariffs", true);
            energyManagementParameter.setField("enableEnergyReports", true);
            energyManagementParameter.setField("enableRealTimeMonitoring", false);
            energyManagementParameter.setField("enableAnomalyDetection", false);
            energyManagementParameter.setField("enableEnergyOptimization", false);
            energyManagementParameter.setField("enableCarbonFootprintCalculation", false);
            energyManagementParameter.setField("dataCollectionInterval", 60);
            energyManagementParameter.setField("reportGenerationInterval", "Monthly");
            energyManagementParameter.setField("defaultCurrency", "USD");
            energyManagementParameter.setField("defaultElectricityUnit", "kWh");
            energyManagementParameter.setField("defaultGasUnit", "M3");
            energyManagementParameter.setField("defaultWaterUnit", "M3");
            energyManagementParameter.setField("defaultSteamUnit", "kg");
            energyManagementParameter.setField("defaultCompressedAirUnit", "Nm3");
            energyManagementParameter.setField("maxHistoricalDataMonths", 24);
            energyManagementParameter.setField("alertThreshold", 10.0);
            energyManagementParameter.setField("anomalyDetectionThreshold", 0.95);
            energyManagementParameter.setField("enableEmailNotifications", false);
            energyManagementParameter.setField("debugMode", false);
            energyManagementParameterDD.save(energyManagementParameter);
        }
    }

}