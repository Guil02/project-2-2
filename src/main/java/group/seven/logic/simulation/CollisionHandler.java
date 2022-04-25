package group.seven.logic.simulation;

import group.seven.enums.TileType;
import group.seven.logic.geometric.Vector;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;
import group.seven.model.environment.Component;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.TileMap;

import java.util.List;



public class CollisionHandler {

    public static void handle(List<Move> moves){
        for (Move move : moves){
            for (int i = 0; i < move.distance(); i++){
                move.agent().executeMove(1);
                if(i < move.agent().getCurrentSpeed() /*is out of bounds*/){
                    if (check(TileType.WALL,move)){
                        break;
                    } else if (check(TileType.PORTAL,move)){
                        
                    }

                }
            }
        }

    }

    public static boolean check(TileType type, Move move){
        return Scenario.TILE_MAP.getTile(move.agent().getX(), move.agent().getY()).getType() == type;
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
