package org.group7.model.component.markerComponent;

import org.group7.gui.geometric.Point;
import org.group7.model.Scenario;
import org.group7.model.component.Component;

public abstract class MarkerComponent extends Component {
    public MarkerComponent(Point point1, Point point2, Scenario scenario) {
        super(point1, point2, scenario);
    }
}
