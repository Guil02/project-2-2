package group.seven.enums;

/**
 * The enum Game mode.
 * Defines winning conditions
 */
public enum GameMode {
    EXPLORATION, //0
    SINGLE_INTRUDER_CAUGHT, // 1
    ALL_INTRUDERS_CAUGHT, // 2
    ONE_INTRUDER_AT_TARGET, // 3
    ALL_INTRUDER_AT_TARGET; // 4

    /**
     * Gets GameMode enum.
     *
     * @param name the winning condition
     * @return the enum associated with that GameModes winning condition
     */
    public static GameMode getEnum(String name) {
        return switch (name) {
            case "Single Intruder Caught" -> SINGLE_INTRUDER_CAUGHT;
            case "All intruders Caught" -> ALL_INTRUDERS_CAUGHT;
            case "One Intruder at Target" -> ONE_INTRUDER_AT_TARGET;
            case "All Intruders at Target" -> ALL_INTRUDER_AT_TARGET;
            default -> EXPLORATION;
        };
    }

}
