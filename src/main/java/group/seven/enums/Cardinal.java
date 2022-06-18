package group.seven.enums;

import group.seven.logic.geometric.XY;
import group.seven.logic.simulation.Simulator;
import javafx.geometry.Point2D;

import static group.seven.enums.Action.*;
import static group.seven.enums.Action.TURN_LEFT;

/**
 * Represents the cardinal directions on a map compass.
 * North, South, East, West
 * also contains value of "Nowhere", representing none of the above.
 */
public enum Cardinal {

    /**
     * The North.
     */
    NORTH(new XY(0, -1)), //0
    /**
     * The South.
     */
    SOUTH(new XY(0, 1)), //1
    /**
     * The East.
     */
    EAST(new XY(1, 0)), //2
    /**
     * The West.
     */
    WEST(new XY(-1, 0)), //3
    /**
     * The Nowhere.
     */
    NOWHERE(new XY(0, 0)); //4

    /**
     * A XY unit vector representing one step in the direction of this
     * Cardinal value in both the x and y directions
     */
    public final XY unitVector;

    Cardinal(XY unitVector) {
        this.unitVector = unitVector;
    }

    /**
     * Rotation cardinal.
     *
     * @param location the location
     * @return the cardinal
     */
    public Cardinal rotation(XY location) {
        if (unitVector.x() * location.x() == 0) {
            if (unitVector.y() * location.y() > 0)
                return NORTH;
            else return SOUTH;
        } else if (unitVector.y() * location.y() == 0) {
            if (unitVector.x() * location.y() > 0)
                return EAST;
            else return WEST;
        } else {
            return NOWHERE;
        }
    }

    /**
     * Gets rotation.
     *
     * @param x the x
     * @param y the y
     * @return the rotation
     */
    public Cardinal getRotation(int x, int y) {
        Point2D p = new Point2D(unitVector().x(), unitVector.y());
        Point2D o = new Point2D(x, y);
        double degrees = p.angle(o);
        Point2D n = o.normalize();
        double atan2 = Math.atan2(n.getX(), n.getY());
        int direction = (int) (((atan2 * 2 / Math.PI)) + 4) % 4;
        System.out.println("degrees : " + degrees);
        System.out.println("direction : " + direction);

        return this;
    }

    /**
     * Returns a random Cardinal direction of the 4 types
     * NOWHERE is excluded
     *
     * @return a random cardinal direction
     */
    public static Cardinal randomDirection() {
        return Cardinal.values()[Simulator.rand.nextInt(4)];
    }

    /**
     * Inverts this Cardinals direction
     *
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

    private Action getAction(Cardinal c) {
        return switch (c) {
            case NORTH -> TURN_UP;
            case SOUTH -> TURN_DOWN;
            case EAST -> TURN_RIGHT;
            case WEST -> TURN_LEFT;
            default -> throw new IllegalArgumentException();
        };
    }

    /**
     * Returns an XY that represents one discrete step in this cardinal's direction
     * in the x and y-axis
     *
     * @return an XY of unit length that represents a step in the cardinals direction
     */
    public XY unitVector() {
        return unitVector;
    }
}
