package group.seven.logic.geometric;

import javafx.geometry.Rectangle2D;

/**
 * Implements {@code Rectangle2D} abstract class with ability
 * to return common methods as integers instead of doubles.
 */
public class Rectangle extends Rectangle2D {
    /**
     * Creates a new instance of {@code Rectangle2D}.
     *
     * @param minX   The x coordinate of the upper-left corner of the {@code Rectangle2D}
     * @param minY   The y coordinate of the upper-left corner of the {@code Rectangle2D}
     * @param width  The width of the {@code Rectangle2D}
     * @param height The height of the {@code Rectangle2D}
     */
    public Rectangle(double minX, double minY, double width, double height) {
        super(minX, minY, width, height);
    }

    public Rectangle() {
        super(0,0,0,0);
    }

    /**
     * Returns the min x value of this rectangle as an int.
     * Use the {@code getMinX()} if you need it as a double
     * @return min x value as int
     */
    public int getX() {
        return (int) getMinX();
    }

    /**
     * Returns the min y value of this rectangle as an int.
     * Use the {@code getMinY()} if you need it as a double
     * @return min y value as int
     */
    public int getY() {
        return (int) getMinY();
    }

    /**
     * Extends the {@code Rectangle2D} to return width as integer
     * @return getWidth() cast to an int
     */
    public int getIntWidth() {
        return (int) getWidth();
    }

    /**
     * Extends the {@code Rectangle2D} to return height as integer
     * @return getHeight() cast to an int
     */
    public int getIntHeight() {
        return (int) getHeight();
    }

    /**
     * Extends the {@code Rectangle2D} to return int instead of double
     * @return getMaxX() cast to an int
     */
    public int getMaxIntX() {
        return (int) getMaxX();
    }

    /**
     * Extends the {@code Rectangle2D} to return int instead of double
     * @return getMaxY() cast to an int
     */
    public int getMaxIntY() {
        return (int) getMaxY();
    }
}
