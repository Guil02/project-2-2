package group.seven.model.agents;

import group.seven.enums.Cardinal;
import group.seven.enums.TileType;

public interface Entity {
    int getX();
    int getY();
    Cardinal getDirection();
    TileType getType();
    void updateVision();
    Move calculateMove();
}
