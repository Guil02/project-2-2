package org.group7.alt.logic.simulation;

import javafx.geometry.Point2D;
import org.group7.alt.enums.Cardinal;
import org.group7.alt.logic.util.records.ObservedTile;
import org.group7.alt.logic.util.records.Frame;
import org.group7.alt.logic.util.records.Range;
import org.group7.alt.logic.util.records.XY;
import org.group7.alt.model.ai.agents.Agent;
import org.group7.alt.model.ai.Pose;
import org.group7.alt.model.map.Tile;

import java.util.LinkedList;
import java.util.List;

import static org.group7.alt.model.map.Environment.TILE_MAP;

public class VisionHandler {

    public static final int VIEW_DISTANCE = 10;

    public static List<ObservedTile> getFOV(Agent agent) {

        Frame frame = TILE_MAP.getLocalFrame(agent);
        Pose pose = agent.getPose();

        //convert agent position from its local coordinate frame to the global frame
        Point2D globalPosition = frame.convertLocal(new Point2D(pose.getX(), pose.getY()));
        XY mapPos = new XY(globalPosition.getX(), globalPosition.getY()); //its just nicer working with integers idk

        List<ObservedTile> observedTiles = new LinkedList<>();

        Cardinal orientation = pose.getDirection();
        Range breadth = orientation.visualBreadth(mapPos);

        //TODO: reduce amount of duplicate code
        switch (orientation) {
            case NORTH -> {
                Range depth = new Range(mapPos.y(), mapPos.y() - VIEW_DISTANCE);
                for (int x = breadth.i(); x < breadth.j(); x++) {
                    for (int y = depth.i(); y > depth.j(); y--) {
                        Tile t = observe(x, y, observedTiles);
                        if (t.isObstacle()) break;
                    }
                }
            }

            case SOUTH -> {
                Range depth = new Range(mapPos.y(), mapPos.y() + VIEW_DISTANCE);
                for (int x = breadth.i(); x < breadth.j(); x++) {
                    for (int y = depth.i(); y < depth.j(); y++) {
                        Tile t = observe(x, y, observedTiles);
                        if (t.isObstacle()) break;
                    }
                }
            }

            case EAST -> {
                Range depth = new Range(mapPos.x(), mapPos.x() + VIEW_DISTANCE);
                for (int y = breadth.i(); y < breadth.j(); y++) {
                    for (int x = depth.i(); x < depth.j(); x++) {
                        Tile t = observe(x, y, observedTiles);
                        if (t.isObstacle()) break;
                    }
                }
            }

            case WEST -> {
                Range depth = new Range(mapPos.x(), mapPos.x() - VIEW_DISTANCE);
                for (int y = breadth.i(); y < breadth.j(); y++) {
                    for (int x = depth.i(); x > depth.j(); x--) {
                        Tile t = observe(x, y, observedTiles);
                        if (t.isObstacle()) break;
                    }
                }
            }

            default -> throw new IllegalStateException("Unexpected value: " + orientation);
        }

        return observedTiles;
    }

    private static Tile observe(int x, int y, List<ObservedTile> observedTiles) {
        Tile seen = TILE_MAP.getTile(x, y);
        seen.setExplored(true); //TODO: move out of VisionHandler
        observedTiles.add(new ObservedTile(seen.getType(), x, y));
        return seen;
    }

}