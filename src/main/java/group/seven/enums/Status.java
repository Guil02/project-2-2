package group.seven.enums;

/**
 * The enum Status.
 */
public enum Status {

    /**
     * Game over status.
     */
    GAME_OVER,
    /**
     * Running status.
     */
    RUNNING,
    /**
     * Guard win status.
     */
    GUARD_WIN(GAME_OVER),
    /**
     * Intruder win status.
     */
    INTRUDER_WIN(GAME_OVER),
    /**
     * Paused status.
     */
    PAUSED;

    Status() {
        type = this;
    }

    /**
     * Specifies a type for applicable Status enums.
     * Specifically GUARD_WIN an INTRUDER_WIN are both of type GAME_OVER
     *
     * @param type the type of this status
     */
    Status(Status type) {
        this.type = type;
    }

    /**
     * If a Status has no specified type, then its type is simply itself,
     * Otherwise, its type is as specified in the constructor
     */
    public final Status type;
}
