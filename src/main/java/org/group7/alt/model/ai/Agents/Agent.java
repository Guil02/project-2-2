package org.group7.alt.model.ai.Agents;

import org.group7.alt.enums.Cell;
import org.group7.alt.logic.graph.Node;
import org.group7.alt.logic.util.ObservedTile;
import org.group7.alt.logic.util.records.Frame;
import org.group7.alt.model.ai.GraphModel;
import org.group7.alt.enums.Action;
import org.group7.alt.model.ai.Pose;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public abstract class Agent {

    String name;
    //double walkSpeed, sprintSpeed;
    Pose localPose;
    Point localOrigin;
    List<Pose> poseHistory;
    List<Action> trajectory;

    GraphModel mapModel;

    public Agent(Pose pose) {
        localPose = pose;   //initial agent pose should be in the local coordinate frame
        mapModel = new GraphModel(this);
        localOrigin = new Point(0,0);

        poseHistory = new LinkedList<>();
        trajectory = new LinkedList<>();

        poseHistory.add(pose);
    }

    public Agent(Pose pose, List<ObservedTile> fov) {
        this(pose);
        for (ObservedTile tile : fov)
            mapModel.addNode(new Node(tile.cell(), new Point(tile.x(), tile.y())));


    }

    public abstract Pose update();

    public Pose getPose() {
        return localPose;
    }

    public void setPose(Pose currentPose) {
        localPose = currentPose;
    }

    public List<Pose> getPoseHistory() {
        return poseHistory;
    }

    public List<Action> getTrajectory() {
        return trajectory;
    }

    public void setTrajectory(List<Action> trajectory) {
        this.trajectory = trajectory;
    }

    public GraphModel getMapModel() {
        return mapModel;
    }

    public void setMapModel(GraphModel mapModel) {
        this.mapModel = mapModel;
    }

    public abstract Cell getType();

    public abstract Pose update(Action flip);
}
