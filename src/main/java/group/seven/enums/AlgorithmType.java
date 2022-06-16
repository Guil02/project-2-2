package group.seven.enums;

public enum AlgorithmType {
    A_STAR,
    ANT,
    BRICK_AND_MORTAR,
    RANDOM,
    EVAW,
    ANT_PURSUIT,
    A_STAR_ALT,
    GENETIC_NEURAL_NETWORK;

    public static AlgorithmType getEnum(String name) {
        return switch (name) {
            case "A*" -> A_STAR;
//            case "A*" -> A_STAR_ALT;
            case "Brick And Mortar" -> BRICK_AND_MORTAR;
            case "Ant Pursuit" -> ANT_PURSUIT;
            case "Genetic Neural Network" -> GENETIC_NEURAL_NETWORK;
            default -> RANDOM;
        };
    }
}
