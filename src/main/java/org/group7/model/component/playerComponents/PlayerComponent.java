package org.group7.model.component.playerComponents;

import org.group7.geometric.Point;
import org.group7.geometric.Vector2D;
import org.group7.model.component.Component;
import org.group7.utils.Config;

/**
 * This class is made as a super class for all the possible component that can be considered player, i.e. agents and intruders.
 * it contains the coordinates of the position of the agent.
 */
public abstract class PlayerComponent extends Component {
    public Vector2D position;
    public Vector2D direction;
    public Vector2D viewField;
    private double directionAngle;
    private double viewFieldLength;
    private double viewFieldAngle; //how wide the visual range is

    public PlayerComponent(Point point1, Point point2, double directionAngle) {
        super(point1, point2);
        this.directionAngle = directionAngle;
        this.viewFieldLength = Config.DEFAULT_VIEW_DISTANCE;
        this.viewFieldAngle = Math.toRadians(90);

        this.directionAngle = Math.toRadians(90);
        position = new Vector2D(getX(), getY());
        direction = new Vector2D(directionAngle);
        viewField = new Vector2D(viewFieldAngle);
    }
    public PlayerComponent(Point point1, Point point2, double directionAngle, double viewFieldLength, double viewFieldAngle) {
        this(point1, point2, directionAngle);
        this.viewFieldLength = viewFieldLength;
        this.viewFieldAngle = viewFieldAngle;
        viewField = new Vector2D(viewFieldAngle);
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
