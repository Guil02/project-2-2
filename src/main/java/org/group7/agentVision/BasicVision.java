package org.group7.agentVision;

import org.group7.enums.Actions;
import org.group7.enums.ComponentEnum;
import org.group7.enums.Orientation;
import org.group7.model.Grid;
import org.group7.model.Scenario;
import org.group7.model.component.playerComponents.PlayerComponent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.group7.enums.ComponentEnum.*;

public class BasicVision {
    Scenario scenario;
    Grid[][] map;
    int distanceViewing;
    List<Grid> furthestFrontierGrid = new ArrayList<>(); //since our vision is containing 3 "rays"(actually grids) for every call this list will have 3 elements.

    public BasicVision(Scenario scenario) {
        this.scenario = scenario;
        this.map = scenario.getMap();
        this.distanceViewing = scenario.getDistantViewing() + 1;  //to fix the fact that the loop doesnt include the >= or <=;
    }

    //Method for Frontier-Based Algorithm
    //TODO: talk with ROMA & XANDER
    public List<Grid> getAgentVision(PlayerComponent player) {
        calculateAgentVision(player);
        return furthestFrontierGrid;
    }

    public Scenario calculateAgentVision(PlayerComponent player) {
        //clear the list otherwise it will increase like rabbits
        if (furthestFrontierGrid != null) {
            furthestFrontierGrid.clear();
        }
        //get position of agent
        int xCoordinate = (int) player.getCoordinates().getX();
        int yCoordinate = (int) player.getCoordinates().getY();
        Grid positionOfAgent = map[xCoordinate][yCoordinate];
        //get direction of agent
        Orientation directionAngle = player.getOrientation(); //up,down,etc.
        //loop through map and mark as explored - for god view and agent view
        switch (directionAngle) {
            case UP -> {
                Grid furthestSoFar = null;
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
                furthestFrontierGrid.add(furthestSoFar);
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
                furthestFrontierGrid.add(furthestSoFar);
                if (xCoordinate + 1 < scenario.getHeight()) { //if it is possible to move one step to the right
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
                furthestFrontierGrid.add(furthestSoFar);
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
                furthestFrontierGrid.add(furthestSoFar);
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
                furthestFrontierGrid.add(furthestSoFar);
                if (xCoordinate + 1 < scenario.getHeight()) { //if it is possible to move one step to the right
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
                furthestFrontierGrid.add(furthestSoFar);
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
                furthestFrontierGrid.add(furthestSoFar);
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
                furthestFrontierGrid.add(furthestSoFar);
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
                furthestFrontierGrid.add(furthestSoFar);
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
                furthestFrontierGrid.add(furthestSoFar);
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
                furthestFrontierGrid.add(furthestSoFar);
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
                furthestFrontierGrid.add(furthestSoFar);
            }
        }
        scenario.updateMap(map);
        return scenario;
    }
}




