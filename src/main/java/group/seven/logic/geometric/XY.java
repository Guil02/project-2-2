package group.seven.logic.geometric;

import javafx.geometry.Point2D;

/**
 * Very simple wrapper to store integer x, y coordinates (or such)
 * Autogenerates the getters, hashcode, equals, and toString
 * Fields are final and so there are no setters
 * @param x int x coordinate
 * @param y int y coordinate
 */
public record XY(int x, int y) {
    public XY add(int X, int Y){
        return new XY(x() + X, y() + Y);
    }

    public XY(Point2D p) {
        this((int) p.getX(), (int) p.getY());
    }
}
