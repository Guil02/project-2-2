package org.group7.model.component.staticComponents;

import org.group7.geometric.Point;

import static org.group7.model.component.ComponentEnum.INTRUDER_SPAWN_AREA;

/**
 * This class describes the area in which the intruders will spawn.
 */
public class IntruderSpawnArea extends StaticComponent{
    public IntruderSpawnArea(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
        setComponent(INTRUDER_SPAWN_AREA);
    }
}