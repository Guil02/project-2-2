package org.group7.model.component.staticComponents;

import org.group7.utils.Point;

import static org.group7.model.component.ComponentEnum.INTRUDER_SPAWN_AREA;

public class IntruderSpawnArea extends StaticComponent{
    public IntruderSpawnArea(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
        setComponent(INTRUDER_SPAWN_AREA);
    }
}
