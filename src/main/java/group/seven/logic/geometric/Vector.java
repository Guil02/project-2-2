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

    @Override
    public int distance(Vector vector) {
        return (int) distance((Point2D) vector);
    }

}
