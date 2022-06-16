package group.seven.logic.algorithms.pathfinding;

import group.seven.enums.TileType;
import group.seven.logic.geometric.Vector;
import group.seven.logic.geometric.XY;
import group.seven.model.environment.Tile;
import javafx.geometry.Point2D;

import static group.seven.enums.TileType.UNKNOWN;

//TODO: everything in this package is
public class ANode implements Comparable<ANode> {
    public int x, y;
    public Point2D pxy;
    public XY xy;
    public double f, h, g;
    public ANode parent;
    public TileType type;
    public double timestamp;
    public boolean traversed;


    /**
     * Root constructor
     *
     * @param x (global) x coordinate
     * @param y (global) y coordinate
     */
    public ANode(int x, int y) {
        this.x = x;
        this.y = y;
        xy = new XY(x, y);
        pxy = new Point2D(x, y);
        type = UNKNOWN;
    }

    /**
     * Child constructor
     *
     * @param xy     coordinates
     * @param parent parent in path of this node
     */
    public ANode(XY xy, ANode parent) {
        this(xy.x(), xy.y());
        this.parent = parent;
    }

    private ANode(NodeBuilder builder) {
        x = builder.x;
        y = builder.y;
        pxy = builder.pxy;
        xy = builder.xy;
        f = builder.f;
        h = builder.h;
        g = builder.g;
        parent = builder.parent;
        type = builder.type;
        timestamp = builder.timestamp;
        traversed = builder.traversed;
    }

//    //----------Builder Methods-----------------
//    public ANode setTimestamp(double timestamp) {
//        this.timestamp = timestamp;
//        return this;
//    }
//
//    public ANode setTraversed() {
//        traversed = true;
//        return this;
//    }
//
//    public ANode setParent(ANode parent) {
//        this.parent = parent;
//        return this;
//    }
//
//    //---------------------------------------


    @Override
    public int compareTo(ANode o) {
        //return f <= o.f ? -1 : 1;
        if ((int) f == (int) o.f) return Math.random() < 0.5 ? -1 : 1; //random tie breaker
        return (int) (this.f - o.f);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point2D getPxy() {
        return pxy;
    }

    public XY getXy() {
        return xy;
    }

    public double getF() {
        return f;
    }

    public double getH() {
        return h;
    }

    public double getG() {
        return g;
    }

    public ANode getParent() {
        return parent;
    }

    public TileType getType() {
        return type;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public boolean isTraversed() {
        return traversed;
    }

    public boolean equals(ANode o) {
        return xy == o.xy;
    }

    /**
     * {@code ANode} builder static inner class.
     */
    public static final class NodeBuilder {
        private int x;
        private int y;
        private Point2D pxy;
        private XY xy;
        private double f;
        private double h;
        private double g;
        private ANode parent;
        private TileType type;
        private double timestamp;
        private boolean traversed;

        public NodeBuilder(Tile tile) {
            this.x = tile.getX();
            this.y = tile.getY();
            this.pxy = new Vector(tile.getXY());
            this.xy = tile.getXY();
            this.f = 0;
            this.h = 0;
            this.g = 0;
//            this.parent = copy.getParent();
            this.type = tile.getType();
//            this.timestamp = copy.getTimestamp();
//            this.traversed = tile.isTraversed();
        }

        public static ANode buildDefault(XY xy) {
            return new NodeBuilder(xy.x(), xy.y()).build();
        }

        public NodeBuilder(int x, int y) {
            this.x = x;
            this.y = y;
            this.xy = new XY(x, y);
            this.pxy = new Point2D(x, y);
            this.type = UNKNOWN;
        }

        public NodeBuilder(ANode copy) {
            this.x = copy.getX();
            this.y = copy.getY();
            this.pxy = copy.getPxy();
            this.xy = copy.getXy();
            this.f = copy.getF();
            this.h = copy.getH();
            this.g = copy.getG();
            this.parent = copy.getParent();
            this.type = copy.getType();
            this.timestamp = copy.getTimestamp();
            this.traversed = copy.isTraversed();
        }

        /**
         * Sets the {@code x} and returns a reference to this Builder enabling method chaining.
         *
         * @param x the {@code x} to set
         * @return a reference to this Builder
         */
        public NodeBuilder withX(int x) {
            this.x = x;
            return this;
        }

        /**
         * Sets the {@code y} and returns a reference to this Builder enabling method chaining.
         *
         * @param y the {@code y} to set
         * @return a reference to this Builder
         */
        public NodeBuilder withY(int y) {
            this.y = y;
            return this;
        }

        /**
         * Sets the {@code pxy} and returns a reference to this Builder enabling method chaining.
         *
         * @param pxy the {@code pxy} to set
         * @return a reference to this Builder
         */
        public NodeBuilder withPxy(Point2D pxy) {
            this.pxy = pxy;
            return this;
        }

        /**
         * Sets the {@code xy} and returns a reference to this Builder enabling method chaining.
         *
         * @param xy the {@code xy} to set
         * @return a reference to this Builder
         */
        public NodeBuilder withXy(XY xy) {
            this.xy = xy;
            return this;
        }

        /**
         * Sets the {@code f} and returns a reference to this Builder enabling method chaining.
         *
         * @param f the {@code f} to set
         * @return a reference to this Builder
         */
        public NodeBuilder withF(double f) {
            this.f = f;
            return this;
        }

        /**
         * Sets the {@code h} and returns a reference to this Builder enabling method chaining.
         *
         * @param h the {@code h} to set
         * @return a reference to this Builder
         */
        public NodeBuilder withH(double h) {
            this.h = h;
            this.f += h; //f = g + h;
            return this;
        }

        /**
         * Sets the {@code g} and returns a reference to this Builder enabling method chaining.
         *
         * @param g the {@code g} to set
         * @return a reference to this Builder
         */
        public NodeBuilder withG(double g) {
            this.g = g;
            this.f += g; //addition is associative so we can just add this heuristic directly to f
            return this;
        }

        /**
         * Sets the {@code parent} and returns a reference to this Builder enabling method chaining.
         *
         * @param parent the {@code parent} to set
         * @return a reference to this Builder
         */
        public NodeBuilder withParent(ANode parent) {
            this.parent = parent;
            return this;
        }

        /**
         * Sets the {@code type} and returns a reference to this Builder enabling method chaining.
         *
         * @param type the {@code type} to set
         * @return a reference to this Builder
         */
        public NodeBuilder withType(TileType type) {
            this.type = type;
            return this;
        }

        /**
         * Sets the {@code timestamp} and returns a reference to this Builder enabling method chaining.
         *
         * @param timestamp the {@code timestamp} to set
         * @return a reference to this Builder
         */
        public NodeBuilder withTimestamp(double timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        /**
         * Sets the {@code traversed} and returns a reference to this Builder enabling method chaining.
         *
         * @param traversed the {@code traversed} to set
         * @return a reference to this Builder
         */
        public NodeBuilder withTraversed(boolean traversed) {
            this.traversed = traversed;
            return this;
        }

        /**
         * Returns a {@code ANode} built from the parameters previously set.
         *
         * @return a {@code ANode} built with parameters of this {@code ANode.Builder}
         */
        public ANode build() {
            return new ANode(this);
        }
    }
}