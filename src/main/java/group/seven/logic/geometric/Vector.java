package group.seven.logic.geometric;

import javafx.geometry.Point2D;

//honestly not even sure If I should include
//TODO probably should not use this class
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

    public XY getXY() {
        return new XY(this);
    }

    @Override
    public int distance(Vector vector) {
        return (int) distance((Point2D) vector);
    }

}
