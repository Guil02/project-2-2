package group.seven.model.environment;

import group.seven.enums.TileType;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Agent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static group.seven.enums.TileType.GUARD;
import static group.seven.model.environment.Scenario.*;
import static group.seven.utils.Methods.print;

public class TileMap {
    //used for coverage calculation
    public static double NUM_TILES;
    public static double INTRUDER_EXPLORATION;
    public static double GUARD_EXPLORATION;
    //TileMap
    public Tile[][] map;
    public Set<XY> guardPositions; //observable?
    public Set<XY> intruderPositions; //observable?
    public Agent[] agents;

    //marker/pheromone stuff. Should this belong here?
    public ArrayList<Marker> markers;
    public ArrayList<Pheromone> pheromones;
    private static final int spreadDistance = 50;


    public TileMap() {
        map = new Tile[Scenario.WIDTH + 1][Scenario.HEIGHT + 1];
        agents = new Agent[NUM_AGENTS];
        //guardPositions = new ArrayList<>(NUM_GUARDS);
        //intruderPositions = new ArrayList<>(NUM_INTRUDERS);
        guardPositions = new HashSet<>(NUM_GUARDS);
        intruderPositions = new HashSet<>(NUM_INTRUDERS);
        markers = new ArrayList<>();
        pheromones = new ArrayList<>();

        //NUM_TILES = Scenario.WIDTH * Scenario.HEIGHT; // +1 (?)
    }

    //update
    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x > map.length || y > map[0].length)
            return new Tile(); // should probably throw an exception instead of empty tile

        return map[x][y];
    }

    public void dropPheromone(int x, int y) {
        for (int i = x - spreadDistance / 2; i < x + spreadDistance / 2; i++) {
            for (int j = y - spreadDistance / 2; j < y + spreadDistance / 2; j++) {
                int dis = Math.abs(x - i + y - j);
                double adjustedStrength = Pheromone.maxStrength - (Pheromone.maxStrength / spreadDistance) * dis;

                try {
                    map[i][j].pheromone.setStrength(Math.max(map[i][j].pheromone.getStrength(), adjustedStrength));
                } catch (Exception e) {
                    print(e.getMessage());
                    print(e.getCause());
                }
            }
        }
        //TODO implement "bomb"
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

    protected void setType(XY xy, TileType type) {
        map[xy.x()][xy.y()].type = type;
    }

    public TileType getType(int x, int y) {
        return map[x][y].type;
    }

    public TileType getType(XY xy) {
        return map[xy.x()][xy.y()].type;
    }

    public void addAgent(Agent agent) {
        agents[agent.getID()] = agent;

        if (agent.getType() == GUARD)
             guardPositions.add(agent.getXY());
        else intruderPositions.add(agent.getXY());
    }

    public void addMarker(Marker marker) {
        markers.add(marker);
    }

    public ArrayList<Marker> getMarkers() {
        return markers;
    }

    public void resetMarkers() {
        markers = new ArrayList<>();
    }

    public void addPheromone(Pheromone pheromone) {
        pheromones.add(pheromone);
    }

    public ArrayList<Pheromone> getPheromones() {
        return pheromones;
    }

    public void resetPheromones() {
        pheromones = new ArrayList<>();
    }


    public Tile getTile(XY xy) {
        return map[xy.x()][xy.y()];
    }
}
