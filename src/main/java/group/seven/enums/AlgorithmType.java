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
            case "Random" -> RANDOM;
            case "Ant" -> ANT;
            case "Brick and Mortar" -> BRICK_AND_MORTAR;
            case "EVAW" -> EVAW;
            case "ANT_PURSUIT" -> ANT_PURSUIT;
            default -> A_STAR;
        };
    }
}
