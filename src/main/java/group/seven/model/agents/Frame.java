package group.seven.model.agents;

import group.seven.logic.geometric.XY;
import javafx.geometry.Point2D;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Transform;

import java.util.Collection;
import java.util.List;

/**
 * Handles coordinate conversions. Uses JavaFX geometry classes.
 * To create this record: new Frame(new Translate(-spawnPoint.getX(), -spawnPoint.getY()))
 * @param frame local agent's coordinate frame Transform
 */
public record Frame(Transform frame) {

    /**
     * Converts global coordinates as doubles to local agent coordinates into an XY record
     * @param x global integer x-coordinate
     * @param y global integer y-coordinate
     * @return new XY record which represents the same point but transformed into agents local coordinate space
     * as a XY record containing integers
     */
    public XY convertToLocal(int x, int y)  {
        return new XY(convertToLocal(x, (double)y));
    }

    public XY convertToLocal(XY xy) {
        return new XY(convertToLocal((double) xy.x(), xy.y()));
    }
    /**
     * Converts a Point2D in global space to a Point2D in agent's local coordinate space
     * @param globalPoint global point which to convert
     * @return same point represented in agents coordinate frame, as a Point2D object
     */
    public Point2D convertToLocal(Point2D globalPoint)  {
        return frame.transform(globalPoint.getX(), globalPoint.getY());
        //return convertToLocal(globalPoint.getX(), globalPoint.getY());
    }

    /**
     * Convert global coordinates as doubles to local (agent) coordinates into a Point2D
     * @param x global x-coordinate
     * @param y global y-coordinate
     * @return Point2D representing the Point in agent's local coordinate frame
     */
    public Point2D convertToLocal(double x, double y)  {
        return frame.transform(x, y);
    }

    /**
     * Converts a point from the agent's local coordinate frame to the global coordinate frame
     * @param localPoint a Point2D object that stores the agent's local representation of the point
     * @return a Point2D object that represents the same point, but in global coordinates
     */
    public Point2D convertToGlobal(Point2D localPoint) {
        Point2D global = Point2D.ZERO;
        try { global = frame.inverseTransform(localPoint); }
        catch (NonInvertibleTransformException e) { e.printStackTrace(); }
        return global;
    }

    /**
     * Converts an XY record from local to global coordinates
     * @param xy (x', y') coordinates from agent's local frame
     * @return XY record (x, y) of global representation of the agent's coordinates
     */
    public XY convertToGlobal(XY xy) {
        return convertToGlobal(xy.x(), xy.y());
    }

    /**
     * Converts an integer x, y coordinate from agent local frame to same point in global frame
     * @param x integer x-coordinate in agent's local coordinate frame
     * @param y integer y-coordinate in agent's local coordinate frame
     * @return a Point2D object that represents the agent's local point, but in the global coordinate frame
     */
    public XY convertToGlobal(int x, int y) {
        return new XY(convertToGlobal(new Point2D(x, y)));
    }

    /**
     * Converts a Collection of global Point2D points into the same points in the agent's local coordinate frame
     * @param points Collection (List, etc) of Point2D objects in global space
     * @return List of the global points converted into agent local Point2D points
     */
    public List<Point2D> convertPoints(Collection<? extends Point2D> points) {
        return points.stream().map(this::convertToLocal).toList();
    }

    /**
     * Returns the agent's local origin (0,0); into its global coordinate representation
     * @return a Point2D object that contains the true origin of the agent in global coordinate frame
     */
    public Point2D getRelativeOrigin() {
        return new Point2D(frame.getTx(), frame.getTy()).multiply(-1);
    }


}
