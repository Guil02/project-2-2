package group.seven.enums;

/**
 * The enum Game mode.
 */
public enum GameMode {
    /**
     * Exploration game mode.
     */
    EXPLORATION, //0
    /**
     * Single intruder caught game mode.
     */
    SINGLE_INTRUDER_CAUGHT, // 1
    /**
     * All intruders caught game mode.
     */
    ALL_INTRUDERS_CAUGHT, // 2
    /**
     * One intruder at target game mode.
     */
    ONE_INTRUDER_AT_TARGET, // 3
    /**
     * All intruder at target game mode.
     */
    ALL_INTRUDER_AT_TARGET; // 4

    /**
     * Gets enum.
     *
     * @param name the name
     * @return the enum
     */
    public static GameMode getEnum(String name) {
        return switch (name) {
            case "Single Intruder Caught" -> SINGLE_INTRUDER_CAUGHT;
            case "All intruders Caught" -> ALL_INTRUDERS_CAUGHT;
            default -> EXPLORATION;
        };
    }

}
