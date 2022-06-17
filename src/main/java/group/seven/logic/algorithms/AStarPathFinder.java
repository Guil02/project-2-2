package group.seven.logic.algorithms;

import group.seven.enums.Action;
import group.seven.enums.Cardinal;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;
import group.seven.model.environment.TileNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static group.seven.enums.TileType.WALL;

public class AStarPathFinder {

    public static int instances = 0;

    private final AStarNode currentNode;


    private AStarNode target;
    private final Agent player;
    int[][] additions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; //TODO: maybe remove
    List<Move> movesLeft; // moves left to do //TODO: maybe remove
    List<AStarNode> open;//TODO: maybe remove
    List<AStarNode> closed; //TODO: maybe remove
    private TileNode[][] internalMap; // agent representation


    public AStarPathFinder(Agent player, XY goal) {
        instances++;
        //System.out.print("\r number: " + instances + " from: " + player.getType());
        this.target = new AStarNode(goal, this);
        open = new ArrayList<>();
        closed = new ArrayList<>();
        movesLeft = new ArrayList<>();
        currentNode = new AStarNode(player.getXY(), this);
        open.add(currentNode); //TODO: maybe remove
        this.player = player;
        this.internalMap = player.getMap();
        //internalMap.add(new TileNode(new Tile(startCoordinate.x(), startCoordinate.y())));
    }


    public List<Move> findPath() {
        internalMap = player.getMap();
        List<AStarNode> openedNodes = new ArrayList<>();
        List<AStarNode> closedNodes = new ArrayList<>();
        openedNodes.add(currentNode);
        currentNode.updateCost();

        while (!openedNodes.isEmpty()) {
            AStarNode node = openedNodes.get(0);
            for (int i = 1; i < openedNodes.size(); i++) {
                if (openedNodes.get(i).getfCost() < node.getfCost()) {
                    node = openedNodes.get(i);
                } else if (openedNodes.get(i).getfCost() == node.getfCost()) {
                    if (openedNodes.get(i).gethCost() < node.gethCost()) {
                        node = openedNodes.get(i);
                    }
                }
            }
            openedNodes.remove(node);
            //System.out.println("agent"+player.getType());
            closedNodes.add(node);
            //System.out.println("OPEN "+Arrays.toString(openedNodes.toArray()));


            if (node.equals(target)) {
                target = node;
                return makePath();
            }
            for (AStarNode neighbor : neighbours(node)) {
                if (closedNodes.contains(neighbor) || internalMap[neighbor.getX()][neighbor.getY()] == null || internalMap[neighbor.getX()][neighbor.getY()].getType() == WALL) {
                    continue;
                }
                //                neighbor.updateCost(ASTAR_PATH);
                int lowestCost = node.getgCost() + Math.abs(node.getX() - neighbor.getX()) + Math.abs(node.getY() - neighbor.getY());
                if (lowestCost < neighbor.getgCost() || !openedNodes.contains(neighbor)) {
                    neighbor.updateHCost();
                    neighbor.setgCost(lowestCost);
                    neighbor.updateFCost();
                    neighbor.setParent(node);
                    if (!openedNodes.contains(neighbor)) {
                        openedNodes.add(neighbor);
                    }
                }
            }
        }
        return makePath();
    }


