package org.group7.model;

import org.group7.model.component.playerComponents.PlayerComponent;
import org.group7.model.component.staticComponents.StaticComponent;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private StaticComponent staticComponent = null;
    private PlayerComponent playerComponent = null;
    public boolean explored = false;
    //array of the size of guards+intruders to see if agent saw the cell
    boolean[] exploredArray;
    public List<Boolean> seen;
    private int x;
    private int y;

    public Grid(int x, int y) {
        this.x = x;
        this.y = y;
        seen = new ArrayList<>();
    }

    public void setStaticComponent(StaticComponent staticComponent) {
        this.staticComponent = staticComponent;
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

    public String getStaticComp(){
        if(staticComponent!=null){
            switch (staticComponent.getComponentEnum()){
                case WALL -> {
                    return "W";
                }
                case TELEPORTER, TARGET_AREA -> {
                    return "T";
                }
                case SHADED_AREA -> {
                    return "S";
                }
                case INTRUDER_SPAWN_AREA -> {
                    return "I";
                }
                case GUARD_SPAWN_AREA -> {
                    return "G";
                }
                default -> {
                    return " ";
                }
            }
        } else return " ";
    }

    @Override
    public String toString() {
        return getStaticComp();
    }

    @Override
    public Grid clone() {
        Grid g = new Grid(x, y);
        g.setPlayerComponent(this.playerComponent);
        g.setStaticComponent(this.staticComponent);
        g.explored = this.explored;
        return g;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
