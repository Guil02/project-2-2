package group.seven.logic.geometric;

import group.seven.enums.Cardinal;

import static group.seven.enums.Cardinal.*;
import static group.seven.enums.Cardinal.SOUTH;

public class Pythagoras {
    public static double getAnglePythagoras(int x1, int y1, int x2, int y2){
        double angle = 0;
        if (x1 != x2 && y1 != y2) { //checking so that no division by 0 happens
            double adjacent = Math.abs(x1 - x2);
            double opposite = Math.abs(y1 - y2);
            angle = Math.atan(opposite / adjacent);
            angle = Math.toDegrees(angle);
        }
        return angle;
    }

    //x1, y1 is the agent
    //x2, y2 is the goal
    public static Cardinal fromAngleToCardinal (double angle, int x1, int x2, int y1, int y2){
        Cardinal orientationToGoal;
        if (angle == 0){  // Checking if goal is in front of agent
            if (x1 > x2 && y1 == y2){
                orientationToGoal = EAST;
            } else if (x1 < x2 && y1 == y2){
                orientationToGoal = WEST;
            } else if (y1 < y2 && x1 == x2){
                orientationToGoal = NORTH;
            } else {
                orientationToGoal = SOUTH;
            }
        }else {
            if ((angle<45 && angle>0) || (angle>315 && angle<360 )){
                orientationToGoal = EAST;
            } else if ((angle>45 && angle<135)) {
                orientationToGoal = NORTH;
            }else if ((angle>135 && angle<225)) {
                orientationToGoal = WEST;
            } else {
                orientationToGoal = SOUTH;
            }
        }
    return orientationToGoal;
    }


}
