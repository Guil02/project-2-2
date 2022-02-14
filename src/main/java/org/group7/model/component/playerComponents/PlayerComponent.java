package org.group7.model.component.playerComponents;

import org.group7.geometric.Point;
import org.group7.model.component.Component;
import org.group7.utils.Config;

/**
 * This class is made as a super class for all the possible component that can be considered player, i.e. agents and intruders.
 * it contains the coordinates of the position of the agent.
 */
public abstract class PlayerComponent extends Component {
    private double directionAngle;
    private double viewFieldLength;
    private double viewFieldAngle; //how wide the visual range is

    public PlayerComponent(Point point1, Point point2, double directionAngle) {
        super(point1, point2);
        this.directionAngle = directionAngle;
        this.viewFieldLength = Config.DEFAULT_VIEW_DISTANCE;
        this.viewFieldAngle = Math.toRadians(20);
    }
    public PlayerComponent(Point point1, Point point2, double directionAngle, double viewFieldLength, double viewFieldAngle) {
        super(point1, point2);
        this.directionAngle = directionAngle;
        this.viewFieldLength = viewFieldLength;
        this.viewFieldAngle = viewFieldAngle;
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
        getArea().getTopLeft().x += dx;
        getArea().getBottomRight().x += dx;
        getArea().getTopLeft().y += dy;
        getArea().getBottomRight().y += dy;
    }

    public double getDirectionAngle() {return directionAngle;}

    public void setDirectionAngle(double directionAngle) {this.directionAngle = directionAngle;}

    public double getViewFieldLength() {return viewFieldLength;}

    public double getViewFieldAngle() {return viewFieldAngle;}
}
