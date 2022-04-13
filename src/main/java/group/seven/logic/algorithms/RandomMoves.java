package group.seven.logic.algorithms;

import group.seven.enums.Action;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;

public class RandomMoves implements Algorithm {
    Agent agent;

    public RandomMoves(Agent agent) {
        this.agent = agent;
    }

    @Override
    public Move getNext() {
        return new Move(Action.FLIP, 0, agent.getDirection(), null);
    }
}
