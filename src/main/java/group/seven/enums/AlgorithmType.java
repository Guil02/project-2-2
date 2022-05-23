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
            case "Brick and Mortar" -> BRICK_AND_MORTAR;
            case "ANT_PURSUIT" -> ANT_PURSUIT;
            default -> RANDOM;
        };
    }
}
