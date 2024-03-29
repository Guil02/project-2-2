package group.seven.logic.algorithms;

import group.seven.enums.AlgorithmType;
import group.seven.logic.geometric.Pythagoras;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Intruder;
import group.seven.model.agents.Move;
import group.seven.model.environment.TileNode;
import group.seven.utils.Methods;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static group.seven.enums.Action.NOTHING;
import static group.seven.enums.AlgorithmType.A_STAR;
import static group.seven.enums.TileType.PORTAL;
import static group.seven.enums.TileType.WALL;

public class AStarGoal implements Algorithm {

    private final int initialX;
    private final int initialY;
    //private final Tile[][] map;
    //private Scenario scenario;

    private AStarNode current;
    private AStarNode target;
    private final Intruder player;
    List<Move> movesLeft;
    List<AStarNode> open;
    List<AStarNode> closed;
    int[][] additions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    int wallPenalization = 2;


    public AStarGoal(Intruder player) {
        this.initialX = player.globalSpawn.x();
        this.initialY = player.globalSpawn.y();
        open = new ArrayList<>();
        closed = new ArrayList<>();
        movesLeft = new ArrayList<>();
        //this.map = scenario.TILE_MAP.getMap();
        current = new AStarNode(player.getXY(), this);
        open.add(current);
        this.player = player;
        //playerMap[initialX][initialY]=new TileNode(map[initialX][initialY],player);
    }


    public void updateOpen() {
//        List<AStarNode> toBeRemoved = new ArrayList<>();
        Set<AStarNode> toBeRemoved = new HashSet<>();
        for (AStarNode node : open) {
            List<AStarNode> neighbours = neighbours(node);
            int count = 0;
            for (AStarNode neighbour : neighbours) {
                if (player.getMap()[neighbour.getX()][neighbour.getY()] != null) {
                    count++;
                }
            }
            if (count >= 4) {
                toBeRemoved.add(node);
            }
        }
        open.removeIf(toBeRemoved::contains);
        closed.addAll(toBeRemoved);
    }

    public List<AStarNode> neighbours(AStarNode node) {
        int x = node.getX();
        int y = node.getY();
        List<AStarNode> neighbours = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            AStarNode neighbor = new AStarNode(new XY(x + additions[i][0], y + additions[i][1]), this);
            if (!outOfBounds(neighbor.getX(), neighbor.getY()) && player.getMap()[neighbor.getX()][neighbor.getY()] != null) {
                neighbours.add(neighbor);
            }
        }
        return neighbours;
    }

    public boolean outOfBounds(int x, int y) {
        return x < 0 || x >= player.getMap().length || y < 0 || y >= player.getMap()[0].length;
    }

    public AStarNode findTarget() {
        updateOpen();
        open.remove(current);
        if (!closed.contains(current))
            closed.add(current);

        for (TileNode[] grids : player.getMap()) {
            for (TileNode grid : grids) {
                if (grid == null)
                    continue;
                AStarNode node = new AStarNode(new XY(grid.getX(), grid.getY()), this);
                if (grid.getType() == WALL) {
                    node.setfCost(node.getfCost() * wallPenalization);
                    for (AStarNode neighbor : neighbours(node)) {
                        neighbor.setfCost(neighbor.getfCost() * wallPenalization);
                    }
                }
                if (open.contains(node) || closed.contains(node) || (player.getIgnorePortal() && grid.getType() == PORTAL)) {
                    continue;
                }
                open.add(node);
            }
        }
        updateOpen();
        int lowestValue = Integer.MAX_VALUE;
        AStarNode currentTarget = null;
        for (AStarNode node : open) {
            node.updateCost();
            if (node.getfCost() < lowestValue) {
                currentTarget = node;
                lowestValue = node.getfCost();
            } else if (node.getfCost() == lowestValue) {
                if (node.gethCost() < current.gethCost()) {
                    lowestValue = node.getfCost();
                    currentTarget = node;
                }
            }
        }
        if (currentTarget != null) {
            open.remove(currentTarget);
            closed.add(currentTarget);
        }
        return currentTarget;
    }


    public int gCost(XY xy) {
        return (Math.abs(initialX - xy.x()) + Math.abs(initialY - xy.y()));
    }


    public int hCost(XY xy) {
        return (Math.abs(current.getX() - xy.x()) + Math.abs(current.getY() - xy.y()));
    }


    public int rCost(XY xy) { //xy = current frontier node tested
        player.updateOrientationToGoal();

        double angleAgentToGoal = player.getAngleToGoal();
        double angleAgentToTile = Pythagoras.angleFromAgentToTarget(xy, player.getXY());
        double r = Math.abs(angleAgentToGoal - angleAgentToTile);

        if (r > 180) {
            r = 360 - r;
        }
        r = (r / 180) * 50;

        return (int) Math.round(r);
    }

    @Override
    public Move getNext() {

        if (player.getIsTeleported()) {
            movesLeft.clear();
            player.setTeleported(false);

        }
        if (movesLeft.isEmpty()) {
            if (target != null) {
                current = target;
            }

            target = findTarget();


            if (target == null) {
                movesLeft.add(new Move(NOTHING, 0, player));
                Methods.print("No target: " + player);
            } else {
                AStarPathFinder aStarPathFinder = new AStarPathFinder(player, target.getCoordinate());
                movesLeft = aStarPathFinder.findPath();
            }
        }

        Move move = new Move(NOTHING, 0, player);
        try {
            move = movesLeft.get(0);
            movesLeft.remove(0);
        } catch (IndexOutOfBoundsException ignore) {
        }
        return move;
    }

    @Override
    public AlgorithmType getType() {
        return A_STAR;
    }


    public class AStarNode {
        private final XY coordinate;
        private int gCost = Integer.MAX_VALUE;
        private int fCost = Integer.MAX_VALUE;
        private int hCost = Integer.MAX_VALUE;
        private int rCost = Integer.MAX_VALUE;
        private AStarNode parent;
        private final AStarGoal aStarGoal;


        public AStarNode(XY xy, AStarGoal aStarGoal) {
            this.coordinate = xy;
            this.parent = null;
            this.aStarGoal = aStarGoal;
        }

        public void updateCost() {
            //updateGCost();
            updateHCost();
            updateRCost();
            updateFCost();
        }

        public void updateHCost() {
            hCost = aStarGoal.hCost(this.coordinate);
        }

        public void updateRCost() {
            rCost = aStarGoal.rCost(this.coordinate);
        }

        public void updateGCost() {
            gCost = aStarGoal.gCost(this.coordinate);
        }


        public void updateFCost() {
            fCost = gCost + hCost + rCost;
        }

        public XY getCoordinate() {
            return coordinate;
        }

        public int getfCost() {
            return fCost;
        }

        public void setfCost(int newCost) {
            this.fCost = newCost;
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
            AStarNode aStarNode = (AStarNode) o;
            return aStarNode.getX() == this.getX() && aStarNode.getY() == this.getY();
            //if (this == o) return true;
            //if (o == null || getClass() != o.getClass()) return false;
            //AStarNode aStarNode = (AStarNode) o;
            //return Objects.equals(coordinate, aStarNode.coordinate);
        }

        @Override
        public int hashCode() {
            return coordinate.hashCode();
        }

        @Override
        public String toString() {
            return "AStarNode{" +
                    "coordinate=" + coordinate +
                    ", gCost=" + gCost +
                    ", fCost=" + fCost +
                    ", hCost=" + hCost +
                    ", rCost=" + rCost +
                    '}';
        }
    }

}