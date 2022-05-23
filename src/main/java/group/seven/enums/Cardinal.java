package group.seven.enums;

import group.seven.logic.geometric.XY;
import group.seven.logic.simulation.Simulator;
import javafx.geometry.Point2D;

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

    public static void main(String[] args) {
//        EAST.getRotation(0, 0);
//        EAST.getRotation(1, 1);
//        EAST.getRotation(45, 75);
//        EAST.getRotation(-30, 90);

        EAST.getRotation(25, 2);
        EAST.getRotation(75, -2);
        EAST.getRotation(-2, -400);
        EAST.getRotation(-90, 2);
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
