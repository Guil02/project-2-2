package group.seven.logic.algorithms.intruders;

import group.seven.enums.Action;
import group.seven.enums.AlgorithmType;
import group.seven.enums.Cardinal;
import group.seven.logic.algorithms.Algorithm;
import group.seven.logic.geometric.Vector;
import group.seven.logic.geometric.VectorPoint;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.Tile;
import group.seven.model.environment.TileMap;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static group.seven.enums.Cardinal.*;
import static group.seven.enums.TileType.WALL;

public class GraphAstar implements Algorithm {

    Random rand = new Random();

    Agent agent;
    Scenario scenario;
    TileMap tileMap;
    Vector targetEstimate;
    VectorPoint currentPos;
    Graph<XY, DefaultWeightedEdge> graph;
    int count = 0;

    public GraphAstar(Agent agent) {
        this.agent = agent;
        currentPos = agent.getXY();
        scenario = agent.scenario;
        tileMap = scenario.getTileMap();

        int xt = scenario.targetArea.area().getX() + rand.nextInt(scenario.targetArea.area().getIntWidth());
        int yt = scenario.targetArea.area().getY() + rand.nextInt(scenario.targetArea.area().getIntHeight());
        targetEstimate = new Vector(xt, yt);

        graph = createGraph();
    }

    public Graph<XY, DefaultWeightedEdge> createGraph() {
        Graph<XY, DefaultWeightedEdge> g = GraphTypeBuilder.undirected()
                .vertexClass(XY.class)
                .edgeClass(DefaultWeightedEdge.class)
                .weighted(true)
                //.allowingSelfLoops(true)
                .buildGraph();

        List<Tile> walls = new LinkedList<>();
        for (int r = 0; r <= scenario.WIDTH; r++) {
            for (int c = 0; c <= scenario.HEIGHT; c++) {
                Tile t = tileMap.getTile(r, c);
                XY id = t.getXY();
                g.addVertex(id);
                if (t.getType() == WALL) {
                    walls.add(t);
                }
            }
        }

        for (int r = 0; r < scenario.WIDTH; r++) {
            for (int c = 0; c < scenario.HEIGHT; c++) {
                Tile t = tileMap.getTile(r, c);
                XY id = t.getXY();
                List<Tile> adj = t.getAdjacent().toList();
                adj.forEach(n -> {
                    g.addEdge(id, n.getXY());
                    if (walls.contains(t) || walls.contains(n))
                        g.setEdgeWeight(id, n.getXY(), Integer.MAX_VALUE);
                    else g.setEdgeWeight(id, n.getXY(), 1);
                });
            }
        }

        return g;
    }

    /**
     * Non-recursive implementation using randomness to prevent collision and always try to move at
     * maximum speed
     *
     * @param path sequence of (tile) coordinates to reach destination
     * @return the Move associated with taking the next step in the sequence
     */
    private Move calculateMove(LinkedList<XY> path) {
        if (path.isEmpty()) {
            Action rotate = randomDirection().getAction();
            return new Move(rotate, 0, agent);
        } else {
            XY tile = path.getFirst();
            Cardinal direction = getCardinal(tile);

            if (agent.getDirection() == direction) {
                int step; //distance to travel this move
                for (step = 0; agent.getDirection() == direction && step <= agent.getSpeed() && !path.isEmpty(); step++) {
                    direction = getCardinal(path.poll()); //update direction to next cordinate
                }
                //to prevent collision adjust move distance randomly with gaussian distribution
                return new Move(Action.MOVE_FORWARD, (int) (step + Math.rint(rand.nextGaussian())), agent);
            } else {
                //also introduce randomness for taking a random rotation, more exploration and helps against agent collisions
                Action rotate = Math.random() < 0.95 ? direction.getAction() : randomDirection().getAction();
                return new Move(rotate, 0, agent);
            }
        }
    }

    public Move calculateMovement(LinkedList<XY> path) {
        Move move;
        Cardinal agentFace = agent.getDirection();
        Cardinal differenceFace = getCardinal(path.get(0));

        if (agentFace == differenceFace && path.size() > 1) {
            count++;
            path.removeFirst();
            calculateMovement(path);
        } else if (count > 0) {
            move = new Move(Action.MOVE_FORWARD, count, agent);
            count = 0;
            return move;
        }
        if (agentFace != differenceFace) {
//            Action rotation = differenceFace.getAction();
//            count = 0;
//            return new Move(rotation, 0, agent);

            //translate cardinal to action
            if (differenceFace == NORTH) {
                move = new Move(Action.TURN_UP, 0, agent);
                count = 0;
                return move;
            } else if (differenceFace == SOUTH) {
                move = new Move(Action.TURN_DOWN, 0, agent);
                count = 0;
                return move;
            } else if (differenceFace == WEST) {
                move = new Move(Action.TURN_LEFT, 0, agent);
                count = 0;
                return move;
            } else if (differenceFace == EAST) {
                move = new Move(Action.TURN_RIGHT, 0, agent);
                count = 0;
                return move;
            }

        }

        return null;
    }

    private Cardinal getCardinal(XY tile) {
        XY pos = agent.getXY();
        if (tile.x() - pos.x() > 0) {
            return EAST;
        } else if (tile.x() - pos.x() < 0) {
            return WEST;
        } else if (tile.y() - pos.y() > 0) {
            return SOUTH;
        } else if (tile.y() - pos.y() < 0) {
            return NORTH;
        } else {
            return NOWHERE;
        }
    }

    @Override
    public Move getNext() {
        AStarShortestPath<XY, DefaultWeightedEdge> astar = new AStarShortestPath<>(graph, XY::distance);
        GraphPath<XY, DefaultWeightedEdge> path = astar.getPath(agent.getXY(), targetEstimate.getXY());

        LinkedList<XY> nodePath = new LinkedList<>(path.getVertexList());

        nodePath.removeFirst();
        XY next = nodePath.peek();
        Move m = calculateMove(nodePath);

//        if (m == null) {
//            System.out.print("\r\n null : " + ++nullCount);
//            agent.update(next);
//            currentPos = next;
//            return new Move(Action.NOTHING, 0, agent);
//        }

        return m;
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.A_STAR_ALT;
    }
}
