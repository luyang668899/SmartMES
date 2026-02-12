package com.qcadoo.mes.energyManagement;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;

import java.util.List;

public class EnergyReportService {

    private DataDefinitionService dataDefinitionService;

    public Entity getEnergyReport(final Long id) {
        DataDefinition energyReportDD = dataDefinitionService.get("energyManagement", "energyReport");
        return energyReportDD.get(id);
    }

    public List<Entity> getAllEnergyReports() {
        DataDefinition energyReportDD = dataDefinitionService.get("energyManagement", "energyReport");
        return energyReportDD.find().list().getEntities();
    }

    public Entity createEnergyReport(final Entity energyReport) {
        DataDefinition energyReportDD = dataDefinitionService.get("energyManagement", "energyReport");
        return energyReportDD.save(energyReport);
    }

    public List<Entity> getEnergyReportsByType(final String reportType) {
        DataDefinition energyReportDD = dataDefinitionService.get("energyManagement", "energyReport");
        return energyReportDD.find().add(com.qcadoo.model.api.search.SearchRestrictions.eq("reportType", reportType)).list().getEntities();
    }

    public List<Entity> getEnergyReportsByDateRange(final java.util.Date startDate, final java.util.Date endDate) {
        DataDefinition energyReportDD = dataDefinitionService.get("energyManagement", "energyReport");
        return energyReportDD.find()
                .add(com.qcadoo.model.api.search.SearchRestrictions.ge("startDate", startDate))
                .add(com.qcadoo.model.api.search.SearchRestrictions.le("endDate", endDate))
                .list().getEntities();
    }

    public List<Entity> getEnergyReportsByStatus(final String status) {
        DataDefinition energyReportDD = dataDefinitionService.get("energyManagement", "energyReport");
        return energyReportDD.find().add(com.qcadoo.model.api.search.SearchRestrictions.eq("status", status)).list().getEntities();
    }

    public Entity generateEnergyReport(final String reportType, final java.util.Date startDate, final java.util.Date endDate, final String generatedBy) {
        DataDefinition energyReportDD = dataDefinitionService.get("energyManagement", "energyReport");
        Entity energyReport = energyReportDD.create();
        energyReport.setField("name", reportType + " Report - " + startDate.toString() + " to " + endDate.toString());
        energyReport.setField("reportType", reportType);
        energyReport.setField("startDate", startDate);
        energyReport.setField("endDate", endDate);
        energyReport.setField("generationDate", new java.util.Date());
        energyReport.setField("generatedBy", generatedBy);
        energyReport.setField("status", "Generated");
        energyReport.setField("currency", "USD");
        // Calculate consumption and cost values
        EnergyConsumptionService energyConsumptionService = new EnergyConsumptionService();
        energyConsumptionService.setDataDefinitionService(dataDefinitionService);
        double electricityConsumption = energyConsumptionService.calculateTotalConsumption("Electricity", startDate, endDate);
        double electricityCost = energyConsumptionService.calculateTotalCost("Electricity", startDate, endDate);
        double gasConsumption = energyConsumptionService.calculateTotalConsumption("Gas", startDate, endDate);
        double gasCost = energyConsumptionService.calculateTotalCost("Gas", startDate, endDate);
        double waterConsumption = energyConsumptionService.calculateTotalConsumption("Water", startDate, endDate);
        double waterCost = energyConsumptionService.calculateTotalCost("Water", startDate, endDate);
        double steamConsumption = energyConsumptionService.calculateTotalConsumption("Steam", startDate, endDate);
        double steamCost = energyConsumptionService.calculateTotalCost("Steam", startDate, endDate);
        double compressedAirConsumption = energyConsumptionService.calculateTotalConsumption("Compressed Air", startDate, endDate);
        double compressedAirCost = energyConsumptionService.calculateTotalCost("Compressed Air", startDate, endDate);
        double totalConsumption = electricityConsumption + gasConsumption + waterConsumption + steamConsumption + compressedAirConsumption;
        double totalCost = electricityCost + gasCost + waterCost + steamCost + compressedAirCost;
        energyReport.setField("electricityConsumption", electricityConsumption);
        energyReport.setField("electricityCost", electricityCost);
        energyReport.setField("gasConsumption", gasConsumption);
        energyReport.setField("gasCost", gasCost);
        energyReport.setField("waterConsumption", waterConsumption);
        energyReport.setField("waterCost", waterCost);
        energyReport.setField("steamConsumption", steamConsumption);
        energyReport.setField("steamCost", steamCost);
        energyReport.setField("compressedAirConsumption", compressedAirConsumption);
        energyReport.setField("compressedAirCost", compressedAirCost);
        energyReport.setField("totalConsumption", totalConsumption);
        energyReport.setField("totalCost", totalCost);
        // Generate recommendations
        String recommendations = generateRecommendations(electricityConsumption, gasConsumption, waterConsumption, steamConsumption, compressedAirConsumption);
        energyReport.setField("recommendations", recommendations);
        return energyReportDD.save(energyReport);
    }

    private String generateRecommendations(double electricityConsumption, double gasConsumption, double waterConsumption, double steamConsumption, double compressedAirConsumption) {
        StringBuilder recommendations = new StringBuilder();
        if (electricityConsumption > 10000) {
            recommendations.append("Consider implementing energy-efficient lighting and equipment to reduce electricity consumption.\n");
        }
        if (gasConsumption > 5000) {
            recommendations.append("Check for gas leaks and optimize heating systems to reduce gas consumption.\n");
        }
        if (waterConsumption > 2000) {
            recommendations.append("Implement water-saving measures such as low-flow fixtures and leak detection systems.\n");
        }
        if (steamConsumption > 1000) {
            recommendations.append("Optimize steam generation and distribution systems to reduce steam consumption.\n");
        }
        if (compressedAirConsumption > 500) {
            recommendations.append("Check for air leaks in compressed air systems and optimize compressor operations.\n");
        }
        if (recommendations.length() == 0) {
            recommendations.append("No specific recommendations at this time. Energy consumption levels appear to be within acceptable ranges.\n");
        }
        return recommendations.toString();
    }

    public void approveEnergyReport(final Long id) {
        DataDefinition energyReportDD = dataDefinitionService.get("energyManagement", "energyReport");
        Entity energyReport = energyReportDD.get(id);
        if (energyReport != null) {
            energyReport.setField("status", "Approved");
            energyReportDD.save(energyReport);
        }
    }

    public void rejectEnergyReport(final Long id) {
        DataDefinition energyReportDD = dataDefinitionService.get("energyManagement", "energyReport");
        Entity energyReport = energyReportDD.get(id);
        if (energyReport != null) {
            energyReport.setField("status", "Rejected");
            energyReportDD.save(energyReport);
        }
    }

    public void archiveEnergyReport(final Long id) {
        DataDefinition energyReportDD = dataDefinitionService.get("energyManagement", "energyReport");
        Entity energyReport = energyReportDD.get(id);
        if (energyReport != null) {
            energyReport.setField("status", "Archived");
            energyReportDD.save(energyReport);
        }
    }

    public void setDataDefinitionService(DataDefinitionService dataDefinitionService) {
        this.dataDefinitionService = dataDefinitionService;
    }

}