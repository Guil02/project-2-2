package group.seven.logic.simulation;

import group.seven.gui.TempView;
import group.seven.model.environment.Scenario;

public class Runner {
    Scenario scenario;

    public Runner(Scenario scenario) {
        this.scenario = scenario;

        TempView view = new TempView(scenario);
    }

    //step
    //updateModel
    //updateGUI
    //gameOver
}
