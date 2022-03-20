package org.group7.model.algorithms;

import org.group7.enums.Actions;
import org.group7.model.Grid;
import org.group7.model.component.playerComponents.PlayerComponent;
import org.group7.utils.Config;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

public class Random implements Algorithm {
    private final int initialX;
    private final int initialY;
    private final Grid[][] map;
    private PlayerComponent player;
    private int maxDistance;


    public Random(int initialX, int initialY, Grid[][] map, PlayerComponent player,double maxDistance) {
        this.initialX = initialX;
        this.initialY = initialY;
        this.map = map;
        this.player = player;
        this.maxDistance = (int)maxDistance;
    }

    @Override
    public ActionTuple calculateMovement() {
        int random = (int) (Math.random()*6);
        //System.out.println("action "+random);
        if (random == 0) {
            return new ActionTuple(Actions.TURN_UP,0);
        }
        if (random == 1) {
            return new ActionTuple(Actions.TURN_RIGHT,0);
        }
        if (random == 2) {
            return new ActionTuple(Actions.TURN_LEFT,0);
        }
        if (random == 3) {
            return new ActionTuple(Actions.TURN_DOWN,0);
        }
        else {
            int distance = (int) (Math.random()*maxDistance);
            return new ActionTuple(Actions.MOVE_FORWARD,distance);
        }
    }
}
