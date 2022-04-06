package group.seven.logic.geo;

public class Point {

    double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y) {
        this(x, (double) y);
    }

    @Override
    public String toString() {
        return "p(x" + x +
                ", " + y + ')';
    }

    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (this == o) {
            result = true;
        } else if (o instanceof Point) {
            Point point = (Point) o;
            if (Double.compare(point.x, x) == 0) {
                result = Double.compare(point.y, y) == 0;
            }
        }

        return result;
    }

    @Override
    public int hashCode() {
        return Integer.parseInt("" + (int)x + (int)y);
    }

}
