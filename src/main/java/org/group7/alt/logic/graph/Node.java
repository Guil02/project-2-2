package org.group7.alt.logic.graph;

import org.group7.alt.enums.Cell;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Node {
    public List<Node> neighbourhood;

    public Cell type;
    public Point coordinate;

    public long timeStepObserved;

    public boolean explored;
    public boolean obstacle;

    public Cell occupant;

    public Node() {
        explored = false;
        type = Cell.UNKOWN;
        occupant = Cell.UNKOWN;
    }

    public Node(Cell c, Point coords) {
        explored = false;
        neighbourhood = new LinkedList<>();
        type = c;
        coordinate = coords;
        obstacle = switch (c) {
            case WALL, AGENT, EXPLORER, GUARD, INTRUDER -> true;
            default -> false;
        };

    }

    public Node(Cell c, Point coords, long timeStep) {
        this(c, coords);
        timeStepObserved = timeStep;
    }

    public int getDegree() {
        return neighbourhood.size();
    }

    public List<Node> getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(List<Node> neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public Cell getType() {
        return type;
    }

    public void setType(Cell type) {
        this.type = type;
    }

    public Point getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Point coordinate) {
        this.coordinate = coordinate;
    }

    public long getTimeStepObserved() {
        return timeStepObserved;
    }

    public void setTimeStepObserved(long timeStepObserved) {
        this.timeStepObserved = timeStepObserved;
    }

    public boolean isExplored() {
        return explored;
    }

    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    public boolean isObstacle() {
        return obstacle;
    }

    public void setObstacle(boolean obstacle) {
        this.obstacle = obstacle;
    }

    public Cell getOccupant() {
        return occupant;
    }

    public void setOccupant(Cell occupant) {
        this.occupant = occupant;
    }


}
