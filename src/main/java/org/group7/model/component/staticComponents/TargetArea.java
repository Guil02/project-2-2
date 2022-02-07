package org.group7.model.component.staticComponents;

import org.group7.utils.Point;

import static org.group7.model.component.ComponentEnum.TARGET_AREA_COMPONENT;

public class TargetArea extends StaticComponent{
    public TargetArea(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
        setComponent(TARGET_AREA_COMPONENT);
    }
}
