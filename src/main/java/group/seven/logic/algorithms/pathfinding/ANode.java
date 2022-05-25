package group.seven.logic.algorithms.pathfinding;

public class ANode implements Comparable<ANode> {
    public int x, y;
    public double f, h, g;
    public ANode parent;

    public ANode(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(ANode o) {
        return f <= o.f ? -1 : 1;
    }
}