package group.seven.enums;

public enum AlgorithmType {
    A_STAR,
    ANT,
    BRICK_AND_MORTAR,
    RANDOM,
    EVAW,
    ANT_PURSUIT;

    public static AlgorithmType getEnum(String name) {
        return switch (name) {
            case "A*" -> A_STAR;
            case "Brick And Mortar" -> BRICK_AND_MORTAR;
            case "Ant Pursuit" -> ANT_PURSUIT;
            default -> RANDOM;
        };
    }
}
