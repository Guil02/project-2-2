package org.group7.alt.enums;

import org.group7.geometric.Point;

public enum Cardinal {

    NORTH, EAST, SOUTH, WEST;

    public Cardinal flip() {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
        };
    }

    public Cardinal rotateRight() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }

    public Cardinal rotateLeft() {
        return switch (this) {
            case NORTH -> WEST;
            case WEST -> SOUTH;
            case SOUTH -> EAST;
            case EAST -> NORTH;
        };
    }

    public Point relativeOffset() {
        return switch (this) {
            case NORTH -> new Point(0, -1);
            case EAST -> new Point(1, 0);
            case SOUTH -> new Point(0, 1);
            case WEST -> new Point(-1, 0);
        };
    }

    public double rotation() {
        return switch (this) {
            case NORTH -> 0;
            case WEST -> 90;
            case SOUTH -> 180;
            case EAST -> 270; // 90
        };
    }
}
