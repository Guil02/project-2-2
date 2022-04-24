package group.seven.model.environment;

import group.seven.enums.TileType;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Agent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
import java.util.List;

import static group.seven.enums.TileType.EMPTY;
import static group.seven.model.environment.Scenario.NUM_AGENTS;

public class Tile {
    //Type
    TileType type;
    XY xy; //or Point2D, or int x, y

    boolean exploredByGuard = false;
    boolean exploredByIntruder = false;
    // Lists keep track of which intruder has seen the tile
    List<Boolean> seen;

    //Graph
    Adjacent adjacent;

    public Tile() {
        type = EMPTY;
        seen = new ArrayList<>(NUM_AGENTS);
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

    //public void setAdjacent(Tile[] adjacent) { this.adjacent = adjacent;}
    public void setAdjacent(Tile north, Tile east, Tile south, Tile west, Tile target) {
        this.adjacent = new Adjacent(north,east,south,west,target);
    }

    //Exploration Status, also not sure about this
    //boolean explored for guard and agent for calculating coverage
    private final BooleanProperty exploredGuardProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty exploredIntruderProperty = new SimpleBooleanProperty(false);

    public BooleanProperty exploredGuardProperty() {
        return exploredGuardProperty;
    }
    public BooleanProperty exploredIntruderProperty() {
        return exploredIntruderProperty;
    }
    public boolean getExploredGuard() {
        return exploredGuardProperty.get();
    }
    public boolean getExploredIntruder() {
        return exploredIntruderProperty.get();
    }


    /**
     * //TODO: check if theres a fancier way to store this information or more efficient data structure
     * Sets a tile as explored and adds to the list, either of guards or intruders, the ID of the agent that saw it
     * @param agent
     */
    public void setExplored(Agent agent){
        if (agent.getType() == TileType.GUARD) { //
            exploredByGuard = true;
            exploredGuardProperty.set(true);
        }else if (agent.getType() == TileType.INTRUDER){
            exploredByIntruder = true;
            exploredIntruderProperty.set(true);
        }
        // Because the int is static it just increases and guards and intruders don't share it, therefore we just save it
        seen.set(agent.getID(), true);
    }

}
