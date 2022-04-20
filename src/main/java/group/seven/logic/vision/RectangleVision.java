package group.seven.logic.vision;

import group.seven.enums.Cardinal;
import group.seven.enums.TileType;
import group.seven.model.agents.Agent;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.Tile;
import group.seven.model.environment.TileMap;

import java.util.ArrayList;
import java.util.List;

public class RectangleVision implements Vision {
    Scenario scenario;
    TileMap[][] map; // TileMap = Map , and Grid = Tile
    int distanceViewing;

    public RectangleVision(Scenario scenario) {
        this.scenario = scenario;
        this.map = scenario.TILE_MAP();
        this.distanceViewing = scenario.VIEW_DISTANCE();  //to fix the fact that the loop doesnt include the >= or <=;
    }

    public void setExplored(Agent agent){
        if (agent.getType() == TileType.GUARD) {

        }else if (agent.getType() == TileType.INTRUDER){

        }
    }

    //Given a player,

    // Given a player, the map is updated based on what it saw
    public void updateMap (Agent player) {

        //get position of agent
        int xCoordinate = player.getX();
        int yCoordinate = player.getY();

        //get direction of agent
        Cardinal directionAngle = player.getDirection(); //up,down,etc.

        //loop through tileMap and mark as explored - for god view and agent view
        switch (directionAngle) {
            case UP -> {
                Tile furthestSoFar = null;
                for (int y = yCoordinate; y > yCoordinate - distanceViewing; y--) { //check straight
                    if (y >= 0) { //cant go lower than y=0, so if the number is negative is out of bound
                        map[xCoordinate][y].explore();

                        map[xCoordinate][y].exploreAgent(player.getId());
                        //CHECK COLLISIONS with walls
                        if (map[xCoordinate][y].getStaticCompE() == WALL) {
                            furthestSoFar = map[xCoordinate][y]; //if the agent sees a wall, we store it as the furthest it has seen.
                            break;
                        }
                        furthestSoFar = map[xCoordinate][y];
                    } else { //out of bound for edges of map
                        furthestSoFar = map[xCoordinate][y + 1];
                        break;
                    }
                }
                if (xCoordinate - 1 >= 0) { //if it is possible to be one of the left without going out of bound
                    for (int y = yCoordinate; y > yCoordinate - distanceViewing; y--) { //check one left
                        if (y >= 0) { //cant go higher than y=0, so if the number is positive is out of bound
                            map[xCoordinate - 1][y].explore();
                            map[xCoordinate - 1][y].exploreAgent(player.getId());
                            //CHECK COLLISIONS with walls
                            if (map[xCoordinate - 1][y].getStaticCompE() == WALL) {
                                furthestSoFar = map[xCoordinate - 1][y]; //if the agent sees a wall, we store it as the furthest it has seen.
                                break;
                            }
                            furthestSoFar = map[xCoordinate - 1][y];
                        } else { //out of bound for edges of map
                            furthestSoFar = map[xCoordinate - 1][y + 1];
                            break;
                        }
                    }
                }
                if (xCoordinate + 1 < scenario.getWidth()) { //if it is possible to move one step to the right
                    for (int y = yCoordinate; y > yCoordinate - distanceViewing; y--) { //check one right
                        if (y >= 0) { //cant go higher than y=0, so if the number is positive is out of bound
                            map[xCoordinate + 1][y].explore();
                            map[xCoordinate + 1][y].exploreAgent(player.getId());
                            //CHECK COLLISIONS with walls
                            if (map[xCoordinate + 1][y].getStaticCompE() == WALL) {
                                furthestSoFar = map[xCoordinate + 1][y]; //if the agent sees a wall, we store it as the furthest it has seen.
                                break;
                            }
                            furthestSoFar = map[xCoordinate + 1][y];
                        } else { //out of bound for edges of map
                            furthestSoFar = map[xCoordinate + 1][y + 1];
                            break;
                        }
                    }
                }
            }

            case DOWN -> {
                Grid furthestSoFar = null;
                for (int y = yCoordinate; y < yCoordinate + distanceViewing; y++) { //check straight
                    if (y < scenario.getHeight()) { //cant go lower than y=map.height, so if the number is larger is out of bound
                        map[xCoordinate][y].explore();
                        map[xCoordinate][y].exploreAgent(player.getId());
                        //CHECK COLLISIONS with walls
                        if (map[xCoordinate][y].getStaticCompE() == WALL) {
                            furthestSoFar = map[xCoordinate][y]; //if the agent sees a wall, we store it as the furthest it has seen.
                            break;
                        }
                        furthestSoFar = map[xCoordinate][y];
                    } else { //out of bound for edges of map
                        furthestSoFar = map[xCoordinate][y - 1];
                        break;
                    }
                }
                if (xCoordinate - 1 >= 0) { //if it is possible to be one of the left without going out of bound
                    for (int y = yCoordinate; y < yCoordinate + distanceViewing; y++) { //check one left
                        if (y < scenario.getHeight()) { //cant go lower than y=map.height, so if the number is larger is out of bound
                            map[xCoordinate - 1][y].explore();
                            map[xCoordinate - 1][y].exploreAgent(player.getId());
                            //CHECK COLLISIONS with walls
                            if (map[xCoordinate - 1][y].getStaticCompE() == WALL) {
                                furthestSoFar = map[xCoordinate -1][y]; //if the agent sees a wall, we store it as the furthest it has seen.
                                break;
                            }
                            furthestSoFar = map[xCoordinate - 1][y];
                        } else { //out of bound for edges of map
                            furthestSoFar = map[xCoordinate - 1][y - 1];
                            break;
                        }
                    }
                }
                if (xCoordinate + 1 < scenario.getWidth()) { //if it is possible to move one step to the right
                    for (int y = yCoordinate; y < yCoordinate + distanceViewing; y++) { //check one right
                        if (y < scenario.getHeight()) { //cant go lower than y=map.height, so if the number is larger is out of bound
                            map[xCoordinate + 1][y].explore();
                            map[xCoordinate + 1][y].exploreAgent(player.getId());
                            //CHECK COLLISIONS with walls
                            if (map[xCoordinate + 1][y].getStaticCompE() == WALL) {
                                furthestSoFar = map[xCoordinate +1][y]; //if the agent sees a wall, we store it as the furthest it has seen.
                                break;
                            }
                            furthestSoFar = map[xCoordinate + 1][y];
                        } else {
                            furthestSoFar = map[xCoordinate + 1][y - 1];
                            break;
                        }
                    }
                }
            }
            case RIGHT -> {
                Grid furthestSoFar = null;
                for (int x = xCoordinate; x < xCoordinate + distanceViewing; x++) { //check straight
                    if (x < scenario.getWidth()) {
                        map[x][yCoordinate].explore();
                        map[x][yCoordinate].exploreAgent(player.getId());
                        //CHECK COLLISIONS with walls
                        if (map[x][yCoordinate].getStaticCompE() == WALL) {
                            furthestSoFar = map[x][yCoordinate]; //if the agent sees a wall, we store it as the furthest it has seen.
                            break;
                        }
                        furthestSoFar = map[x][yCoordinate];
                    } else { //out of bound for edges of map
                        furthestSoFar = map[x - 1][yCoordinate];
                        break;
                    }
                }
                if (yCoordinate - 1 >= 0) {
                    for (int x = xCoordinate; x < xCoordinate + distanceViewing; x++) {
                        if (x < scenario.getWidth()) { //cant go higher than y=0, so if the number is positive is out of bound
                            map[x][yCoordinate - 1].explore();
                            map[x][yCoordinate - 1].exploreAgent(player.getId());
                            //CHECK COLLISIONS with walls
                            if (map[x][yCoordinate - 1].getStaticCompE() == WALL) {
                                furthestSoFar = map[x][yCoordinate -1]; //if the agent sees a wall, we store it as the furthest it has seen.
                                break;
                            }
                            furthestSoFar = map[x][yCoordinate - 1];
                        } else {
                            furthestSoFar = map[x - 1][yCoordinate - 1];
                            break;
                        }
                    }
                }
                if (yCoordinate + 1 < scenario.getHeight()) {
                    for (int x = xCoordinate; x < xCoordinate + distanceViewing; x++) {
                        if (x < scenario.getWidth()) {
                            map[x][yCoordinate + 1].explore();
                            map[x][yCoordinate + 1].exploreAgent(player.getId());
                            //CHECK COLLISIONS with walls
                            if (map[x][yCoordinate + 1].getStaticCompE() == WALL) {
                                furthestSoFar = map[x][yCoordinate +1]; //if the agent sees a wall, we store it as the furthest it has seen.
                                break;
                            }
                            furthestSoFar = map[x][yCoordinate + 1];
                        } else {
                            furthestSoFar = map[x - 1][yCoordinate + 1];
                            break;
                        }
                    }
                }
            }
            case LEFT -> {
                Grid furthestSoFar = null;
                for (int x = xCoordinate; x > xCoordinate - distanceViewing; x--) { //check straight
                    if (x >= 0) {
                        map[x][yCoordinate].explore();
                        map[x][yCoordinate].exploreAgent(player.getId());
                        //CHECK COLLISIONS with walls
                        if (map[x][yCoordinate].getStaticCompE() == WALL) {
                            furthestSoFar = map[x][yCoordinate]; //if the agent sees a wall, we store it as the furthest it has seen.

                            break;
                        }
                        furthestSoFar = map[x][yCoordinate];
                    } else { //out of bound for edges of map
                        furthestSoFar = map[x + 1][yCoordinate];
                        break;
                    }
                }
                if (yCoordinate - 1 >= 0) {
                    for (int x = xCoordinate; x > xCoordinate - distanceViewing; x--) {
                        if (x >= 0) { //cant go higher than y=0, so if the number is positive is out of bound
                            map[x][yCoordinate - 1].explore();
                            map[x][yCoordinate - 1].exploreAgent(player.getId());
                            //CHECK COLLISIONS with walls
                            if (map[x][yCoordinate - 1].getStaticCompE() == WALL) {
                                furthestSoFar = map[x][yCoordinate -1]; //if the agent sees a wall, we store it as the furthest it has seen.

                                break;
                            }
                            furthestSoFar = map[x][yCoordinate - 1];
                        } else {
                            furthestSoFar = map[x + 1][yCoordinate - 1];
                            break;
                        }
                    }
                }
                if (yCoordinate + 1 < scenario.getHeight()) {
                    for (int x = xCoordinate; x > xCoordinate - distanceViewing; x--) {
                        if (x >= 0) {
                            map[x][yCoordinate + 1].explore();
                            map[x][yCoordinate + 1].exploreAgent(player.getId());
                            //CHECK COLLISIONS with walls
                            if (map[x][yCoordinate + 1].getStaticCompE() == WALL) {
                                furthestSoFar = map[x][yCoordinate + 1]; //if the agent sees a wall, we store it as the furthest it has seen.

                                break;
                            }
                            furthestSoFar = map[x][yCoordinate + 1];
                        } else {
                            furthestSoFar = map[x + 1][yCoordinate + 1];
                            break;
                        }
                    }
                }
            }
        }
    }



}
