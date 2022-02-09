package org.group7.geometric;

import org.group7.model.component.Component;
import org.group7.model.component.playerComponents.PlayerComponent;

import java.util.ArrayList;
import java.util.HashMap;


public class Ray {

    private Point position;
    private Vector2D direction;
    private double viewField;
    private double viewFieldAngle;
    private HashMap<Integer, ArrayList<DistanceAngleTuple>> hashMapComponentDistanceAngle = new HashMap<>();

    public Ray(PlayerComponent agent) {
        this.position = agent.getCoordinates();
        this.direction = new Vector2D(agent.getAngle());
        this.viewField = agent.getViewField();
        this.viewFieldAngle = agent.getViewFieldAngle();
    }

    public HashMap<Integer, ArrayList<DistanceAngleTuple>> getVisualField(ArrayList<Component> allAreas) {
        //TODO: make transformation from 20 degrees to start and end of visual field
        isHit(allAreas, direction);
        isHit(allAreas, direction);

        //For printing purposes
        for (Integer name: hashMapComponentDistanceAngle.keySet()) {
            String key = name.toString();
            String value = hashMapComponentDistanceAngle.get(name).toString();
            System.out.println(key + " " + value);
        }

        return hashMapComponentDistanceAngle;
    }


    public void isHit(ArrayList<Component> allAreas, Vector2D direction) {

        for (Component oneComponentComposed : allAreas){
            Area oneAreaComposed = oneComponentComposed.getArea();
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
                    int componentId = oneComponentComposed.getComponentEnum().getId();
                    DistanceAngleTuple seenObjectInfo = new DistanceAngleTuple(shortestDistance,direction);

                    ArrayList<DistanceAngleTuple> items =  hashMapComponentDistanceAngle.get(componentId);
                    if (items == null) {
                        items = new ArrayList<DistanceAngleTuple>();
                        items.add(seenObjectInfo);
                        hashMapComponentDistanceAngle.put(componentId,items);
                    } else {
                        items.add(seenObjectInfo);
                        hashMapComponentDistanceAngle.put(componentId,items);
                    }

                }
            }
        }
    }

    public ArrayList<Area> decomposeArea(Area area){
        ArrayList<Area> decomposedAreas = new ArrayList<>();
        decomposedAreas.add(new Area(area.getTopLeft(), new Point(area.getTopLeft().getX(), area.getBottomRight().getY())));
        decomposedAreas.add(new Area(area.getTopLeft(), new Point(area.getBottomRight().getX(), area.getTopLeft().getY())));
        decomposedAreas.add(new Area(area.getBottomRight(), new Point(area.getBottomRight().getX(), area.getTopLeft().getY())));
        decomposedAreas.add(new Area(area.getBottomRight(), new Point(area.getTopLeft().getX(), area.getBottomRight().getY())));

        return decomposedAreas;
    }

}
