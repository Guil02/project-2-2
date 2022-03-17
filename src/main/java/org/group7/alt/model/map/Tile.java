package org.group7.alt.model.map;

import javafx.scene.paint.Color;
import org.group7.alt.enums.Cell;

public class Tile {
    Cell type;
    boolean explored;
    //Marker marker;

    public Tile() {
        type = Cell.EMPTY;
        explored = false;
    }

    public Tile(Cell t) {
        this();
        type = t;
    }

    public boolean isExplored() {
        return explored;
    }

    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    public boolean isObstacle() {
        return !type.traversable();
    }

    public Color getColorTexture() {
        return type.getColor();
    }

    public void updateTile(Cell type, boolean explored) {
        this.type = type;
        this.explored = explored;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "type=" + type +
                ", explored=" + explored +
                '}';
    }
}
