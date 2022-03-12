package org.group7.alt.logic.simulation;

import javafx.animation.AnimationTimer;
import org.group7.alt.enums.GameMode;
import org.group7.alt.model.ai.Agent;
import org.group7.alt.model.map.Map;
import org.group7.model.Scenario;

import java.util.List;

public class SimController extends AnimationTimer {

    double elapsedTimeSteps;
    double timeStep = 0.1;

    Map godMap;
    List<Agent> agents;

    public SimController(Scenario s) {
        elapsedTimeSteps = 0;
        godMap = new Map(s);

    }

    @Override
    public void handle(long now) {
        elapsedTimeSteps += timeStep;
    }

    private void spawnAgents(GameMode gameMode) {
        switch (gameMode) {
            case EXPLORATION -> {
                for (int i = 0; i < Map.NUM_GAURDS; i++) {
                    //spawn explorers in spawn area
                }
            }

            case SINGLE_INTRUDER, MULTI_INDRUDER -> {
                for (int i = 0; i < Map.NUM_GAURDS; i++) {
                    //spawn guards in spawn area
                }

                for (int i = 0; i < Map.NUM_INTRUDERS; i++) {
                    //spawn intruders in spawn area
                }
            }
        }
    }
}
