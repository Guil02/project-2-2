package org.group7.alt.model.ai;

import org.group7.alt.enums.Cardinal;

import java.awt.*;

public class Pose {
    Point position;
    Cardinal direction;

    public Pose(Point pos, Cardinal orientation) {
        position = pos;
        direction = orientation;
    }

    //creates new Pose object with next pose, could also update this pos object directly
    public Pose stepFoward() {
        Point newLocation;
        switch (direction) {
            case NORTH -> newLocation = new Point(position.x, position.y - 1);
            case SOUTH -> newLocation = new Point(position.x, position.y + 1);
            case EAST -> newLocation = new Point(position.x + 1, position.y);
            case WEST -> newLocation = new Point(position.x - 1, position.y);
            default -> newLocation = new Point(position);
        }

        return new Pose(newLocation, direction);
    }

    public Pose rotate(Cardinal orientation) {
        return new Pose(new Point(position), orientation);
    }

    public Point getPosition() {
        return position;
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    public Cardinal getDirection() {
        return direction;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setDirection(Cardinal direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Pose{" +
                "position=(" + position.x + ", " + position.y + ")" +
                ", direction=" + direction +
                '}';
    }
}
