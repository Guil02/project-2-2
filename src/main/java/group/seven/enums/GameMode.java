package group.seven.enums;

public enum GameMode {
    EXPLORATION, //0
    SINGLE_INTRUDER_CAUGHT, // 1
    ALL_INTRUDERS_CAUGHT, // 2
    ONE_INTRUDER_AT_TARGET, // 3
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
