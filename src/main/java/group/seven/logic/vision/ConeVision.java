package group.seven.logic.vision;

import group.seven.enums.Cardinal;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Agent;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.Tile;

import java.util.LinkedList;
import java.util.List;

import static group.seven.enums.TileType.WALL;
import static group.seven.logic.vision.Vision.Type.CONE;
import static group.seven.model.environment.Scenario.TILE_MAP;

public class ConeVision implements Vision {

    public Type type = CONE;
    private Agent agent;

    public ConeVision() {

    }

    public ConeVision(Agent agent) {
        this.agent = agent;
    }

    @Override
    public void observe(int x, int y, List<Tile> observedTile, Agent agent) {
        TILE_MAP.getTile(x,y).setExplored(agent);
        observedTile.add(TILE_MAP.getTile(x,y));
    }


    @Override
    public List<Tile> updateAndGetVisionAgent(Agent agent) {
        List<Tile> observedTiles = new LinkedList<>();
        int xCoordinate = agent.getX();
        int yCoordinate = agent.getY();
        int distance = Scenario.VIEW_DISTANCE;   // shortens the view distance if wall is encountered
        Cardinal directionAgent = agent.getDirection();

        switch (directionAgent) {
            // north is 90 degrees, so u need a ray at 45 degrees and 135 degrees
            case NORTH -> {
                List<Integer> blockedTiles = new LinkedList<>();
                int counter = 1;
                int see_wall = 1;
                // <= or <, ceck the for loops with debugging
                for (int y = yCoordinate - counter; y >= yCoordinate - distance; y--) {
                    System.out.println("yCoordinate"+ yCoordinate);
                    System.out.println("y "+y);
                    System.out.println("until "+ (yCoordinate-distance));
                    for (int x = xCoordinate - counter; x <= xCoordinate + counter ; x++) {
                        if (blockedTiles.contains(x)) {
                            break;
                        }
                        if (y > Scenario.HEIGHT || x < Scenario.WIDTH || x <= 0 || y <= 0) {
                            observe(x, y, observedTiles, agent);
                        }
                        if (TILE_MAP.getTile(x, y).getType() == WALL) {
                            blockedTiles.add(x);
                            for (int i = y; y <= yCoordinate - Scenario.VIEW_DISTANCE; i--) {
                                blockedTiles.add(x - see_wall * i);
                                blockedTiles.add(x + see_wall * i);
                            }
                        }
                        see_wall++;
                    }
                }
            }
            case SOUTH -> {
                for (int y = yCoordinate; y < yCoordinate + Scenario.VIEW_DISTANCE; y++) { //check straight
                    if (y < Scenario.HEIGHT) { //cant go lower than y=map.height, so if the number is larger is out of bound
                        observe(xCoordinate, y, observedTiles, agent);

                        //CHECK COLLISIONS with walls
                        if (TILE_MAP.getTile(xCoordinate, y).getType() == WALL) {
                            break;
                        }
                    } else { //out of bound for edges of map
                        observe(xCoordinate, y - 1, observedTiles, agent);

                        break;
                    }
                }
                //create rays
            }
            case EAST -> {
                for (int x = xCoordinate; x < xCoordinate + Scenario.VIEW_DISTANCE; x++) { //check straight
                    if (x < Scenario.WIDTH) {
                        observe(x, yCoordinate, observedTiles, agent);

                        //CHECK COLLISIONS with walls
                        if (TILE_MAP.getTile(x, yCoordinate).getType() == WALL) {
                            break;
                        }
                    } else { //out of bound for edges of map
                        observe(x - 1, yCoordinate, observedTiles, agent);
                        break;
                    }
                }
            }
            //create rays
            case WEST -> {
                for (int x = xCoordinate; x > xCoordinate - Scenario.VIEW_DISTANCE; x--) { //check straight
                    if (x >= 0) {
                        observe(x, yCoordinate, observedTiles, agent);

                        //CHECK COLLISIONS with walls
                        if (TILE_MAP.getTile(x, yCoordinate).getType() == WALL) {
                            break;
                        }
                    } else { //out of bound for edges of map
                        observe(x + 1, yCoordinate, observedTiles, agent);
                        break;
                    }
                }
            }
        }
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
