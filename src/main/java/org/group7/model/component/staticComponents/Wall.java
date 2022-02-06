package org.group7.model.component.staticComponents;

import org.group7.utils.Point;
import static org.group7.model.component.ComponentEnum.WALL_COMPONENT;

public class Wall extends StaticComponent {
    public Wall(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
        setComponent(WALL_COMPONENT);
    }
}
