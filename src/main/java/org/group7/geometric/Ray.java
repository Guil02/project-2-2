package org.group7.geometric;

import org.group7.model.component.Component;
import org.group7.model.component.playerComponents.PlayerComponent;
import org.group7.model.component.staticComponents.Wall;

import java.util.ArrayList;
import java.util.HashMap;

public class Ray {

    private Point position;
    private Vector2D direction;
    private double viewField;

    public Ray(PlayerComponent agent) {
        this.position = agent.getCoordinates();
        this.direction = new Vector2D(agent.getAngle());
        this.viewField = agent.getViewField();
    }

    //TODO: change input to ArrayList of Components
    public HashMap<Component, ArrayList<Double>> isHit(ArrayList<Area> allAreas) {
        HashMap<Component, ArrayList<Double>> componentsInterceptedDistance = new HashMap<>();

        for (Area oneAreaComposed : allAreas){
            boolean agentSawSomething = false;
            double shortestDistance = this.viewField;
            ArrayList<Area> areasDecomposed = decomposeArea(oneAreaComposed);

            for (Area area : areasDecomposed) {
                double x1 = area.getTopLeft().getX();
                double y1 = area.getTopLeft().getY();
                double x2 = area.getBottomRight().getX();
                double y2 = area.getBottomRight().getY();
                //ray coordinates
                double x3 = this.position.getX(); //ray position
                double y3 = this.position.getY(); //ray position
                double x4 = this.position.getX() + this.direction.getX(); //ray endpoint
                double y4 = this.position.getY() + this.direction.getY(); //ray endpoint

                double denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
                //if denominator is 0 area and ray are perpendicular ie. will never intersect
                if (denominator == 0) {
                    break;
                }
                double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / denominator;
                double u = ((x1 - x3) * (y1 - y2) - (y1 - y3) * (x1 - x2)) / denominator;

                if (t > 0 && t < 1 && u > 0) {
                    double interX = x1 + t * (x2 - x1);
                    double interY = y1 + t * (y2 - y1);
                    Vector2D interPoint = new Vector2D(interX, interY);
                    Vector2D agentPosition = new Vector2D(x3, y3);
                    double distance = interPoint.distance(agentPosition);
                    if (distance<=viewField){
                        shortestDistance=distance;
                        agentSawSomething = true;
                    }
                }
                if (agentSawSomething){
                    //TODO: Insert component and distance to componentInterceptedDistance
                    ArrayList<Double> temp = new ArrayList<>();
                    temp.add(shortestDistance);
                    componentsInterceptedDistance.put(new Wall(new Point(1,1), new Point(2,2)),temp);
                }
            }
        }
        return componentsInterceptedDistance;
    }

    public ArrayList<Area> decomposeArea(Area area){
        ArrayList<Area> decomposedAreas = new ArrayList<>();
        //works
        decomposedAreas.add(new Area(area.getTopLeft(), new Point(area.getTopLeft().getX(), area.getBottomRight().getY())));

        decomposedAreas.add(new Area(area.getTopLeft(), new Point(area.getBottomRight().getX(), area.getTopLeft().getY())));
        decomposedAreas.add(new Area(area.getBottomRight(), new Point(area.getBottomRight().getX(), area.getTopLeft().getY())));

        //works
        decomposedAreas.add(new Area(area.getBottomRight(), new Point(area.getTopLeft().getX(), area.getBottomRight().getY())));

        return decomposedAreas;
    }

    // TODO:
}
