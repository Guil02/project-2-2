package group.seven.model.agents;

import group.seven.enums.Action;
import group.seven.enums.Cardinal;
import group.seven.enums.TileType;
import group.seven.model.environment.Tile;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Intruder implements Entity {

    protected Cardinal direction;
    private final IntegerProperty xProp = new SimpleIntegerProperty();
    private final IntegerProperty yProp = new SimpleIntegerProperty();

    private TileType agentType;

    @Override
    public Move calculateMove() {
        return new Move(Action.STEP, 3, direction, new Tile());
    }

    @Override
    public void updateVision() {

    }

    @Override
    public int getX() {
        return xProp.get();
    }

    @Override
    public int getY() {
        return yProp.get();
    }

    @Override
    public Cardinal getDirection() {
        return direction;
    }

    @Override
    public TileType getType() {
        return agentType;
    }
}
