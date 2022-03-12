package org.group7.alt.model.map;

import javafx.scene.paint.Color;
import org.group7.alt.enums.Component;

public class Tile extends MapComponent{

    boolean obstacle;
    Color colorTexture;
    boolean explored = false;

    public Tile(Component componentType, int x, int y) {
        super(componentType, x, y);
        obstacle = !componentType.traversable();
        colorTexture = componentType.getColor();
    }

    @Override
    public String toString() {
        return " " + getType().name();
//        return "Tile{" +
//                "" + type +
//                ", position=" + position +
////                ", orientation=" + orientation +
////                ", obstacle=" + obstacle +
////                ", colorTexture=" + colorTexture +
//                '}';
    }
}
