package group.seven.enums;

public enum AlgorithmType {
    A_STAR,
    ANT,
    BRICK_AND_MORTAR,
    RANDOM,
    EVAW;

    public static AlgorithmType getEnum(String name) {
        return switch (name) {
            case "Random" -> RANDOM;
            case "Ant" -> ANT;
            case "Brick and Mortar" -> BRICK_AND_MORTAR;
            case "EVAW" -> EVAW;
            default -> A_STAR;
        };
    }
}
