package group.seven.logic.algorithms;

import group.seven.enums.Action;
import group.seven.enums.AlgorithmType;
import group.seven.enums.Cardinal;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;
import group.seven.model.environment.TileNode;
import group.seven.model.environment.Scenario;

import java.util.*;

import static group.seven.enums.AlgorithmType.EVAW;
import static group.seven.enums.TileType.WALL;

public class EVAW implements Algorithm {
    private Cardinal lastMove = null;
    Agent agent;
    private Scenario s;

    public EVAW(Agent a) {
        this.agent = a;
        s = a.scenario;
    }

//    public EVAW(Agent a, Scenario scenario) {
//        this.agent = a;
//        s = scenario;
//    }

    LinkedList<Move> moves = new LinkedList<>();

    /**
     * This method will check if
     *
     * @return the next move to be executed by the game runner
     */
    @Override
    public Move getNext() {
        if (moves.isEmpty()) {
            calculateMove();
        }
        if (moves.isEmpty()) {
            return new Move(Action.NOTHING, 0, agent);
        }
//        Move nextMove = moves.get(0);
//        moves.remove(0);
//        return nextMove;

        return moves.poll();
    }


    /**
     * This method will find the next action  to do based upon the Ants algorithm proposed in:
     * Theoretical Study of ant-based Algorithms for Multi-Agent Patrolling. by Arnaud Glad, Olivier Simonin,
     * Olivier Buffet and François Charpillet.
     */
    public void calculateMove() {
        TileNode target = chooseTile(agent.getX(), agent.getY(), 10); //TODO, this is agents global pos
        s.TILE_MAP.dropPheromone(target.getX(), target.getY());
        AStarPathFinder pf = new AStarPathFinder(agent, new XY(target.getX(), target.getY()));
        moves.addAll(pf.findPath());
    }

    /**
     * inner class to store the new target orientation and target node.
     */
    static class Difference {
        TileNode target;
        Cardinal targetOrientation;
    }

    /**
     * This method will check if the new tile has a lower pheromone strength compared to the current lowest.
     *
     * @param difference         an inner class that stores the current lowest value pheromone tile
     * @param compare            The tile to which the current lowest is compared
     * @param compareOrientation the orientation of the tile {@code compare} with respect to the initial position.
     */
    public void checkDifference(Difference difference, TileNode compare, Cardinal compareOrientation) {
        if (compare != null) {
            if (compare.getPheromoneStrength() < difference.target.getPheromoneStrength()) {
                difference.target = compare;
                difference.targetOrientation = compareOrientation;
            } else if (compare.getPheromoneStrength() == difference.target.getPheromoneStrength()) {
                if (Math.random() > 0.5) {
                    difference.target = compare;
                    difference.targetOrientation = compareOrientation;
                }
            }
        }
    }

    //receives x and y in global position
    public TileNode chooseTile(int x, int y, int distance) {
        TileNode target = agent.getMapPosition(x, y); //x and y are agent global here
        List<TileNode> choice = new ArrayList<>();
        choice.add(target);
        for (int i = x - distance; i < x + distance; i++) {
            for (int j = y - distance; j < y + distance; j++) {
                try {
                    TileNode t = agent.getMapPosition(i, j);
                    if (t.getType() == WALL) {
                        continue;
                    }
                    if (t.getPheromoneStrength() < target.getPheromoneStrength()) {
                        choice.clear();
                        choice.add(t);
                        target = t;
                    } else if (t.getPheromoneStrength() == target.getPheromoneStrength() && Math.random() > 0.5) {
                        choice.add(t);
                        target = t;
                    }
                } catch (NullPointerException ignored) {
                }
            }
        }

        if (choice.size() > 1) {
            Random rand = new Random();
            return choice.get(rand.nextInt(choice.size()));
        } else return target;
    }

    @Override
    public AlgorithmType getType() {
        return EVAW;
    }
}
