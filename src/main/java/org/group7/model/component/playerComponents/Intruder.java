package org.group7.model.component.playerComponents;

import org.group7.geometric.Point;
import org.group7.model.Scenario;

import static org.group7.enums.AlgorithmEnum.A_STAR;
import static org.group7.enums.ComponentEnum.GUARD;
import static org.group7.enums.ComponentEnum.INTRUDER;

/**
 * This class represent the player in the game that is an intruder.
 */
public class Intruder extends PlayerComponent{
    public Intruder(Point point1, Point point2, double angle, Scenario scenario) {
        super(point1, point2, angle,scenario);
        setComponent(INTRUDER);
    }

    public Intruder(Point point1, Point point2, double directionAngle,  Scenario scenario, double baseSpeed,
                    double distanceViewing,double smellingDistance) {
        super(point1,point2,  directionAngle, scenario, baseSpeed, distanceViewing, smellingDistance);

        setComponent(INTRUDER);
        setAlgorithmValue(A_STAR);
    }

}
