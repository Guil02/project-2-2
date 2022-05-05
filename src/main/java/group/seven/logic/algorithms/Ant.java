package group.seven.logic.algorithms;

import group.seven.enums.AlgorithmType;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;

public class Ant implements Algorithm {

    private final Agent agent;

    public Ant(Agent agent) {
        this.agent = agent;
    }

    @Override
    public Move getNext() {
        return null;
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.ANT;
    }
}
