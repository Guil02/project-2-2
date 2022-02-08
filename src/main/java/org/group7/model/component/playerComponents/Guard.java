package org.group7.model.component.playerComponents;

import static org.group7.model.component.ComponentEnum.GUARD_COMPONENT;

/**
 * This class represent the player in the game that is a guard.
 */
public class Guard extends PlayerComponent{
    public Guard(double x, double y) {
        super(x, y);
        setComponent(GUARD_COMPONENT);
    }
}
