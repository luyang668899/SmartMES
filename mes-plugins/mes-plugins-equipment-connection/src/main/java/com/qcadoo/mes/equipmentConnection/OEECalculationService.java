package com.qcadoo.mes.equipmentConnection;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OEECalculationService {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    public void calculateAndSaveOEEData(Entity equipment, int plannedProductionTime, int operatingTime,
                                       int goodCount, int totalCount, int downtimeMinutes, String downtimeReason) {

        // Calculate Availability
        double availability = calculateAvailability(plannedProductionTime, operatingTime, downtimeMinutes);

        // Calculate Performance
        double performance = calculatePerformance(operatingTime, totalCount, 1.0); // Assuming ideal cycle time of 1.0

        // Calculate Quality
        double quality = calculateQuality(goodCount, totalCount);

        // Calculate OEE
        double oee = availability * performance * quality;

        // Save OEE data
        saveOEEData(equipment, availability, performance, quality, oee,
                plannedProductionTime, operatingTime, goodCount, totalCount,
                downtimeMinutes, downtimeReason);
    }

    private double calculateAvailability(int plannedProductionTime, int operatingTime, int downtimeMinutes) {
        if (plannedProductionTime == 0) {
            return 0.0;
        }
        int actualProductionTime = operatingTime;
        return (double) actualProductionTime / plannedProductionTime * 100.0;
    }

    private double calculatePerformance(int operatingTime, int totalCount, double idealCycleTime) {
        if (operatingTime == 0 || totalCount == 0) {
            return 0.0;
        }
        double actualCycleTime = (double) operatingTime / totalCount;
        return (idealCycleTime / actualCycleTime) * 100.0;
    }

    private double calculateQuality(int goodCount, int totalCount) {
        if (totalCount == 0) {
            return 0.0;
        }
        return (double) goodCount / totalCount * 100.0;
    }

    private void saveOEEData(Entity equipment, double availability, double performance, double quality, double oee,
                           int plannedProductionTime, int operatingTime, int goodCount, int totalCount,
                           int downtimeMinutes, String downtimeReason) {

        DataDefinition oeeDataDD = dataDefinitionService.get("equipmentConnection", "oeeData");
        Entity oeeData = oeeDataDD.create();

        oeeData.setField("equipment", equipment);
        oeeData.setField("timestamp", new Date());
        oeeData.setField("availability", availability);
        oeeData.setField("performance", performance);
        oeeData.setField("quality", quality);
        oeeData.setField("oee", oee);
        oeeData.setField("plannedProductionTime", plannedProductionTime);
        oeeData.setField("operatingTime", operatingTime);
        oeeData.setField("goodCount", goodCount);
        oeeData.setField("totalCount", totalCount);
        oeeData.setField("downtimeMinutes", downtimeMinutes);
        oeeData.setField("downtimeReason", downtimeReason);

        // Calculate ideal and actual cycle times
        if (totalCount > 0) {
            double actualCycleTime = (double) operatingTime / totalCount;
            oeeData.setField("actualCycleTime", actualCycleTime);
            // Assuming ideal cycle time is 80% of actual for demonstration
            oeeData.setField("idealCycleTime", actualCycleTime * 0.8);
        }

        oeeData.save();
    }

    public double getAverageOEEForEquipment(Entity equipment, int days) {
        // Implement logic to calculate average OEE for equipment over specified days
        // This would involve querying oeeData records for the equipment and averaging the oee values
        return 0.0; // Placeholder implementation
    }

    public double getAverageOEEForWorkstation(Entity workstation, int days) {
        // Implement logic to calculate average OEE for all equipment in a workstation
        return 0.0; // Placeholder implementation
    }

    public double getAverageOEEForFactory(Entity factory, int days) {
        // Implement logic to calculate average OEE for all equipment in a factory
        return 0.0; // Placeholder implementation
    }

}
