package group.seven.enums;

import static group.seven.enums.Cardinal.*;

/**
 * The enum Action.
 */
public enum Action {
    TURN_UP(NORTH),
    TURN_DOWN(SOUTH),
    TURN_LEFT(WEST),
    TURN_RIGHT(EAST),
    MOVE_FORWARD,
    FLIP,
    NOTHING;

    /**
     * The Direction.
     */
    Cardinal direction;

    Action() {
    }

    Action(Cardinal cardinal) {
        direction = cardinal;
    }

    /**
     * Gets direction.
     *
     * @param cardinal the cardinal
     * @return the direction
     */
    public Action getDirection(Cardinal cardinal) {
        if (direction == cardinal)
            return this;
        else {
            return Action.values()[(int) (Math.random() * 6)];
        }
    }

//    STEP, SPRINT, HALT,
}
