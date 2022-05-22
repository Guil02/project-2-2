package group.seven.logic.algorithms;

import group.seven.enums.Action;
import group.seven.enums.AlgorithmType;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;

import static group.seven.enums.Action.MOVE_FORWARD;
import static group.seven.logic.simulation.Simulator.rand;

//Created this class just to test out some gui stuff. We can delete once RandomMoves class has been implemented
public class RandomTest implements Algorithm {

    private final Agent agent;

    public RandomTest(Agent agent) {
        this.agent = agent;
    }

    @Override
    public Move getNext() {
        Action action= Math.random() < 0.3 ? Action.values()[rand.nextInt(5)] : MOVE_FORWARD;
        return new Move(action, action == MOVE_FORWARD ? agent.getCurrentSpeed() : 0, agent);
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.A_STAR; //not really
    }
}
