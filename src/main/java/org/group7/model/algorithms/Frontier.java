package org.group7.model.algorithms;

import org.group7.enums.Actions;
import org.group7.enums.ComponentEnum;
import org.group7.enums.Orientation;
import org.group7.model.Grid;
import org.group7.model.component.playerComponents.PlayerComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Node {
    private int x;
    private int y;
    private Grid value;

    public Node(int x, int y, Grid value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Grid getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "(x: " + x + " y: " + y + ")";
    }

    @Override
    public int hashCode() {
        return x * y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y;
    }
}


public class Frontier implements Algorithm {
    private static Grid centroid = null;
    private static boolean firstTime = true;
    private static Grid tempGrid = null;
    private static Grid previousGrid = null;
    private static List<Actions> actionPath = new ArrayList<>();
    private static Grid currentGrid = null;
    private static List<Orientation> listOfOrientations = new ArrayList<>();
    private static boolean firstEntry = true;
    //private final Grid[][] playerMap;
    private final int initialX;
    private final int initialY;
    private final PlayerComponent player;
    private final Grid[][] map;
    private List<Grid> leftOverFrontiers = new ArrayList<>();
    private List<Grid> furthestFrontierGrid = new ArrayList<>();
    private int temp = 0;

    public Frontier(int initialX, int initialY, Grid[][] map, PlayerComponent player) {
        this.initialX = initialX;

        this.initialY = initialY;
        this.map = map;
        this.player = player;
    }

    public List<Node> shortestPath() {
        // key node, value parent
        Map<Node, Node> parents = new HashMap<Node, Node>();
        Node start = null;
        Node end = null;

        // find the start node
        for (int row = 0; row < map.length; row++) {
            for (int column = 0; column < map[row].length; column++) {
                if (map[row][column] == map[(int) player.getX()][(int) player.getY()]) {
                    start = new Node(row, column, map[row][column]);
                    break;
                }
            }
            if (start != null) {
                break;
            }
        }

        if (start == null) {
            throw new RuntimeException("can't find start node");
        }

        // traverse every node using breadth first search until reaching the destination
        List<Node> temp = new ArrayList<Node>();
        temp.add(start);
        parents.put(start, null);

        boolean reachDestination = false;
        while (temp.size() > 0 && !reachDestination) {
            Node currentNode = temp.remove(0);
            List<Node> children = getChildren(currentNode);
            for (Node child : children) {
                // Node can only be visited once
                if (!parents.containsKey(child)) {
                    parents.put(child, currentNode);

                    Grid value = child.getValue();
                    if (value.getStaticCompE() != ComponentEnum.WALL && value.explored) { //seen and not wall
                        temp.add(child);
                    } else if (value.equals(centroid)) { //destination
                        temp.add(child);
                        reachDestination = true;
                        end = child;
                        break;
                    }
                }
            }
        }

        if (end == null) {
            throw new RuntimeException("can't find end node");
        }

        // get the shortest path
        Node node = end;
        List<Node> path = new ArrayList<Node>();
        while (node != null) {
            path.add(0, node);
            node = parents.get(node);
        }
        printPath(path);
        return path;
    }

    private List<Node> getChildren(Node parent) {
        List<Node> children = new ArrayList<Node>();
        int x = parent.getX();
        int y = parent.getY();
        if (x - 1 >= 0) {
            Node child = new Node(x - 1, y, map[x - 1][y]);
            children.add(child);
        }
        if (y - 1 >= 0) {
            Node child = new Node(x, y - 1, map[x][y - 1]);
            children.add(child);
        }
        if (x + 1 < map.length) {
            Node child = new Node(x + 1, y, map[x + 1][y]);
            children.add(child);
        }
        if (y + 1 < map[0].length) {
            Node child = new Node(x, y + 1, map[x][y + 1]);
            children.add(child);
        }
        return children;
    }

