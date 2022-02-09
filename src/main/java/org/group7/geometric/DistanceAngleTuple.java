package org.group7.geometric;

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

