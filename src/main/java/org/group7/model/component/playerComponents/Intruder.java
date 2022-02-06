package org.group7.model.component.playerComponents;

import static org.group7.model.component.ComponentEnum.INTRUDER_COMPONENT;

public class Intruder extends PlayerComponent{
    public Intruder(double x, double y) {
        super(x, y);
        setComponent(INTRUDER_COMPONENT);
    }

}
