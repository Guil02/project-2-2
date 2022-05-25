package group.seven.model.environment;

import group.seven.enums.MarkerType;
import group.seven.enums.TileType;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Agent;

import java.util.ArrayList;
import java.util.List;

import static group.seven.enums.MarkerType.UNEXPLORED;
import static group.seven.enums.PheromoneType.SMELL;
import static group.seven.enums.TileType.*;
import static group.seven.model.environment.Scenario.NUM_AGENTS;

public class Tile {
    TileType type;
    XY xy; //or Point2D, or int x, y
    boolean exploredByGuard = false;
    boolean exploredByIntruder = false;
    // Lists keep track of which agent has seen the tile
    List<Boolean> seen;
    //Graph
    public Adjacent<Tile> adjacent;
    public Pheromone pheromone;
    private MarkerType exploreType = UNEXPLORED;
//    public List<Marker> guard_marker;

    public Tile() {
        xy = new XY(0,0);
        pheromone = new Pheromone(xy.x(),xy.y(), SMELL, 0);

        Scenario.TILE_MAP.pheromones.add(pheromone);
        type = EMPTY;
//        guard_marker = new ArrayList<>();
//        for (int i = 0; i < NUM_GUARDS; i++) {
//            guard_marker.add(new Marker(xy.x(),xy.y(),MarkerType.UNEXPLORED,i,Cardinal.NORTH));
//        }
//        Scenario.TILE_MAP.markers.addAll(guard_marker);
        seen = new ArrayList<>(NUM_AGENTS);
        for (int i = 0; i < NUM_AGENTS; i++)
            seen.add(false);
    }
    public List<Boolean> getSeen(){
        return seen;
    }

    public Tile(int x, int y) {
        this();
        xy = new XY(x, y);
        pheromone = new Pheromone(x, y, SMELL, 0);
//        guard_marker.clear();
//        for (int i = 0; i < NUM_GUARDS; i++) {
//            guard_marker.add(new Marker(xy.x(),xy.y(),MarkerType.UNEXPLORED,i,Cardinal.NORTH));
//        }
    }

    public Tile(TileType type, int x, int y) {
        this(x, y);
        this.type = type;
        pheromone = new Pheromone(x, y, SMELL, 0);
    }

    //Actionable
    // void doAction() {}
    public XY getXY(){
        return xy;
    }

    public int getX() {
        return xy.x();
    }

    public int getY() {
        return xy.y();
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    //public void setAdjacent(Tile[] adjacent) { this.adjacent = adjacent;}
    public void setAdjacent(Tile north, Tile east, Tile south, Tile west, Tile target) {
        this.adjacent = new Adjacent<>(north, east, south, west, target);
    }

    public boolean getExploredGuard() {
        return exploredByGuard;
    }

    public boolean getExploredIntruder() {
        return exploredByIntruder;
    }

    public Adjacent<Tile> getAdjacent() {
        return this.adjacent;
    }


    /**
     * //TODO: check if theres a fancier way to store this information or more efficient data structure
     * Sets a tile as explored and adds to the list, either of guards or intruders, the ID of the agent that saw it
     *
     * @param agent
     */
    public void setExplored(Agent agent) {
        if (agent.getType() == GUARD) {
            if (!exploredByGuard) {
                TileMap.GUARD_EXPLORATION++; //prolly need to test this
                agent.updateNumExplored(); //maybe this belongs outside the if?
            }

            exploredByGuard = true;         //exploredGuardProperty.set(true);
        } else if (agent.getType() == INTRUDER) {
            if (!exploredByIntruder) {
                TileMap.INTRUDER_EXPLORATION++; //and this
                agent.updateNumExplored();
            }
            exploredByIntruder = true;      //exploredIntruderProperty.set(true);
        }
        // Because the int is static it just increases and guards and intruders don't share it, therefore we just save it
        seen.set(agent.getID(), true);
    }

    public MarkerType getExploreType() {
        return exploreType;
    }

    public void setExploreType(MarkerType exploreType) {
        this.exploreType = exploreType;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "xy=" + xy +
                '}';
    }

}
