package org.group7.model.geometric;

import org.group7.geometric.Area;
import org.group7.geometric.Point;
import org.group7.geometric.Ray;
import org.group7.geometric.Vector2D;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RayTest {

    @Test
    public void isHitFalse() {
        Area area = new Area(new Point(300,100),new Point(400,300));
        Ray ray = new Ray(new Point(100,200),new Vector2D(0,1));
        assertFalse(ray.isHit(area));
    }

    @Test
    public void isHitTrue() {
        Area area = new Area(new Point(300,100),new Point(300,300));
        Ray ray = new Ray(new Point(100,200),new Vector2D(1,0));
        assertTrue(ray.isHit(area));
    }
}
