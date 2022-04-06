package group.seven.logic.records;

import group.seven.model.agents.Agent;

public class Records {
    public static record Seen(Agent agent, double timeStep, boolean seen){}
}

