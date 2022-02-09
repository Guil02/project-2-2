package org.group7.model.geometric;

import org.group7.geometric.*;
import org.group7.model.component.Component;
import org.group7.model.component.playerComponents.Guard;
import org.group7.model.component.playerComponents.PlayerComponent;
import org.group7.model.component.staticComponents.Wall;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;


public class RayTest {

    @Test
    public void isHit() {
        PlayerComponent pc = new Guard(new Point(100,200), new Point(100,200),2*Math.PI);
        Ray ray = new Ray(pc);
        Component wall1 = new Wall(new Point(300,300), new Point(350,100));
        Component wall2 = new Wall(new Point(300,550), new Point(350,400));
        ArrayList<Component> areaArray = new ArrayList<>();
        areaArray.add(wall1);
        areaArray.add(wall2);
        HashMap<Integer, ArrayList<DistanceAngleTuple<Double, Vector2D>>> test = ray.getVisualField(areaArray);
        System.out.println("TEST");
    }



}
