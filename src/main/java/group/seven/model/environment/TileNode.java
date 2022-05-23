package group.seven.model.environment;

import group.seven.enums.MarkerType;
import group.seven.enums.TileType;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Agent;

import java.util.ArrayList;
import java.util.List;

import static group.seven.enums.TileType.PORTAL;

public class TileNode {

    //global coordinates
    final int x, y;
    XY xy;
    //Or Point2D?

    TileType type;
    Agent agent;
    TileType agentType = null;
    List<Marker> markers;
    Adjacent<TileNode> adjacent;
    Pheromone pheromone;

    public TileNode(Tile tile, Agent a) {
        x = tile.getX();
        y = tile.getY();
        xy = tile.getXY();
        agent = a;
        pheromone = tile.pheromone;
        for (Agent agent : Scenario.TILE_MAP.agents) {
            //if(agent!=null && agent.x==tile.getX()&& agent.y==tile.getY()){ //theirs
            if (agent != null && agent.getXY().equals(xy)) {//mine
                agentType = agent.agentType;
                break;
            }
        }
        updateAdjacent();

        markers = new ArrayList<>();
        for (Marker marker : Scenario.TILE_MAP.markers) {
            if (marker.getXY().equals(xy)) {
                markers.add(marker);
            }
        }
        type = tile.getType();
    }

    //updates one node
    public void update() {
        agentType = null;
        markers.clear();
//        for (Agent agentEntity : Scenario.TILE_MAP.agents) { //theirs
//            if (agentEntity.x == x && agentEntity.y == y) {
//                this.agentType = agentEntity.agentType;
//                break;
//            }
//        }
        for (Agent agent : Scenario.TILE_MAP.agents) { //mine
            if (agent.getXY().equals(xy)) {
                this.agentType = agent.agentType;
                break;
            }
        }

        for (Marker marker : Scenario.TILE_MAP.markers) {
            if (marker.getXY().equals(xy)) {
                markers.add(marker);
            }
        }

        updateAdjacent();

    }

    /**
     * This methods gets the adjacent positions and updates the internal map of the agent
     */
    public void updateAdjacent() {
        //commented out is agent local
//        TileNode north = agent.getMapPosition(agent.x, agent.y - 1);
//        TileNode east = agent.getMapPosition(agent.x + 1, agent.y);
//        TileNode south = agent.getMapPosition(agent.x, agent.y + 1);
//        TileNode west = agent.getMapPosition(agent.x - 1, agent.y);

        //now in agent global
        XY agentGlobal = agent.getXY(); // calculate coordinate once
        TileNode north = agent.getMapPosition(agentGlobal.x(), agentGlobal.y() - 1);
        TileNode east = agent.getMapPosition(agentGlobal.x() + 1, agentGlobal.y());
        TileNode south = agent.getMapPosition(agentGlobal.x(), agentGlobal.y() + 1);
        TileNode west = agent.getMapPosition(agentGlobal.x() - 1, agentGlobal.y());

        TileNode target = null;
        if (type == PORTAL) {
            int xTar = Scenario.TILE_MAP.getTile(x, y).adjacent.targetLocation().getX();
            int yTar = Scenario.TILE_MAP.getTile(x, y).adjacent.targetLocation().getY();
            target = agent.getMapPosition(xTar, yTar); //TODO should this be local or global?
        }

        adjacent = new Adjacent<>(north, east, south, west, target);
    }

    public double getPheromoneStrength() {
        return pheromone.getStrength();
    }

    public Adjacent<TileNode> getAdjacent() {
        return adjacent;
    }

    public Pheromone getPheromone() {
        return pheromone;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TileType getType() {
        return type;
    }

    public MarkerType getExploreType() {
        return Scenario.TILE_MAP.getTile(x, y).getExploreType();
    }

    public void setExploreType(MarkerType m) {
        Scenario.TILE_MAP.getTile(x, y).setExploreType(m);
    }
}
