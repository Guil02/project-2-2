package group.seven.logic.algorithms;

import group.seven.enums.Action;
import group.seven.enums.AlgorithmType;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;

import static group.seven.enums.Action.MOVE_FORWARD;
import static group.seven.logic.simulation.Simulator.rand;

public record RandomAlt(Agent agent) implements Algorithm {

    @Override
    public Move getNext() {
        Action action = Math.random() < 0.3 ? Action.values()[rand.nextInt(5)] : MOVE_FORWARD;
        return new Move(action, (action == MOVE_FORWARD ? agent.getCurrentSpeed() : 0), agent);
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.RANDOM; //not really
    }
}
