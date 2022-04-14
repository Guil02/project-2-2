package group.seven.model.environment;

import group.seven.enums.Cardinal;
import group.seven.logic.geometric.Rectangle;
import group.seven.logic.geometric.XY;

public record Portal(Rectangle area, XY exit, Cardinal exitDirection){}