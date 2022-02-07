package org.group7.model.component.staticComponents;

import org.group7.utils.Point;

import static org.group7.model.component.ComponentEnum.GUARD_SPAWN_AREA;

public class GuardSpawnArea extends StaticComponent{
    public GuardSpawnArea(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
        setComponent(GUARD_SPAWN_AREA);
    }
}
