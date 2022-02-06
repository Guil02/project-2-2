package org.group7.model.component.staticComponents;

import org.group7.utils.Point;

public class Wall extends StaticComponent {
    public Wall(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
    }

    public Wall(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
    }

    @Override
    public String getTexture() {
        return "";
    }


}
