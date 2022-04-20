package group.seven.logic.vision;

import group.seven.enums.Cardinal;
import group.seven.enums.TileType;
import group.seven.model.agents.Agent;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.Tile;
import group.seven.model.environment.TileMap;


public class RectangleVision implements Vision {
    Scenario scenario;
    TileMap map; // TileMap = Map , and Grid = Tile
    int distanceViewing;

    public RectangleVision(Scenario scenario) {
        this.scenario = scenario;
        this.map = scenario.get().TILE_MAP;
        this.distanceViewing = scenario.get().VIEW_DISTANCE;
    }

    //Given a player, it returns the observed Tiles


    // Given a player, the map is updated based on what it saw
    public void updateVisionMap (Agent player) {

        //get position of agent
        int xCoordinate = player.getX();
        int yCoordinate = player.getY();
        Tile furthestSoFar = null;

        Cardinal directionAgent = player.getDirection(); //up,down,etc.

        switch (directionAgent) {
            case NORTH -> {
                for (int y = yCoordinate; y > yCoordinate - distanceViewing; y--) { //check straight
                    if (y >= 0) { //can't go lower than y=0, so if the number is negative is out of bound
                        // set that the player saw the tile
                        map.getTile(xCoordinate,y).setExplored(player);
                        furthestSoFar = map.getTile(xCoordinate,y);
                        //CHECK COLLISIONS with walls
                        if (map.getTile(xCoordinate,y).getType() == TileType.WALL) { //TODO: check how to get rid of TileType.
                            //if the agent sees a wall, we break as it cant see any further
                            break;
                        }
                    } else { //out of bound for edges of map
                        furthestSoFar = map.getTile(xCoordinate,y+1);
                        break;
                    }
                }
                if (xCoordinate - 1 >= 0) { //if it is possible to be one of the left without going out of bound
                    for (int y = yCoordinate; y > yCoordinate - distanceViewing; y--) { //check one left
                        if (y >= 0) { //cant go higher than y=0, so if the number is positive is out of bound
                            map.getTile(xCoordinate - 1,y).setExplored(player);
                            furthestSoFar = map.getTile(xCoordinate - 1,y);

                            //CHECK COLLISIONS with walls
                            if (map.getTile(xCoordinate - 1,y).getType() == TileType.WALL) { // TODO: check this
                                //if the agent sees a wall, we break as it cant see any further
                                break;
                            }
                        } else { //out of bound for edges of map
                            furthestSoFar = map.getTile(xCoordinate - 1,y + 1);
                            break;
                        }
                    }
                }
                if (xCoordinate + 1 < scenario.get().WIDTH) { //if it is possible to move one step to the right
                    for (int y = yCoordinate; y > yCoordinate - distanceViewing; y--) { //check one right
                        if (y >= 0) { //cant go higher than y=0, so if the number is positive is out of bound

                            map.getTile(xCoordinate + 1,y).setExplored(player);
                            furthestSoFar = map.getTile(xCoordinate + 1,y);
                            //CHECK COLLISIONS with walls
                            if (map.getTile(xCoordinate + 1,y).getType() == TileType.WALL) {
                                break;
                            }
                        } else { //out of bound for edges of map
                            furthestSoFar =map.getTile(xCoordinate + 1,y + 1);
                            break;
                        }
                    }
                }
            }

            case SOUTH -> {
                for (int y = yCoordinate; y < yCoordinate + distanceViewing; y++) { //check straight
                    if (y < scenario.get().HEIGHT) { //cant go lower than y=map.height, so if the number is larger is out of bound
                        map.getTile(xCoordinate,y).setExplored(player);
                        furthestSoFar = map.getTile(xCoordinate,y);

                        //CHECK COLLISIONS with walls
                        if (map.getTile(xCoordinate,y).getType() == TileType.WALL) {
                            break;
                        }
                    } else { //out of bound for edges of map
                        furthestSoFar = map.getTile(xCoordinate,y-1);
                        break;
                    }
                }
                if (xCoordinate - 1 >= 0) { //if it is possible to be one of the left without going out of bound
                    for (int y = yCoordinate; y < yCoordinate + distanceViewing; y++) { //check one left
                        if (y < scenario.get().HEIGHT) { //cant go lower than y=map.height, so if the number is larger is out of bound
                            map.getTile(xCoordinate-1,y).setExplored(player);
                            furthestSoFar = map.getTile(xCoordinate-1,y);

                            //CHECK COLLISIONS with walls
                            if (map.getTile(xCoordinate-1,y).getType() == TileType.WALL) {
                                break;
                            }
                        } else { //out of bound for edges of map
                            furthestSoFar = map.getTile(xCoordinate-1,y-1);
                            break;
                        }
                    }
                }
                if (xCoordinate + 1 < scenario.get().WIDTH) { //if it is possible to move one step to the right
                    for (int y = yCoordinate; y < yCoordinate + distanceViewing; y++) { //check one right
                        if (y < scenario.get().HEIGHT) { //cant go lower than y=map.height, so if the number is larger is out of bound
                            map.getTile(xCoordinate+1,y).setExplored(player);
                            furthestSoFar = map.getTile(xCoordinate-1,y);

                            //CHECK COLLISIONS with walls
                            if (map.getTile(xCoordinate+1,y).getType() == TileType.WALL) {
                                break;
                            }
                        } else {
                            furthestSoFar = map.getTile(xCoordinate+1,y-1);
                            break;
                        }
                    }
                }
            }
            case RIGHT -> {
                for (int x = xCoordinate; x < xCoordinate + distanceViewing; x++) { //check straight
                    if (x < scenario.get().WIDTH) {
                        map.getTile(x,yCoordinate).setExplored(player);
                        furthestSoFar = map.getTile(x,yCoordinate);

                        //CHECK COLLISIONS with walls
                        if (map.getTile(x,yCoordinate).getType() == TileType.WALL) {
                            break;
                        }
                    } else { //out of bound for edges of map
                        furthestSoFar = map.getTile(x-1,yCoordinate);
                        break;
                    }
                }
                if (yCoordinate - 1 >= 0) {
                    for (int x = xCoordinate; x < xCoordinate + distanceViewing; x++) {
                        if (x < scenario.get().WIDTH) { //cant go higher than y=0, so if the number is positive is out of bound
                            map.getTile(x,yCoordinate-1).setExplored(player);
                            furthestSoFar = map.getTile(x,yCoordinate-1);

                            //CHECK COLLISIONS with walls
                            if (map.getTile(x,yCoordinate-1).getType() == TileType.WALL) {
                                break;
                            }
                        } else {
                            furthestSoFar = map.getTile(x-1,yCoordinate-1);
                            break;
                        }
                    }
                }
                if (yCoordinate + 1 < scenario.get().HEIGHT) {
                    for (int x = xCoordinate; x < xCoordinate + distanceViewing; x++) {
                        if (x < scenario.get().WIDTH) {
                            map.getTile(x,yCoordinate+1).setExplored(player);
                            furthestSoFar = map.getTile(x,yCoordinate+1);

                            //CHECK COLLISIONS with walls
                            if (map.getTile(x,yCoordinate+1).getType() == TileType.WALL) {
                                break;
                            }
                        } else {
                            furthestSoFar = map.getTile(x-1,yCoordinate+1);
                            break;
                        }
                    }
                }
            }
            case LEFT -> {
                for (int x = xCoordinate; x > xCoordinate - distanceViewing; x--) { //check straight
                    if (x >= 0) {
                        map.getTile(x,yCoordinate).setExplored(player);
                        furthestSoFar = map.getTile(x,yCoordinate);
                        //CHECK COLLISIONS with walls
                        if (map.getTile(x,yCoordinate).getType() == TileType.WALL) {

                            break;
                        }
                    } else { //out of bound for edges of map
                        furthestSoFar = map.getTile(x+1,yCoordinate);
                        break;
                    }
                }
                if (yCoordinate - 1 >= 0) {
                    for (int x = xCoordinate; x > xCoordinate - distanceViewing; x--) {
                        if (x >= 0) { //cant go higher than y=0, so if the number is positive is out of bound
                            map.getTile(x,yCoordinate-1).setExplored(player);
                            furthestSoFar = map.getTile(x,yCoordinate-1);

                            //CHECK COLLISIONS with walls
                            if (map.getTile(x,yCoordinate-1).getType() == TileType.WALL) {
                                break;
                            }
                        } else {
                            furthestSoFar = map.getTile(x+1,yCoordinate-1);
                            break;
                        }
                    }
                }
                if (yCoordinate + 1 < scenario.get().HEIGHT) {
                    for (int x = xCoordinate; x > xCoordinate - distanceViewing; x--) {
                        if (x >= 0) {
                            map.getTile(x,yCoordinate+1).setExplored(player);
                            furthestSoFar = map.getTile(x,yCoordinate+1);

                            //CHECK COLLISIONS with walls
                            if (map.getTile(x,yCoordinate+1).getType() == TileType.WALL) {
                                break;
                            }
                        } else {
                            furthestSoFar = map.getTile(x+1,yCoordinate+1);
                            break;
                        }
                    }
                }
            }
        }
    }



}
