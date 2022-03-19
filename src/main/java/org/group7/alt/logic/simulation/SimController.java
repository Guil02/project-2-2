package org.group7.alt.logic.simulation;

import javafx.animation.AnimationTimer;
import org.group7.alt.enums.Action;
import org.group7.alt.enums.Cardinal;
import org.group7.alt.enums.GameMode;
import org.group7.alt.logic.util.records.ObservedTile;
import org.group7.alt.model.ai.Agents.Agent;
import org.group7.alt.model.ai.Agents.Explorer;
import org.group7.alt.model.ai.Pose;
import org.group7.alt.model.map.Environment;
import org.group7.alt.views.Renderer;
import org.group7.model.Scenario;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.group7.alt.model.map.Environment.TILE_MAP;

public class SimController extends AnimationTimer {

    double elapsedTimeSteps;
    double timeStep = 0.1;

    Environment environment;
    List<Agent> agents;
    Renderer renderer;

    public SimController(Scenario s) {
        elapsedTimeSteps = 0;
        environment = new Environment(s);
        agents = new ArrayList<>(Environment.NUM_GAURDS + Environment.NUM_INTRUDERS);
        //or just tuple list

        spawnAgents(Environment.GAME_MODE);
        renderer = new Renderer(environment);
        start();
    }

    @Override
    public void handle(long now) {
        //get agent vision
        //update agent graph
        //query agent action
        //verify validity
        //execute agent action //all agents should be executed after their vision has been updated
        //upate global model
        //update gui
        //view.update();

        for (Agent a : Environment.getTileMap().getAgentList()) {
            //moveAgent(a);
            //update agent vision
            a.updateFOV(VisionHandler.getFOV(a)); //TODO map to local coordinates
            a.update();
        }

        renderer.update();
        //check for game over condition
        elapsedTimeSteps += timeStep;

        System.out.print("\r elapsed time: " + elapsedTimeSteps + "   " + TILE_MAP.getExplorationPercent());
    }

    private void spawnAgents(GameMode gameMode) {
        switch (gameMode) {
            case EXPLORATION -> {
                for (int i = 0; i < Environment.NUM_GAURDS; i++) {
                    //spawn explorers in spawn area
                    Point spawnPoint = Environment.GUARD_SPAWN_GRIDS.get((int)(Math.random() * Environment.GUARD_SPAWN_GRIDS.size()));

                    Pose initialPose = new Pose(new Point(0,0), Cardinal.NORTH); //default orientation is NORTH
                    Explorer agent = new Explorer(initialPose);

                    TILE_MAP.addAgent(agent, spawnPoint);
                }
            }

            case SINGLE_INTRUDER, MULTI_INDRUDER -> {
                for (int i = 0; i < Environment.NUM_GAURDS; i++) {
                    //spawn guards in spawn area
                    //add to agents list

                }

                for (int i = 0; i < Environment.NUM_INTRUDERS; i++) {
                    //spawn intruders in spawn area
                    //add to agents list
                }
            }
        }
    }
}
