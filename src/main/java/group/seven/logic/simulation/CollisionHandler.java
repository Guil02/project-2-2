package group.seven.logic.simulation;

import group.seven.enums.TileType;
import group.seven.logic.geometric.Vector;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;
import group.seven.model.environment.Component;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.TileMap;

import java.util.List;



public class CollisionHandler {

    public static void handle(List<Move> moves){
        for (Move move : moves){
            Agent agent = move.agent();
            for (int i = 0; i < move.distance(); i++){
                int x = agent.getX();
                int y = agent.getY();
                XY targetPos = new XY(x,y);
                targetPos = targetPos.add(agent.getDirection().unitVector.x(), agent.getDirection().unitVector.y());
                if(!(i < move.agent().getCurrentSpeed())){/*is out of bounds*/
                    break;
                }
                if(isOutOfBounds(targetPos)){
                    break;
                }
                if (check(TileType.WALL, targetPos)){
                    break;
                } else if (check(TileType.PORTAL,targetPos)){
                    Component portal = getComponent(targetPos, TileType.PORTAL);
                    agent.moveTo(portal.exit());
                    break;
                }
                else{
                    move.agent().executeMove(1);
                }
            }
        }

    }

    public static boolean isOutOfBounds(XY pos){
        return pos.x()<0||pos.x()>=Scenario.WIDTH||pos.y()<0||pos.y()>=Scenario.HEIGHT;
    }

    public static boolean check(TileType type, XY targetPosition){
        return Scenario.TILE_MAP.getTile(targetPosition.x(), targetPosition.y()).getType() == type;
    }

    public static Component getComponent(XY pos, TileType type){
        switch(type){
            case PORTAL -> {return getComponentFromList(pos,Scenario.portals);}
            case WALL -> {return getComponentFromList(pos,Scenario.walls);}
        }
        return null;
    }

    public static Component getComponentFromList(XY pos, List<Component> components){
        for(Component portal: components){
            if(portal.area().getX()==pos.x()&&portal.area().getY()==pos.y()){
                return portal;
            }
        }
        return null;
    }


    //walls
    //teleporters
    //agents




















    //idkkkkk
    //for (int i = 3; i < [10]; i++) {
    //      for (Agent agent : agents) {
    //          if (i < move.distance()) {
    //              //agent takes one step
                    //check collision
                        //only update if no collision
    //          }

    //      }
    // }

}
