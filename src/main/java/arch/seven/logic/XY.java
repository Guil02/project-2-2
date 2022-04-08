package arch.seven.logic;

/**
 * Final object which stores an (x, y) as doubles.
 * Use integer constructor for ints
 * getX() and getY() return integers
 * x() and y() return doubles
 *
 * @param x coordinate
 * @param y coordinate
 */
public record XY(double x, double y) {
    /**
     * Constructor for integers
     * @param x an integer
     * @param y and integer
     */
    public XY(int x, int y) {
        this(x, (double) y);
    }

    /**
     * Gets integer value of "x"
     * @return x as integer
     */
    public int getX() {
        return (int) x;
    }

    /**
     * Gets integer value of "y"
     * @return y as integer
     */
    public int getY() {
        return (int) y;
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }

}
