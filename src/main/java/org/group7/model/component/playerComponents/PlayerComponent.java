package org.group7.model.component.playerComponents;

import org.group7.geometric.Point;
import org.group7.model.component.Component;

/**
 * This class is made as a super class for all the possible component that can be considered player, i.e. agents and intruders.
 * it contains the coordinates of the position of the agent.
 */
public abstract class PlayerComponent extends Component {
    private double angle;
    private double viewField = 20;

    public PlayerComponent(Point point1, Point point2, double angle) {
        super(point1, point2);
        this.angle = angle;
    }

    public Point getCoordinates(){
        return getArea().getTopLeft();
    }

    public double getX() {
        return getArea().getTopLeft().x;
    }

    public double getY() {
        return getArea().getTopLeft().y;
    }

    public void move(double dx, double dy){
        getArea().getTopLeft().x+=dx;
        getArea().getBottomRight().x+=dx;
        getArea().getTopLeft().y+=dy;
        getArea().getBottomRight().y+=dy;
    }

    public double getAngle() {return angle;}

    public void setAngle(double angle) {this.angle = angle;}

    public double getViewField() {return viewField;}
}
