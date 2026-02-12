package com.qcadoo.mes.supplyChain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;

@Service
public class SupplyChainOnStartupService {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Transactional
    public void onStartup() {
        // 初始化供应链参数
        initSupplyChainParameters();
    }

    private void initSupplyChainParameters() {
        Entity supplyChainParameter = dataDefinitionService.get("supplyChain", "supplyChainParameter").find().setMaxResults(1).uniqueResult();
        if (supplyChainParameter == null) {
            supplyChainParameter = dataDefinitionService.get("supplyChain", "supplyChainParameter").create();
            supplyChainParameter.setField("defaultLeadTime", 7);
            supplyChainParameter.setField("defaultPaymentTerm", "NET30");
            supplyChainParameter.setField("qualityWeight", 0.4);
            supplyChainParameter.setField("deliveryWeight", 0.3);
            supplyChainParameter.setField("priceWeight", 0.2);
            supplyChainParameter.setField("serviceWeight", 0.1);
            supplyChainParameter.setField("enableSupplierRating", true);
            supplyChainParameter.setField("enableProcurementOptimization", true);
            supplyChainParameter.setField("enableAutomaticPOCreation", false);
            supplyChainParameter.save();
        }
    }

}
