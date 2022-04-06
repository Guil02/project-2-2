package org.group7.alt.model.ai.agents;

import org.group7.alt.enums.Action;
import org.group7.alt.enums.Cardinal;
import org.group7.alt.enums.Cell;
import org.group7.alt.logic.algorithms.DefaultExploreStategy;
import org.group7.alt.logic.algorithms.interfaces.ExplorationStrategy;
import org.group7.alt.logic.simulation.PhysicsHandler;
import org.group7.alt.model.ai.Pose;

import java.util.ArrayList;
import java.util.List;

public class Explorer extends Agent {

    static List<String> EXPLORERS = new ArrayList<>(List.of("Francis Drake", "James Cook", "Walter Releigh", "Ferdinand Magellan", "Francisco Pizarro"));

    ExplorationStrategy exploreStrategy;
    Pose prevPose;

    public Explorer(Pose pose) {
        super(pose);
        prevPose = pose;

        name = EXPLORERS.get((int) (Math.random() * EXPLORERS.size())); //just for fun
        EXPLORERS.remove(name);

        exploreStrategy = new DefaultExploreStategy(this); //TODO add constructor with exploration strategy
    }


    @Override
    public Pose update() {
        //get FOV
        //getFOV();

        //update agentGraph

        //update pose
        Pose newPose = exploreStrategy.getNextPose();

        if (!PhysicsHandler.collision(this, newPose)) {
            //update(prevPose);
            setPose(newPose);
        } else {
            update(Action.FLIP);
        }

        //random next move
        exploreStrategy.setNextPose((Math.random() > 0.35) ? (localPose.stepFoward()) : (localPose.rotate(Cardinal.values()[(int)(Math.random() * 4)])));

        return getPose();
    }

    public Pose update(Cardinal direction) {
        return localPose = localPose.rotate(direction);
    }

    public Pose update(Pose newPose) {
        prevPose = localPose;
        return localPose = newPose;
    }

    @Override
    public Pose update(Action action) {
        return switch (action) {
            case TURN_LEFT -> update(localPose.getDirection().rotateLeft());
            case TURN_RIGHT -> update(localPose.getDirection().rotateRight());
            case FLIP -> update(localPose.getDirection().flip());

            case STEP -> update(localPose.stepFoward());
            case SPRINT -> update(localPose.stepFoward().stepFoward());
            case HALT -> localPose;
        };
    }

    public void updateState() {

    }

    @Override
    public Cell getType() {
        return Cell.EXPLORER;
    }

    @Override
    public String toString() {
        return "Explorer{" +
                "name='" + name + '\'' +
                ", localPose=" + localPose +
                ", exploreStrategy=" + exploreStrategy +
                '}';
    }
}
