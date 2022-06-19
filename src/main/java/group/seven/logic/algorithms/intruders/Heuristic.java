package group.seven.logic.algorithms.intruders;

import group.seven.logic.algorithms.pathfinding.ANode;

public interface Heuristic {

    <T extends ANode> double calculate(T from, T to);
}
