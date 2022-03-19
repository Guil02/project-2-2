package org.group7.alt.logic.simulation;

import javafx.animation.AnimationTimer;
import org.group7.alt.enums.Action;
import org.group7.alt.enums.Cardinal;
import org.group7.alt.enums.GameMode;
import org.group7.alt.logic.util.CoordinateMapper;
import org.group7.alt.logic.util.ObservedTile;
import org.group7.alt.model.ai.Agents.Agent;
import org.group7.alt.model.ai.Agents.Explorer;
import org.group7.alt.model.ai.Pose;
import org.group7.alt.model.map.Environment;
import org.group7.alt.views.Renderer;
import org.group7.model.Scenario;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

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
        //verify validitu
        //execute agent action //all agents should be executed after their vision has been updated
        //upate global model
        //update gui
            //view.update();

        for (Agent a : Environment.getTileMap().getAgentList()) {
            //moveAgent(a);
        }

        renderer.update();
        //check for game over condition
        elapsedTimeSteps += timeStep;
    }

    public void moveAgent(Agent agent) {

        //TODO collision Handling
        if (PhysicsHandler.collision(Environment.getTileMap(), agent)) {
            System.out.println("COLLLIDE: " + agent + ", pose" + agent.getPose());
        }
        Pose newPose = agent.update(Action.STEP);
        Rectangle map = new Rectangle(Environment.WIDTH + 1, Environment.HEIGHT + 1);
        if (map.contains(newPose.getPosition())) {
            if (Environment.getTileMap().getTile(newPose.getPosition()).isObstacle()) {
                agent.update(Action.FLIP);
                agent.update(Action.STEP);
            }
//        } else {
//            agent.update(Action.FLIP);
//            while (!map.contains(newPose.getPosition())) {
//                newPose = agent.update(Action.STEP);
//            }
        }
//        agent.step();
//        agent.rotate(Cardinal.values()[(int)(Math.random() * 4)]);
    }

    private void spawnAgents(GameMode gameMode) {
        switch (gameMode) {
            case EXPLORATION -> {
                for (int i = 0; i < Environment.NUM_GAURDS; i++) {
                    //spawn explorers in spawn area
                    Point spawnPoint = Environment.GUARD_SPAWN_GRIDS.get((int)(Math.random() * Environment.GUARD_SPAWN_GRIDS.size()));
                    //Pose initialPose = new Pose(spawnPoint, Cardinal.NORTH); //default orientation is NORTH

                    Pose initialPose = new Pose(new Point(0,0), Cardinal.NORTH); //default orientation is NORTH
                    Explorer agent = new Explorer(initialPose);

                    Environment.getTileMap().addAgent(agent, spawnPoint);
                    List<ObservedTile> fov = VisionHandler.getFOV(agent);
                    System.out.println("\nAgent: (" + spawnPoint.x + ", " + spawnPoint.y + ")\n");
                    for (int f = 0; f < fov.size(); f++) {
                        if (f % VisionHandler.VIEW_DISTANCE == 0 && f!=0) System.out.println();
                        System.out.print(" <" + fov.get(f).cell() + ", x=" + fov.get(f).x() + ", y="+fov.get(f).y() +">  ");
                    }
                    System.out.println();


                    System.out.println(agent);
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
