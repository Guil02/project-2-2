package group.seven.model.agents;

import group.seven.enums.TileType;
import group.seven.model.environment.Marker;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.Tile;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

import static group.seven.enums.TileType.PORTAL;
import static group.seven.enums.TileType.UNKNOWN;

public class TileNode {

    //from agent's perspective
    int x, y;
    //Or Point2D?

    TileType type;
    TileType agent = null;
    List<Marker> markers;
    List<TileNode> adjacent;

    public TileNode(Tile tile){
        adjacent = new ArrayList<>();
        for(Agent agent: Scenario.TILE_MAP.agents){
            if(agent.x==tile.getX()&& agent.y==tile.getY()){
                this.agent = agent.agentType;
                break;
            }
        }
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
        agent = null;
        markers.clear();
        for(Agent agent: Scenario.TILE_MAP.agents){
            if(agent.x==x&& agent.y==y){
                this.agent = agent.agentType;
                break;
            }
        }
        for(Marker marker : Scenario.TILE_MAP.markers){
            if(marker.getXY().x()==x && marker.getXY().y()==y){
                markers.add(marker);
            }
        }
        int ID = 0;
        Agent cur;
        if(adjacent.size()<4 && type!=PORTAL || adjacent.size()>=5) {
            //check if in adj list and not null in the map
            //Something like this? not sure if what I'm doing is correct - Gio help!
            Tile north = Scenario.TILE_MAP.getTile(x-1,y);
            Tile east = Scenario.TILE_MAP.getTile(x,y+1);
            Tile south = Scenario.TILE_MAP.getTile(x+1,y);
            Tile west = Scenario.TILE_MAP.getTile(x,y-1);
            if (north != null && east != null && south != null && west != null) {
                if (!(adjacent.contains(north))){
                    //adjacent.add(north)
                }
            }
            //check for north, south, etc. in tile node map

        }

    }
}
