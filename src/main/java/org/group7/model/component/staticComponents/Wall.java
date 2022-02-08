package org.group7.model.component.staticComponents;

import org.group7.geometric.Point;
import static org.group7.model.component.ComponentEnum.WALL_COMPONENT;

/**
 * The class that describes a wall that appears in the game. It is a wall.
 */
public class Wall extends StaticComponent {
    public Wall(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
        setComponent(WALL_COMPONENT);
    }
}
