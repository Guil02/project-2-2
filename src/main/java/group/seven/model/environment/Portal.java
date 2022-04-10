package group.seven.model.environment;

import group.seven.enums.Cardinal;
import group.seven.logic.geometric.XY;
import java.awt.Rectangle;

public record Portal(Rectangle area, XY exit, Cardinal exitDirection){}