package group.seven.logic.algorithms.pathfinding;

public class Manhattan implements Heuristic {

    @Override
    public double calculate(ANode from, ANode to) {
        return Math.abs(from.x - to.x) + Math.abs(from.y - to.y);
    }

}
