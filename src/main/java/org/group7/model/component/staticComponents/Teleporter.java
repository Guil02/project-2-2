package org.group7.model.component.staticComponents;

import org.group7.geometric.Point;
import org.group7.model.Scenario;

import static org.group7.enums.ComponentEnum.TELEPORTER;

/**
 * The class that describes the teleporter in the game. It teleports a player (when they enter into it i.e. if isHit()
 * is true) to the target point.
 */
public class Teleporter extends StaticComponent{
    private Point target;

    public Teleporter(Point topLeft, Point bottomRight, Scenario scenario) {
        super(topLeft, bottomRight, scenario);
        target = new Point(0,0);
        setComponent(TELEPORTER);
    }

    public Teleporter(Point topLeft, Point bottomRight, Point target, Scenario scenario) {
        super(topLeft, bottomRight, scenario);
        this.target = target;
        setComponent(TELEPORTER);
    }

    public Point getTarget() {
        return target;
    }
}
