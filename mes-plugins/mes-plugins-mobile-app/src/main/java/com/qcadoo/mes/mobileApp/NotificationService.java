package com.qcadoo.mes.mobileApp;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;

import java.util.List;

public class NotificationService {

    private DataDefinitionService dataDefinitionService;

    public Entity getNotification(final Long id) {
        DataDefinition notificationDD = dataDefinitionService.get("mobileApp", "notification");
        return notificationDD.get(id);
    }

    public List<Entity> getAllNotifications() {
        DataDefinition notificationDD = dataDefinitionService.get("mobileApp", "notification");
        return notificationDD.find().list().getEntities();
    }

    public Entity createNotification(final Entity notification) {
        DataDefinition notificationDD = dataDefinitionService.get("mobileApp", "notification");
        return notificationDD.save(notification);
    }

    public List<Entity> getNotificationsByRecipient(final String recipient) {
        DataDefinition notificationDD = dataDefinitionService.get("mobileApp", "notification");
        return notificationDD.find().add(com.qcadoo.model.api.search.SearchRestrictions.eq("recipient", recipient)).list().getEntities();
    }

    public List<Entity> getNotificationsByDevice(final String deviceId) {
        DataDefinition notificationDD = dataDefinitionService.get("mobileApp", "notification");
        return notificationDD.find().add(com.qcadoo.model.api.search.SearchRestrictions.eq("deviceId", deviceId)).list().getEntities();
    }

    public List<Entity> getNotificationsByStatus(final String status) {
        DataDefinition notificationDD = dataDefinitionService.get("mobileApp", "notification");
        return notificationDD.find().add(com.qcadoo.model.api.search.SearchRestrictions.eq("status", status)).list().getEntities();
    }

    public void sendNotification(final Long id) {
        DataDefinition notificationDD = dataDefinitionService.get("mobileApp", "notification");
        Entity notification = notificationDD.get(id);
        if (notification != null) {
            notification.setField("status", "Delivered");
            notification.setField("deliveredAt", new java.util.Date());
            notificationDD.save(notification);
            // Implement actual notification sending logic here
        }
    }

    public void markNotificationAsRead(final Long id) {
        DataDefinition notificationDD = dataDefinitionService.get("mobileApp", "notification");
        Entity notification = notificationDD.get(id);
        if (notification != null) {
            notification.setField("status", "Read");
            notification.setField("readAt", new java.util.Date());
            notificationDD.save(notification);
        }
    }

    public void setDataDefinitionService(DataDefinitionService dataDefinitionService) {
        this.dataDefinitionService = dataDefinitionService;
    }

}