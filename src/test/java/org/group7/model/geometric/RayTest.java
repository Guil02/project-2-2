package org.group7.model.geometric;

import org.group7.geometric.Area;
import org.group7.geometric.Point;
import org.group7.geometric.Ray;
import org.group7.model.component.Component;
import org.group7.model.component.playerComponents.Guard;
import org.group7.model.component.playerComponents.PlayerComponent;
import org.group7.model.component.staticComponents.Wall;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;


public class RayTest {

    // @Test
    //public void isHitTrue() {

      //  Area area = new Area(new Point(300,300),new Point(350,100));
       // Ray ray = new Ray(new Point(100,200),new Vector2D(1,0));
       // assertEquals(200, ray.isHit(area));
    //}

    @Test
    public void isHit() {
        PlayerComponent pc = new Guard(new Point(100,200), new Point(100,200),0);
        Ray ray = new Ray(pc);
        Wall wall = new Wall(new Point(300,300), new Point(350,100));
        ArrayList<Area> areaArray = new ArrayList<>();
        areaArray.add(wall.getArea());
        HashMap<Component,ArrayList<Double>> test = ray.isHit(areaArray);
        System.out.println("MELI");




    }


}
