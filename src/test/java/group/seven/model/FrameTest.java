package group.seven.model;

import group.seven.logic.geometric.XY;
import group.seven.model.agents.Frame;
import javafx.scene.transform.Translate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FrameTest {

    XY globalOrigin = new XY(2, 4);
    Frame frame = new Frame(new Translate(-globalOrigin.x(), -globalOrigin.y()));

    @Test
    void globalOriginToLocal() {
        XY localOrigin = frame.convertToLocal(globalOrigin);

        assertEquals(0, localOrigin.x());
        assertEquals(0, localOrigin.y());
    }

    @Test
    void localOriginToGlobal() {
        XY globalOrigin = frame.convertToGlobal(new XY(0, 0));

        assertEquals(2, globalOrigin.x());
        assertEquals(4, globalOrigin.y());
    }

    @Test
    void globalPointToLocal() {
        XY local = frame.convertToLocal(new XY(3, 2));

        assertEquals(1, local.x());
        assertEquals(-2, local.y());
    }

    @Test
    void localPointToGlobal() {
        XY global = frame.convertToGlobal(new XY(4, 3));

        assertEquals(6, global.x());
        assertEquals(7, global.y());
    }

    @Test
    void convertGlobalXtoLocal() {
        int x = 4;
        XY local = frame.convertToLocal(x, 0);
        assertEquals(2, local.x());
    }

    @Test
    void convertGlobalYtoLocal() {
        int y = 2;
        XY local = frame.convertToLocal(0, y);
        assertEquals(-2, local.y());
    }
}
