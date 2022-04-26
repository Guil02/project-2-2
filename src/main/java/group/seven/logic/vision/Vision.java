package group.seven.logic.vision;

import group.seven.model.agents.Agent;
import group.seven.model.environment.Tile;

import java.util.List;

public interface Vision {

    enum Type {RECTANGULAR, CONE} //maybe should be moved to the enum package, but not sure if matters

    /**
     * 1. Updates the information of the tile by calling setExplored from the Tile class (this sets it to seen, specifying what type of agent saw it)
     * 2. Adds the tile to the observedList (which each agent and gets re set to null in every call of the method updateAndGetVisionAgent)
     * @param x
     * @param y
     * @param observedTile
     * @param agent
     */
    void observe(int x, int y, List<Tile> observedTile, Agent agent);

    /**
     * Given an agent, an observeList of Tiles is created
     * The vision of the agent is calculated and the method observe is called to generate all updates needed
     * @param agent
     * @return list of tiles seen by agent
     */
    List<Tile> updateAndGetVisionAgent (Agent agent);
    List<Tile> updateAndGetVisionAgent();
    Type getType();


}
