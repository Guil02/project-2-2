package org.group7.alt.logic.simulation;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import org.group7.alt.enums.Cardinal;
import org.group7.alt.enums.Cell;
import org.group7.alt.logic.util.ObservedTile;
import org.group7.alt.logic.util.records.Frame;
import org.group7.alt.model.ai.Agents.Agent;
import org.group7.alt.model.map.Environment;
import org.group7.alt.model.map.Tile;
import org.group7.alt.model.map.TileMap;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class VisionHandler {

    static final TileMap tileMap = Environment.getTileMap();
    static final int VIEW_DISTANCE = 10;

    record Coord(int x, int y){
        public Coord(double xd, double yd) {
            this((int) xd, (int) yd);
        }
    };

    public static List<ObservedTile> getFOV(Agent agent) {

        Frame frame = Environment.getTileMap().getLocalFrame(agent);
        int x = agent.getPose().getX();
        int y = agent.getPose().getY();
        Point2D localPosition = new Point2D(x, y);
        Point2D globalPosition = frame.convertLocal(localPosition);

        Coord mapPos = new Coord(globalPosition.getX(), globalPosition.getY());

        Tile tile = tileMap.getTile(mapPos.x, mapPos.y);
        ObservedTile agentPosition = new ObservedTile(tile.getType(), mapPos.x, mapPos.y);

        List<ObservedTile> observedTiles = new LinkedList<>();

        List<Coord> tilesCoords = new LinkedList<>();


        switch (agent.getPose().getDirection()) {
            case NORTH -> {

                Rectangle viewArea = new Rectangle(mapPos.x, mapPos.y, 3, VIEW_DISTANCE);
                viewArea.translate(-1, -VIEW_DISTANCE);

                System.out.println(viewArea);
                System.out.println(mapPos);
                System.out.println(VIEW_DISTANCE);
                System.out.println(viewArea.getMinX() + " - " + viewArea.getMaxX());
                System.out.println("(" + viewArea.getLocation().x + ", " + viewArea.getLocation().y + ")");
                System.out.println(viewArea.getMinY() + " - " + viewArea.getMaxY());

                for (int i = (int) viewArea.getMinX(); i < viewArea.getMaxX(); i++) {
                    for (int j = (int) viewArea.getMinY() + 1; j <= viewArea.getMaxY(); j++) {
                        Tile seen = tileMap.getTile(i, j);
                        seen.setExplored(true);

                        ObservedTile t = new ObservedTile(seen.getType(), i, j);
                        if (t.cell() == Cell.WALL && i != mapPos.x) {
                            observedTiles.add(t);
                            break;
                        }
                        observedTiles.add(t);
                    }
                }


                //System.out.println(tilesCoords);


//                Point left = new Point(mapPos.x - 1, mapPos.y);
//                Point right = new Point(mapPos.x + 1, mapPos.y);
//                Point middle = new Point(mapPos.x, mapPos.y);
//                int maxViewDistance = mapPos.y + VIEW_DISTANCE;
//
//                Cell leftRayPrev = tileMap.getTile(left.x, left.y).getType();
//
//                while (left.y >= 0 && left.y <= maxViewDistance) {
//                    ObservedTile o = new ObservedTile(tileMap.getTile(left.x, left.y).getType(), left.x, left.y);
//                    observedTiles.add(o);
//
//                    //agent should see walls
//                    if (o.cell() == Cell.WALL && o.cell() != leftRayPrev)
//                        break;
//                    y++;
//                }

            }
        }




        //return List.of(agentPosition);
        return observedTiles;
    }
}
