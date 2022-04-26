package group.seven.enums;

import javafx.scene.paint.Color;

/**
 * Represents the type of the tile
 */
public enum TileType {
    EMPTY, WALL, SHADED,
    PORTAL, EXIT_PORTAL,
    GUARD_SPAWN, INTRUDER_SPAWN,
    GUARD, INTRUDER,
    UNKNOWN, TARGET;

    public boolean isObstacle() {
        return switch (this) {
            case WALL, GUARD, INTRUDER -> true;
            default -> false;
        };
    }

    //TODO: make these properties of the enums or store in hashmap
    public Color getColor() {
        return switch (this) {
            case WALL-> Color.BLACK;
            case EMPTY -> Color.WHITE;
            case GUARD -> Color.BLUE;
            case INTRUDER -> Color.ORANGE;
            case PORTAL -> Color.PURPLE;
            case EXIT_PORTAL -> Color.VIOLET;
            case GUARD_SPAWN -> Color.AQUA;
            case INTRUDER_SPAWN -> Color.TOMATO;
            case SHADED -> Color.SLATEGREY;
            case UNKNOWN -> Color.DIMGRAY;
            case TARGET -> Color.GREEN;
        };
    }
}
