package org.group7.model.algorithms;

import org.group7.agentVision.BasicVision;
import org.group7.enums.Actions;
import org.group7.enums.Orientation;
import org.group7.model.Grid;
import org.group7.model.component.playerComponents.PlayerComponent;

import java.util.ArrayList;
import java.util.List;

import static org.group7.enums.Actions.*;

public class Frontier implements Algorithm {
    private final int initialX;
    private final int initialY;
    private final Grid[][] map;
    private final Grid[][] playerMap;
    private PlayerComponent player;

    List<int[]> frontiers;

    public Frontier(int initialX, int initialY, Grid[][] map, PlayerComponent player) {
        this.initialX = initialX;
        this.initialY = initialY;
        this.map = map;
        this.player = player;
        //needs to be discussed
        frontiers = new ArrayList<>();
        playerMap = new Grid[map.length][map[0].length];
        playerMap[initialX][initialY]=map[initialX][initialY];
    }

    //needs to be done later
    @Override
    public ActionTuple calculateMovement() {
        return null;//TODO implement
        //TODO shortest path here
    }

    // Might need it later
    public List<Orientation> compass() {
        List<Orientation> compass = new ArrayList<>();
        compass.add(Orientation.UP);
        compass.add(Orientation.DOWN);
        compass.add(Orientation.LEFT);
        compass.add(Orientation.RIGHT);
        return compass;
    }

    //List of the path from starting point to the end destination, so it doesn't evaluate new options very timestep
    public List<ActionTuple> pathList() {
        List<ActionTuple> path = new ArrayList<>();
        List<Actions> actions = new ArrayList<>(); //need a list of coordinates from start to end destination
        double speed = player.getBaseSpeed();

        for(int x = 0; x < actions.size(); x++) {
            if(actions.get(x) == MOVE_FORWARD) {
                int count = 1;
                for (int y = x + 1; y < actions.size() && y < speed; y++) {
                    if (actions.get(y) == MOVE_FORWARD) {
                        x++;
                        count++;
                    } else {
                        break;
                    }
                }
                path.add(new ActionTuple(actions.get(x), count));
            }else {
                path.add(new ActionTuple(actions.get(x), 0));
            }
        }
        return path;
    }

    //make 360 and add those actions
    public List<Actions> circlePath() {
        List<Actions> turn_actions = new ArrayList<>();
        Orientation orientation = player.getOrientation();
        if(orientation == Orientation.UP) {
            turn_actions.add(TURN_RIGHT);
            turn_actions.add(TURN_DOWN);
            turn_actions.add(TURN_LEFT);
        }
        else if(orientation == Orientation.RIGHT) {
            turn_actions.add(TURN_UP);
            turn_actions.add(TURN_DOWN);
            turn_actions.add(TURN_LEFT);
        }
        else if(orientation == Orientation.DOWN) {
            turn_actions.add(TURN_RIGHT);
            turn_actions.add(TURN_UP);
            turn_actions.add(TURN_LEFT);
        }
        else if(orientation == Orientation.LEFT) {
            turn_actions.add(TURN_RIGHT);
            turn_actions.add(TURN_DOWN);
            turn_actions.add(TURN_UP);
        }
        return turn_actions;
    }

    public List<Grid> getNorthFrontiers() {
        Orientation orientation = player.getOrientation();
        List<Grid> north_frontiers = new ArrayList<>();
        if(orientation == Orientation.UP) {
            north_frontiers.addAll(player.getAgentsCurrentVision());
        }
        return north_frontiers;
    }

    public List<Grid> getSouthFrontiers() {
        Orientation orientation = player.getOrientation();
        List<Grid> south_frontiers = new ArrayList<>();
        if(orientation == Orientation.DOWN) {
            south_frontiers.addAll(player.getAgentsCurrentVision());
        }
        return south_frontiers;
    }

    public List<Grid> getWestFrontiers() {
        Orientation orientation = player.getOrientation();
        List<Grid> west_frontiers = new ArrayList<>();
        if(orientation == Orientation.LEFT) {
            west_frontiers.addAll(player.getAgentsCurrentVision());
        }
        return west_frontiers;
    }

    public List<Grid> getEastFrontiers() {
        Orientation orientation = player.getOrientation();
        List<Grid> east_frontiers = new ArrayList<>();
        if(orientation == Orientation.RIGHT) {
            east_frontiers.addAll(player.getAgentsCurrentVision());
        }
        return east_frontiers;
    }

    //maybe useful for u Roman
    public int distance_frontier(int x, int y) {
        return Math.abs(initialX - x) + Math.abs(initialY - y);
    }

}
