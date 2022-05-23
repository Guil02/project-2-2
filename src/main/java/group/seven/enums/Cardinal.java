package group.seven.enums;

import group.seven.logic.geometric.XY;
import group.seven.logic.simulation.Simulator;

public enum Cardinal {

    NORTH(new XY(0, -1)),
    SOUTH(new XY(0, 1)),
    EAST(new XY(1, 0)),
    WEST(new XY(-1, 0)),
    NOWHERE(new XY(0,0));

    public final XY unitVector;

    Cardinal(XY unitVector) {
        this.unitVector = unitVector;
    }

    /**
     * Returns a random Cardinal direction of the 4 types
     * NOWHERE is excluded
     * @return a random cardinal direction
     */
    public static Cardinal randomDirection() {
        return Cardinal.values()[Simulator.rand.nextInt(5)];
    }

    /**
     * Inverts this Cardinals direction
     * @return the opposite direction of this Cardinal
     */
    public Cardinal flip() {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
            case NOWHERE -> NOWHERE;
        };
    }

    /**
     * Returns an XY that represents one discrete step in this cardinal's direction
     * in the x and y-axis
     * @return an XY of unit length that represents a step in the cardinals direction
     */
    public XY unitVector() {
        return unitVector;
    }
}
