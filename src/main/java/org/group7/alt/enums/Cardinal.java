package org.group7.alt.enums;

import org.group7.alt.logic.util.records.Range;
import org.group7.alt.logic.util.records.XY;

import java.awt.*;

public enum Cardinal {

    NORTH(new XY(0, -1)),
    EAST(new XY(1, 0)),
    SOUTH(new XY(0, 1)),
    WEST(new XY(-1, 0));

    Cardinal(XY xy) {
        unitVector = xy;
    }

    public final XY unitVector;

    public boolean vertical() {
        return switch (this) {
            case NORTH, SOUTH -> true;
            case EAST, WEST -> false;
        };
    }

    public Range visualBreadth(XY pos) {
        return switch (this) {
            case NORTH, SOUTH -> new Range(pos.x() - 1,pos.x() + 2);
            case EAST, WEST -> new Range(pos.y() - 1, pos.y() + 2);
        };
    }

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
