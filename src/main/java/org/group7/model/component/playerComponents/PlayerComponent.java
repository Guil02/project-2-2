package org.group7.model.component.playerComponents;

import org.group7.geometric.Point;
import org.group7.model.component.Component;

/**
 * This class is made as a super class for all the possible component that can be considered player, i.e. agents and intruders.
 * it contains the coordinates of the position of the agent.
 */
public abstract class PlayerComponent extends Component {

    public PlayerComponent(Point point1, Point point2) {
        super(point1, point2);
    }

    public Point getCoordinates(){
        return getArea().getPoint1();
    }

    public double getX() {
        return getArea().getPoint1().x;
    }

    public double getY() {
        return getArea().getPoint1().y;
    }

    public void move(double dx, double dy){
        getArea().getPoint1().x+=dx;
        getArea().getPoint2().x+=dx;
        getArea().getPoint1().y+=dy;
        getArea().getPoint2().y+=dy;
    }
}
