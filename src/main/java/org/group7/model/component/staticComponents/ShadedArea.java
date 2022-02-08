package org.group7.model.component.staticComponents;

import org.group7.geometric.Point;

import static org.group7.model.component.ComponentEnum.SHADED_AREA_COMPONENT;

/**
 * The class that describes what the shaded areas look like.
 */
public class ShadedArea extends StaticComponent{
    public ShadedArea(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
        setComponent(SHADED_AREA_COMPONENT);
    }
}