    public List<AStarNode> neighbours(AStarNode node) {
        int x = node.getX();
        int y = node.getY();
        List<AStarNode> neighbours = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            AStarNode neighbor = new AStarNode(new XY(x + additions[i][0], y + additions[i][1]), this);
            if (!outOfBounds(neighbor.getX(), neighbor.getY()) && internalMap[neighbor.getX()][neighbor.getY()] != null) {
                if (!neighbours.contains(neighbor))
                    neighbours.add(neighbor);

            }
        }
        return neighbours;
    }


    public boolean outOfBounds(int x, int y) {
        return x < 0 || x >= internalMap.length || y < 0 || y >= internalMap[0].length;
    }

    // convert path of nodes into path of actions and then convert it into path of moves
    public List<Move> makePath() {
        List<Action> actionPath = actionsPath(nodePath()); //convert from path of nodes to path of actions
        List<Move> path = new ArrayList<>();
        double speed = player.getCurrentSpeed();
        for (int i = 0; i < actionPath.size(); i++) {
            if (actionPath.get(i) == Action.MOVE_FORWARD) {
                int count = 1;
                for (int j = i + 1; j < speed && j < actionPath.size(); j++) {
                    if (actionPath.get(j) == Action.MOVE_FORWARD) {
                        i++;
                        count++;
                    } else {
                        break;
                    }
                }
                path.add(new Move(actionPath.get(i), count, player)); // move, distance, agent
            } else {
                path.add(new Move(actionPath.get(i), 0, player));
            }
        }
        return path;
    }

    // from path of nodes to path of actions
    public List<Action> actionsPath(List<AStarNode> nodePath) {
        List<Action> actionPath = new ArrayList<>();
        Cardinal orientation = player.getDirection();
        for (int i = 0; i < nodePath.size() - 1; i++) {
            AStarNode previous = nodePath.get(i);
            AStarNode next = nodePath.get(i + 1);
            if (next.getX() > previous.getX() && next.getY() == previous.getY()) {
                if (orientation == Cardinal.EAST)
                    actionPath.add(Action.MOVE_FORWARD);
                else {
                    actionPath.add(Action.TURN_RIGHT);
                    actionPath.add(Action.MOVE_FORWARD);
                    orientation = Cardinal.EAST;
                }
            } else if (next.getX() < previous.getX() && next.getY() == previous.getY()) {
                if (orientation == Cardinal.WEST)
                    actionPath.add(Action.MOVE_FORWARD);
                else {
                    actionPath.add(Action.TURN_LEFT);
                    actionPath.add(Action.MOVE_FORWARD);
                    orientation = Cardinal.WEST;
                }
            } else if (next.getX() == previous.getX() && next.getY() > previous.getY()) {
                if (orientation == Cardinal.SOUTH)
                    actionPath.add(Action.MOVE_FORWARD);
                else {
                    actionPath.add(Action.TURN_DOWN);
                    actionPath.add(Action.MOVE_FORWARD);
                    orientation = Cardinal.SOUTH;
                }
            } else if (next.getX() == previous.getX() && next.getY() < previous.getY()) {
                if (orientation == Cardinal.NORTH)
                    actionPath.add(Action.MOVE_FORWARD);
                else {
                    actionPath.add(Action.TURN_UP);
                    actionPath.add(Action.MOVE_FORWARD);
                    orientation = Cardinal.NORTH;
                }
            }
        }
        return actionPath;
    }


    public List<AStarNode> nodePath() {
        List<AStarNode> nodePath = new ArrayList<>();
        AStarNode node = target;
        while (node != null && !node.equals(currentNode)) {
            nodePath.add(node);
            node = node.getParent();
        }
        if (node != null)
            nodePath.add(node);
        Collections.reverse(nodePath);
        return nodePath;
    }

    public int gCost(XY xy) {
        return Math.abs(currentNode.getX() - xy.x()) + Math.abs(currentNode.getY() - xy.y());
    }

    public int hCost(XY xy) {
        return Math.abs(target.getX() - xy.x()) + Math.abs(target.getY() - xy.y());
    }


    public static class AStarNode {
        private final XY coordinate;
        private int gCost = Integer.MAX_VALUE;
        private int fCost = Integer.MAX_VALUE;
        private int hCost = Integer.MAX_VALUE;
        private final int rCost = Integer.MAX_VALUE;
        private AStarNode parent;
        private final AStarPathFinder aStarPath;

        // TODO: if bugs, check this
        public AStarNode(XY xy, AStarPathFinder aStarPath) {
            this.coordinate = xy;
            this.parent = null;
            this.aStarPath = aStarPath;
        }


        public void updateCost() {
            updateGCost();
            updateHCost();
            updateFCost();
        }

        public void updateHCost() {
            hCost = aStarPath.hCost(this.coordinate);
        }

        public void updateGCost() {
            gCost = aStarPath.gCost(this.coordinate);
        }


        public void updateFCost() {
            fCost = gCost + hCost;
        }

        public XY getCoordinate() {
            return coordinate;
        }


        public int getfCost() {
            return fCost;
        }

        public double getrCost() {
            return rCost;
        }

        public int getgCost() {
            return gCost;
        }

        public int gethCost() {
            return hCost;
        }


        public void setgCost(int gCost) {
            this.gCost = gCost;
        }

        public int getX() {
            return this.coordinate.x();
        }

        public int getY() {
            return this.coordinate.y();
        }

        public AStarNode getParent() {
            return parent;
        }

        public void setParent(AStarNode parent) {
            this.parent = parent;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AStarNode aStarNode = (AStarNode) o;
            return Objects.equals(coordinate, aStarNode.coordinate);
        }


        @Override
        public String toString() {
            return "AStarNode{" +
                    "coordinate=" + coordinate +
                    '}';
        }
    }

}