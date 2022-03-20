package org.group7.model.algorithms;

import org.group7.enums.Actions;
import org.group7.enums.ComponentEnum;
import org.group7.enums.Orientation;
import org.group7.model.Grid;
import org.group7.model.component.playerComponents.PlayerComponent;

import java.util.ArrayList;
import java.util.List;

public class Frontier implements Algorithm {
    private final int initialX;
    private final int initialY;
    private final Grid[][] map;
    private final Grid[][] playerMap;
    private final PlayerComponent player;
    private final List<Grid> leftOverFrontiers = new ArrayList<>();
    private final List<Grid> furthestFrontierGrid = new ArrayList<>();
    List<ActionTuple> movesLeft;
    private boolean firstTime = true;
    private Grid tempGrid = null;


    private int temp = 0;

    public Frontier(int initialX, int initialY, Grid[][] map, Grid[][] playerMap, PlayerComponent player) {
        this.initialX = initialX;

        this.initialY = initialY;
        this.map = map;
        this.playerMap = playerMap;
        this.player = player;
    }

    public List<Orientation> updateOrientaion(List<Orientation> temp) {

        temp.add(Orientation.LEFT);
        temp.add(Orientation.RIGHT);
        temp.add(Orientation.UP);
        temp.add(Orientation.DOWN);

        return temp;
    }

    public List<Grid> getNorthFrontiers() {
        Orientation orientation = player.getOrientation();
        List<Grid> north_frontiers = new ArrayList<>();
        if (orientation == Orientation.UP) {
            north_frontiers.addAll(player.getAgentsCurrentVision());
        }
        return north_frontiers;
    }

    public List<Grid> getSouthFrontiers() {
        Orientation orientation = player.getOrientation();
        List<Grid> south_frontiers = new ArrayList<>();
        if (orientation == Orientation.DOWN) {
            south_frontiers.addAll(player.getAgentsCurrentVision());
        }
        return south_frontiers;
    }

    public List<Grid> getWestFrontiers() {
        Orientation orientation = player.getOrientation();
        List<Grid> west_frontiers = new ArrayList<>();
        if (orientation == Orientation.LEFT) {
            west_frontiers.addAll(player.getAgentsCurrentVision());
        }
        return west_frontiers;
    }

    public List<Grid> getEastFrontiers() {
        Orientation orientation = player.getOrientation();
        List<Grid> east_frontiers = new ArrayList<>();
        if (orientation == Orientation.RIGHT) {
            east_frontiers.addAll(player.getAgentsCurrentVision());
        }
        return east_frontiers;
    }

