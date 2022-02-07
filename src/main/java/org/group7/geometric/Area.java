package org.group7.geometric;


public class Area {

    private Point point1;
    private Point point2;


    public Area(Point point1, Point point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public Point getPoint1() {
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }

    /*
        Check whether a point is in the target area
    */
    public boolean isHit(double x, double y) {
        return (y > point1.getX()) & (y < point1.getY()) & (x > point2.getX()) & (x < point2.getY());
    }

    /*
        Check whether something with a radius is in the target area
        STILL TO BE IMPLEMENTED
    */
    public boolean isHit(double x, double y, double radius) {
        return false;
    }
}
