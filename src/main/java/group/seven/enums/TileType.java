package group.seven.enums;

import javafx.scene.paint.Color;

import static javafx.scene.paint.Color.*;

/**
 * Represents the type of the tile
 */
public enum TileType {
    EMPTY(WHITE), WALL(BLACK), SHADED(SLATEGREY),
    PORTAL(Color.rgb(142, 68, 173)),
    EXIT_PORTAL(Color.rgb(175, 79, 154)),
    GUARD_SPAWN(Color.rgb(95, 174, 227)),
    INTRUDER_SPAWN(Color.rgb(230, 142, 34)),
    GUARD(Color.rgb(41, 128, 185, 1.0)),
    INTRUDER(Color.rgb(211, 84, 0)),
    UNKNOWN(DIMGRAY),
    TARGET(Color.rgb(39, 174, 96));

    TileType(Color color) {
        this.color = color;
    }

    /**
     * The Color.
     */
    public final Color color;

    /**
     * Is obstacle boolean.
     *
     * @return the boolean
     */
    public boolean isObstacle() {
        return switch (this) {
            case WALL, GUARD, INTRUDER -> true;
            default -> false;
        };
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }

}
