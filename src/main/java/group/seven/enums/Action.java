package group.seven.enums;

public enum Action {
    STEP,       //move forwards

    FLIP,       //rotate 180
    TURN_RIGHT, //rotate clockwise 90
    TURN_LEFT,  //rotate counterclockwise 90

    //set orientation
    LOOK_NORTH,
    LOOK_SOUTH,
    LOOK_EAST,
    LOOK_WEST,

    HALT,       //stop moving

}
