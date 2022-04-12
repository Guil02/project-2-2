package group.seven.enums;

public enum AlgorithmType {
    A_STAR,
    WALL_FOLLOWING,
    FLOOD_FILL,
    FRONTIER,
    RANDOM;

    public static AlgorithmType getEnum(String name) {
        return switch (name) {
            case "Random" -> RANDOM;
            case "Wall Following" -> WALL_FOLLOWING;
            case "Frontier" -> FRONTIER;
            case "Flood Fill" -> FLOOD_FILL;
            default -> A_STAR;
        };
    }
}
