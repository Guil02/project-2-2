package group.seven.logic.algorithms.pathfinding;

import group.seven.enums.Action;
import group.seven.enums.AlgorithmType;
import group.seven.enums.Cardinal;
import group.seven.logic.algorithms.Algorithm;
import group.seven.logic.geometric.Rectangle;
import group.seven.logic.geometric.VectorPoint;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;
import group.seven.model.environment.TileMap;
import group.seven.model.environment.TileNode;

import java.util.*;

import static group.seven.enums.Action.*;
import static group.seven.enums.Cardinal.*;
import static group.seven.enums.TileType.WALL;

public class PathFinder implements Algorithm {
    private final Agent player;
    Random random = new Random();

    private Node currentNode;
    private Node target;
    VectorPoint globalTargetPoint;
    int distanceToGoal;

    int[][] additions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    //List<Move> movesLeft; // moves left to do
    Queue<Node> open;
    Set<Node> closed;
    private TileNode[][] internalMap; // agent representation

    TileMap tileMap;
    Rectangle targetArea;
    List<VectorPoint> targetPoints;

    LinkedList<Move> trajectory;


    public PathFinder(Agent player) {
        this.player = player;
        XY currentXY = player.getXY();

        internalMap = player.getMap();
        trajectory = new LinkedList<>();

        tileMap = player.scenario.TILE_MAP;
        targetArea = player.scenario.targetArea.area();
        targetPoints = new ArrayList<>();
        for (int x = targetArea.getX(); x < targetArea.getMaxIntX(); x++) {
            for (int y = targetArea.getY(); y < targetArea.getMaxIntY(); y++) {
                targetPoints.add(new XY(x, y));
            }
        }
        globalTargetPoint = targetPoints.get(random.nextInt(targetPoints.size()));
        distanceToGoal = currentXY.manhattan(globalTargetPoint);

        target = new Node(globalTargetPoint.getXY(), this);

        //open = new PriorityQueue<>();
        //closed = new TreeSet<>();
        //movesLeft = new LinkedList<>();

        currentNode = new Node(currentXY, this);

        //open.add(currentNode);

        //internalMap.add(new TileNode(new Tile(startCoordinate.x(), startCoordinate.y())));
    }

