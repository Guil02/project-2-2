package org.group7.model;

import org.group7.model.component.playerComponents.PlayerComponent;
import org.group7.model.component.staticComponents.StaticComponent;

public class Grid {
    private StaticComponent staticComponent = null;
    private PlayerComponent playerComponent = null;
    boolean explored = false;

    public Grid() {
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
        }
        else return " ";
    }

    @Override
    public String toString() {
        return getStaticComp();
    }
}
