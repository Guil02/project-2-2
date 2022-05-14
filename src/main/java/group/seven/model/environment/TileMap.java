package group.seven.model.environment;

import group.seven.enums.TileType;
import group.seven.model.agents.Agent;

import java.util.ArrayList;

public class TileMap {
    //TileMap
    public Tile[][] map; //observable?
    public Agent[] agents; //observable?

    public ArrayList<Marker> markers = new ArrayList<>();
    public ArrayList<Pheromone> pheromones = new ArrayList<>();

    public TileMap() {
        //not sure if TileMap needs a reference to the scenario, or if builder can take care of all that
        //alternatively scenario fields could be static
        map = new Tile[Scenario.WIDTH + 1][Scenario.HEIGHT + 1];
        agents = new Agent[Scenario.NUM_AGENTS];
    }

    //update
    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x > map.length || y > map[0].length)
            return new Tile(); // should probably throw an exception instead of empty tile

        return map[x][y];
    }
    private static final int spreadDistance = 20;
    public void dropPheromone(int x, int y){
        for(int i = x-spreadDistance/2; i<x+spreadDistance/2; i++){
            for(int j = y-spreadDistance/2; j<y+spreadDistance / 2; j++){
                int dis = Math.abs(x-i+y-j);
                double adjustedStrength = Pheromone.maxStrength - (Pheromone.maxStrength/spreadDistance)*dis;
                try{
                    map[i][j].pheromone.setStrength(Math.max(map[i][j].pheromone.getStrength(),adjustedStrength));
                }
                catch (Exception ignored){
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

    public TileType getType(int x, int y) {
        return map[x][y].type;
    }

    public void addAgent(Agent agent) {
        agents[agent.getID()] = agent;
    }

    public void addMarker(Marker marker) {
        markers.add(marker);
    }

    public ArrayList<Marker> getMarkers() {
        return markers;
    }

    public void resetMarkers() {
        markers = new ArrayList<Marker>();
    }

    public void addPheromone(Pheromone pheromone) {
        pheromones.add(pheromone);
    }

    public ArrayList<Pheromone> getPheromones() {
        return pheromones;
    }

    public void resetPheromones() {
        pheromones = new ArrayList<Pheromone>();
    }

}
