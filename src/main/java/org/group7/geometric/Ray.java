package org.group7.geometric;

import java.util.Vector;

public class Ray {

    private Point position;
    private double direction;

    public Ray(Point position, Vector2D angle) {
        this.position = position;
        this.direction = direction.getAngle();
    }
    public void changeDirection (Vector2D lookAt){
        this.direction = direction.sub(lookAt);
        this.direction = direction.getNormalized();
    }

    // Return -1,-1 if it doesnt hit anything
    public double isHit(Area area) {
        //intersection area
        double x1 = area.getPoint1().getX();
        double y1 = area.getPoint1().getY();
        double x2 = area.getPoint2().getX();
        double y2 = area.getPoint2().getY();
        //ray coordinates
        double x3 = this.position.getX(); //ray position
        double y3 = this.position.getY(); //ray position
        double x4 = this.position.getX() + this.direction.getX(); //ray endpoint
        double y4 = this.position.getY() + this.direction.getY(); //ray endpoint

        double denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        //if denominator is 0 area and ray are perpendicular ie. will never intersect
        if (denominator == 0) {
            return -1;
        }
        double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / denominator;
        double u = ((x1 - x3) * (y1 - y2) - (y1 - y3) * (x1 - x2)) / denominator;

        if (t > 0 && t < 1 && u > 0) {
            double interX = x1 + t*(x2-x1);
            double interY = y1 + t*(y2-y1);
            Vector2D interPoint = new Vector2D(interX, interY);
            Vector2D agentPosition = new Vector2D(x3,y3);
            double distance = interPoint.distance(agentPosition);
            return distance;
        }
        return -1;
    }

    // TODO:
}
