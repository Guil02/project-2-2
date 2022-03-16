package org.group7.alt.model.ai;

import org.group7.alt.enums.Cardinal;
import org.group7.alt.enums.Cell;
import org.group7.alt.logic.algorithms.DefaultExploreStategy;
import org.group7.alt.logic.algorithms.interfaces.ExplorationStrategy;
import org.group7.alt.model.map.MapComponent;

import java.awt.*;

public class Explorer extends MapComponent implements Agent {

    static final String[] EXPLORERS = {"Marco Polo", "Francis Drake", "Hermán Cortéz", "Walter Releigh", "Ferdinand Magellan", "James Cook", "Francisco Pizarro"};

    String name;
    double walkSpeed, sprintSpeed;
    protected Cardinal orientation;
    Point localPos;
    ExplorationStrategy exploreStrategy;

    Pose currentPose;

    public Explorer(Point pos) {
        super(Cell.EXPLORER, pos);
        localPos = new Point(0,0);
        name = EXPLORERS[(int) (Math.random() * EXPLORERS.length)]; //just for fun

        //defaults
        orientation = Cardinal.SOUTH;
        currentPose = new Pose(localPos, orientation);
        exploreStrategy = new DefaultExploreStategy();
    }

    public Explorer(Point pos, Cardinal direction, ExplorationStrategy strat) {
        this(pos);
        exploreStrategy = strat;
        orientation = direction;
        currentPose = new Pose(localPos, orientation);
    }

    public Explorer(int x, int y, Cardinal orientation, ExplorationStrategy strat) {
        this(new Point(x, y), orientation, strat);
    }

    public Pose update(Pose pose) {
        currentPose = pose;
        orientation = pose.getDirection();
        localPos = pose.getPosition();
        return currentPose;
    }


    @Override
    public Pose step() {
        Pose nextPose = currentPose.stepFoward();
        currentPose = nextPose;
        localPos = currentPose.getPosition();
        //orientation = currentPose.getDirection();
        return nextPose;
    }

    @Override
    public Pose rotate(Cardinal rotation) {
        Pose next = currentPose.rotate(rotation);
        currentPose = next;
        orientation = next.getDirection();
        return next;
    }

    @Override
    public boolean run() {
        return false;
    }

    @Override
    public boolean stop() {
        return false;
    }




    @Override
    public String toString() {
        return "Explorer{" +
                "name:'" + name +
                ", local: (" + localPos.x + ", " + localPos.y + ")" +
                ", orientation: " + currentPose.direction +
                //", strategy: " + exploreStrategy +
                '}';
    }

    //---------
    //factory methods:

    public static Explorer create(Point position) {
        return new Explorer(position);
    }

    public Explorer setWalkSpeed(double speed) {
        walkSpeed = speed;
        return this;
    }

    public Explorer setSprintSpeed(double speed) {
        sprintSpeed = speed;
        return this;
    }


}
