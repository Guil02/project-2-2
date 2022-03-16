package org.group7.alt.logic.simulation;

import javafx.animation.AnimationTimer;
import org.group7.alt.enums.Cardinal;
import org.group7.alt.enums.Cell;
import org.group7.alt.enums.GameMode;
import org.group7.alt.logic.algorithms.DefaultExploreStategy;
import org.group7.alt.model.ai.Agent;
import org.group7.alt.model.ai.Explorer;
import org.group7.alt.model.ai.Pose;
import org.group7.alt.model.map.Map;
import org.group7.alt.model.map.Tile;
import org.group7.alt.views.Renderer;
import org.group7.alt.views.View;
import org.group7.model.Scenario;

import java.util.ArrayList;
import java.util.List;

public class SimController extends AnimationTimer {

    double elapsedTimeSteps;
    double timeStep = 0.1;

    Map godMap;
    List<Agent> agents;
    View view;
    Renderer renderer;

    public SimController(Scenario s) {
        elapsedTimeSteps = 0;
        godMap = new Map(s);
        agents = new ArrayList<>(Map.NUM_GAURDS + Map.NUM_INTRUDERS);

        spawnAgents(Map.GAME_MODE);
        //view = new View(godMap.tileMap);
        renderer = new Renderer(godMap);
        start();
    }

    @Override
    public void handle(long now) {
        //get agent vision
        //update agent graph
        //query agent action
        //verify validitu
        //execute agent action //all agents should be executed after their vision has been updated
        //upate global model
        //update gui
            //view.update();
        for (Agent a : agents) {
            moveAgent(a);
        }
        renderer.update();
        //check for game over condition
        elapsedTimeSteps += timeStep;
    }

    public void moveAgent(Agent agent) {
        agent.step();
        agent.rotate(Cardinal.values()[(int)(Math.random() * 4)]);
        System.out.println(agent);
    }

    private void spawnAgents(GameMode gameMode) {
        switch (gameMode) {
            case EXPLORATION -> {
                for (int i = 0; i < Map.NUM_GAURDS; i++) {
                    //spawn explorers in spawn area
                    Tile spawnCell = Map.GUARD_SPAWN_CELLS.get((int)(Math.random() * Map.GUARD_SPAWN_CELLS.size()));

                    Cardinal viewDirection = Cardinal.values()[(int)(Math.random() * Cardinal.values().length)];

                    Explorer agent = new Explorer(spawnCell.getPosition(), viewDirection, new DefaultExploreStategy());
                    Tile tile = godMap.tileMap[spawnCell.getPosition().x][spawnCell.getPosition().y];

                    //need a better way to do this
                    tile.setObstacle(true);
                    tile.setExplored(true);
                    tile.setType(Cell.EXPLORER);
                    tile.setColorTexture(Cell.EXPLORER.getColor());
                    tile.setOrientation(Cardinal.values()[(int)(Math.random() * Cardinal.values().length)]);

                    Map.INTRUDER_SPAWN_CELLS.remove(spawnCell);
                    //add to agents list
                    agents.add(agent);

                    System.out.println(agent);
                    System.out.println(tile + "\n");

                }
            }

            case SINGLE_INTRUDER, MULTI_INDRUDER -> {
                for (int i = 0; i < Map.NUM_GAURDS; i++) {
                    //spawn guards in spawn area
                    //add to agents list
                }

                for (int i = 0; i < Map.NUM_INTRUDERS; i++) {
                    //spawn intruders in spawn area
                    //add to agents list
                }
            }
        }
    }
}
