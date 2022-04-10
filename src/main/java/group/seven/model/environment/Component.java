package group.seven.model.environment;

import group.seven.enums.TileType;

import java.awt.*;

public record Component(Rectangle area, TileType type) {}
