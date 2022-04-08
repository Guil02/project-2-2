package arch.seven.logic.geo;

import javafx.geometry.Point2D;

public class Vector extends Point2D {

    int x, y;

    /**
     * Creates a new instance of {@code Point2D}.
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     */
    private Vector(double x, double y) {
        super(x, y);
    }

    public Vector(int x, int y) {
        this(x, (double) y);
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

}
