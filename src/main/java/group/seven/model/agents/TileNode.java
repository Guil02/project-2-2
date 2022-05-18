package group.seven.model.agents;

import group.seven.enums.TileType;
import group.seven.model.environment.Adjacent;
import group.seven.model.environment.Marker;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.Tile;

import java.util.ArrayList;
import java.util.List;

import static group.seven.enums.TileType.TARGET;

public class TileNode {

    int x, y;
    //Or Point2D?

    TileType type;
    Agent agent;
    TileType agentType = null;
    List<Marker> markers;
    Adjacent<TileNode> adjacent;

    public TileNode(Tile tile, Agent a){
        this.agent = a;
        for(Agent agent: Scenario.TILE_MAP.agents){
            if(agent.getX() == tile.getX() && agent.getY() == tile.getY()){
                this.agentType = agent.agentType;
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
        x = tile.getX();
        y = tile.getY();
    }

    public void update(){
        agentType = null;
        markers.clear();
        for(Agent agent: Scenario.TILE_MAP.agents){
            if(agent.getX()==x&& agent.getY()==y){
                this.agentType = agent.agentType;
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

    public void updateAdjacent(){
        TileNode north = agent.getMapPosition(agent.x,agent.y-1);
        TileNode east = agent.getMapPosition(agent.x+1,agent.y);
        TileNode south = agent.getMapPosition(agent.x,agent.y+1);
        TileNode west = agent.getMapPosition(agent.x-1,agent.y);
        TileNode target = null;
        int xTar=Scenario.TILE_MAP.getTile(x,y).adjacent.targetLocation().getX(), yTar=Scenario.TILE_MAP.getTile(x,y).adjacent.targetLocation().getY();
        if(type==TARGET){
            target = agent.getMapPosition(xTar,yTar);
        }

        adjacent = new Adjacent<>(north,east,south,west,target);
    }
}
