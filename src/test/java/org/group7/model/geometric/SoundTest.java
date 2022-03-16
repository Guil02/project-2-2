package org.group7.model.geometric;

import org.group7.geometric.Area;
import org.group7.geometric.Point;
import org.group7.model.State;
import org.group7.model.component.playerComponents.Guard;
import org.group7.model.component.playerComponents.Intruder;
import org.group7.model.component.playerComponents.PlayerComponent;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SoundTest {

   /* @Test
    public void createSoundArea() {
        PlayerComponent pc = new Guard(new Point(45,45), new Point(45,45),0);
        PlayerComponent pc2 = new Intruder(new Point(40,40), new Point(40,40),0);
        List<Guard> guard = new ArrayList<>();
        List<Intruder> intruder = new ArrayList<>();
        intruder.add((Intruder) pc2);
        guard.add((Guard) pc);

        State currentState = new State(guard,intruder);

        boolean check = checkSoundCollision(pc, currentState);
        assertTrue(check);
    }

    private boolean checkSoundCollision(PlayerComponent p, State currentState) {
        p.setMovingSound();
        Area soundArea = p.getMovingSound();
        for (Guard guard: currentState.getGuards()) {
            guard.setMovingSound();
            if (soundArea.isHit(guard.getX(), guard.getY())) {
                //make sure agents don't go nuts when they hear themselves
                if (p.getCoordinates() != guard.getCoordinates())
                    return true;
            }
        }
        for (Intruder intruder: currentState.getIntruders()) {
            intruder.setMovingSound();
            if (soundArea.isHit(intruder.getX(), intruder.getY())) {
                //make sure agents don't go nuts when they hear themselves
                if (p.getCoordinates() != intruder.getCoordinates())
                    return true;
            }
        }
        return false;
    }

    @Test
    public void isHit(){
        Area test = new Area(new Point(10,10),new Point(20,20));
        System.out.println(test.toString());
        System.out.println(test.isHit(15,20));
        System.out.println(test.isHit(new Point(15,15)));
    }

    */
}
