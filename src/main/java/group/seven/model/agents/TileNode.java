package group.seven.model.agents;

import group.seven.enums.TileType;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.Tile;
import javafx.geometry.Point2D;

import static group.seven.enums.TileType.UNKNOWN;

public class TileNode {

    //from agent's perspective
    int x, y;
    //Or Point2D?

    TileType type;
    Agent agent;

    public TileNode(Tile tile){
        for(Agent agent: Scenario.TILE_MAP.agents){
            if(agent.x==tile.getX()&& agent.y==tile.getY()){
                this.agent = agent;
                break;
            }
        }
        type  = tile.getType();
        x = tile.getX();
        y = tile.getY();
    }

    public void update(){
        agent = null;
        for(Agent agent: Scenario.TILE_MAP.agents){
            if(agent.x==x&& agent.y==y){
                this.agent = agent;
                break;
            }
        }
    }

    public TileType getType(){
        return type;
    }
}
