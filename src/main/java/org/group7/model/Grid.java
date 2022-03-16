package org.group7.model;

import org.group7.enums.ComponentEnum;
import org.group7.model.component.Component;
import org.group7.model.component.playerComponents.PlayerComponent;
import org.group7.model.component.staticComponents.StaticComponent;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private StaticComponent staticComponent = null;
    private PlayerComponent playerComponent = null;
    public boolean explored = false;
    //array of the size of guards+intruders to see if agent saw the cell
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

    public void exploreAgent(int id) {seen.set(id,Boolean.TRUE);}

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



    public ComponentEnum getStaticCompE(){
        if(staticComponent!=null){
            switch (staticComponent.getComponentEnum()){
                case WALL -> {
                    return ComponentEnum.WALL;
                }
                case TELEPORTER -> {
                    return ComponentEnum.TELEPORTER;
                }
                case TARGET_AREA -> {
                    return ComponentEnum.TARGET_AREA;
                }
                case SHADED_AREA -> {
                    return ComponentEnum.SHADED_AREA;
                }
                case INTRUDER_SPAWN_AREA -> {
                    return ComponentEnum.INTRUDER_SPAWN_AREA;
                }
                case GUARD_SPAWN_AREA -> {
                    return ComponentEnum.GUARD_SPAWN_AREA;
                }
                default -> {
                    return null;
                }
            }
        } else return null;
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
