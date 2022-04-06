package org.group7.alt.logic.util.records;

import javafx.geometry.Point2D;
import org.group7.alt.enums.Cell;

public record ObservedTile(Cell cell, int x, int y) {

    public double distance(Point2D point) {
        return point.distance(x, y);
    }

    public boolean obstacle() {
        return !cell.traversable();
    }
}


