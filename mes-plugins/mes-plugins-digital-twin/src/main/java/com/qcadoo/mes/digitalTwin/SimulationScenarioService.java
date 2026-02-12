package com.qcadoo.mes.digitalTwin;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;

import java.util.List;

public class SimulationScenarioService {

    private DataDefinitionService dataDefinitionService;

    public Entity getSimulationScenario(final Long id) {
        DataDefinition simulationScenarioDD = dataDefinitionService.get("digitalTwin", "simulationScenario");
        return simulationScenarioDD.get(id);
    }

    public List<Entity> getAllSimulationScenarios() {
        DataDefinition simulationScenarioDD = dataDefinitionService.get("digitalTwin", "simulationScenario");
        return simulationScenarioDD.find().list().getEntities();
    }

    public Entity createSimulationScenario(final Entity simulationScenario) {
        DataDefinition simulationScenarioDD = dataDefinitionService.get("digitalTwin", "simulationScenario");
        return simulationScenarioDD.save(simulationScenario);
    }

    public void runSimulation(final Long id) {
        Entity scenario = getSimulationScenario(id);
        // Implement simulation run logic
    }

    public void setDataDefinitionService(DataDefinitionService dataDefinitionService) {
        this.dataDefinitionService = dataDefinitionService;
    }

}
