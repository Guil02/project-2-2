package group.seven.enums;

public enum AlgorithmType {
    A_STAR,
    WALL_FOLLOWING,
    FLOOD_FILL,
    FRONTIER,
    ANT,
    BRICK_AND_MORTAR,
    RANDOM;

    public static AlgorithmType getEnum(String name) {
        return switch (name) {
            case "Random" -> RANDOM;
            case "Ant" -> ANT;
            case "Brick and Mortar" -> BRICK_AND_MORTAR;
            case "Wall Following" -> WALL_FOLLOWING;
            case "Frontier" -> FRONTIER;
            case "Flood Fill" -> FLOOD_FILL;
            default -> A_STAR;
        };
    }
}
