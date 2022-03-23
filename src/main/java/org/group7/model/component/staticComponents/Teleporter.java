package org.group7.model.component.staticComponents;

import org.group7.geometric.Point;
import org.group7.geometric.Vector2D;
import org.group7.model.Scenario;

import static org.group7.enums.ComponentEnum.TELEPORTER;

/**
 * The class that describes the teleporter in the game. It teleports a player (when they enter into it i.e. if isHit()
 * is true) to the target point.
 */
public class Teleporter extends StaticComponent{
    private Point target;
    private Vector2D direction;

    public Teleporter(Point topLeft, Point bottomRight, Scenario scenario) {
        super(topLeft, bottomRight, scenario);
        target = new Point(0,0);
        setComponent(TELEPORTER);
    }

    public Teleporter(Point topLeft, Point bottomRight, Point target, Scenario scenario, Double angle) {
        super(topLeft, bottomRight, scenario);
        this.target = target;
        setComponent(TELEPORTER);
        this.direction = transformAngleIntoDirection(angle);
        System.out.println(direction);
    }

    public Vector2D transformAngleIntoDirection(double angle) {
        if (46 < angle && angle <= 135 )
            return new Vector2D(1,0);
        else if (135 < angle && angle <= 225)
            return new Vector2D(0,-1);
        else if (225 < angle && angle <= 315 )
            return new Vector2D(-1,0);
        else
            return new Vector2D(0,1);
    }

    public Point getTarget() {
        return target;
    }
    public Vector2D getDirection() {return direction;}
}
