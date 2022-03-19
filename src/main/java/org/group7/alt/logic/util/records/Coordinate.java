package org.group7.alt.logic.util.records;


import javafx.geometry.Point2D;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Translate;

import java.util.Arrays;

public record Coordinate(int x, int y, Frame frame){
    public Coordinate {
        Point2D p = frame.frame().transform(x, y);
        x = (int) p.getX();
        y = (int) p.getY();
    }

    public Coordinate toGlobal() throws NonInvertibleTransformException {
        Point2D p = frame.frame().inverseTransform(x, y);
        return new Coordinate((int) p.getX(), (int) p.getY(), frame);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

