package org.group7.model.component;

import org.group7.geometric.Area;
import org.group7.geometric.Point;

import java.util.Comparator;

/**
 * This class is meant to be the super class of all the components that will exist in the project.
 * They contain a reference to their own object in the componentEnum so that the gui can easily access the texture path.
 */
public abstract class Component {
    private ComponentEnum component;
    private Area area;

    public Component(Point point1, Point point2){
        this.area = new Area(point1, point2);
    }

    public void setComponent(ComponentEnum component) {
        this.component = component;
    }
    public ComponentEnum getComponentEnum() {return component;}

    public Area getArea() {
        return area;
    }

    /**
     * this method returns to the user a string with the path to the texture.
     * @return the path to the texture of the specified component.
     */
    public String getTexture(){
        return component.getTexture();
    }

    public static class ComponentTypeSort implements Comparator<Component> {

        @Override
        public int compare(Component o1, Component o2) {
            return 0;
        }
    }
}
