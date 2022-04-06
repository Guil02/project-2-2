package org.group7.model;

import org.group7.enums.ComponentEnum;
import org.group7.model.component.playerComponents.PlayerComponent;
import org.group7.model.component.staticComponents.StaticComponent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.group7.enums.ComponentEnum.EMPTY_SPACE;
import static org.group7.enums.ComponentEnum.WALL;

public class Grid {
    public static int[] numGridsSeenBy;

    private StaticComponent staticComponent = null;
    private PlayerComponent playerComponent = null;
    public boolean explored = false;
    //array of the size of guards+intruders to see if agent saw the cell
    public List<Boolean> seen;
    private final int x;
    private final int y;

    private ComponentEnum type;

    public Grid(int x, int y) {
        this.x = x;
        this.y = y;
        seen = new ArrayList<>(10);
        type = EMPTY_SPACE;
    }

    public void setStaticComponent(StaticComponent staticComponent) {
        this.staticComponent = staticComponent;
        type = staticComponent.getComponentEnum();
    }

    public void setPlayerComponent(PlayerComponent playerComponent) {
        this.playerComponent = playerComponent;
    }

    public StaticComponent getStaticComponent() {
        return staticComponent;
    }

    public PlayerComponent getPlayerComponent() {
        return playerComponent;
    }

    public void explore(){
        this.explored = true;
    }

    public void exploreAgent(int id) {
        if (!seen.get(id)) numGridsSeenBy[id]++;

        seen.set(id,Boolean.TRUE);
    }

    public ComponentEnum getStaticCompE(){
        return getType();
    }

    public ComponentEnum getType() {
        if (playerComponent != null)
            return playerComponent.getComponentEnum();
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    @Override
    public String toString() {
        return "Grid{" +
                "staticComponent=" + staticComponent +
                ", playerComponent=" + playerComponent +
                ", x=" + x +
                ", y=" + y +
                ", type=" + type +
                '}';
    }

    @Override
    public Grid clone() {
        Grid g = new Grid(x, y);
        g.setPlayerComponent(this.playerComponent);
        g.setStaticComponent(this.staticComponent);
        g.explored = this.explored;
        return g;
    }
}
