package org.group7.alt.logic.simulation;

import javafx.geometry.Point2D;
import org.group7.alt.model.ai.agents.Agent;
import org.group7.alt.model.ai.Pose;
import org.group7.alt.model.map.Tile;

import static org.group7.alt.model.map.Environment.TILE_MAP;

public class PhysicsHandler {
    //collision check
    //applying move
    //spreading sound
    //spreading smell

    public static boolean collision(Agent a, Pose pose) {
        Point2D agentGlobal = TILE_MAP.getLocalFrame(a).convertLocal(pose.getX(), pose.getY());
        Tile tile = TILE_MAP.getTile(agentGlobal.getX(), agentGlobal.getY());
        return tile.isObstacle();
    }
}
