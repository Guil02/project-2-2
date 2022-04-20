package org.group7.model.component.staticComponents;

import org.group7.gui.geometric.Point;
import org.group7.model.Scenario;

import static org.group7.enums.ComponentEnum.WALL;

/**
 * The class that describes a wall that appears in the game. It is a wall.
 */
public class Wall extends StaticComponent {
    public Wall(Point topLeft, Point bottomRight, Scenario scenario) {
        super(topLeft, bottomRight, scenario);
        setComponent(WALL);
    }
}
