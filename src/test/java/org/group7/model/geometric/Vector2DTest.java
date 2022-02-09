package org.group7.model.geometric;

import org.group7.geometric.Vector2D;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class Vector2DTest {

    @Test
    public void testAdd() {
        Vector2D one = new Vector2D(2,3);
        Vector2D two = new Vector2D(1,4);
        Vector2D added = one.add(two);
        assertTrue(added.getX() == 3 && added.getY() == 7);
    }
    @Test
    public void testSub() {
        Vector2D one = new Vector2D(2,3);
        Vector2D two = new Vector2D(-1,4);
        Vector2D substracted = one.sub(two);
        assertTrue(substracted.getX() == 3 && substracted.getY() == -1);
    }

    @Test
    public void testDot() {
        Vector2D one = new Vector2D(2,3);
        Vector2D two = new Vector2D(-1,4);
        double dotProd = one.dot(two);
        assertEquals(10, dotProd);
    }

     @Test
    public void testScale() {
        Vector2D v = new Vector2D(2,-3);
        double scalar = 3;
        Vector2D scaled = v.scale(scalar);
        assertTrue(scaled.getX() == 6 && scaled.getY() == -9);
    }

    @Test
    public void testDistance() {
        Vector2D one = new Vector2D(2,3);
        Vector2D two = new Vector2D(-1,4);
        double distance = one.distance(two);
        assertEquals(Math.sqrt(10), distance);
    }

    @Test
    public void testGetRotatedBy(){
        Vector2D vector2D = new Vector2D(1,0);
        Vector2D actual = vector2D.getRotatedBy(0.5*Math.PI);
        Vector2D expected = new Vector2D(0,1);
        assertEquals(expected, actual);
    }
}
