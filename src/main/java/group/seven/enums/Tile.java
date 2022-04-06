package group.seven.enums;

public enum Tile {
    EMPTY,
    WALL,
    SHADED,
    GUARD_SPAWN,
    INTRUDER_SPAWN,
    TELEPORTER,
    EXIT_PORTAL,
    GUARD,
    INTRUDER;

    public boolean isObstacle() {
        return switch (this) {
            case WALL, GUARD, INTRUDER -> true;
            default -> false;
        };
    }
}
