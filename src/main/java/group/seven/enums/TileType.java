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
            case GUARD -> Color.rgb(41, 128, 185, 1.0);
            case INTRUDER -> Color.rgb(211, 84, 0);
            case PORTAL -> Color.rgb(142, 68, 173);
            case EXIT_PORTAL -> Color.rgb(175, 79, 154);
            case GUARD_SPAWN -> Color.rgb(95,174,227);
            case INTRUDER_SPAWN -> Color.rgb(230,142,34);
            case SHADED -> Color.SLATEGREY;
            case UNKNOWN -> Color.DIMGRAY;
            case TARGET -> Color.rgb(39, 174, 96);
        };
    }
}
