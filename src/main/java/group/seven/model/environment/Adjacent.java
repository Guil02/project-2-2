package group.seven.model.environment;

import group.seven.enums.Cardinal;

public record Adjacent<T>(T north, T east, T south, T west, T targetLocation) {

    public T getAdjacent(Cardinal c){
        return switch (c){
            case NORTH -> north;
            case SOUTH -> south;
            case EAST -> east;
            case WEST -> west;
            default -> null;
        };
    }
}
