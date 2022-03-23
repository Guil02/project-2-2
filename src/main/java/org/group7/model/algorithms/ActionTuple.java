package org.group7.model.algorithms;

import org.group7.enums.Actions;

public class ActionTuple {
    Actions action;
    int distance;

    public ActionTuple(Actions action, int distance) {
        this.action = action;
        this.distance = distance;
    }

    public Actions getAction() {return action;}
    public int getDistance() {return distance;}

    @Override
    public String toString() {
        return "ActionTuple{" +
                "action=" + action +
                ", distance=" + distance +
                '}';
    }

    //base speed = 4
    //ActionTuple(MOVE_FORWARD, 2)
    //ActionTuple(TURN_DOWN, 0)
    //ActionTuple(MOVE_FORWARD, 4)
    //ActionTuple(MOVE_FORWARD, 1)
    //ActionTuple(TURN_UP, 0)

    // [MOVE_FORWARD, MOVE_FORWARD, TURN_down,move_forward, move_forward, move_forward, move_forward, move_forward, TURN_UP]
}
