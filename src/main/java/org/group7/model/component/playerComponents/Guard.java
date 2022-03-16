package org.group7.model.component.playerComponents;

import org.group7.agentVision.Ray;
import org.group7.geometric.Point;
import org.group7.geometric.Vector2D;
import org.group7.model.Scenario;

import static org.group7.enums.ComponentEnum.GUARD;
import static org.group7.enums.AlgorithmEnum.A_STAR;

/**
 * This class represent the player in the game that is a guard.
 */
public class Guard extends PlayerComponent{
    public Guard(Point point1, Point point2, double angle, Scenario scenario) {
        super(point1, point2, angle, scenario);
        setComponent(GUARD);
        setAlgorithmValue(A_STAR);
    }


    public Guard(Point point1, Point point2, double directionAngle,  Scenario scenario, double baseSpeed,
                 double distanceViewing,double smellingDistance) {


        super(point1,point2,  directionAngle, scenario, baseSpeed, distanceViewing, smellingDistance);
        setComponent(GUARD);
        setAlgorithmValue(A_STAR);


    }

}
