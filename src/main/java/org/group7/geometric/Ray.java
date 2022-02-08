package org.group7.geometric;

public class Ray {

    private Point position;
    private Vector2D direction;

    public Ray(Point position, Vector2D direction) {
        this.position = position;
        this.direction = direction;
    }

    public boolean isHit(Area area) {
        //intersection area
        double x1 = area.getPoint1().getX();
        double y1 = area.getPoint1().getY();
        double x2 = area.getPoint2().getX();
        double y2 = area.getPoint2().getY();
        //ray coordinates
        double x3 = this.position.getX();
        double y3 = this.position.getY();
        double x4 = this.position.getX() + this.direction.getX();
        double y4 = this.position.getY() + this.direction.getY();

        double denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        //if denominator is 0 area and ray are perpendicular ie. will never intersect
        if (denominator == 0) {
            return false;
        }
        double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / denominator;
        double u = ((x1 - x3) * (y1 - y2) - (y1 - y3) * (x1 - x2)) / denominator;
        // ASK MISCHA: THIS IS NOT THE SAME AS WIKI OR VIDEO, in video he adds - but not on wiki?
        u = - ((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / denominator;

        if (t > 0 && t < 1 && u > 0) {

            //calculate intersection
            return true;
        }
        return false;
    }
}
