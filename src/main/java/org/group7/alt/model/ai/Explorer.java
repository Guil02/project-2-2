package org.group7.alt.model.ai;

import org.group7.alt.enums.Cardinal;
import org.group7.alt.enums.Component;
import org.group7.alt.logic.algorithms.DefaultExploreStategy;
import org.group7.alt.logic.algorithms.interfaces.ExplorationStrategy;
import org.group7.alt.model.interaction.Action;
import org.group7.alt.model.map.Map;
import org.group7.alt.model.map.MapComponent;

import java.awt.*;

public class Explorer extends MapComponent implements Agent {

    static final String[] EXPLORERS = {"Marco Polo", "Francis Drake", "Hermán Cortéz", "Walter Releigh", "Ferdinand Magellan", "James Cook", "Francisco Pizarro"};

    String name;
    double walkSpeed, sprintSpeed;
    protected Cardinal orientation;
    //Point position;
    ExplorationStrategy exploreStrategy;

    public Explorer(Point pos) {
        super(Component.EXPLORER);
        position = pos;

        name = EXPLORERS[(int) (Math.random() * EXPLORERS.length)]; //just for fun

        //defaults
        orientation = Cardinal.SOUTH;
        exploreStrategy = new DefaultExploreStategy();
    }

    public Explorer(Point pos, Cardinal direction, ExplorationStrategy strat) {
        this(pos);
        exploreStrategy = strat;
        orientation = direction;
    }

    public Explorer(int x, int y, Cardinal orientation, ExplorationStrategy strat) {
        this(new Point(x, y), orientation, strat);
    }


    @Override
    public Action chooseAction() {
        return exploreStrategy.choose();
    }

    @Override
    public boolean step() {

        return false;
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
    public boolean rotate() {
        return false;
    }


    @Override
    public String toString() {
        return "Explorer{" +
                "position=" + position +
                ", strategy=" + exploreStrategy +
                ", name='" + name + '\'' +
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
