package org.group7.alt.model.map;

import org.group7.alt.enums.Cardinal;
import org.group7.alt.enums.Component;

import java.awt.*;

public abstract class MapComponent {

    protected Component type;
    protected Point position = new Point();
    protected Cardinal orientation;

    public MapComponent(){}

    public MapComponent(Component componentType) {
        type = componentType;
        orientation = Cardinal.NORTH; //TODO: put this in seperate constructor, or isolate into dynamic map components
    }

    public MapComponent(Component componentType, Point p) {
        this(componentType);
        position = p;
    }

    public MapComponent(Component componentType, int x, int y) {
        this(componentType, new Point(x, y));
    }


    public Component getType() {
        return type;
    }

    public Point getPosition() {
        return position;
    }

    public Cardinal getOrientation() {
        return orientation;
    }

    public void move(Point p) {
        position = p;
    }

    public void rotate(Cardinal direction) {
        orientation = direction;
    }


}
