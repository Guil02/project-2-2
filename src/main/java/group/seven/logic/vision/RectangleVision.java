package group.seven.logic.vision;

import group.seven.enums.Cardinal;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Agent;
import group.seven.model.environment.Tile;

import java.util.LinkedList;
import java.util.List;

import static group.seven.enums.TileType.WALL;
import static group.seven.logic.vision.Vision.Type.RECTANGULAR;

/**
 * Class implements a rectangular vision for agent of  size [3 x distanceViewing]
 */
public class RectangleVision implements Vision {

    private Agent agent;
    public final Type type = RECTANGULAR;

    public RectangleVision() {

    }

    public RectangleVision(Agent agent) {
        this.agent = agent;
    }

    @Override
    public void observe(int x, int y, List<Tile> observedTile, Agent agent) {
        agent.scenario.TILE_MAP.getTile(x, y).setExplored(agent);
        observedTile.add(agent.scenario.TILE_MAP.getTile(x, y));
    }

    @Override
    public List<Tile> updateAndGetVisionAgent(Agent agent) {
        List<Tile> observedTiles = new LinkedList<>(); // list contains all the tiles seen by agent
        //get position of agent
        XY agentGlobal = agent.getXY(); //only have to transform once
        int xCoordinate = agentGlobal.x();
        int yCoordinate = agentGlobal.y();
        Cardinal directionAgent = agent.getDirection(); //get direction of agent

        switch (directionAgent) {
            case NORTH -> {
                for (int y = yCoordinate; y > yCoordinate - agent.scenario.VIEW_DISTANCE; y--) { //check straight
                    if (y >= 0) { //can't go lower than y=0, so if the number is negative is out of bound
                        // set that the player saw the tile
                        observe(xCoordinate, y, observedTiles, agent);
                        //CHECK COLLISIONS with walls
                        if (agent.scenario.TILE_MAP.getTile(xCoordinate, y).getType() == WALL) {
                            //if the agent sees a wall, we break as it cant see any further
                            break;
                        }
                    } else { //out of bound for edges of map
                        observe(xCoordinate, y + 1, observedTiles, agent);
                        break;
                    }
                }
                if (xCoordinate - 1 >= 0) { //if it is possible to be one of the left without going out of bound
                    for (int y = yCoordinate; y > yCoordinate - agent.scenario.VIEW_DISTANCE; y--) { //check one left
                        if (y >= 0) { //cant go higher than y=0, so if the number is positive is out of bound
                            observe(xCoordinate - 1, y, observedTiles, agent);

                            //CHECK COLLISIONS with walls
                            if (agent.scenario.TILE_MAP.getTile(xCoordinate - 1, y).getType() == WALL) { // TODO: check this
                                //if the agent sees a wall, we break as it cant see any further
                                break;
                            }
                        } else { //out of bound for edges of map
                            observe(xCoordinate - 1, y + 1, observedTiles, agent);

                            break;
                        }
                    }
                }
                if (xCoordinate + 1 < agent.scenario.WIDTH) { //if it is possible to move one step to the right
                    for (int y = yCoordinate; y > yCoordinate - agent.scenario.VIEW_DISTANCE; y--) { //check one right
                        if (y >= 0) { //cant go higher than y=0, so if the number is positive is out of bound
                            observe(xCoordinate + 1, y, observedTiles, agent);

                            //CHECK COLLISIONS with walls
                            if (agent.scenario.TILE_MAP.getTile(xCoordinate + 1, y).getType() == WALL) {
                                break;
                            }
                        } else { //out of bound for edges of map
                            observe(xCoordinate + 1, y + 1, observedTiles, agent);
                            break;
                        }
                    }
                }
            }

            case SOUTH -> {
                for (int y = yCoordinate; y < yCoordinate + agent.scenario.VIEW_DISTANCE; y++) { //check straight
                    if (y < agent.scenario.HEIGHT) { //cant go lower than y=map.height, so if the number is larger is out of bound
                        observe(xCoordinate, y, observedTiles, agent);

                        //CHECK COLLISIONS with walls
                        if (agent.scenario.TILE_MAP.getTile(xCoordinate, y).getType() == WALL) {
                            break;
                        }
                    } else { //out of bound for edges of map
                        observe(xCoordinate, y - 1, observedTiles, agent);

                        break;
                    }
                }
                if (xCoordinate - 1 >= 0) { //if it is possible to be one of the left without going out of bound
                    for (int y = yCoordinate; y < yCoordinate + agent.scenario.VIEW_DISTANCE; y++) { //check one left
                        if (y < agent.scenario.HEIGHT) { //cant go lower than y=map.height, so if the number is larger is out of bound
                            observe(xCoordinate - 1, y, observedTiles, agent);

                            //CHECK COLLISIONS with walls
                            if (agent.scenario.TILE_MAP.getTile(xCoordinate - 1, y).getType() == WALL) {
                                break;
                            }
                        } else { //out of bound for edges of map
                            observe(xCoordinate - 1, y - 1, observedTiles, agent);
                            break;
                        }
                    }
                }
                if (xCoordinate + 1 < agent.scenario.WIDTH) { //if it is possible to move one step to the right
                    for (int y = yCoordinate; y < yCoordinate + agent.scenario.VIEW_DISTANCE; y++) { //check one right
                        if (y < agent.scenario.HEIGHT) { //cant go lower than y=map.height, so if the number is larger is out of bound
                            observe(xCoordinate + 1, y, observedTiles, agent);
                            //CHECK COLLISIONS with walls
                            if (agent.scenario.TILE_MAP.getTile(xCoordinate + 1, y).getType() == WALL) {
                                break;
                            }
                        } else {
                            observe(xCoordinate + 1, y - 1, observedTiles, agent);
                            break;
                        }
                    }
                }
            }
            case EAST -> {
                for (int x = xCoordinate; x < xCoordinate + agent.scenario.VIEW_DISTANCE; x++) { //check straight
                    if (x < agent.scenario.WIDTH) {
                        observe(x, yCoordinate, observedTiles, agent);

                        //CHECK COLLISIONS with walls
                        if (agent.scenario.TILE_MAP.getTile(x, yCoordinate).getType() == WALL) {
                            break;
                        }
                    } else { //out of bound for edges of map
                        observe(x - 1, yCoordinate, observedTiles, agent);
                        break;
                    }
                }
                if (yCoordinate - 1 >= 0) {
                    for (int x = xCoordinate; x < xCoordinate + agent.scenario.VIEW_DISTANCE; x++) {
                        if (x < agent.scenario.WIDTH) { //cant go higher than y=0, so if the number is positive is out of bound
                            observe(x, yCoordinate - 1, observedTiles, agent);
                            //CHECK COLLISIONS with walls
                            if (agent.scenario.TILE_MAP.getTile(x, yCoordinate - 1).getType() == WALL) {
                                break;
                            }
                        } else {
                            observe(x - 1, yCoordinate - 1, observedTiles, agent);
                            break;
                        }
                    }
                }
                if (yCoordinate + 1 < agent.scenario.HEIGHT) {
                    for (int x = xCoordinate; x < xCoordinate + agent.scenario.VIEW_DISTANCE; x++) {
                        if (x < agent.scenario.WIDTH) {
                            observe(x, yCoordinate + 1, observedTiles, agent);
                            //CHECK COLLISIONS with walls
                            if (agent.scenario.TILE_MAP.getTile(x, yCoordinate + 1).getType() == WALL) {
                                break;
                            }
                        } else {
                            observe(x - 1, yCoordinate + 1, observedTiles, agent);
                            break;
                        }
                    }
                }
            }
            case WEST -> {
                for (int x = xCoordinate; x > xCoordinate - agent.scenario.VIEW_DISTANCE; x--) { //check straight
                    if (x >= 0) {
                        observe(x, yCoordinate, observedTiles, agent);

                        //CHECK COLLISIONS with walls
                        if (agent.scenario.TILE_MAP.getTile(x, yCoordinate).getType() == WALL) {
                            break;
                        }
                    } else { //out of bound for edges of map
                        observe(x + 1, yCoordinate, observedTiles, agent);
                        break;
                    }
                }
                if (yCoordinate - 1 >= 0) {
                    for (int x = xCoordinate; x > xCoordinate - agent.scenario.VIEW_DISTANCE; x--) {
                        if (x >= 0) { //cant go higher than y=0, so if the number is positive is out of bound
                            observe(x, yCoordinate - 1, observedTiles, agent);
                            //CHECK COLLISIONS with walls
                            if (agent.scenario.TILE_MAP.getTile(x, yCoordinate - 1).getType() == WALL) {
                                break;
                            }
                        } else {
                            observe(x + 1, yCoordinate - 1, observedTiles, agent);
                            break;
                        }
                    }
                }
                if (yCoordinate + 1 < agent.scenario.HEIGHT) {
                    for (int x = xCoordinate; x > xCoordinate - agent.scenario.VIEW_DISTANCE; x--) {
                        if (x >= 0) {
                            observe(x, yCoordinate + 1, observedTiles, agent);

                            //CHECK COLLISIONS with walls
                            if (agent.scenario.TILE_MAP.getTile(x, yCoordinate + 1).getType() == WALL) {
                                break;
                            }
                        } else {
                            observe(x + 1, yCoordinate + 1, observedTiles, agent);
                            break;
                        }
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
