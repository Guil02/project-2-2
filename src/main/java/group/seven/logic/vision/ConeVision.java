package group.seven.logic.vision;

import group.seven.model.agents.Agent;
import group.seven.model.environment.Tile;

import java.util.List;

import static group.seven.logic.vision.Vision.Type.CONE;

public class ConeVision implements Vision {

    public Type type = CONE;
    private Agent agent;

    public ConeVision() {

    }

    public ConeVision(Agent agent) {
        this.agent = agent;
    }

    @Override
    public void observe(int x, int y, List<Tile> observedTile, Agent agent) {

    }

    @Override
    public List<Tile> updateAndGetVisionAgent(Agent agent) {
        return null;
    }

    @Override
    public List<Tile> updateAndGetVisionAgent() {
        return updateAndGetVisionAgent(agent);
    }

    @Override
    public Type getType() {
        return type;
    }
}
