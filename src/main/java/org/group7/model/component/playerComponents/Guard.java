package org.group7.model.component.playerComponents;

import org.group7.geometric.Point;

import static org.group7.model.component.ComponentEnum.GUARD;

/**
 * This class represent the player in the game that is a guard.
 */
public class Guard extends PlayerComponent{
    public Guard(Point point1, Point point2, double angle) {
        super(point1, point2, angle);
        setComponent(GUARD);
    }

    public Guard(Point point1, Point point2, double directionAngle, double viewFieldLength, double viewFieldAngle) {
        super(point1, point2, directionAngle, viewFieldLength, viewFieldAngle);
        setComponent(GUARD);
    }

}
