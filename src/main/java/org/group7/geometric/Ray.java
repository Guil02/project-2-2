package org.group7.geometric;

import org.group7.model.component.Component;
import org.group7.model.component.playerComponents.PlayerComponent;

import java.util.ArrayList;
import java.util.HashMap;

//inspired by //https://github.com/CodingTrain/website/tree/main/CodingChallenges/CC_145_Ray_Casting/Processing
//inspired by https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection
public class Ray {

    private Point position;
    private Vector2D direction;
    private double viewFieldLength;
    private double viewFieldAngle;
    private HashMap<Integer, ArrayList<DistanceAngleTuple<Double, Vector2D>>> hashMapComponentDistanceAngle = new HashMap<>();
    private PlayerComponent agent;

    public Ray(PlayerComponent agent) {
        this.position = agent.getCoordinates();
        this.direction = new Vector2D(agent.getDirectionAngle());
        this.viewFieldLength = agent.getViewFieldLength();
        this.viewFieldAngle = agent.getViewFieldAngle();
        this.agent = agent;
    }

    public void refresh(){
        this.position = agent.getCoordinates();
        this.direction = new Vector2D(agent.getDirectionAngle());
        this.viewFieldLength = agent.getViewFieldLength();
        this.viewFieldAngle = agent.getViewFieldAngle();
    }

    public HashMap<Integer, ArrayList<DistanceAngleTuple<Double, Vector2D>>> getVisualField(ArrayList<Component> allAreas) {
        refresh();
        //calculates the start and end angles of the area to look for
        Vector2D startAngle = direction.getRotatedBy(viewFieldAngle/2);
        Vector2D endAngle = direction.getRotatedBy(-(viewFieldAngle/2));
        Vector2D currentAngle = startAngle;

        while(currentAngle.getAngle() >= endAngle.getAngle()) {
            isHit(allAreas, currentAngle);
            //change for each ray - experiment with this value
            currentAngle = currentAngle.getRotatedBy(Math.toRadians(-5));
        }

        //For printing purposes
//        for (Integer name: hashMapComponentDistanceAngle.keySet()) {
//            String key = name.toString();
//            String value = hashMapComponentDistanceAngle.get(name).toString();
//            System.out.println(key + " " + value);
//        }

        return hashMapComponentDistanceAngle;
    }


    public void isHit(ArrayList<Component> allAreas, Vector2D direction) {
        //get one component of the area list
        for (Component oneComponentComposed : allAreas){
            Area oneAreaComposed = oneComponentComposed.getArea();
            boolean agentSawSomething = false;
            double shortestDistance = this.viewFieldLength;
            ArrayList<Area> areasDecomposed = decomposeArea(oneAreaComposed);
            //decompose one area object into four lines and check intersection with ray
            // and keep track of shortest distance
            for (Area area : areasDecomposed) {
                double x1 = area.getTopLeft().getX();
                double y1 = area.getTopLeft().getY();
                double x2 = area.getBottomRight().getX();
                double y2 = area.getBottomRight().getY();
                //ray coordinates
                double x3 = this.position.getX(); //ray position
                double y3 = this.position.getY(); //ray position
                double x4 = this.position.getX() + direction.getX(); //ray endpoint
                double y4 = this.position.getY() + direction.getY(); //ray endpoint

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
                    if (distance<= shortestDistance){
                        shortestDistance=distance;
                        agentSawSomething = true;
                    }
                }
                if (agentSawSomething){
                    int componentId = oneComponentComposed.getComponentEnum().getId();
                    DistanceAngleTuple<Double, Vector2D> seenObjectInfo = new DistanceAngleTuple<>(shortestDistance, direction);

                    ArrayList<DistanceAngleTuple<Double, Vector2D>> items =  hashMapComponentDistanceAngle.get(componentId);
                    if (items == null) {
                        items = new ArrayList<>();
                        items.add(seenObjectInfo);
                        hashMapComponentDistanceAngle.put(componentId,items);
                    } else {
                        items.add(seenObjectInfo);
                        hashMapComponentDistanceAngle.put(componentId,items);
                    }
                    agentSawSomething = false;

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
