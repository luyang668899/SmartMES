package com.qcadoo.mes.supplyChain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.EntityList;

@Service
public class SupplierProductService {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Transactional
    public Entity createSupplierProduct(Entity supplierProduct) {
        return dataDefinitionService.get("supplyChain", "supplierProduct").save(supplierProduct);
    }

    @Transactional
    public Entity updateSupplierProduct(Entity supplierProduct) {
        return dataDefinitionService.get("supplyChain", "supplierProduct").save(supplierProduct);
    }

    @Transactional
    public void deleteSupplierProduct(Long supplierProductId) {
        Entity supplierProduct = dataDefinitionService.get("supplyChain", "supplierProduct").get(supplierProductId);
        if (supplierProduct != null) {
            supplierProduct.delete();
        }
    }

    public Entity getSupplierProduct(Long supplierProductId) {
        return dataDefinitionService.get("supplyChain", "supplierProduct").get(supplierProductId);
    }

    public EntityList getAllSupplierProducts() {
        return dataDefinitionService.get("supplyChain", "supplierProduct").find().list();
    }

    public EntityList getSupplierProductsBySupplier(Long supplierId) {
        return dataDefinitionService.get("supplyChain", "supplierProduct").find()
                .addFilterCondition("supplier.id", supplierId)
                .list();
    }

    public EntityList getSupplierProductsByProduct(Long productId) {
        return dataDefinitionService.get("supplyChain", "supplierProduct").find()
                .addFilterCondition("product.id", productId)
                .list();
    }

}
