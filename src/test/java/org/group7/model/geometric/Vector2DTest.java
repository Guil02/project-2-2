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

}
