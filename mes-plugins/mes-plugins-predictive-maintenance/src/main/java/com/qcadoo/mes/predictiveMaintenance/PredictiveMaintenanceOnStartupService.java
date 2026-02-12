package com.qcadoo.mes.predictiveMaintenance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;

@Service
public class PredictiveMaintenanceOnStartupService {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Transactional
    public void onStartup() {
        // Initialize predictive maintenance parameters
        initializeDefaultParameters();
    }

    private void initializeDefaultParameters() {
        DataDefinition predictiveMaintenanceParameterDD = dataDefinitionService.get("predictiveMaintenance", "predictiveMaintenanceParameter");
        Entity predictiveMaintenanceParameter = predictiveMaintenanceParameterDD.find().setMaxResults(1).uniqueResult();
        if (predictiveMaintenanceParameter == null) {
            predictiveMaintenanceParameter = predictiveMaintenanceParameterDD.create();
            predictiveMaintenanceParameter.setField("parameter", "healthScoreThreshold");
            predictiveMaintenanceParameter.setField("value", "80");
            predictiveMaintenanceParameter.setField("description", "Health score threshold for equipment status");
            predictiveMaintenanceParameter.setField("active", true);
            predictiveMaintenanceParameterDD.save(predictiveMaintenanceParameter);
        }
    }

}
