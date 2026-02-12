package com.qcadoo.mes.supplyChain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.EntityList;

@Service
public class SupplierService {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Transactional
    public Entity createSupplier(Entity supplier) {
        return dataDefinitionService.get("supplyChain", "supplier").save(supplier);
    }

    @Transactional
    public Entity updateSupplier(Entity supplier) {
        return dataDefinitionService.get("supplyChain", "supplier").save(supplier);
    }

    @Transactional
    public void deleteSupplier(Long supplierId) {
        Entity supplier = dataDefinitionService.get("supplyChain", "supplier").get(supplierId);
        if (supplier != null) {
            supplier.delete();
        }
    }

    public Entity getSupplier(Long supplierId) {
        return dataDefinitionService.get("supplyChain", "supplier").get(supplierId);
    }

    public EntityList getAllSuppliers() {
        return dataDefinitionService.get("supplyChain", "supplier").find().list();
    }

    @Transactional
    public void calculateSupplierRatings(Long supplierId) {
        Entity supplier = getSupplier(supplierId);
        if (supplier == null) {
            return;
        }

        // 获取供应商的所有评估记录
        EntityList ratings = dataDefinitionService.get("supplyChain", "supplierRating").find()
                .addFilterCondition("supplier.id", supplierId)
                .list();

        if (ratings.isEmpty()) {
            return;
        }

        // 计算平均评分
        double totalQualityRating = 0;
        double totalDeliveryRating = 0;
        double totalPriceRating = 0;
        double totalServiceRating = 0;

        for (Entity rating : ratings) {
            totalQualityRating += rating.getDecimalField("qualityRating").doubleValue();
            totalDeliveryRating += rating.getDecimalField("deliveryRating").doubleValue();
            totalPriceRating += rating.getDecimalField("priceRating").doubleValue();
            totalServiceRating += rating.getDecimalField("serviceRating").doubleValue();
        }

        int ratingCount = ratings.size();
        double avgQualityRating = totalQualityRating / ratingCount;
        double avgDeliveryRating = totalDeliveryRating / ratingCount;
        double avgPriceRating = totalPriceRating / ratingCount;
        double avgServiceRating = totalServiceRating / ratingCount;

        // 获取权重配置
        Entity params = dataDefinitionService.get("supplyChain", "supplyChainParameter").find().setMaxResults(1).uniqueResult();
        double qualityWeight = params.getDecimalField("qualityWeight").doubleValue();
        double deliveryWeight = params.getDecimalField("deliveryWeight").doubleValue();
        double priceWeight = params.getDecimalField("priceWeight").doubleValue();
        double serviceWeight = params.getDecimalField("serviceWeight").doubleValue();

        // 计算综合评分
        double overallRating = (avgQualityRating * qualityWeight) +
                              (avgDeliveryRating * deliveryWeight) +
                              (avgPriceRating * priceWeight) +
                              (avgServiceRating * serviceWeight);

        // 更新供应商评分
        supplier.setField("qualityRating", avgQualityRating);
        supplier.setField("deliveryRating", avgDeliveryRating);
        supplier.setField("priceRating", avgPriceRating);
        supplier.setField("serviceRating", avgServiceRating);
        supplier.setField("overallRating", overallRating);

        updateSupplier(supplier);
    }

}
