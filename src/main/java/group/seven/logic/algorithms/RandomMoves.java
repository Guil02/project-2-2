package group.seven.logic.algorithms;

import group.seven.enums.Action;
import group.seven.enums.AlgorithmType;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;

public class RandomMoves implements Algorithm {
    private Agent agent;
    public RandomMoves(Agent agent) {
        this.agent = agent;
    }

    public RandomMoves() {
    }

    @Override
    public Move getNext() {
        //TODO: implement a random moves agent

        return new Move(Action.FLIP, 0, agent);
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.RANDOM;
    }
}
