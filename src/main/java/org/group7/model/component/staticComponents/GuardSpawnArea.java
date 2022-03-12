package org.group7.model.component.staticComponents;

import org.group7.geometric.Point;
import org.group7.model.Scenario;

import static org.group7.model.enums.ComponentEnum.GUARD_SPAWN_AREA;

/**
 * This class describes the location in which the guards spawn (start the game).
 */
public class GuardSpawnArea extends StaticComponent{
    public GuardSpawnArea(Point topLeft, Point bottomRight, Scenario scenario) {
        super(topLeft, bottomRight, scenario);
        setComponent(GUARD_SPAWN_AREA);
    }
}
