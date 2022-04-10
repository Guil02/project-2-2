package group.seven.model.environment;

import group.seven.enums.TileType;
import group.seven.logic.geometric.XY;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;

import static group.seven.enums.TileType.EMPTY;

public class Tile {
    //Type
    TileType type;
    XY xy; //prolly better to just store them as ints, idk;

    //Exploration Status, also not sure about this
    //boolean explored for guard and agent for calculating coverage
    ObservableBooleanValue exploredGuard = new SimpleBooleanProperty(false);
    ObservableBooleanValue exploredAgent = new SimpleBooleanProperty(false);

    //Graph
    Tile[] adjacent;

    protected Tile() {
        type = EMPTY;
    }

    public Tile(int x, int y) {
        this();
        xy = new XY(x, y);
    }

    public Tile(TileType type, int x, int y) {
        this(x, y);
        this.type = type;
    }

    public int getX() {
        return xy.x();
    }
    public int getY() {
        return xy.y();
    }

    //Actionable
    // void doAction() {}
    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public void setAdjacent(Tile[] adjacent) {
        this.adjacent = adjacent;
    }

    public boolean getExploredGuard() {
        return exploredGuard.get();
    }
    public ObservableBooleanValue exploredGuardProperty() {
        return exploredGuard;
    }
    public boolean getExploredAgent() {
        return exploredAgent.get();
    }
    public ObservableBooleanValue exploredAgentProperty() {
        return exploredAgent;
    }
}
