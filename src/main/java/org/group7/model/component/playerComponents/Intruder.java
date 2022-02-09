package org.group7.model.component.playerComponents;

import org.group7.geometric.Point;

import static org.group7.model.component.ComponentEnum.INTRUDER_COMPONENT;

/**
 * This class represent the player in the game that is an intruder.
 */
public class Intruder extends PlayerComponent{
    public Intruder(Point point1, Point point2, double angle) {
        super(point1, point2, angle);
        setComponent(INTRUDER_COMPONENT);
    }

}
