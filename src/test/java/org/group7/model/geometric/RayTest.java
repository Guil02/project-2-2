package org.group7.model.geometric;

import org.group7.geometric.*;
import org.group7.model.component.Component;
import org.group7.model.component.playerComponents.Guard;
import org.group7.model.component.playerComponents.Intruder;
import org.group7.model.component.playerComponents.PlayerComponent;
import org.group7.model.component.staticComponents.ShadedArea;
import org.group7.model.component.staticComponents.TargetArea;
import org.group7.model.component.staticComponents.Wall;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;


public class RayTest {

    @Test
    public void isHit() {
        PlayerComponent pc = new Guard(new Point(250,299), new Point(250,299),0);
        Ray ray = new Ray(pc);
        Component wall1 = new Wall(new Point(300,300), new Point(350,100));
        Component wall2 = new ShadedArea(new Point(300,500), new Point(350,300));
        ArrayList<Component> areaArray = new ArrayList<>();
        areaArray.add(wall1);
        areaArray.add(wall2);
        HashMap<Integer, ArrayList<DistanceAngleTuple<Double, Vector2D>>> test = ray.getVisualField(areaArray);
        System.out.println("TEST");
    }

}
