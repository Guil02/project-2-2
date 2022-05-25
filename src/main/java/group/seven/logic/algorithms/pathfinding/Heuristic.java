package group.seven.logic.algorithms.pathfinding;

public interface Heuristic {

    <T extends ANode> double calculate(T from, T to);
}
