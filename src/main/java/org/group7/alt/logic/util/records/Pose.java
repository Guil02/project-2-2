package org.group7.alt.logic.util.records;

import javafx.geometry.Point2D;
import org.group7.alt.enums.Cardinal;

import java.awt.*;

//idkkkk
public record Pose(Point2D point, Cardinal orientation) {

    public Pose {
        point = new Point2D( (int) point.getX(), (int) point.getY());
    }

    public Pose(Point p, Cardinal o) {
        this(new Point2D(p.x, p.y), o);
    }

    public Pose(XY p, Cardinal o) {
        this(new Point2D(p.x(), p.y()), o);
    }

    public Pose rotate(Cardinal orientation) {
        return new Pose(new Point2D(point.getX(), point().getY()), orientation);
    }

    public Point getAwtPoint() {
        return new Point((int) point.getX(), (int) point.getY());
    }

    public XY getXY() {
        return new XY((int) point.getX(), (int) point.getY());
    }

    public int getX() {
        return (int )point.getX();
    }

    public int getY() {
        return (int) point.getY();
    }

}
