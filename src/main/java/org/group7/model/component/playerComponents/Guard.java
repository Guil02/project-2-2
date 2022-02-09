package org.group7.model.component.playerComponents;

import org.group7.geometric.Point;

import static org.group7.model.component.ComponentEnum.GUARD_COMPONENT;

/**
 * This class represent the player in the game that is a guard.
 */
public class Guard extends PlayerComponent{
    public Guard(Point point1, Point point2) {
        super(point1, point2);
        setComponent(GUARD_COMPONENT);
    }
}
