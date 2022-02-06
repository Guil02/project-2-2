package org.group7.model.component.staticComponents;

import org.group7.model.component.Component;
import org.group7.utils.Point;

public abstract class StaticComponent extends Component {
    private Point topLeft;
    private Point bottomRight;

    public StaticComponent(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public StaticComponent(int x1, int y1, int x2, int y2){
        topLeft = new Point(x1,y1);
        bottomRight = new Point(x2, y2);
    }

    public abstract String getTexture();
}
