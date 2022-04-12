package group.seven.model.environment;

import group.seven.enums.TileType;
import group.seven.model.agents.Agent;

public class TileMap {
    //TileMap
    public Tile[][] map; //observable?
    public Agent[] agents; //observable?

    public TileMap(Scenario scenario) {
        //not sure if TileMap needs a reference to the scenario, or if builder can take care of all that
        //alternatively scenario fields could be static

        map = new Tile[scenario.WIDTH + 1][scenario.HEIGHT + 1];
        agents = new Agent[scenario.NUM_GUARDS + scenario.NUM_INTRUDERS];
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

    public void setType(int x, int y, TileType type) {
        map[x][y].type = type;
    }

    public void addAgent(Agent agent) {

    }
}
