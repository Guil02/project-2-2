package org.group7.enums;

import javafx.scene.paint.Color;

import java.util.EnumMap;

import static javafx.scene.paint.Color.TAN;

/**
 * This ENUM contains all the possible different components with a path to their texture file.
 */
public enum ComponentEnum {
    // all the components with the string path.
    EMPTY_SPACE(0,""),
    GUARD(1,""),
    INTRUDER(2,""),
    WALL(3,""),
    TELEPORTER(4,""),
    SHADED_AREA(5,""),
    TARGET_AREA(6,""),
    INTRUDER_SPAWN_AREA(7,""),
    GUARD_SPAWN_AREA(8,"");

    // a string with the path to the texture.
    private final String texture;
    // the id of the component
    private final int id;

    // the internal enum constructor and the getter for the texture and the id.
    ComponentEnum(int id, String texture){this.id = id; this.texture = texture;}
    public String getTexture(){return texture;}
    public int getId() {
        return id;
    }

    public Color getColor() {
        return colorTexture.getOrDefault(this, TAN);
    }

    public boolean isObstacle() {
        return switch (this) {
            case WALL, GUARD, INTRUDER -> true;
            default -> false;
        };
    }

    private static final EnumMap<ComponentEnum, Color> colorTexture;

    static {
        colorTexture = new EnumMap<>(ComponentEnum.class);
        colorTexture.put(EMPTY_SPACE, Color.WHITE);
        colorTexture.put(WALL, Color.BLACK);
        colorTexture.put(SHADED_AREA, Color.GRAY);
        colorTexture.put(GUARD_SPAWN_AREA, Color.DEEPSKYBLUE);
        colorTexture.put(INTRUDER_SPAWN_AREA, Color.CORAL);
        colorTexture.put(TELEPORTER, Color.PURPLE);
        //colorTexture.put(PORTO, Color.MEDIUMPURPLE);
        colorTexture.put(TARGET_AREA, Color.GREEN);
        //colorTexture.put(EXPLORER, Color.GOLD);
        colorTexture.put(GUARD, Color.CORNFLOWERBLUE);
        colorTexture.put(INTRUDER, Color.TOMATO);
    }

}
