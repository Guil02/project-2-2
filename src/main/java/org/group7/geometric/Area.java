package org.group7.geometric;


public class Area {

    private Point point1;
    private Point point2;


    public Area(Point point1, Point point2) {
        this.point1 = point1;
        this.point2 = point2;
        if ((point1.getX() <= point2.getX()) && (point1.getY() > point2.getY())) {
            double temp = point1.y;
            point1.y = point2.y;
            point2.y = temp;
        } else if ((point1.getX() > point2.getX()) && (point1.getY() <= point2.getY())) {
            double temp = point1.x;
            point1.x = point2.x;
            point2.x = temp;
        } else if ((point1.getX() > point2.getX()) && (point1.getY() > point2.getY())) {
            this.point1 = point2;
            this.point2 = point1;
        }

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
