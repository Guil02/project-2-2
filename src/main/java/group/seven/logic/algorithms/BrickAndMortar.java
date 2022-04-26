package group.seven.logic.algorithms;

import group.seven.enums.AlgorithmType;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;

public class BrickAndMortar implements Algorithm {

    private Agent agent;

    public BrickAndMortar(Agent agent) {
        this.agent = agent;
    }

    @Override
    public Move getNext() {
        return null;
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.BRICK_AND_MORTAR;
    }
}
