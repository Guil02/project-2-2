package group.seven.enums;

import group.seven.logic.geometric.XY;

import static group.seven.utils.Methods.print;

//TODO: maybe change name to Orientation, but JavaFX has its own Orientation enum, so might be confusing
public enum Cardinal {
    UP(new XY(0, -1)), DOWN(new XY(0, 1)), LEFT(new XY(-1, 0)), RIGHT(new XY(1, 0)),
    NORTH(new XY(0, -1)), SOUTH(new XY(0, 1)), EAST(new XY(-1, 0)), WEST(new XY(1, 0));

    public XY unitVector;

    Cardinal(XY unitVector) {
        this.unitVector = unitVector;
    }

    public static void main(String[] args) {
        print(UP.unitVector);
    }
}
