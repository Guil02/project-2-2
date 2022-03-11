package org.group7.model.component.staticComponents;

import org.group7.geometric.Point;
import org.group7.model.Scenario;

import static org.group7.model.component.ComponentEnum.TARGET_AREA;

/**
 * The class that describes the target area for the intruder. It is the goal of the intruders to get to this area.
 */
public class TargetArea extends StaticComponent{
    public TargetArea(Point topLeft, Point bottomRight, Scenario scenario) {
        super(topLeft, bottomRight, scenario);
        setComponent(TARGET_AREA);
    }
}
