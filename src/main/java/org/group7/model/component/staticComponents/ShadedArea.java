package org.group7.model.component.staticComponents;

import org.group7.geometric.Point;
import org.group7.model.Scenario;

import static org.group7.enums.ComponentEnum.SHADED_AREA;

/**
 * The class that describes what the shaded areas look like.
 */
public class ShadedArea extends StaticComponent{
    public ShadedArea(Point topLeft, Point bottomRight, Scenario scenario) {
        super(topLeft, bottomRight, scenario);
        setComponent(SHADED_AREA);
    }
}
