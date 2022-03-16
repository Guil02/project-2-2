package org.group7.alt.model.map;

import org.group7.alt.enums.Cardinal;
import org.group7.alt.enums.Cell;
import org.group7.alt.model.ai.Pose;

import java.awt.*;

public abstract class MapComponent {

    protected Cell type;
    protected Cell previousType;

    protected Point position = new Point();
    protected Cardinal orientation;


    public MapComponent(Cell componentType) {
        type = componentType;
        previousType = type;
        orientation = Cardinal.NORTH; //TODO: put this in seperate constructor, or isolate into dynamic map components
    }

    public MapComponent(Cell componentType, Point p) {
        this(componentType);
        position = p;
    }

    public MapComponent(Cell componentType, int x, int y) {
        this(componentType, new Point(x, y));
    }

    public Cell getType() {
        return type;
    }

    public Cell getPreviousType() {
        return previousType;
    }

    public Point getPosition() {
        return position;
    }

    public Cardinal getOrientation() {
        return orientation;
    }

//    public void move(Point p) {
//        position = p;
//    }

    public Pose rotate(Cardinal direction) {
        orientation = direction;
        return new Pose(position, orientation);
    }

    public void setType(Cell type) {
        previousType = this.type;
        this.type = type;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setOrientation(Cardinal orientation) {
        this.orientation = orientation;
    }



}
