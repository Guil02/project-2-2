package group.seven.logic.geometric;

import javafx.geometry.Point2D;

//honestly not even sure If I should include
public class Vector extends Point2D implements VectorPoint {
    //Orientation orientation;

    /**
     * Creates a new instance of {@code Point2D}.
     * as a vector. Basically just a wrapper class.
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     */
    public Vector(double x, double y) {
        super(x, y);
    }

    public Vector(XY xy) {
        super(xy.x(), xy.y());
    }

    @Override
    public XY getXY() {
        return new XY(this);
    }

    @Override
    public int distance(VectorPoint vector) {
        if (vector instanceof Point2D v) {
            return (int) v.distance(v);
        } else if (vector instanceof XY xy) {
            return (int) distance(xy.x(), xy.y());
        } else throw new IllegalArgumentException();
    }

    @Override
    public int manhattan(VectorPoint vector) {
        XY diff = getXY().sub(vector.getXY());
        return Math.abs(diff.x()) + Math.abs(diff.y());
    }

}
