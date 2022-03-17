package org.group7.alt.model.ai.Agents;

import org.group7.alt.enums.Action;
import org.group7.alt.enums.Cardinal;
import org.group7.alt.enums.Cell;
import org.group7.alt.logic.algorithms.DefaultExploreStategy;
import org.group7.alt.logic.algorithms.interfaces.ExplorationStrategy;
import org.group7.alt.model.ai.Pose;

import java.util.ArrayList;
import java.util.List;

public class Explorer extends Agent {

    static List<String> EXPLORERS = new ArrayList<>(List.of("Francis Drake", "Hermán Cortéz", "Walter Releigh", "Ferdinand Magellan","James Cook", "Francisco Pizarro"));

    ExplorationStrategy exploreStrategy;

    public Explorer(Pose pose) {
        super(pose);
        name = EXPLORERS.get((int) (Math.random() * EXPLORERS.size())); //just for fun
        EXPLORERS.remove(name);
        exploreStrategy = new DefaultExploreStategy(); //TODO add constructor with exploration strategy
    }

    @Override
    public Pose update() {
        //update AgentGraph
            //get FOV
        //update pose
        return (Math.random() > 0.35) ? (localPose = localPose.stepFoward()) : (localPose = localPose.rotate(Cardinal.values()[(int)(Math.random() * 4)]));
    }

    public Pose update(Cardinal direction) {
        return localPose = localPose.rotate(direction);
    }

    public Pose update(Pose newPose) {
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
