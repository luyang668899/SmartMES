package com.qcadoo.mes.equipmentConnection;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EquipmentConnectionService {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Autowired
    private MQTTConnectionService mqttConnectionService;

    @Autowired
    private OEECalculationService oeeCalculationService;

    public Entity createEquipmentConnection(Entity equipment, String status, String message) {
        DataDefinition connectionDD = dataDefinitionService.get("equipmentConnection", "equipmentConnection");
        Entity connection = connectionDD.create();
        connection.setField("equipment", equipment);
        connection.setField("connectionTime", new Date());
        connection.setField("status", status);
        connection.setField("message", message);
        return connection.save();
    }

    public void updateEquipmentConnection(Entity connection, String status, String message) {
        connection.setField("status", status);
        connection.setField("message", message);
        connection.save();
    }

    public void disconnectEquipmentConnection(Entity connection) {
        connection.setField("disconnectionTime", new Date());
        connection.setField("status", "DISCONNECTED");
        connection.save();
    }

    public void connectEquipment(Entity equipment) {
        if (!equipment.getBooleanField("enabled")) {
            return;
        }

        String protocol = equipment.getStringField("protocol");
        if (protocol == null) {
            return;
        }

        switch (protocol) {
            case "MQTT":
                mqttConnectionService.connect(equipment);
                break;
            case "OPCUA":
                // Implement OPC UA connection
                break;
            case "REST":
                // Implement REST API connection
                break;
            case "Modbus":
                // Implement Modbus connection
                break;
        }
    }

    public void disconnectEquipment(Entity equipment) {
        String protocol = equipment.getStringField("protocol");
        if (protocol == null) {
            return;
        }

        switch (protocol) {
            case "MQTT":
                mqttConnectionService.disconnect(equipment);
                break;
            case "OPCUA":
                // Implement OPC UA disconnection
                break;
            case "REST":
                // Implement REST API disconnection
                break;
            case "Modbus":
                // Implement Modbus disconnection
                break;
        }
    }

    public void updateEquipmentStatus(Entity equipment, String status) {
        equipment.setField("status", status);
        equipment.save();
    }

    public void calculateAndSaveOEEData(Entity equipment, int plannedProductionTime, int operatingTime,
                                       int goodCount, int totalCount, int downtimeMinutes, String downtimeReason) {
        oeeCalculationService.calculateAndSaveOEEData(equipment, plannedProductionTime, operatingTime,
                goodCount, totalCount, downtimeMinutes, downtimeReason);
    }

}
