package group.seven.enums;

/**
 * The enum Algorithm type.
 */
public enum AlgorithmType {
    /**
     * A star algorithm type.
     */
    A_STAR,
    /**
     * Ant algorithm type.
     */
    ANT,
    /**
     * Brick and mortar algorithm type.
     */
    BRICK_AND_MORTAR,
    /**
     * Random algorithm type.
     */
    RANDOM,
    /**
     * Evaw algorithm type.
     */
    EVAW,
    /**
     * Ant pursuit algorithm type.
     */
    ANT_PURSUIT,
    /**
     * A star alt algorithm type.
     */
    A_STAR_ALT;

    /**
     * Gets enum.
     *
     * @param name the name
     * @return the enum
     */
    public static AlgorithmType getEnum(String name) {
        return switch (name) {
            case "A*" -> A_STAR;
            case "A* Alt" -> A_STAR_ALT;
            case "Brick And Mortar" -> BRICK_AND_MORTAR;
            case "Ant Pursuit" -> ANT_PURSUIT;
            default -> RANDOM;
        };
    }
}
