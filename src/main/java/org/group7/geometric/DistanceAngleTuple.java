package org.group7.geometric;

//TODO: why is this a parameterized class?
//<Double, Vector2D> are generic types here; they can represent any class, the names are generic, so any object of any
//class type can be passed as parameters. (E.g. you could pass a String object for the "Vector2D" parameter)
//The constructor only accepts double primitives, so the "Double" parameter is also never used
public class DistanceAngleTuple<Double, Vector2D> {

    private final double distance;
    private final Vector2D angle;

    public DistanceAngleTuple(double distance, Vector2D angle) {
        this.distance = distance;
        this.angle = angle;
    }

    public double getDistance() {return distance;}
    public Vector2D getAngle() {return angle;}
    public String toString() {
        return distance + " " + angle.toString();
    }

}

