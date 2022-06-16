package group.seven.enums;

import static group.seven.enums.Cardinal.*;

public enum Action {
    TURN_UP(NORTH),
    TURN_DOWN(SOUTH),
    TURN_LEFT(WEST),
    TURN_RIGHT(EAST),
    FLIP,
    MOVE_FORWARD,
    NOTHING;

    Cardinal direction;

    Action() {
    }

    Action(Cardinal cardinal) {
        direction = cardinal;
    }

    public Action getDirection(Cardinal cardinal) {
        if (direction == cardinal)
            return this;
        else {
            return Action.values()[(int) (Math.random() * 6)];
        }
    }

//    STEP, SPRINT, HALT,
}
