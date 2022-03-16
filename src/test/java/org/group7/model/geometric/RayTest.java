package org.group7.model.geometric;

import org.group7.agentVision.Ray;
import org.group7.geometric.*;
import org.group7.model.component.Component;
import org.group7.model.component.playerComponents.Guard;
import org.group7.model.component.playerComponents.PlayerComponent;
import org.group7.model.component.staticComponents.ShadedArea;
import org.group7.model.component.staticComponents.Wall;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.group7.enums.ComponentEnum.WALL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class RayTest {

   /* @Test
    public void isHit() {
        //agents viewFieldAngle 20 degrees
        PlayerComponent pc = new Guard(new Point(250,299), new Point(250,299),0, null);
        Ray ray = new Ray(pc);
        Component wall1 = new Wall(new Point(300,300), new Point(350,100), null);
        Component wall2 = new ShadedArea(new Point(300,500), new Point(350,300), null);
        ArrayList<Component> areaArray = new ArrayList<>();
        areaArray.add(wall1);
        areaArray.add(wall2);
        //ray rotation by 5 degrees
        HashMap<Integer, ArrayList<DistanceAngleTuple<Double, Vector2D>>> test = ray.getVisualField(areaArray);
        int seenWalls = test.get(3).size();
        int seenShadedAreas = test.get(5).size(); // needs to be 4

        assertEquals(3, seenWalls ); assertEquals(4, seenShadedAreas );
//        assertEquals(50.0,  test.get(0).get(0).getDistance() );

    }
    @Test
    public void sortList() {
        PlayerComponent pc = new Guard(new Point(250,299), new Point(250,299),0);
        Ray ray = new Ray(pc);
        Component wall1 = new Wall(new Point(1000,1000), new Point(2250,900));
        Component wall2 = new ShadedArea(new Point(300,500), new Point(350,300));
        ArrayList<Component> areaArray = new ArrayList<>();
        areaArray.add(wall1);
        areaArray.add(wall2);
        ray.sortList(areaArray);
        assertEquals(areaArray.get(0), wall2);
        assertEquals(areaArray.get(1), wall1);

    }


    @Test
    public void checkRotation() {
        Vector2D vector2D = new Vector2D(0,1);
        Vector2D actual = vector2D.getRotatedBy(Math.toRadians(-90));
        Vector2D expected = new Vector2D(-1,0);
        assertEquals(expected, actual);
    }

    @Test
    //Checking if agent looks over/throw  another guard (apart from wall)
    public void isHit2() {
        PlayerComponent pc = new Guard(new Point(50,100), new Point(50,100),0, null);
        Ray ray = new Ray(pc);
        Component guard = new Guard(new Point(100,150), new Point(125,50), 0, null);
        Component wall = new Wall(new Point(150,150), new Point(200,50), null);
        ArrayList<Component> areaArray = new ArrayList<>();
        areaArray.add(guard);
        areaArray.add(wall);
        HashMap<Integer, ArrayList<DistanceAngleTuple<Double, Vector2D>>> test = ray.getVisualField(areaArray);

    }
*/
    @Test
    public void testt() {
        for (int y = 1; y < 10; y++) { //check straight
            //cant go higher than y=0, so if the number is positive is out of bound

                System.out.println(y);
                if (y >= 5){
                    break;
                }


        }
        for (int i = 2; i<5;i++) {
            System.out.println("new "+i);
        }

    }
}
