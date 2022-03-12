package org.group7.alt.enums;

import javafx.scene.paint.Color;

import java.util.EnumMap;

import static javafx.scene.paint.Color.TAN;

public enum Component {
    EMPTY, WALL, SHADED, GUARD_SPAWN, INTRUDER_SPAWN, TELEPORTER,
    PORTO,      //Teleporter destinination
    TARGET,     //its what the intruders crave
    TEXTURED,   //no idea, but potentially terrain with non-default properties

    AGENT,      //an agent, could be guard or intruder, !not sure if needed
    EXPLORER,   //for gamemode 0: guard agents = exploration agents, !not sure if needed
    GUARD,
    INTRUDER,

    ORIGIN,     //tile denoted as coordinate frame origin, !not sure if needed
    UNKOWN;    //component unknown by agent

    private static final EnumMap<Component, Color> colorTexture;

    public Color getColor() {
        return colorTexture.getOrDefault(this, TAN);
    }

    public boolean traversable() {
        return switch (this) {
            case WALL, AGENT, EXPLORER, GUARD, INTRUDER, UNKOWN -> false;
            default -> true;
        };
    }

    static {
        colorTexture = new EnumMap<>(Component.class);
        colorTexture.put(EMPTY, Color.WHITE);
        colorTexture.put(WALL, Color.BLACK);
        colorTexture.put(SHADED, Color.GRAY);
        colorTexture.put(GUARD_SPAWN, Color.DEEPSKYBLUE);
        colorTexture.put(INTRUDER_SPAWN, Color.CORAL);
        colorTexture.put(TELEPORTER, Color.PURPLE);
        colorTexture.put(PORTO, Color.MEDIUMPURPLE);
        colorTexture.put(TARGET, Color.GREEN);
        colorTexture.put(EXPLORER, Color.GOLD);
        colorTexture.put(GUARD, Color.CORNFLOWERBLUE);
        colorTexture.put(INTRUDER, Color.TOMATO);
        colorTexture.put(UNKOWN, Color.DIMGRAY);
    }
}
