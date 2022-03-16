package org.group7.alt.model.map;

import javafx.scene.paint.Color;
import org.group7.alt.enums.Cell;

public class Tile extends MapComponent {

    boolean obstacle;
    Color colorTexture;

    boolean explored = false;

    public Tile(Cell componentType, int x, int y) {
        super(componentType, x, y);
        obstacle = !componentType.traversable();
        colorTexture = componentType.getColor();
    }

    public boolean isObstacle() {
        return obstacle;
    }

    public void setObstacle(boolean obstacle) {
        this.obstacle = obstacle;
    }

    public Color getColorTexture() {
        return colorTexture;
    }

    public void setColorTexture(Color colorTexture) {
        this.colorTexture = colorTexture;
    }

    public boolean isExplored() {
        return explored;
    }

    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    @Override
    public String toString() {
        //return " " + getType().name();
        return "Tile{" +
                "" + type +
                ", position: (" + position.x + ", " + position.y + ")" +
                ", orientation: " + orientation +
//                ", obstacle=" + obstacle +
//                ", colorTexture=" + colorTexture +
                '}';
    }
}
