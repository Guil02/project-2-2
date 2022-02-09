package org.group7.model.component.staticComponents;

import org.group7.geometric.Area;
import org.group7.model.component.Component;
import org.group7.geometric.Point;

/**
 * A general class for all the static components in the game. With static component it is meant to be components that
 * will not be moved during the game such as walls, target areas etc.
 *
 * This class contains an object of the area class that defines the surface of this static component. so a wall going from
 * (x,y) = (20,4) to (25,6) will be a rectangle with a height of 2 and a width of 5.
 */
public abstract class StaticComponent extends Component {

    public StaticComponent(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
    }

    /**
     * @return the top left corner of the area that the component covers..
     */
    public Point getTopLeft() {
        return getArea().getPoint1();
    }

    /**
     * @return the bottom right corner of the area that the component covers.
     */
    public Point getBottomRight() {
        return getArea().getPoint2();
    }

    /**
     * This method checks whether something with coordinates x and y intersects with this static component. This is done
     * using the internal isHit() method in the area class.
     * @param x the x coordinate of the thing with which we are checking if it intersects with this component.
     * @param y the y coordinate of the thing with which we are checking if it intersects with this component.
     * @return true if the thing is intersecting with this component else it returns false.
     */
    public boolean isHit(double x, double y){
        return getArea().isHit(x, y);
    }

    /**
     * This method checks whether something with intersects with this static component. This is done
     * using the internal isHit() method in the area class.
     * @param point a point for which we check if it intersects with this component's area.
     * @return true if the thing is intersecting with this component else it returns false.
     */
    public boolean isHit(Point point){
        return getArea().isHit(point);
    }
}
