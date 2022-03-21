package org.group7.model.algorithms;

import org.group7.enums.Actions;
import org.group7.enums.ComponentEnum;
import org.group7.enums.Orientation;
import org.group7.model.Grid;
import org.group7.model.component.playerComponents.PlayerComponent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Frontier implements Algorithm {
    static final int ROW = 4;
    static final int COL = 4;
//    static int dRow[] = {-1, 0, 1, 0};
//    static int dCol[] = {0, 1, 0, -1};
    private static Grid centroid = null;
    private final int initialX;
    private final int initialY;
    private final PlayerComponent player;
    private final List<Grid> leftOverFrontiers = new ArrayList<>();
    private final List<Grid> furthestFrontierGrid = new ArrayList<>();
    private final Grid[][] map;
    private final Grid[][] playerMap;
    List<ActionTuple> movesLeft;
    private boolean firstTime = true;
    private Grid tempGrid = null;
    private Grid previousGrid = null;
    private List<Actions> actionPath = new ArrayList<>();
    private int temp = 0;

    public Frontier(int initialX, int initialY, Grid[][] map, Grid[][] playerMap, PlayerComponent player) {
        this.initialX = initialX;

        this.initialY = initialY;
        this.map = map;
        this.playerMap = playerMap;
        this.player = player;
    }

    // Function to perform the BFS traversal
//    static void BFS(int grid[][], boolean vis[][],
//                    int row, int col) {
//
//        // Stores indices of the matrix cells
//        Queue<pair> q = new LinkedList<>();
//
//        // Mark the starting cell as visited
//        // and push it into the queue
//        q.add(new pair(row, col));
//        vis[row][col] = true;
//
//        // Iterate while the queue
//        // is not empty
//        while (!q.isEmpty()) {
//            pair cell = q.peek();
//            int x = cell.first;
//            int y = cell.second;
//
//            System.out.print(grid[x][y] + " ");
//
//            q.remove();
//
//            // Go to the adjacent cells
//            for (int i = 0; i < 4; i++) {
//                int adjx = x + dRow[i];
//                int adjy = y + dCol[i];
//
//                if (isValid(vis, adjx, adjy)) {
//                    q.add(new pair(adjx, adjy));
//                    vis[adjx][adjy] = true;
//                }
//            }
//        }
//
//    }
//
//    // Function to check if a cell
//// is be visited or not
//    public static boolean isValid(boolean vis[][], int row, int col) {
//
//        // If cell lies out of bounds
//        if (row < 0 || col < 0 ||
//                row >= ROW || col >= COL)
//            return false;
//
//        // If cell is already visited
//        if (vis[row][col])
//            return false;
//
//        // Otherwise
//        return true;
//    }

    //TODO: Please fix compilation errors. Do not push code to github that does not compile!!!
    /*
    private static int pathExists(Grid[][] grids) {

        Node source = new Node(0, 0, 0);
        Queue<Node> queue = new LinkedList<Node>();

        queue.add(source);

        while (!queue.isEmpty()) {
            Node poped = queue.poll();

            Grid centroid = Frontier.centroid;
            if (grids[poped.x][poped.y].equals(centroid)) {
                return poped.distanceFromSource;
            } else {

                if (grids[poped.x - 1][poped.y].getStaticCompE() == ComponentEnum.WALL || grids[poped.x - 1][poped.y].seen=true)) {

                    List<Node> neighbourList = addNeighbours(poped, grids);
                    queue.addAll(neighbourList);
                }
            }
        }
        return -1;
    }

    private static List<Node> addNeighbours(Node poped, Grid[][] matrix) {

        List<Node> list = new LinkedList<Node>();

        if ((poped.x - 1 >= 0 && poped.x - 1 < matrix.length) && (!(matrix[poped.x - 1][poped.y].getStaticCompE() == ComponentEnum.WALL) || !matrix[poped.x - 1][poped.y].seen)) { //
            list.add(new Node(poped.x - 1, poped.y, poped.distanceFromSource + 1));
        }
        if ((poped.x + 1 >= 0 && poped.x + 1 < matrix.length) && (!(matrix[poped.x - 1][poped.y].getStaticCompE() == ComponentEnum.WALL) || !matrix[poped.x - 1][poped.y].seen)) {
            list.add(new Node(poped.x + 1, poped.y, poped.distanceFromSource + 1));
        }
        if ((poped.y - 1 >= 0 && poped.y - 1 < matrix.length) && (!(matrix[poped.x - 1][poped.y].getStaticCompE() == ComponentEnum.WALL) || !matrix[poped.x - 1][poped.y].seen)) {
            list.add(new Node(poped.x, poped.y - 1, poped.distanceFromSource + 1));
        }
        if ((poped.y + 1 >= 0 && poped.y + 1 < matrix.length) && (!(matrix[poped.x - 1][poped.y].getStaticCompE() == ComponentEnum.WALL) || !matrix[poped.x - 1][poped.y].seen)) {
            list.add(new Node(poped.x, poped.y + 1, poped.distanceFromSource + 1));
        }
        return list;
    }
    */

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

        if (actionPath.isEmpty()) {


            //List<Actions> actionPath = new ArrayList<>();
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
// {list of potential centroids to use...n-1, n }
                //we are choosing the last entry

                furthestFrontierGrid.clear();

                if (previousGrid.getX() == leftOverFrontiers.get(leftOverFrontiers.size() - 1).getX() && previousGrid.getY() == leftOverFrontiers.get(leftOverFrontiers.size() - 1).getY()) {
                    centroid = leftOverFrontiers.get(leftOverFrontiers.size() - 2);
                    leftOverFrontiers.remove(leftOverFrontiers.size() - 2);
                } else {
                    centroid = leftOverFrontiers.get(leftOverFrontiers.size() - 1);
                    leftOverFrontiers.remove(leftOverFrontiers.size() - 1);
                }
                previousGrid = new Grid((int) (player.getX()), (int) (player.getY()));

                //TODO: CENTROID->GO TO IT
                //TODO:map for every player
                int centroidX = centroid.getX();
                int centroidY = centroid.getY();

                player.getCoordinates().getX();
                player.getCoordinates().getY();

                int xDif = (int) (centroidX - player.getX());
                int yDif = (int) (centroidY - player.getY());


                //TODO: use that map to go towards centroid
                //TODO: return ActionTuple or list of ActionTuple idk
                //actionPath = findPath(centroid);

                firstTime = true;
                leftOverFrontiers.remove(leftOverFrontiers.size() - 1);


            }


        } else {
            for (Actions action : actionPath) {
                //store action\
                //execute it
                // remove that action
                actionPath.remove(action);
            }
        }

        ActionTuple actionTuple = new ActionTuple(Actions.MOVE_FORWARD, 0);
        return actionTuple;
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

    static class pair {
        int first, second;

        public pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }
}

class Node {
    int x;
    int y;
    int distanceFromSource;

    Node(int x, int y, int dis) {
        this.x = x;
        this.y = y;
        this.distanceFromSource = dis;
    }

    //maybe useful for u Roman


}


