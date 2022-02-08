package org.group7.geometric;


public class Area {

    private Point point1;
    private Point point2;


    public Area(Point point1, Point point2) { //TODO add convention to make sure that point 1 is top left and point 2 is bottom right!
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
        return y > point1.getY() && y<point2.getY() && x>point1.getX() && x<point2.getX();
    }

    public boolean isHit(Point point) {
        return point.y > point1.getY() && point.y<point2.getY() && point.x>point1.getX() && point.x<point2.getX();
    }

    /*
        Check whether something with a radius is in the target area
        STILL TO BE IMPLEMENTED
    */
    public boolean isHit(double x, double y, double radius) {
        return false;
    }
}
