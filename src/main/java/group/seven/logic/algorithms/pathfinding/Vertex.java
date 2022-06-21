package group.seven.logic.algorithms.pathfinding;

import group.seven.logic.geometric.VectorPoint;
import group.seven.logic.geometric.XY;

public class Vertex implements Comparable<Vertex> {
    int f;
    int g = Integer.MAX_VALUE;
    int h;
    Vertex parent;
    VectorPoint pos;
    int visits = 0;
    int distanceToTarget;

    public int f(Vertex start, VectorPoint target) {
        g = start.g + 1; //pos.manhattan(start.pos); -> breaks teleporter feasibility
        h = pos.manhattan(target);
        distanceToTarget = pos.distance(target);
        return f = g + h
                + visits;
    }

    @Override
    public int compareTo(Vertex o) {
        if (f == o.f) {
            return Integer.compare(distanceToTarget, o.distanceToTarget);
        }
        return Integer.compare(f, o.f);
    }

    public Vertex withPos(XY exit) {
        pos = exit;
        return this;
    }

    public int getX() {
        return pos.getXY().x();
    }

    public int getY() {
        return pos.getXY().y();
    }
}
