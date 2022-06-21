package group.seven.logic.vision;

import group.seven.enums.Cardinal;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Agent;
import group.seven.model.environment.Tile;

import java.util.LinkedList;
import java.util.List;

import static group.seven.enums.TileType.WALL;
import static group.seven.logic.vision.Vision.Type.CONE;

public class ConeVision implements Vision {

    public static int counterBIG = 0;
    public Type type = CONE;
    private Agent agent;

    public ConeVision() {

    }

    public ConeVision(Agent agent) {
        this.agent = agent;
    }

    @Override
    public void observe(int x, int y, List<Tile> observedTile, Agent agent) {
        agent.scenario.TILE_MAP.getTile(x, y).setExplored(agent);
        observedTile.add(agent.scenario.TILE_MAP.getTile(x, y));
    }


    @Override
    public List<Tile> updateAndGetVisionAgent(Agent agent) {
        List<Tile> observedTiles = new LinkedList<>();
        XY globalCoordinates = agent.getXY();
        int xCoordinate = globalCoordinates.x();
        int yCoordinate = globalCoordinates.y();
        int distance = agent.scenario.VIEW_DISTANCE;   // shortens the view distance if wall is encountered
        Cardinal directionAgent = agent.getDirection();

        switch (directionAgent) {
            // north is 90 degrees, so u need a ray at 45 degrees and 135 degrees
            case NORTH -> {
                List<Integer> blockedTiles = new LinkedList<>();
                int counter = 1;
                int x_counter = 1;
                int see_wall = 0;
                // <= or <, ceck the for loops with debugging
                for (int y = yCoordinate - counter; y >= yCoordinate - distance; y--) {
                    for (int x = xCoordinate - x_counter; x <= xCoordinate + x_counter; x++) {
                        if (y < agent.scenario.HEIGHT && x < agent.scenario.WIDTH && x >= 0 && y >= 0) {
                            if (x == xCoordinate - x_counter) {
                                if (blockedTiles.contains(xCoordinate - x_counter + 1)) {
                                    blockedTiles.add(x);
                                    continue;
                                } else doChecksX(agent, observedTiles, blockedTiles, x, y);
                            } else if (x == xCoordinate + x_counter) {
                                if (blockedTiles.contains(xCoordinate + x_counter - 1)) {
                                    blockedTiles.add(x);
                                    continue;
                                } else doChecksX(agent, observedTiles, blockedTiles, x, y);
                            } else if (blockedTiles.contains(x)) {
                                continue;
                            } else doChecksX(agent, observedTiles, blockedTiles, x, y);
                        }
                    }
                    x_counter++;
                }
                blockedTiles.clear();
            }
            case SOUTH -> {
                List<Integer> blockedTiles = new LinkedList<>();
                int counter = 1;
                int x_counter = 1;
                int see_wall = 0;
                for (int y = yCoordinate + counter; y <= yCoordinate + distance; y++) {
                    for (int x = xCoordinate - x_counter; x <= xCoordinate + x_counter; x++) {
                        if (y < agent.scenario.HEIGHT && x < agent.scenario.WIDTH && x >= 0 && y >= 0) {
                            if (x == xCoordinate - x_counter) {
                                if (blockedTiles.contains(xCoordinate - x_counter + 1)) {
                                    blockedTiles.add(x);
                                    continue;
                                } else doChecksX(agent, observedTiles, blockedTiles, x, y);
                            } else if (x == xCoordinate + x_counter) {
                                if (blockedTiles.contains(xCoordinate + x_counter - 1)) {
                                    blockedTiles.add(x);
                                    continue;
                                } else doChecksX(agent, observedTiles, blockedTiles, x, y);
                            } else if (blockedTiles.contains(x)) {
                                continue;
                            } else doChecksX(agent, observedTiles, blockedTiles, x, y);
                        }
                    }
                    x_counter++;
                }
                blockedTiles.clear();
            }
            case EAST -> {
                List<Integer> blockedTiles = new LinkedList<>();
                int counter = 1;
                int y_counter = 1;
                int see_wall = 0;
                for (int x = xCoordinate + counter; x <= xCoordinate + distance; x++) {
                    for (int y = yCoordinate - y_counter; y <= yCoordinate + y_counter; y++) {
                        if (y < agent.scenario.HEIGHT && x < agent.scenario.WIDTH && x >= 0 && y >= 0) {
                            if (y == yCoordinate - y_counter) {
                                if (blockedTiles.contains(yCoordinate - y_counter + 1)) {
                                    blockedTiles.add(y);
                                    continue;
                                } else {
                                    doChecksY(agent, observedTiles, blockedTiles, x, y);
                                }
                            } else if (y == yCoordinate + y_counter - 1) {
                                if (blockedTiles.contains(yCoordinate + y_counter - 1)) {
                                    blockedTiles.add(y);
                                    continue;
                                } else {
                                    doChecksY(agent, observedTiles, blockedTiles, x, y);
                                }
                            } else if (blockedTiles.contains(y)) {
                                continue;
                            } else doChecksY(agent, observedTiles, blockedTiles, x, y);
                        }
                    }
                    y_counter++;
                }
                blockedTiles.clear();
            }
            //create rays
            case WEST -> {
                List<Integer> blockedTiles = new LinkedList<>();
                int counter = 1;
                int y_counter = 1;
                for (int x = xCoordinate - counter; x >= xCoordinate - distance; x--) {
                    for (int y = yCoordinate - y_counter; y <= yCoordinate + y_counter; y++) {
                        if (y < agent.scenario.HEIGHT && x < agent.scenario.WIDTH && x >= 0 && y >= 0) {
                            if (y == yCoordinate - y_counter) {
                                if (blockedTiles.contains(yCoordinate - y_counter + 1)) {
                                    blockedTiles.add(y);
                                    continue;
                                } else {
                                    doChecksY(agent, observedTiles, blockedTiles, x, y);
                                }
                            } else if (y == yCoordinate + y_counter - 1) {
                                if (blockedTiles.contains(yCoordinate + y_counter - 1)) {
                                    blockedTiles.add(y);
                                    continue;
                                } else {
                                    doChecksY(agent, observedTiles, blockedTiles, x, y);
                                }
                            } else if (blockedTiles.contains(y)) {
                                continue;
                            } else doChecksY(agent, observedTiles, blockedTiles, x, y);
                        }
                    }
                    y_counter++;
                }
                blockedTiles.clear();
            }
        }

        counterBIG++;
        return observedTiles;
    }

    private void doChecksX(Agent agent, List<Tile> observedTiles, List<Integer> blockedTiles, int x, int y) {
        if (agent.scenario.TILE_MAP.getTile(x, y).getType() == WALL) {
            observe(x, y, observedTiles, agent);
            blockedTiles.add(x);
        } else {
            observe(x, y, observedTiles, agent);
        }
    }

    private void doChecksY(Agent agent, List<Tile> observedTiles, List<Integer> blockedTiles, int x, int y) {
        if (agent.scenario.TILE_MAP.getTile(x, y).getType() == WALL) {
            blockedTiles.add(y);
            observe(x, y, observedTiles, agent);
        } else {
            observe(x, y, observedTiles, agent);
        }
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
