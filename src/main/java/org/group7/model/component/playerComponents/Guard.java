package org.group7.model.component.playerComponents;

import static org.group7.model.component.ComponentEnum.GUARD_COMPONENT;

public class Guard extends PlayerComponent{
    public Guard(double x, double y) {
        super(x, y);
        setComponent(GUARD_COMPONENT);
    }
}
