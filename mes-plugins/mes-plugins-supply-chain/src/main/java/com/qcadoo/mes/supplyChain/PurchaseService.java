package com.qcadoo.mes.supplyChain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.EntityList;

@Service
public class PurchaseService {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Transactional
    public Entity createPurchaseRequest(Entity purchaseRequest) {
        return dataDefinitionService.get("supplyChain", "purchaseRequest").save(purchaseRequest);
    }

    @Transactional
    public Entity updatePurchaseRequest(Entity purchaseRequest) {
        return dataDefinitionService.get("supplyChain", "purchaseRequest").save(purchaseRequest);
    }

    @Transactional
    public void deletePurchaseRequest(Long purchaseRequestId) {
        Entity purchaseRequest = dataDefinitionService.get("supplyChain", "purchaseRequest").get(purchaseRequestId);
        if (purchaseRequest != null) {
            purchaseRequest.delete();
        }
    }

    @Transactional
    public Entity createPurchaseOrder(Entity purchaseOrder) {
        return dataDefinitionService.get("supplyChain", "purchaseOrder").save(purchaseOrder);
    }

    @Transactional
    public Entity updatePurchaseOrder(Entity purchaseOrder) {
        return dataDefinitionService.get("supplyChain", "purchaseOrder").save(purchaseOrder);
    }

    @Transactional
    public void deletePurchaseOrder(Long purchaseOrderId) {
        Entity purchaseOrder = dataDefinitionService.get("supplyChain", "purchaseOrder").get(purchaseOrderId);
        if (purchaseOrder != null) {
            purchaseOrder.delete();
        }
    }

    @Transactional
    public Entity createPurchaseOrderItem(Entity purchaseOrderItem) {
        return dataDefinitionService.get("supplyChain", "purchaseOrderItem").save(purchaseOrderItem);
    }

    public Entity getPurchaseRequest(Long purchaseRequestId) {
        return dataDefinitionService.get("supplyChain", "purchaseRequest").get(purchaseRequestId);
    }

    public EntityList getAllPurchaseRequests() {
        return dataDefinitionService.get("supplyChain", "purchaseRequest").find().list();
    }

    public Entity getPurchaseOrder(Long purchaseOrderId) {
        return dataDefinitionService.get("supplyChain", "purchaseOrder").get(purchaseOrderId);
    }

    public EntityList getAllPurchaseOrders() {
        return dataDefinitionService.get("supplyChain", "purchaseOrder").find().list();
    }

    @Transactional
    public Entity convertRequestToOrder(Long purchaseRequestId, Long supplierId) {
        Entity purchaseRequest = getPurchaseRequest(purchaseRequestId);
        if (purchaseRequest == null) {
            return null;
        }

        Entity supplier = dataDefinitionService.get("supplyChain", "supplier").get(supplierId);
        if (supplier == null) {
            return null;
        }

        Entity purchaseOrder = dataDefinitionService.get("supplyChain", "purchaseOrder").create();
        purchaseOrder.setField("supplier", supplier);
        purchaseOrder.setField("orderDate", new java.util.Date());
        purchaseOrder.setField("expectedDeliveryDate", purchaseRequest.getDateField("expectedDeliveryDate"));
        purchaseOrder.setField("status", "DRAFT");
        purchaseOrder.setField("totalAmount", purchaseRequest.getDecimalField("totalAmount"));
        purchaseOrder.setField("currency", purchaseRequest.getBelongsToField("currency"));
        purchaseOrder.setField("paymentTerms", supplier.getStringField("paymentTerms"));
        purchaseOrder.setField("paymentStatus", "UNPAID");
        purchaseOrder.setField("notes", purchaseRequest.getStringField("notes"));

        return purchaseOrder.save();
    }

}
