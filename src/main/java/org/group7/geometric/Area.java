package org.group7.geometric;


public class Area {

    private Point topLeft;
    private Point bottomRight;


    public Area(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        if ((topLeft.getX() <= bottomRight.getX()) && (topLeft.getY() > bottomRight.getY())) {
            double temp = topLeft.y;
            topLeft.y = bottomRight.y;
            bottomRight.y = temp;
        } else if ((topLeft.getX() > bottomRight.getX()) && (topLeft.getY() <= bottomRight.getY())) {
            double temp = topLeft.x;
            topLeft.x = bottomRight.x;
            bottomRight.x = temp;
        } else if ((topLeft.getX() > bottomRight.getX()) && (topLeft.getY() > bottomRight.getY())) {
            this.topLeft = bottomRight;
            this.bottomRight = topLeft;
        }

    }

    public Point getPoint1() {
        return topLeft;
    }

    public Point getPoint2() {
        return bottomRight;
    }

    public double getWidth() {
        return bottomRight.x-topLeft.x;
    }

    public double getHeight() {
        return bottomRight.y-topLeft.y;
    }

    /*
            Check whether a point is in the target area
        */
    public boolean isHit(double x, double y) {
        return y > topLeft.getY() && y< bottomRight.getY() && x> topLeft.getX() && x< bottomRight.getX();
    }

    public boolean isHit(Point point) {
        return point.y > topLeft.getY() && point.y< bottomRight.getY() && point.x> topLeft.getX() && point.x< bottomRight.getX();
    }

    /*
        Check whether something with a radius is in the target area
        STILL TO BE IMPLEMENTED
    */
    public boolean isHit(double x, double y, double radius) {
        return false;
    }
}