    @Override
    public ActionTuple calculateMovement() {

        List<Actions> actionPath = new ArrayList<>();
        Orientation orientation = player.getOrientation();
        List<Orientation> listOfOrientations = new ArrayList<>();

        if (firstTime) {
            firstTime = false;
            listOfOrientations = updateOrientaion(listOfOrientations);
        }

        if (!listOfOrientations.isEmpty() && !firstTime && orientation == Orientation.RIGHT) {  // ---->>>

            if (player.getAgentsCurrentVision().size() == 2) {
                furthestFrontierGrid.add(null);
            }

            furthestFrontierGrid.addAll(player.getAgentsCurrentVision());
            listOfOrientations.remove(Orientation.RIGHT);

            return new ActionTuple(Actions.TURN_DOWN, 0);

        } else if (!listOfOrientations.isEmpty() && !firstTime && orientation == Orientation.DOWN) {

            if (player.getAgentsCurrentVision().size() == 2) {
                furthestFrontierGrid.add(null);
            }
            furthestFrontierGrid.addAll(player.getAgentsCurrentVision());
            listOfOrientations.remove(Orientation.DOWN);

            return new ActionTuple(Actions.TURN_LEFT, 0);

        } else if (!listOfOrientations.isEmpty() && !firstTime && orientation == Orientation.LEFT) {

            if (player.getAgentsCurrentVision().size() == 2) {
                furthestFrontierGrid.add(null);
            }
            furthestFrontierGrid.addAll(player.getAgentsCurrentVision());

            return new ActionTuple(Actions.TURN_UP, 0);

        } else if (!listOfOrientations.isEmpty() && !firstTime && orientation == Orientation.UP) {

            if (player.getAgentsCurrentVision().size() == 2) {
                furthestFrontierGrid.add(null);
            }
            furthestFrontierGrid.addAll(player.getAgentsCurrentVision());
            listOfOrientations.remove(Orientation.UP);

            return new ActionTuple(Actions.TURN_RIGHT, 0);
        }

        if (listOfOrientations.isEmpty()) {

            //TODO: make list of frontiers(leftOverFrontiers)
            //TODO: delete used frontier from leftOverFrontiers

            boolean firstEntry = true;

            int count = 0;

            for (Grid grid : furthestFrontierGrid) {//loop through list of the furthest grid arrays

                if (count != 3) {

                    if (grid.getStaticComponent() == null && firstEntry) { //checks for frontiers
                        firstEntry = false;

                        count++;

                        tempGrid = grid;

                        if (count == 3) {
                            leftOverFrontiers.add(tempGrid);
                        }
                        // adds frontier to the frontier array
                        //looks through the biggest first frontier
                        //goes towards it,delete it from the leftOverList
                    } else if (grid.getStaticCompE() == ComponentEnum.WALL && !firstEntry) { //checks for frontiers
                        if (count == 1 || count == 2) {
                            if (tempGrid == null) {
                                count++;
                            } else if (count == 2) {
                                firstEntry = true;
                                count++;

                                leftOverFrontiers.add(tempGrid);
                            }
                        }
                        //end the array/create a new one

                    } else if (grid.getStaticCompE() == null && !firstEntry) {
                        count++;
                        if (count == 3) {
                            leftOverFrontiers.add(tempGrid);
                        }
                        tempGrid = grid;

                    } else if (grid.getStaticCompE() == ComponentEnum.WALL && firstEntry) {
                        count++;
                        //end the array/create a new one

                    }
                } else {

                    firstEntry = true;
                    count = 0;
                    tempGrid = null;

                }


            }


            furthestFrontierGrid.clear();
            Grid centroid = leftOverFrontiers.get(leftOverFrontiers.size() - 1);

            //TODO: CENTROID->GO TO IT
            //TODO:map for every player
            int centroidX = centroid.getX();
            int centroidY = centroid.getY();

            player.getCoordinates().getX();
            player.getCoordinates().getY();

            int xDif = (int) (centroidX - player.getX());
            int yDif = (int) (centroidY - player.getY());

            if(xDif>0){
                if(yDif>0){

                }else if (yDif<0){

                }else {

                }
            }else if(xDif<0){
                if(yDif>0){

                }else if (yDif<0){

                }else {

                }
            }if(xDif == 0){
                if(yDif>0){

                }else if (yDif<0){

                }else {

                }
            }



            //TODO: use that map to go towards centroid
            //TODO: return ActionTuple or list of ActionTuple idk
            //actionPath = findPath(centroid);

            firstTime = true;
            leftOverFrontiers.remove(leftOverFrontiers.size() - 1);


        }

        return null;
    }

    //List of the path from starting point to the end destination, so it doesn't evaluate new options very timestep
    public List<ActionTuple> findPath() {
        List<ActionTuple> path = new ArrayList<>();
        List<Actions> actions = new ArrayList<>(); //need a list of coordinates from start to end destination
        double speed = player.getBaseSpeed();

        for (int x = 0; x < actions.size(); x++) {
            if (actions.get(x) == Actions.MOVE_FORWARD) {
                int count = 1;
                for (int y = x + 1; y < actions.size() && y < speed; y++) {
                    if (actions.get(y) == Actions.MOVE_FORWARD) {
                        x++;
                        count++;
                    } else {
                        break;
                    }
                }
                path.add(new ActionTuple(actions.get(x), count));
            } else {
                path.add(new ActionTuple(actions.get(x), 0));
            }
        }
        return path;
    }

    //maybe useful for u Roman
    public int distance_frontier(int x, int y) {
        return Math.abs(initialX - x) + Math.abs(initialY - y);
    }

}
