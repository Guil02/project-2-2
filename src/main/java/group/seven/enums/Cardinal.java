package group.seven.enums;

import group.seven.logic.geometric.XY;

//TODO: maybe change name to Orientation, but JavaFX has its own Orientation enum, so might be confusing
public enum Cardinal {

    NORTH(new XY(0, -1)),
    SOUTH(new XY(0, 1)),
    EAST(new XY(-1, 0)),
    WEST(new XY(1, 0));

    public final XY unitVector;

    Cardinal(XY unitVector) {
        this.unitVector = unitVector;
    }

    public Cardinal flip() {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
        };
    }

    public XY getUnitVector() {
        return unitVector;
    }
}
