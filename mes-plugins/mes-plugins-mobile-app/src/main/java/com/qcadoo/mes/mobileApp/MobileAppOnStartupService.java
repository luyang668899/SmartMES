package com.qcadoo.mes.mobileApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;

@Service
public class MobileAppOnStartupService {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Transactional
    public void onStartup() {
        // Initialize mobile app parameters
        initMobileAppParameters();
    }

    private void initMobileAppParameters() {
        DataDefinition mobileAppParameterDD = dataDefinitionService.get("mobileApp", "mobileAppParameter");
        Entity mobileAppParameter = mobileAppParameterDD.find().setMaxResults(1).uniqueResult();
        if (mobileAppParameter == null) {
            mobileAppParameter = mobileAppParameterDD.create();
            mobileAppParameter.setField("enableMobileApp", true);
            mobileAppParameter.setField("enableShopFloorDataCollection", true);
            mobileAppParameter.setField("enableRealTimeNotifications", true);
            mobileAppParameter.setField("enableGeolocation", false);
            mobileAppParameter.setField("enableBarcodeScanning", true);
            mobileAppParameter.setField("enableQRCodeScanning", true);
            mobileAppParameter.setField("enableOfflineMode", true);
            mobileAppParameter.setField("maxOfflineDataSize", 1000);
            mobileAppParameter.setField("dataSyncInterval", 60);
            mobileAppParameter.setField("notificationSyncInterval", 30);
            mobileAppParameter.setField("sessionTimeout", 3600);
            mobileAppParameter.setField("maxDevicesPerUser", 2);
            mobileAppParameter.setField("allowedDeviceTypes", "iOS,Android,Windows");
            mobileAppParameter.setField("sslEnabled", true);
            mobileAppParameter.setField("debugMode", false);
            mobileAppParameterDD.save(mobileAppParameter);
        }
    }

}