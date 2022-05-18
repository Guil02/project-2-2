package group.seven.model.agents;

import group.seven.enums.TileType;
import group.seven.model.environment.*;

import java.util.ArrayList;
import java.util.List;

import static group.seven.enums.TileType.PORTAL;
import static group.seven.enums.TileType.TARGET;

public class TileNode {

    //from agent's perspective
    int x, y;
    //Or Point2D?

    TileType type;
    Agent agent;
    TileType agentType = null;
    List<Marker> markers;
    Adjacent<TileNode> adjacent;
    Pheromone pheromone;

    public TileNode(Tile tile, Agent a){
        x = tile.getX();
        y = tile.getY();
        this.agent = a;
        this.pheromone = tile.pheromone;
        for(Agent agent: Scenario.TILE_MAP.agents){
            if(a.x==tile.getX()&& a.y==tile.getY()){
                this.agentType = a.agentType;
                break;
            }
        }
        updateAdjacent();

        markers = new ArrayList<>();
        for(Marker marker : Scenario.TILE_MAP.markers){
            if(marker.getXY().x()==x && marker.getXY().y()==y){
                markers.add(marker);
            }
        }
        type  = tile.getType();
    }

    public void update(){
        agentType = null;
        markers.clear();
        for(Agent agentEntity: Scenario.TILE_MAP.agents){
            if(agentEntity.x==x&& agentEntity.y==y){
                this.agentType = agentEntity.agentType;
                break;
            }
        }
        for(Marker marker : Scenario.TILE_MAP.markers){
            if(marker.getXY().x()==x && marker.getXY().y()==y){
                markers.add(marker);
            }
        }
        updateAdjacent();

    }

    /**
     * This methods gets the adjacent positions and updates the internal map of the agent
     */
    public void updateAdjacent(){
        TileNode north = agent.getMapPosition(agent.x,agent.y-1);
        TileNode east = agent.getMapPosition(agent.x+1,agent.y);
        TileNode south = agent.getMapPosition(agent.x,agent.y+1);
        TileNode west = agent.getMapPosition(agent.x-1,agent.y);
        TileNode target = null;
        if(type==PORTAL){
            int xTar=Scenario.TILE_MAP.getTile(x,y).adjacent.targetLocation().getX(), yTar=Scenario.TILE_MAP.getTile(x,y).adjacent.targetLocation().getY();
            target = agent.getMapPosition(xTar,yTar);
        }

        adjacent = new Adjacent<>(north,east,south,west,target);
    }

    public double getPheromoneStrength() {
        return pheromone.getStrength()  ;
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
}
