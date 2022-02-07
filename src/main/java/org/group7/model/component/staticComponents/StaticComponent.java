package org.group7.model.component.staticComponents;

import org.group7.model.component.Component;
import org.group7.geometric.Point;

public abstract class StaticComponent extends Component {
    private Point topLeft;
    private Point bottomRight;

    public StaticComponent(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }
}
