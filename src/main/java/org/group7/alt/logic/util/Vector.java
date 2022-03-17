package org.group7.alt.logic.util;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

import java.awt.Point;


public class Vector extends Point2D {

    public Vector(double x, double y) {
        super(x, y);
        //or convert to int first
    }

    public Vector(int x, int y) {
        this((double) x, y);
    }

    public Vector(Point p) {
        this(p.x, p.y);
    }

    public Vector(Line line) {
        this(line.getEndX(), line.getEndY());
    }

    public Point asPoint() {
        return new Point((int) getX(), (int) getY());
    }

    public Point2D add(Point point) {
        return this.add(point.x, point.y);
    }
}
