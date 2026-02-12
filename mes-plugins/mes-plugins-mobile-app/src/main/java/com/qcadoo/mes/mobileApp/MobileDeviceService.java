package com.qcadoo.mes.mobileApp;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;

import java.util.List;

public class MobileDeviceService {

    private DataDefinitionService dataDefinitionService;

    public Entity getMobileDevice(final Long id) {
        DataDefinition mobileDeviceDD = dataDefinitionService.get("mobileApp", "mobileDevice");
        return mobileDeviceDD.get(id);
    }

    public List<Entity> getAllMobileDevices() {
        DataDefinition mobileDeviceDD = dataDefinitionService.get("mobileApp", "mobileDevice");
        return mobileDeviceDD.find().list().getEntities();
    }

    public Entity createMobileDevice(final Entity mobileDevice) {
        DataDefinition mobileDeviceDD = dataDefinitionService.get("mobileApp", "mobileDevice");
        return mobileDeviceDD.save(mobileDevice);
    }

    public void updateDeviceStatus(final String deviceId, final String status) {
        DataDefinition mobileDeviceDD = dataDefinitionService.get("mobileApp", "mobileDevice");
        List<Entity> devices = mobileDeviceDD.find().add(com.qcadoo.model.api.search.SearchRestrictions.eq("deviceId", deviceId)).list().getEntities();
        if (!devices.isEmpty()) {
            Entity device = devices.get(0);
            device.setField("status", status);
            mobileDeviceDD.save(device);
        }
    }

    public void updateDeviceLastConnected(final String deviceId) {
        DataDefinition mobileDeviceDD = dataDefinitionService.get("mobileApp", "mobileDevice");
        List<Entity> devices = mobileDeviceDD.find().add(com.qcadoo.model.api.search.SearchRestrictions.eq("deviceId", deviceId)).list().getEntities();
        if (!devices.isEmpty()) {
            Entity device = devices.get(0);
            device.setField("lastConnected", new java.util.Date());
            mobileDeviceDD.save(device);
        }
    }

    public void setDataDefinitionService(DataDefinitionService dataDefinitionService) {
        this.dataDefinitionService = dataDefinitionService;
    }

}