    private void printPath(List<Node> path) {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";

        for (int row = 0; row < map.length; row++) {
            for (int column = 0; column < map[row].length; column++) {
                String value = map[row][column] + "";

                // mark path with red X
                for (int i = 1; i < path.size() - 1; i++) {
                    Node node = path.get(i);
                    if (node.getX() == row && node.getY() == column) {
                        value = ANSI_RED + "X" + ANSI_RESET;
                        break;
                    }
                }
                if (column == map[row].length - 1) {
                    System.out.println(value);
                } else {
                    System.out.print(value + ".....");
                }
            }

            if (row < map.length - 1) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < map[row].length - 1; j++) {
                        System.out.print(".     ");
                    }
                    System.out.println(".     ");
                }
            }
        }
        System.out.println();
        System.out.println("Path: " + path);
    }

    public List<Orientation> updateOrientaion(List<Orientation> temp) {

        temp.add(Orientation.LEFT);
        temp.add(Orientation.RIGHT);
        temp.add(Orientation.UP);
        temp.add(Orientation.DOWN);

        return temp;
    }

    @Override
    public ActionTuple calculateMovement() {


        //List<Actions> actionPath = new ArrayList<>();
        Orientation orientation = player.getOrientation();


        if (firstTime) {
            firstTime = false;
            listOfOrientations = updateOrientaion(listOfOrientations);
        }
        while (!listOfOrientations.isEmpty()) {
            if (!firstTime && orientation == Orientation.RIGHT) {  // ---->>>

//            if (player.getAgentsCurrentVision().size() == 2) {
//                Grid g = new Grid(0, 0);
//                g.setTypeWall();
//                furthestFrontierGrid.add(g);
//            }

                List<Grid> gridss = player.getAgentsCurrentVision();
                furthestFrontierGrid.addAll(gridss);
                listOfOrientations.remove(Orientation.RIGHT);

                return new ActionTuple(Actions.TURN_DOWN, 0);

            } else if (!firstTime && orientation == Orientation.DOWN) {

//            if (player.getAgentsCurrentVision().size() != 3) {
//                Grid g = new Grid(0, 0);
//                g.setTypeWall();
//                furthestFrontierGrid.add(g);
//            }
                List<Grid> gridss = player.getAgentsCurrentVision();
                furthestFrontierGrid.addAll(gridss);
                listOfOrientations.remove(Orientation.DOWN);

                return new ActionTuple(Actions.TURN_LEFT, 0);

            } else if (!firstTime && orientation == Orientation.LEFT) {

//            if (player.getAgentsCurrentVision().size() != 3) {
//                Grid g = new Grid(0, 0);
//                g.setTypeWall();
//                furthestFrontierGrid.add(g);
//            }
                List<Grid> gridss = player.getAgentsCurrentVision();
                furthestFrontierGrid.addAll(gridss);
                listOfOrientations.remove(Orientation.LEFT);

                return new ActionTuple(Actions.TURN_UP, 0);

            } else if (!firstTime && orientation == Orientation.UP) {

//            if (player.getAgentsCurrentVision().size() != 3) {
//                Grid g = new Grid(0, 0);
//                g.setTypeWall();
//                furthestFrontierGrid.add(g);
//            }
                List<Grid> gridss = player.getAgentsCurrentVision();
                furthestFrontierGrid.addAll(gridss);
                listOfOrientations.remove(Orientation.UP);

                return new ActionTuple(Actions.TURN_RIGHT, 0);
            }

        }


        int count = 0;

        for (Grid grid : furthestFrontierGrid) {//loop through list of the furthest grid arrays
            while (count != 3) {
                if (grid.getStaticComponent() == null && firstEntry) { //checks for frontiers
                    firstEntry = false;

                    count++;

                    tempGrid = grid;

                    if (count == 3) {
                        leftOverFrontiers.add(tempGrid);
                        firstEntry = true;
                        count = 0;
                        tempGrid = null;
                    }
                    // adds frontier to the frontier array
                    //looks through the biggest first frontier
                    //goes towards it,delete it from the leftOverList
                } else if (grid.getStaticCompE() == ComponentEnum.WALL && !firstEntry) { //checks for frontiers


                    if (count == 2 || count == 1) {
                        leftOverFrontiers.add(tempGrid);
                        firstEntry = true;
                        tempGrid = null;
                        count++;
                    } else if (count == 3) {
                        leftOverFrontiers.add(tempGrid);
                        firstEntry = true;
                        count = 0;
                        tempGrid = null;
                    }

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
            }
            //end the array/create a new one

        }


        furthestFrontierGrid.clear();

//                if (previousGrid.getX() == leftOverFrontiers.get(leftOverFrontiers.size() - 1).getX() && previousGrid.getY() == leftOverFrontiers.get(leftOverFrontiers.size() - 1).getY()) {
//                    centroid = leftOverFrontiers.get(leftOverFrontiers.size() - 2);
//                    leftOverFrontiers.remove(leftOverFrontiers.size() - 2);
//                } else {
//                    centroid = leftOverFrontiers.get(leftOverFrontiers.size() - 1);
//                    leftOverFrontiers.remove(leftOverFrontiers.size() - 1);
//                }

        //System.out.println(leftOverFrontiers.get(leftOverFrontiers.size() - 1));
        centroid = leftOverFrontiers.get(leftOverFrontiers.size() - 1);
        leftOverFrontiers.remove(leftOverFrontiers.size() - 1);
        previousGrid = new Grid((int) (player.getX()), (int) (player.getY()));

        //TODO: CENTROID->GO TO IT
        //TODO:map for every player


        //Grid[][] currentGrid = new Grid[(int) player.getCoordinates().getX()][(int) player.getCoordinates().getY()];
        List<Node> nodePath = shortestPath();

        for (Node node :
                nodePath) {


            if (node.getX() - player.getCoordinates().getX() > 0) {//right

                if (player.getOrientation() != Orientation.RIGHT) {

                    return new ActionTuple(Actions.TURN_RIGHT, 0);
                } else if (player.getOrientation() == Orientation.RIGHT) {
                    return new ActionTuple(Actions.MOVE_FORWARD, 1);
                }
            }
            if (node.getX() - player.getCoordinates().getX() < 0) {//left
                if (player.getOrientation() != Orientation.LEFT) {

                    return new ActionTuple(Actions.TURN_LEFT, 0);
                } else if (player.getOrientation() == Orientation.LEFT) {
                    return new ActionTuple(Actions.MOVE_FORWARD, 1);
                }
            }


            if (node.getY() - player.getCoordinates().getY() > 0) {//down
                if (player.getOrientation() != Orientation.DOWN) {
                    return new ActionTuple(Actions.TURN_DOWN, 0);
                } else if (player.getOrientation() == Orientation.DOWN) {
                    return new ActionTuple(Actions.MOVE_FORWARD, 1);
                }
            } else if (node.getY() - player.getCoordinates().getY() < 0) {//up
                if (player.getOrientation() != Orientation.UP) {

                    return new ActionTuple(Actions.TURN_UP, 0);
                } else if (player.getOrientation() == Orientation.UP) {
                    return new ActionTuple(Actions.MOVE_FORWARD, 1);
                }
            }

        }


        return null;
    }

}




