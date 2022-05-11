package group.seven.model.environment;

public record Adjacent<T>(T north, T east, T south, T west, T targetLocation) {}
