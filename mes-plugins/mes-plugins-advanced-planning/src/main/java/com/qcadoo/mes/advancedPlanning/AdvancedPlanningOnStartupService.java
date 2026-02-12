package com.qcadoo.mes.advancedPlanning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;

@Service
public class AdvancedPlanningOnStartupService {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Transactional
    public void onStartup() {
        // 初始化高级计划参数
        initAdvancedPlanningParameters();
    }

    private void initAdvancedPlanningParameters() {
        Entity advancedPlanningParameter = dataDefinitionService.get("advancedPlanning", "advancedPlanningParameter").find().setMaxResults(1).uniqueResult();
        if (advancedPlanningParameter == null) {
            advancedPlanningParameter = dataDefinitionService.get("advancedPlanning", "advancedPlanningParameter").create();
            advancedPlanningParameter.setField("defaultPlanHorizon", 30);
            advancedPlanningParameter.setField("defaultOptimizationLevel", "STANDARD");
            advancedPlanningParameter.setField("maxConcurrentOrders", 100);
            advancedPlanningParameter.setField("schedulingInterval", 5);
            advancedPlanningParameter.setField("maxSchedulingTime", 300);
            advancedPlanningParameter.setField("enableRealTimeScheduling", false);
            advancedPlanningParameter.setField("resourceUtilizationTarget", 0.85);
            advancedPlanningParameter.setField("overtimeAllowed", false);
            advancedPlanningParameter.setField("maxOvertimeHours", 10);
            advancedPlanningParameter.setField("optimizationAlgorithm", "GENETIC");
            advancedPlanningParameter.setField("populationSize", 100);
            advancedPlanningParameter.setField("maxGenerations", 500);
            advancedPlanningParameter.setField("enableEmailNotifications", true);
            advancedPlanningParameter.setField("enableAuditLogging", true);
            advancedPlanningParameter.save();
        }
    }

}
