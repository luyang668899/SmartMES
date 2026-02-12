package com.qcadoo.mes.dataAnalysis;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;

import java.util.List;

public class KpiMetricService {

    private DataDefinitionService dataDefinitionService;

    public Entity getKpiMetric(final Long id) {
        DataDefinition kpiMetricDD = dataDefinitionService.get("dataAnalysis", "kpiMetric");
        return kpiMetricDD.get(id);
    }

    public List<Entity> getAllKpiMetrics() {
        DataDefinition kpiMetricDD = dataDefinitionService.get("dataAnalysis", "kpiMetric");
        return kpiMetricDD.find().list().getEntities();
    }

    public Entity createKpiMetric(final Entity kpiMetric) {
        DataDefinition kpiMetricDD = dataDefinitionService.get("dataAnalysis", "kpiMetric");
        return kpiMetricDD.save(kpiMetric);
    }

    public void setDataDefinitionService(DataDefinitionService dataDefinitionService) {
        this.dataDefinitionService = dataDefinitionService;
    }

}
