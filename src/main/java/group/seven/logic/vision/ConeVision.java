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
                outerloop:
                for (int y = yCoordinate - counter; y >= yCoordinate - distance; y--) {
                    for (int x = xCoordinate - x_counter; x <= xCoordinate + x_counter; x++) {
                        if (y < agent.scenario.HEIGHT && x < agent.scenario.WIDTH && x > 0 && y > 0) {
                            if (blockedTiles.contains(x)) {
                                break outerloop;
                            } else if (agent.scenario.TILE_MAP.getTile(x, y).getType() == WALL) {
                                blockedTiles.add(x);
                                break outerloop;
                            } else {
                                observe(x, y, observedTiles, agent);
                            }
                            // see_wall++;
                            // for (int i = y; y <= yCoordinate - Scenario.VIEW_DISTANCE; i--) {
                            //     blockedTiles.add(x - see_wall * i);
                            //     blockedTiles.add(x + see_wall * i);
                            //     System.out.println("STUCK HERE 1");
                            // }
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
                outerloop:
                for (int y = yCoordinate + counter; y <= yCoordinate + distance; y++) {
                    for (int x = xCoordinate - x_counter; x <= xCoordinate + x_counter; x++) {
                        if (y < agent.scenario.HEIGHT && x < agent.scenario.WIDTH && x > 0 && y > 0) {
                            if (blockedTiles.contains(x)) {
                                break outerloop;
                            } else if (agent.scenario.TILE_MAP.getTile(x, y).getType() == WALL) {
                                blockedTiles.add(x);
                                break outerloop;
                            } else {
                                observe(x, y, observedTiles, agent);
                            }
                            // see_wall++;
                            // for (int i = y; y <= yCoordinate - Scenario.VIEW_DISTANCE; i--) {
                            //     blockedTiles.add(x - see_wall * i);
                            //     blockedTiles.add(x + see_wall * i);
                            //     System.out.println("STUCK HERE 1");
                            // }
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
                outerloop:
                for (int x = xCoordinate + counter; x <= xCoordinate + distance; x++) {
                    for (int y = yCoordinate - y_counter; y <= yCoordinate + y_counter; y++) {
                        if (y < agent.scenario.HEIGHT && x < agent.scenario.WIDTH && x > 0 && y > 0) {
                            if (blockedTiles.contains(x)) {
                                break outerloop;
                            } else if (agent.scenario.TILE_MAP.getTile(x, y).getType() == WALL) {
                                blockedTiles.add(x);
                                break outerloop;
                            } else {
                                observe(x, y, observedTiles, agent);
                            }
                            // see_wall++;
                            // for (int i = y; y <= yCoordinate - Scenario.VIEW_DISTANCE; i--) {
                            //     blockedTiles.add(x - see_wall * i);
                            //     blockedTiles.add(x + see_wall * i);
                            //     System.out.println("STUCK HERE 1");
                            // }
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
                int see_wall = 0;
                outerloop:
                for (int x = xCoordinate - counter; x >= xCoordinate - distance; x--) {
                    for (int y = yCoordinate - y_counter; y <= yCoordinate + y_counter; y++) {
                        if (y < agent.scenario.HEIGHT && x < agent.scenario.WIDTH && x > 0 && y > 0) {
                            if (blockedTiles.contains(x)) {
                                break outerloop;
                            } else if (agent.scenario.TILE_MAP.getTile(x, y).getType() == WALL) {
                                blockedTiles.add(x);
                                break outerloop;
                            } else {
                                observe(x, y, observedTiles, agent);
                            }
                            // see_wall++;
                            // for (int i = y; y <= yCoordinate - Scenario.VIEW_DISTANCE; i--) {
                            //     blockedTiles.add(x - see_wall * i);
                            //     blockedTiles.add(x + see_wall * i);
                            //     System.out.println("STUCK HERE 1");
                            // }
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

    @Override
    public List<Tile> updateAndGetVisionAgent() {
        return updateAndGetVisionAgent(agent);
    }

    @Override
    public Type getType() {
        return type;
    }
}
