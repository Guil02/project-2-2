package group.seven.model.environment;

import group.seven.enums.Cardinal;
import group.seven.enums.TileType;
import group.seven.logic.geometric.Rectangle;
import group.seven.logic.geometric.XY;

import static group.seven.enums.TileType.PORTAL;

// have this as component where exit and exit direction are null if not a teleporter
public record Component(Rectangle area, TileType type, XY exit, Cardinal exitDirection) {
    public boolean isPortal() {
        return exit != null && type == PORTAL;
    }
}
