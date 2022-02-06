package org.group7.model.component.playerComponents;

import org.group7.model.component.Component;

public abstract class PlayerComponent extends Component {
    private double x;
    private double y;

    public PlayerComponent(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
