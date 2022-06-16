package group.seven.enums;

public enum Status {

    GAME_OVER, RUNNING, GUARD_WIN(GAME_OVER), INTRUDER_WIN(GAME_OVER), PAUSED;

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
