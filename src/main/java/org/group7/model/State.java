package org.group7.model;

import org.group7.geometric.Vector2D;
import org.group7.model.component.markerComponent.MarkerComponent;
import org.group7.model.component.playerComponents.Guard;
import org.group7.model.component.playerComponents.Intruder;

import java.util.ArrayList;
import java.util.List;

public class State {

    private List<Intruder> intruders;
    private List<Guard> guards;

    public State(List<Guard> guards, List<Intruder> intruders) {
        this.guards = guards;
        this.intruders = intruders;
        /*for (Guard guard : guards) {
            guards.add(guard);
        }

        for (Intruder intruder : intruders) {
            intruders.add(intruder);
        }*/
    }

    public List<Intruder> getIntruders() {
        return intruders;
    }

    public List<Guard> getGuards() {
        return guards;
    }

    @Override
    public String toString() {
        return "State{" +
                "intruderCoords=" + intruders +
                ", guardCoords=" + guards +
                '}';
    }
}
