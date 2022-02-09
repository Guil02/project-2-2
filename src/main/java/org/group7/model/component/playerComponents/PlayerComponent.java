package org.group7.model.component.playerComponents;

import org.group7.geometric.Point;
import org.group7.model.component.Component;

/**
 * This class is made as a super class for all the possible component that can be considered player, i.e. agents and intruders.
 * it contains the coordinates of the position of the agent.
 */
public abstract class PlayerComponent extends Component {
    private double x;
    private double y;

    public PlayerComponent(Point point1, Point point2) {
        super(point1, point2);
    }

    public Point getCoordinates(){
        return new Point(x,y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
