package org.group7.alt.logic.util.records;

import javafx.geometry.Point2D;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Transform;

import java.util.Collection;
import java.util.List;

public record Frame(Transform frame) {

    //converts to local
    public Point2D convert(int x, int y)  {
        return frame.transform(x, y);
    }

    //converts to local
    public Point2D convert(Point2D globalPoint)  {
        return frame.transform(globalPoint.getX(), globalPoint.getY());
    }

    //converts local points to global
    public Point2D convertLocal(Point2D localPoint) {
        Point2D global = Point2D.ZERO;
        try {
            global = frame.inverseTransform(localPoint);
        } catch (NonInvertibleTransformException e) {
            e.printStackTrace();
        }
        return global;
    }

    public Point2D convertLocal(int x, int y) {
        return convertLocal(new Point2D(x, y));
    }

    public List<Point2D> convertPoints(Collection<? extends Point2D> points) {
        return points.stream().map(this::convert).toList();
    }

    //returns the agent's origin in global coordinates
    public Point2D getRelativeOrigin() {
        return new Point2D(frame.getTx(), frame.getTy()).multiply(-1);
    }
}
