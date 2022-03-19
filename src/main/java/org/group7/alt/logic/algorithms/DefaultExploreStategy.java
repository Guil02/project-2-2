package org.group7.alt.logic.algorithms;

import org.group7.alt.logic.algorithms.interfaces.ExplorationStrategy;
import org.group7.alt.model.ai.Agents.Agent;
import org.group7.alt.model.ai.Pose;

public class DefaultExploreStategy implements ExplorationStrategy {

    public static Pose overidePose;
    private Pose nextPose;
    private Agent agent;

    public DefaultExploreStategy(Agent agent) {
        this.agent = agent;
        nextPose = agent.getPose();
        overidePose = nextPose;
    }

    @Override
    public void setNextPose(Pose pose) {
        nextPose = pose;
    }

    @Override
    public Pose getNextPose() {
        return nextPose;
    }

    @Override
    public String toString() {
        return "DefaultExplorationStrategy";
    }
}
