package org.group7.model.component.staticComponents;

import org.group7.utils.Point;

import static org.group7.model.component.ComponentEnum.TELEPORTER_COMPONENT;

public class Teleporter extends StaticComponent{
    private Point target;
    public Teleporter(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
        target = new Point(0,0);
        setComponent(TELEPORTER_COMPONENT);
    }

    public Teleporter(Point topLeft, Point bottomRight, Point target) {
        super(topLeft, bottomRight);
        this.target = target;
        setComponent(TELEPORTER_COMPONENT);
    }
}
