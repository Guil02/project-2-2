package org.group7.model.component;

import org.group7.enums.ComponentEnum;
import org.group7.gui.geometric.Area;
import org.group7.gui.geometric.Point;
import org.group7.model.Scenario;

/**
 * This class is meant to be the super class of all the components that will exist in the project.
 * They contain a reference to their own object in the componentEnum so that the gui can easily access the texture path.
 */
public abstract class Component {
    private ComponentEnum component;
    private Area area;
    private Scenario scenario;
    //TODO: MELI will figure this out - if needed
    private Point topRightPoint;
    private Point bottomleftPoint;

    public Component(Point point1, Point point2, Scenario scenario){
        this.area = new Area(point1, point2);
        this.scenario = scenario;
        //TODO: Area is defined by topLeft and bottomRight - decide on one common way!
        this.topRightPoint = point1;
        this.bottomleftPoint = point1;
    }

    public void setComponent(ComponentEnum component) {
        this.component = component;
    }

    /**
     * Gets middle from component (so that it can be used in the distance calculation to agent as an approximation).
     * @return
     */
    public Point getMiddlePoint(){
       return new Point((topRightPoint.getX() + bottomleftPoint.getX())/2,(topRightPoint.getY() + bottomleftPoint.getY())/2);
    }

    /**
     * Calculates the euclidean distance from the component to a given agent, based in the position of this.
     * @param position_agent
     * @return distance
     */
    public Double getDistanceToAgent(Point position_agent){
        Point position_component = this.getMiddlePoint();
        double distance = Math.pow(position_agent.getX() - position_component.getX(), 2) + Math.pow(position_agent.getY() - position_component.getY(), 2) ;
        distance = Math.sqrt(distance);
        return distance;
    }

    public ComponentEnum getComponentEnum() {return component;}

    public Area getArea() {
        return area;
    }

    public void setArea(Area area){
        this.area = area;
    }

    public Scenario getScenario() {
        return scenario;
    }

    /**
     * this method returns to the user a string with the path to the texture.
     * @return the path to the texture of the specified component.
     */
    public String getTexture(){
        return component.getTexture();
    }

}
