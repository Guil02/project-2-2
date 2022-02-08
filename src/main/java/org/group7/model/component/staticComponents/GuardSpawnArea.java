package org.group7.model.component.staticComponents;

import org.group7.geometric.Point;

import static org.group7.model.component.ComponentEnum.GUARD_SPAWN_AREA;

/**
 * This class describes the location in which the guards spawn (start the game).
 */
public class GuardSpawnArea extends StaticComponent{
    public GuardSpawnArea(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
        setComponent(GUARD_SPAWN_AREA);
    }
}
