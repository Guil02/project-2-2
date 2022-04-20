package org.group7.model.component.staticComponents;

import org.group7.gui.geometric.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StaticComponentTest {

    @Test
    void getTopLeft() {
        Point point = new Point(2,3);
        assertEquals(point, createComponent().getTopLeft());
    }

    @Test
    void getBottomRight() {
        Point point = new Point(4,8);
        assertEquals(point, createComponent().getBottomRight());
    }

    @Test
    void isHit1() {
        Point inside = new Point(3,5);
        assertTrue(createComponent().isHit(inside));
    }

    @Test
    void isHit2() {
        Point outside = new Point(1,2);
        assertFalse(createComponent().isHit(outside));
    }

    @Test
    void isHit3(){
        Point partiallyInsideX = new Point(3,1);
        assertFalse(createComponent().isHit(partiallyInsideX));
    }

    @Test
    void isHit4(){
        Point partiallyInsideY = new Point(1,5);
        assertFalse(createComponent().isHit(partiallyInsideY));
    }

    private StaticComponent createComponent(){
        Point point = new Point(2,3);
        Point point2 = new Point(4,8);
        return new Wall(point, point2,null);
    }
}