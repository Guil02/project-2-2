package group.seven.logic.geometric;

/**
 * Originally created to override Point2D methods to return
 * Integers and Vector objects instead of doubles and Point2D
 * <p>
 * Not sure if I will keep
 */
public interface VectorPoint {
    int distance(VectorPoint vector);

    int manhattan(VectorPoint vector);

    XY getXY();
}
