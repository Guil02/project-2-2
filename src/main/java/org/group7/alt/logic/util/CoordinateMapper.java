package org.group7.alt.logic.util;

import javafx.geometry.Point2D;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import org.group7.alt.model.ai.Agents.Agent;

import java.awt.*;
import java.awt.geom.AffineTransform;


public class CoordinateMapper {

    public static Point getLocalOriginFromGlobal(Point agentOrigin) {
        Point2D p2D = new Translate(agentOrigin.x, agentOrigin.y).transform(0,0);
        return new Point( (int) p2D.getX(), (int) p2D.getY());
    }

    public static Point getLocalOriginFromGlobal(Point global, Point local) {
        return new Point(global.x - local.x, global.y - local.y);
    }

    public static Point convertGlobalToLocal(Point agentOrigin) {
        Point2D p2D = new Translate(agentOrigin.x, agentOrigin.y).transform(0,0);
        return new Point( (int) p2D.getX(), (int) p2D.getY());
    }

    public static Point convertGlobalToLocal(Point relativeOrigin, Point global) {
        return convertLocalToGlobal(new Point(-relativeOrigin.x,-relativeOrigin.y), global);
    }

    public static Point convertLocalToGlobal(Point relativeOrigin, Point local) {
        return new Point(relativeOrigin.x + local.x, relativeOrigin.y + local.y);
    }

    public static Point convertLocalToGlobal(Point convertGlobalToLocal) {
        return new Point(-1, -1);
    }
}
