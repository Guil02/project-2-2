package group.seven.model.environment;

import group.seven.enums.Cardinal;

public record Adjacent<T>(T north, T east, T south, T west, T targetLocation) {
    public T getAdjacent(Cardinal c){
        switch (c){

            case NORTH -> {
                return north;
            }
            case SOUTH -> {
                return south;
            }
            case EAST -> {
                return east;
            }
            case WEST -> {
                return west;
            }
            default -> {
                return null;
            }
        }
    }
}
