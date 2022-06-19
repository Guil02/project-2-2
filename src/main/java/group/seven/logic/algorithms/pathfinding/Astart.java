package group.seven.logic.algorithms.pathfinding;

import group.seven.enums.Action;
import group.seven.enums.AlgorithmType;
import group.seven.enums.Cardinal;
import group.seven.enums.TileType;
import group.seven.logic.algorithms.Algorithm;
import group.seven.logic.geometric.Rectangle;
import group.seven.logic.geometric.Vector;
import group.seven.logic.geometric.VectorPoint;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Intruder;
import group.seven.model.agents.Move;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.Tile;
import group.seven.model.environment.TileMap;

import java.util.*;

public class Astart implements Algorithm {

    Random random = new Random();

    Intruder agent;
    TileMap tileMap;
    Scenario scenario;

    Vertex[][] internalMap;

    Rectangle targetRegion;
    List<VectorPoint> targetPoints;

    XY currentXY;
    VectorPoint globalTarget;
    int distanceToGoal;
    IntSummaryStatistics distances;
    private Vertex target;

    public Astart(Intruder agent) {
        this.agent = agent;
        targetPoints = new ArrayList<>();
        scenario = agent.scenario;
        tileMap = scenario.getTileMap();
        targetRegion = scenario.targetArea.area();
        for (int x = targetRegion.getX(); x <= targetRegion.getMaxIntX(); x++) {
            for (int y = targetRegion.getY(); y <= targetRegion.getMaxIntY(); y++) {
                targetPoints.add(new XY(x, y));
            }
        }
        distances = new IntSummaryStatistics();
        currentXY = agent.getGlobalSpawn();
        globalTarget = targetPoints.get(0);
        distanceToGoal = currentXY.manhattan(globalTarget);
        distances.accept(distanceToGoal);

    }

    public boolean outOfBounds(int x, int y) {
        return x < 0 || x >= internalMap.length || y < 0 || y >= internalMap[0].length;
    }

    @Override
    public Move getNext() {
        PriorityQueue<Vertex> open = new PriorityQueue<>();
        Set<Vertex> closed = new HashSet<>();
        currentXY = agent.getXY();
        Vertex start = new Vertex().withPos(currentXY);
//        start.parent = start;
        start.f(start, targetPoints.get(random.nextInt(targetPoints.size())));
        open.add(start);

        while (!open.isEmpty()) {
            Vertex next = open.poll();
            closed.add(next);

            if (targetPoints.contains(next.pos)) {
                target = next;
                return compileMove();
            }

            for (Vertex v : getAdjacent(next)) {
                if (!closed.contains(v)) {
                    v.f(next, globalTarget);
                    distances.accept(v.distanceToTarget);
                    v.parent = next;
                    open.add(v);
                }
            }

        }

        return null;
    }

    private Move compileMove() {
        List<Vertex> nodePath = getNodePath(target);
        if (nodePath.isEmpty()) {
            System.out.println("Empty node path :(");
        }
        Vertex next = nodePath.get(0);
        Vector nexPos = new Vector(next.pos.getXY());
//        nexPos.in
        Cardinal agentDirection = agent.getDirection();
//        if (new Vector(currentXY).angle(nexPos) )
        System.out.println(new Vector(currentXY).angle(nexPos));
//        XY diff = currentXY.sub(next.pos.getXY());
        if (next.pos.getXY().x() > currentXY.x()) {
            //move right
        }
        return new Move(Action.TURN_DOWN, 0, agent);
    }

    //returns just the nodes in the path
    public List<Vertex> getNodePath(Vertex currentNode) {
        List<Vertex> path = new LinkedList<>();
        while (currentNode.parent != null) {
            path.add(currentNode);
            currentNode = currentNode.parent;
        }
        return path;
    }

    private List<Vertex> getAdjacent(Vertex v) {
        List<Vertex> adj = new ArrayList<>(5);
        XY vpos = v.pos.getXY();
        for (int i = 0; i < 4; i++) {
            Vertex n = new Vertex();
            Tile t = tileMap.getTile(vpos.add(Cardinal.values()[i].unitVector()));
            if (t != null) {
                n.pos = t.getXY();
                if (t.getType() == TileType.PORTAL)
                    adj.add(new Vertex().withPos(scenario.portals.get(0).exit()));

                if (!adj.contains(n))
                    adj.add(n);
            }
        }
//        tileMap.getTile(vpos.add(NORTH.unitVector()));
//        tileMap.getTile(vpos.add(EAST.unitVector()));
//        tileMap.getTile(vpos.add(SOUTH.unitVector()));
//        tileMap.getTile(vpos.add(WEST.unitVector()));
        return adj;
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.A_STAR_ALT;
    }
}
