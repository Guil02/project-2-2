package group.seven.logic.simulation;

import group.seven.enums.Cardinal;
import group.seven.enums.TileType;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;
import group.seven.model.environment.Component;
import group.seven.model.environment.Scenario;
import javafx.geometry.Point2D;

import java.util.List;

import static group.seven.enums.TileType.*;
import static group.seven.utils.Methods.print;


public class CollisionHandler {

    public static void handle(List<Move> moves) {
        for (Move move : moves) {
            Agent agent = move.agent();
            agent.clearVision();
            agent.updateVision();
            for (int i = 0; i < move.distance(); i++){
                int x = agent.getX();
                int y = agent.getY();
                XY targetPos = new XY(x,y);
                //XY targetPos = agent.getXY();
                targetPos = targetPos.add(agent.getDirection().unitVector.x(), agent.getDirection().unitVector.y());

                if(!(i < move.agent().getCurrentSpeed())){/*is out of bounds*/
                    break;
                }
                if (isOutOfBounds(targetPos)) {
                    break;
                }
                if (check(WALL, targetPos) || check(INTRUDER, targetPos) || check(GUARD, targetPos)) {
                    break;
                } else if (check(PORTAL, targetPos)) {
                    Component portal = getComponent(targetPos, PORTAL);
                    print(agent.getID() + " just whooshed");
                    agent.moveTo(portal.exit());
                    agent.updateVision();
                    break;
                } else {
                    move.agent().executeMove(1);
                    agent.updateVision();
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
        print("Could not get :( returning default");
        return new Component(null, null, pos, Cardinal.NORTH);
    }

    public static Component getComponentFromList(XY pos, List<Component> components){
        //You can use JavaFX Rectangle and Point2D
        Point2D agentPos = new Point2D(pos.x(), pos.y());
        for (Component component : components)
            if (component.area().contains(agentPos))
                return component;

        /*
        Here the issue was that only the top left corner of the Rectangle was being
        compared against the agent's position, rather than every coordinate in 2d shape

        for(Component portal: components){
            if(portal.area().getX()==pos.x() && portal.area().getY()==pos.y()){
                return portal;
            }
        }
        */

        print("not found");
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
