package org.group7.alt.model.map;

import javafx.scene.transform.Translate;
import org.group7.alt.logic.util.records.Frame;
import org.group7.alt.model.ai.Agents.Agent;
import org.group7.alt.model.ai.Agents.Explorer;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TileMap {

    Tile[][] map;
    double numTiles;
    int numExplored;

    List<Agent> agentList;
    Map<Agent, Point> relativeOrigins;
    Map<Agent, Frame> localFrames;

    public TileMap() {
        map = new Tile[Environment.WIDTH + 1][Environment.HEIGHT + 1];
        agentList = new LinkedList<>();
        relativeOrigins = new HashMap<>(Environment.NUM_GAURDS + Environment.NUM_INTRUDERS);
        localFrames = new HashMap<>(Environment.NUM_GAURDS + Environment.NUM_INTRUDERS);

        numTiles = (Environment.WIDTH + 1) * (Environment.HEIGHT + 1);
        numExplored = 0;
    }

    public Tile[][] getMap() {
        return map;
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x > map.length || y > map[0].length)
            return new Tile();

        return map[x][y];
    }

    //change to integer percent
    public double getExplorationPercent() {
        int amountExplored = 0;
        for (int x = 0; x <= Environment.WIDTH; x++) {
            for (int y = 0; y <= Environment.HEIGHT; y++) {
                if (map[x][y].isExplored())
                    amountExplored++;
            }
        }
        numExplored = amountExplored;

        return numExplored / numTiles;
    }

    public void addAgent(Agent agent, Point spawnPoint) {
        agentList.add(agent);
        relativeOrigins.put(agent, spawnPoint);
        localFrames.put(agent, new Frame(new Translate(-spawnPoint.getX(), -spawnPoint.getY())));

        getTile(agent.getPose().getPosition()).setExplored(true);
    }

    public List<Agent> getAgentList() {
        return agentList;
    }

    public Point getSpawn(Agent agent) {
        return relativeOrigins.get(agent);
    }

    public Frame getLocalFrame(Agent agent) {
        return localFrames.get(agent);
    }

    public Tile getTile(double x, double y) {
        return getTile((int) x, (int) y );
    }

    public Tile getTile(Point pos) {
        return getTile(pos.x, pos.y);
    }

    public void setTile(Point pos, Tile tile) {
        map[pos.x][pos.y] = tile;
    }

    public void setTile(int x, int y, Tile tile) {
        map[x][y] = tile;
    }

    @Override
    public String toString() {
        return "TileMap{" +
                "dimensions=" + map.length + " x " + map[0].length +
                ", numTiles=" + numTiles +
                ", numExplored=" + numExplored +
                ", agentList=" + agentList +
                '}';
    }
}
