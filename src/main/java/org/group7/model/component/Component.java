package org.group7.model.component;

import org.group7.geometric.Area;
import org.group7.geometric.Point;
import org.group7.model.Scenario;

import java.util.Comparator;

/**
 * This class is meant to be the super class of all the components that will exist in the project.
 * They contain a reference to their own object in the componentEnum so that the gui can easily access the texture path.
 */
public abstract class Component {
    private ComponentEnum component;
    private Area area;
    private Scenario scenario;

    public Component(Point point1, Point point2, Scenario scenario){
        this.area = new Area(point1, point2);
        this.scenario = scenario;
    }

    public void setComponent(ComponentEnum component) {
        this.component = component;
    }

    public ComponentEnum getComponentEnum() {return component;}

    public Area getArea() {
        return area;
    }

    public void setArea(Area area){
        this.area = area;
    }

    /**
     * this method returns to the user a string with the path to the texture.
     * @return the path to the texture of the specified component.
     */
    public String getTexture(){
        return component.getTexture();
    }

}
