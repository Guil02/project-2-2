package org.group7.model.component.playerComponents;

import static org.group7.model.component.ComponentEnum.INTRUDER_COMPONENT;

/**
 * This class represent the player in the game that is an intruder.
 */
public class Intruder extends PlayerComponent{
    public Intruder(double x, double y) {
        super(x, y);
        setComponent(INTRUDER_COMPONENT);
    }

}
