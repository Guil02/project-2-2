package group.seven.enums;

import static group.seven.enums.Cardinal.*;

/**
 * The enum Action.
 */
public enum Action {
    /**
     * Turn up action.
     */
    TURN_UP(NORTH),

    /**
     * Turn down action.
     */
    TURN_DOWN(SOUTH),

    /**
     * Turn left action.
     */
    TURN_LEFT(WEST),

    /**
     * Turn right action.
     */
    TURN_RIGHT(EAST),

    /**
     * Move forward action.
     */
    MOVE_FORWARD,

    /**
     * Flip action.
     */
    FLIP,

    /**
     * Nothing action.
     */
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
