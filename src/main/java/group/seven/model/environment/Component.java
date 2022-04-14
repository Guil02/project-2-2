package group.seven.model.environment;

import group.seven.enums.TileType;
import group.seven.logic.geometric.Rectangle;

public record Component(Rectangle area, TileType type) {}
//TODO: definitely need to reconsider this