package group.seven.logic.simulation;

import group.seven.enums.Cardinal;
import group.seven.enums.TileType;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;
import group.seven.model.environment.Component;
import group.seven.model.environment.Scenario;
import javafx.geometry.Point2D;

import java.util.Collections;
import java.util.List;

import static group.seven.enums.TileType.*;
import static group.seven.model.environment.Scenario.TILE_MAP;
import static group.seven.utils.Methods.print;


public class CollisionHandler {

    public static void handle2(List<Move> moves){
        for (Move move : moves){
            Agent agent = move.agent();
            agent.clearVision();
            agent.updateVision();
            agent.updateMap();
            for (int i = 0; i < move.distance(); i++){
                int x = agent.getX();
                int y = agent.getY();
                XY targetPos = new XY(x,y);
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
                    agent.updateMap();
                    break;
                } else {
                    move.agent().executeMove(1);
                    agent.updateVision();
                    agent.updateMap();
                }
            }
        }

    }

    public static void handle(List<Move> moves){
        int max_distance = max_distance(moves);
        for (int i = 0; i < max_distance; i++) {
            for (Move move : moves) {
                Agent agent = move.agent();
                agent.clearVision();
                agent.updateVision();
                int x = agent.getX();
                int y = agent.getY();
                XY position = agent.getXY();
                XY targetPos = new XY(x, y);
                targetPos = targetPos.add(agent.getDirection().unitVector.x(), agent.getDirection().unitVector.y());
                int distance = Math.abs((position.x()-targetPos.x()) + (position.y() - targetPos.y()));
                if (i < move.distance()) {
                    if (isOutOfBounds(targetPos)) {
                        break;
                    }
                    if (check(WALL, targetPos)) {
                        break;
                    } else
                    if (checkAgents(agent, targetPos)){
                        print("intruder or guard");
                        break;
                    }
                    else if (check(PORTAL, targetPos)) {
                        Component portal = getComponent(targetPos, PORTAL);
                        print(agent.getID() + " just whooshed");
                        agent.moveTo(portal.exit());
                        agent.updateVision();
                        break;
                    } else {
                        move.agent().executeMove(1);
                        agent.updateVision();
                    }
                } //else System.out.println(move);

            }
        }

    }

    private static int max_distance(List<Move> moves) {
        int max = 0;
        for(Move move : moves) {
            max = Math.max(max, move.distance());
        }
        return max;
    }


    public static boolean isOutOfBounds(XY pos){
        return pos.x()<0||pos.x()>=Scenario.WIDTH||pos.y()<0||pos.y()>=Scenario.HEIGHT;
    }

    public static boolean checkAgents(Agent agent, XY target) {
        for (Agent other : TILE_MAP.agents) {
            if (agent != other) {
                if (other.getXY() == target) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean check(TileType type, XY targetPosition){
        return TILE_MAP.getTile(targetPosition.x(), targetPosition.y()).getType() == type;
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
}
