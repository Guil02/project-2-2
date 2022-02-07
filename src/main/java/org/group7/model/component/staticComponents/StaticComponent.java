package org.group7.model.component.staticComponents;

import org.group7.geometric.Area;
import org.group7.model.component.Component;
import org.group7.geometric.Point;

public abstract class StaticComponent extends Component {
    private Area area;

    public StaticComponent(Point topLeft, Point bottomRight) {
        area = new Area(topLeft, bottomRight);
    }

    public Point getTopLeft() {
        return area.getPoint1();
    }

    public Point getBottomRight() {
        return area.getPoint2();
    }

    public Area getArea() {
        return area;
    }

    public boolean isHit(double x, double y){
        return area.isHit(x, y);
//        return x > topLeft.x && x < bottomRight.x && y > topLeft.y && y < bottomRight.y;
    }

    public boolean isHit(Point point){
        return area.isHit(point);
//        return point.x > topLeft.x && point.x < bottomRight.x && point.y > topLeft.y && point.y < bottomRight.y;
    }
}
