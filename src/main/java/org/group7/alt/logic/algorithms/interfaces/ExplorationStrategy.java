package org.group7.alt.logic.algorithms.interfaces;


import org.group7.alt.model.ai.Pose;

public interface ExplorationStrategy extends AgentStrategy {
    void setNextPose(Pose pose);
    Pose getNextPose();
}
