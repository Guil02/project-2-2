package group.seven.model.environment;

import group.seven.enums.TileType;
import group.seven.model.agents.Agent;

public class TileMap {
    //TileMap
    public Tile[][] map; //observable?
    public Agent[] agents; //observable?

    public TileMap(Scenario s) {
        //not sure if TileMap needs a reference to the scenario, or if builder can take care of all that
        //alternatively scenario fields could be static
        map = new Tile[s.WIDTH + 1][s.HEIGHT + 1];
        agents = new Agent[s.NUM_AGENTS];
    }

    //update
    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x > map.length || y > map[0].length)
            return new Tile(); // should probably throw an exception instead of empty tile

        return map[x][y];
    }

    protected void setTile(int x, int y, Tile tile) {
        map[x][y] = tile;
    }

    public Tile[][] getMap() {
        return map;
    }

    protected void setType(int x, int y, TileType type) {
        map[x][y].type = type;
    }

    public TileType getType(int x, int y) {
        return map[x][y].type;
    }

    public void addAgent(Agent agent) {
        agents[agent.getID()] = agent;
    }
}
