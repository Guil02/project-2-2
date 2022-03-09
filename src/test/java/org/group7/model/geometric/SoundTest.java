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

    @Test
    public void createSoundArea() {
        PlayerComponent pc = new Guard(new Point(45,45), new Point(45,45),0);
        pc.setMovingSound();
        System.out.println(pc.getMovingSound());
        PlayerComponent pc2 = new Guard(new Point(40,40), new Point(40,40),0);
        List<Guard> guard = new ArrayList<>();
        List<Intruder> intruder = new ArrayList<>();
        guard.add((Guard) pc);
        State currentState = new State(guard,intruder);

        pc2.setMovingSound();
        Area soundArea = pc2.getMovingSound();
        System.out.println("soundArea "+soundArea);
        for (Guard inGuard: currentState.getGuards()) {
            //guard.setMovingSound();
            System.out.println("Guard X "+inGuard.getX());
            System.out.println("Guard Y "+inGuard.getY());
            if (soundArea.isHit(inGuard.getX(), inGuard.getY())) {
                System.out.println("WORKED");
            }
        }
    }

    @Test
    public void isHit(){
        Area test = new Area(new Point(10,20),new Point(20,10));
        System.out.println(test.isHit(15,19.9));
        System.out.println(test.isHit(new Point(15,15)));
    }
}
