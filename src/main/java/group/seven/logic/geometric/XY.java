package group.seven.logic.geometric;

import javafx.geometry.Point2D;

/**
 * Very simple wrapper to store integer x, y coordinates (or such)
 * Autogenerates the getters, hashcode, equals, and toString
 * Fields are final and so there are no setters
 *
 * @param x int x coordinate
 * @param y int y coordinate
 */
public record XY(int x, int y) implements VectorPoint {
    public XY(Point2D p) {
        this((int) p.getX(), (int) p.getY());
    }

    public XY add(int X, int Y) {
        return new XY(x() + X, y() + Y);
    }

    public boolean equalsWithinRange(XY xy, int range) {
        Point2D guard = new Point2D(this.x, this.y);
        Point2D intruder = new Point2D(xy.x, xy.y);
        double distance = guard.distance(intruder);
        return distance <= range;
    }

    public boolean equals(XY xy) {
        return this.x() == xy.x() && this.y() == xy.y();
    }

    @Override
    public int distance(VectorPoint vector) {
        return new Vector(x, y).distance(vector);
    }

    @Override
    public XY getXY() {
        return this;
    }
}