    public List<Move> findPath() {
        internalMap = player.getMap();
        Queue<Node> openedNodes = new PriorityQueue<>();
        List<Node> closedNodes = new LinkedList<>();
        currentNode.updateCost();
        openedNodes.add(currentNode);

        while (!openedNodes.isEmpty()) {
            Node node = openedNodes.poll();
            closedNodes.add(node);

//            VectorPoint currentPos = node.pos;

            if (node.equals(target)) {
                target = node;
                return makePath();
            }

            for (Node neighbor : neighbours(node)) {
//                XY pos = neighbor.pos.getXY();
                if (closedNodes.contains(neighbor) || internalMap[node.getX()][node.getY()] == null || internalMap[node.getX()][node.getY()].getType() == WALL) {
                    continue;
                }

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


    public List<Node> neighbours(Node node) {
        int x = node.getX();
        int y = node.getY();
        List<Node> neighbours = new ArrayList<>(5);
        for (int i = 0; i < 4; i++) {
            Node neighbor = new Node(new XY(x + additions[i][0], y + additions[i][1]), this);
            if (!outOfBounds(neighbor.getX(), neighbor.getY()) && internalMap[neighbor.getX()][neighbor.getY()] != null) {
                if (!neighbours.contains(neighbor))
                    neighbours.add(neighbor);
            }
        }
        return neighbours;
    }
//    private List<Vertex> getAdjacent(Vertex v) {
//        List<Vertex> adj = new ArrayList<>(5);
//        XY vpos = v.pos.getXY();
//        for (int i = 0; i < 4; i++) {
//            Vertex n = new Vertex();
//            Tile t = player.scenario.TILE_MAP.getTile(vpos.add(Cardinal.values()[i].unitVector()));
//            if (t != null) {
//                n.pos = t.getXY();
//                if (t.getType() == TileType.PORTAL)
//                    adj.add(new Vertex().withPos(player.scenario.portals.get(0).exit()));
//
//                if (!adj.contains(n))
//                    adj.add(n);
//            }
//        }
////        tileMap.getTile(vpos.add(NORTH.unitVector()));
////        tileMap.getTile(vpos.add(EAST.unitVector()));
////        tileMap.getTile(vpos.add(SOUTH.unitVector()));
////        tileMap.getTile(vpos.add(WEST.unitVector()));
//        return adj;
//    }

    public boolean outOfBounds(int x, int y) {
        return x < 0 || x >= internalMap.length || y < 0 || y >= internalMap[0].length;
    }

    // convert path of nodes into path of actions and then convert it into path of moves
    public List<Move> makePath() {
        List<Action> actionPath = actionsPath(nodePath()); //convert from path of nodes to path of actions
        List<Move> path = new ArrayList<>();
        double speed = player.getSpeed();
        for (int i = 0; i < actionPath.size(); i++) {
            if (actionPath.get(i) == MOVE_FORWARD) {
                int count = 1;
                for (int j = i + 1; j < speed && j < actionPath.size(); j++) {
                    if (actionPath.get(j) == MOVE_FORWARD) {
                        i++;
                        count++;
                    } else {
                        break;
                    }
                }
                //player.setSpeed(i);
                path.add(new Move(actionPath.get(i), count, player)); // move, distance, agent
            } else {
                path.add(new Move(actionPath.get(i), 0, player));
            }
        }
        return path;
    }

    // from path of nodes to path of actions
    public List<Action> actionsPath(List<Node> nodePath) {
        List<Action> actionPath = new LinkedList<>();
        Cardinal orientation = player.getDirection();
        for (int i = 0; i < nodePath.size() - 1; i++) {
            Node previous = nodePath.get(i);
            Node next = nodePath.get(i + 1);
            if (next.getX() > previous.getX() && next.getY() == previous.getY()) {
                if (orientation == EAST)
                    actionPath.add(MOVE_FORWARD);
                else {
                    actionPath.add(TURN_RIGHT);
                    actionPath.add(MOVE_FORWARD);
                    orientation = EAST;
                }
            } else if (next.getX() < previous.getX() && next.getY() == previous.getY()) {
                if (orientation == WEST)
                    actionPath.add(MOVE_FORWARD);
                else {
                    actionPath.add(TURN_LEFT);
                    actionPath.add(MOVE_FORWARD);
                    orientation = WEST;
                }
            } else if (next.getX() == previous.getX() && next.getY() > previous.getY()) {
                if (orientation == SOUTH)
                    actionPath.add(MOVE_FORWARD);
                else {
                    actionPath.add(TURN_DOWN);
                    actionPath.add(MOVE_FORWARD);
                    orientation = SOUTH;
                }
            } else if (next.getX() == previous.getX() && next.getY() < previous.getY()) {
                if (orientation == NORTH)
                    actionPath.add(MOVE_FORWARD);
                else {
                    actionPath.add(TURN_UP);
                    actionPath.add(MOVE_FORWARD);
                    orientation = NORTH;
                }
            }
        }
        return actionPath;
    }

    public List<Node> nodePath() {
        List<Node> nodePath = new ArrayList<>();
        Node node = target;
        while (node != null && !node.equals(currentNode)) {
            nodePath.add(node);
            node = node.parent;
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

    @Override
    public Move getNext() {
        XY currentXY = player.getXY();
        currentNode = new Node(currentXY, this);

        //TODO: if first time
        if (player.getIsTeleported()) {
            trajectory.clear();
            player.setTeleported(false);
        }

        if (trajectory.isEmpty()) {
            //need to calculate path
            if (targetPoints.contains(currentXY)) {
                //agent is at target
                //TODO: check FOV for guards
                //action: rotate or make room for other intruders
                return new Move(FLIP, 0, player);

            } else {
                trajectory.addAll(findPath());
                assert (!trajectory.isEmpty());
                return trajectory.poll();
            }
        } else {
            //TODO check visual field for obstacle
            XY inDirection = currentXY.add(player.getDirection().unitVector);
            if (tileMap.getTile(inDirection).getType().isObstacle()) {
                //recalculate
                trajectory.clear();
                return new Move(FLIP, 0, player);
            } else {
                return trajectory.poll();
            }
        }

//        if (movesLeft.isEmpty()) {
//            if (target != null) {
//                currentNode = target;
//            }
//
//            if (target == null) {
//                movesLeft.add(new Move(NOTHING, 0, player));
//                Methods.print("No target: " + player);
//            } else {
//                AStarPathFinder aStarPathFinder = new AStarPathFinder(player, target.getCoordinate());
//                movesLeft = aStarPathFinder.findPath();
//            }
//        }

//        Move move = new Move(NOTHING, 0, player);
//        try {
//            move = trajectory.poll();
//            move = movesLeft.get(0);
//            movesLeft.remove(0);
//        } catch (IndexOutOfBoundsException e) {
//            print(e.getMessage());
//        }
//        return move;
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.A_STAR_ALT;
    }

    public static class Node implements Comparable<Node> {
        private final XY coordinate;
        private int gCost = Integer.MAX_VALUE;
        private int fCost = Integer.MAX_VALUE;
        private int hCost = Integer.MAX_VALUE;
        //        private final int rCost = Integer.MAX_VALUE;
        private Node parent;
        private final PathFinder aStarPath;

        public Node(XY xy, PathFinder aStarPath) {
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

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o instanceof Node other) {
                return coordinate.equals(other.coordinate);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return coordinate.hashCode();
        }

        @Override
        public String toString() {
            return "AStarNode{" +
                    "coordinate=" + coordinate +
                    '}';
        }

        @Override
        public int compareTo(Node o) {
            if (fCost == o.fCost) {
                return Integer.compare(hCost, o.hCost);
            }
            return Integer.compare(fCost, o.fCost);
        }
    }

}