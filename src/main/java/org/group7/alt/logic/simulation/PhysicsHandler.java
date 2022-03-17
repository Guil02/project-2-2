package org.group7.alt.logic.simulation;

import org.group7.alt.logic.util.CoordinateMapper;
import org.group7.alt.model.ai.Agents.Agent;
import org.group7.alt.model.map.Tile;
import org.group7.alt.model.map.TileMap;

import java.awt.*;

public class PhysicsHandler {
    //collision check
    //applying move
    //spreading sound
    //spreading smell

    public static boolean collision(TileMap map, Agent a) {
        final Point global = CoordinateMapper.convertLocalToGlobal(map.getSpawn(a), a.getPose().getPosition());
        final Tile tile = map.getTile(global);

        if (tile.isObstacle()) return true;
        return map.getAgentList().stream().anyMatch(agent -> map.getSpawn(agent).equals(map.getSpawn(a)));
    }
}
