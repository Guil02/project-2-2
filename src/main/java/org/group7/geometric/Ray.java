package org.group7.geometric;

import org.group7.model.component.Component;
import org.group7.model.component.playerComponents.PlayerComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//inspired by //https://github.com/CodingTrain/website/tree/main/CodingChallenges/CC_145_Ray_Casting/Processing
//inspired by https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection
public class Ray {

    private Point position;
    private Vector2D direction;
    private double viewFieldLength;
    private double viewFieldAngle;
    private HashMap<Integer, ArrayList<DistanceAngleTuple<Double, Vector2D>>> hashMapComponentDistanceAngle = new HashMap<>();
    private PlayerComponent agent;
    private List<Point> interSectionPoints = new ArrayList<Point>() ;

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
        //hashMapComponentDistanceAngle = new HashMap<>();
        hashMapComponentDistanceAngle.clear();
        interSectionPoints.clear();
    }

    public HashMap<Integer, ArrayList<DistanceAngleTuple<Double, Vector2D>>> getVisualField(ArrayList<Component> allAreas) {
        refresh();
        //calculates the start and end angles of the area to look for
        Vector2D startAngle = direction.getRotatedBy(viewFieldAngle/2);
        Vector2D endAngle = direction.getRotatedBy(-(viewFieldAngle/2));
        Vector2D currentAngle = startAngle;
        double previous = currentAngle.getAngle();

        while(currentAngle.getAngle() >= endAngle.getAngle() && previous >= currentAngle.getAngle()) {
            isHit(allAreas, currentAngle);
            //change for each ray - experiment with this value
            previous = currentAngle.getAngle();
            currentAngle = currentAngle.getRotatedBy(Math.toRadians(-5));
        }

        //For printing purposes
        for (Integer name: hashMapComponentDistanceAngle.keySet()) {
            String key = name.toString();
            String value = hashMapComponentDistanceAngle.get(name).toString();
            System.out.println(key + " " + value);
         }

        return hashMapComponentDistanceAngle;
    }

    public void isHit(ArrayList<Component> allAreas, Vector2D direction) {
        //TODO: sort list so that closest element to user is first (explain to mischa the problem)
        boolean objectSeen= false;

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

                double interSectionX = 0;
                double interSectionY = 0;

                double denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
                //if denominator is 0 area and ray are perpendicular ie. will never intersect
                if (denominator == 0) {
                    break;
                }
                double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / denominator;
                double u = ((x1 - x3) * (y1 - y2) - (y1 - y3) * (x1 - x2)) / denominator;

                if (t > 0 && t < 1 && u > 0) { //there exists an interception with a component
                    interSectionX = x1 + t * (x2 - x1);
                    interSectionY = y1 + t * (y2 - y1);
                    Vector2D interPoint = new Vector2D(interSectionX, interSectionY);
                    Vector2D agentPosition = new Vector2D(x3, y3);
                    double distance = interPoint.distance(agentPosition);
                    if (distance<= shortestDistance){
                        if (oneComponentComposed.getComponentEnum().getId() ==3){ // 3= wall
                            shortestDistance=distance;
                            int componentId = oneComponentComposed.getComponentEnum().getId();
                            addToHashMap(distance, componentId, direction);
                        } else {
                            // if agent sees something else (not a wall) we dont store the shortest distance,
                            // as it could see other things behind it
                            int  componentId = oneComponentComposed.getComponentEnum().getId();
                            addToHashMap(distance, componentId, direction);
                         }
                        objectSeen= true;
                    }
                    //save the intersection point
                    interSectionPoints.add(new Point(interSectionX,interSectionY));
                }
            }

        }
        if (!objectSeen){ //the agent sees nothing with the ray
            int componentId=0;
            addToHashMap(this.viewFieldLength, componentId, direction);
        }
    }

    public void addToHashMap(double shortestDistance, int componentId, Vector2D direction){
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
    }

    public ArrayList<Area> decomposeArea(Area area){
        ArrayList<Area> decomposedAreas = new ArrayList<>();
        decomposedAreas.add(new Area(area.getTopLeft(), new Point(area.getTopLeft().getX(), area.getBottomRight().getY())));
        decomposedAreas.add(new Area(area.getTopLeft(), new Point(area.getBottomRight().getX(), area.getTopLeft().getY())));
        decomposedAreas.add(new Area(area.getBottomRight(), new Point(area.getBottomRight().getX(), area.getTopLeft().getY())));
        decomposedAreas.add(new Area(area.getBottomRight(), new Point(area.getTopLeft().getX(), area.getBottomRight().getY())));

        return decomposedAreas;
    }

    public List<Point> getInterSectionPoints() {return interSectionPoints;}

}
