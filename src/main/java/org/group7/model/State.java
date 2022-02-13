package org.group7.model;

import org.group7.geometric.Vector2D;
import org.group7.model.component.markerComponent.MarkerComponent;
import org.group7.model.component.playerComponents.Guard;
import org.group7.model.component.playerComponents.Intruder;

import java.util.ArrayList;
import java.util.List;

public class State {

    List<Vector2D> intruderCoords;
    List<Vector2D> guardCoords;

    public State(List<Guard> guards, List<Intruder> intruders) {
        guardCoords = new ArrayList<>();
        for (Guard guard : guards) {
            Vector2D v = new Vector2D(guard.getX(), guard.getY());
            guardCoords.add(v);
        }

        intruderCoords = new ArrayList<>();
        for (Intruder intruder : intruders) {
            Vector2D v = new Vector2D(intruder.getX(), intruder.getY());
            intruderCoords.add(v);
        }
    }

    public List<Vector2D> getIntruderCoords() {
        return intruderCoords;
    }

    public List<Vector2D> getGuardCoords() {
        return guardCoords;
    }

    @Override
    public String toString() {
        return "State{" +
                "intruderCoords=" + intruderCoords +
                ", guardCoords=" + guardCoords +
                '}';
    }
}
