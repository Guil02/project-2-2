package org.group7.geometric;

//TODO: why is this a parameterized class? <Double, Vector2D> are generic types here; they can represent any class
public class DistanceAngleTuple<Double, Vector2D> {

    //TODO generic parameter <Double> is never even used. Why include as a generic parameter?
    private final double distance;
    private final Vector2D angle;
    //TODO Vector2D is not an actual Vector2D class here, it's a generic type which can represent any class. It could be a String or Area, or any object. I don't think this is the intent and can potentially cause bugs

    public DistanceAngleTuple(double distance, Vector2D angle) {
        this.distance = distance;
        this.angle = angle;
    }

    public double getDistance() {return distance;}
    public Vector2D getAngle() {return angle;} //TODO since Vector2D is a generic type, getAngle() can potentially return an object of any class type
    public String toString() {
        return distance + " " + angle.toString();
    }

}

