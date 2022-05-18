package group.seven.logic.algorithms;

import group.seven.enums.Action;
import group.seven.enums.AlgorithmType;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;
import group.seven.model.environment.Scenario;

public class RandomMoves implements Algorithm {
    private Agent agent;
    public RandomMoves(Agent agent) {
        this.agent = agent;
    }

    public RandomMoves() {
    }

    /**
     * Calculates a random action for a agent where the chance of moving forward has a higher probability
     * then a change in cardinal direction. Move forward = 1/3 - each cardinal change = 1/6
     * @return a new move with taking into account the max viewing distance if the move is forward
     */
    @Override
    public Move getNext() {
        int randomInt = (int) (Math.random() * 6);
        if (randomInt == 0) {
            return new Move(Action.TURN_UP, 0, agent);
        }
        if (randomInt == 1) {
            return new Move(Action.TURN_RIGHT, 0, agent);
        }
        if (randomInt == 2) {
            return new Move(Action.TURN_LEFT, 0, agent);
        }
        if (randomInt == 3) {
            return new Move(Action.TURN_DOWN, 0, agent);
        } else {
            int distance = (int) (Math.random() * Scenario.VIEW_DISTANCE);
            return new Move(Action.MOVE_FORWARD, (distance == 0 ? 1 : distance), agent);
        }
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.RANDOM;
    }
}
