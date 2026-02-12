package com.qcadoo.mes.mobileApp;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;

import java.util.List;

public class ShopFloorDataService {

    private DataDefinitionService dataDefinitionService;

    public Entity getShopFloorData(final Long id) {
        DataDefinition shopFloorDataDD = dataDefinitionService.get("mobileApp", "shopFloorData");
        return shopFloorDataDD.get(id);
    }

    public List<Entity> getAllShopFloorData() {
        DataDefinition shopFloorDataDD = dataDefinitionService.get("mobileApp", "shopFloorData");
        return shopFloorDataDD.find().list().getEntities();
    }

    public Entity createShopFloorData(final Entity shopFloorData) {
        DataDefinition shopFloorDataDD = dataDefinitionService.get("mobileApp", "shopFloorData");
        return shopFloorDataDD.save(shopFloorData);
    }

    public List<Entity> getShopFloorDataByOrder(final String order) {
        DataDefinition shopFloorDataDD = dataDefinitionService.get("mobileApp", "shopFloorData");
        return shopFloorDataDD.find().add(com.qcadoo.model.api.search.SearchRestrictions.eq("order", order)).list().getEntities();
    }

    public List<Entity> getShopFloorDataByDevice(final String deviceId) {
        DataDefinition shopFloorDataDD = dataDefinitionService.get("mobileApp", "shopFloorData");
        return shopFloorDataDD.find().add(com.qcadoo.model.api.search.SearchRestrictions.eq("deviceId", deviceId)).list().getEntities();
    }

    public List<Entity> getShopFloorDataByType(final String dataType) {
        DataDefinition shopFloorDataDD = dataDefinitionService.get("mobileApp", "shopFloorData");
        return shopFloorDataDD.find().add(com.qcadoo.model.api.search.SearchRestrictions.eq("dataType", dataType)).list().getEntities();
    }

    public void processShopFloorData(final Long id) {
        DataDefinition shopFloorDataDD = dataDefinitionService.get("mobileApp", "shopFloorData");
        Entity shopFloorData = shopFloorDataDD.get(id);
        if (shopFloorData != null) {
            shopFloorData.setField("status", "Processed");
            shopFloorDataDD.save(shopFloorData);
            // Implement additional processing logic here
        }
    }

    public void setDataDefinitionService(DataDefinitionService dataDefinitionService) {
        this.dataDefinitionService = dataDefinitionService;
    }

}