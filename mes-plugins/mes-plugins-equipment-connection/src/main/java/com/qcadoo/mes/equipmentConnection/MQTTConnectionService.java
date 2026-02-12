package com.qcadoo.mes.equipmentConnection;

import com.qcadoo.model.api.Entity;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MQTTConnectionService {

    private Map<String, MqttClient> clients = new HashMap<>();

    public void connect(Entity equipment) {
        String equipmentNumber = equipment.getStringField("number");
        String ipAddress = equipment.getStringField("ipAddress");
        Integer port = equipment.getIntegerField("port");

        if (ipAddress == null || port == null) {
            return;
        }

        String broker = "tcp://" + ipAddress + ":" + port;
        String clientId = "qcadoo-mes-" + equipmentNumber;
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient client = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setConnectionTimeout(10);
            connOpts.setKeepAliveInterval(60);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Connection lost for equipment " + equipmentNumber + ": " + cause.getMessage());
                    equipment.setField("status", "DOWN");
                    equipment.save();
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("Message arrived for equipment " + equipmentNumber + ": " + new String(message.getPayload()));
                    // Process received message
                    processMessage(equipment, topic, message);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("Delivery complete for equipment " + equipmentNumber);
                }
            });

            client.connect(connOpts);
            clients.put(equipmentNumber, client);

            // Subscribe to relevant topics
            client.subscribe("equipment/" + equipmentNumber + "/status");
            client.subscribe("equipment/" + equipmentNumber + "/data");

            equipment.setField("status", "PRODUCING");
            equipment.save();

        } catch (MqttException e) {
            System.err.println("MQTT connection error for equipment " + equipmentNumber + ": " + e.getMessage());
            equipment.setField("status", "DOWN");
            equipment.save();
        }
    }

    public void disconnect(Entity equipment) {
        String equipmentNumber = equipment.getStringField("number");
        MqttClient client = clients.get(equipmentNumber);

        if (client != null && client.isConnected()) {
            try {
                client.disconnect();
                client.close();
                clients.remove(equipmentNumber);
                equipment.setField("status", "IDLE");
                equipment.save();
            } catch (MqttException e) {
                System.err.println("MQTT disconnection error for equipment " + equipmentNumber + ": " + e.getMessage());
            }
        }
    }

    public void publishMessage(Entity equipment, String topic, String message) {
        String equipmentNumber = equipment.getStringField("number");
        MqttClient client = clients.get(equipmentNumber);

        if (client != null && client.isConnected()) {
            try {
                MqttMessage mqttMessage = new MqttMessage(message.getBytes());
                mqttMessage.setQos(1);
                client.publish("equipment/" + equipmentNumber + "/" + topic, mqttMessage);
            } catch (MqttException e) {
                System.err.println("MQTT publish error for equipment " + equipmentNumber + ": " + e.getMessage());
            }
        }
    }

    private void processMessage(Entity equipment, String topic, MqttMessage message) {
        String payload = new String(message.getPayload());

        // Example message processing
        if (topic.contains("status")) {
            // Update equipment status based on message
            if (payload.equals("IDLE")) {
                equipment.setField("status", "IDLE");
            } else if (payload.equals("PRODUCING")) {
                equipment.setField("status", "PRODUCING");
            } else if (payload.equals("DOWN")) {
                equipment.setField("status", "DOWN");
            } else if (payload.equals("MAINTENANCE")) {
                equipment.setField("status", "MAINTENANCE");
            }
            equipment.save();
        } else if (topic.contains("data")) {
            // Process production data for OEE calculation
            // Example: parse JSON data with production counts, cycle times, etc.
            // Then call OEE calculation service
        }
    }

}
