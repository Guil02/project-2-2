package group.seven.logic.algorithms.intruders;

import group.seven.enums.Action;
import group.seven.enums.AlgorithmType;
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

import static group.seven.enums.TileType.WALL;

public class GraphAstar implements Algorithm {

    Random rand = new Random();

    Agent agent;
    Scenario scenario;
    TileMap tileMap;
    Vector targetEstimate;
    VectorPoint currentPos;
    Graph<XY, DefaultWeightedEdge> graph;

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

    @Override
    public Move getNext() {
        AStarShortestPath<XY, DefaultWeightedEdge> astar = new AStarShortestPath<>(graph, XY::distance);
        GraphPath<XY, DefaultWeightedEdge> path = astar.getPath(agent.getXY(), targetEstimate.getXY());

        LinkedList<XY> nodePath = new LinkedList<>(path.getVertexList());

        nodePath.removeFirst();
        XY next = nodePath.removeFirst();
        agent.update(next);
        agent.setXY(next);
        currentPos = next;
        currentPos = agent.getXY();

        return new Move(Action.NOTHING, 0, agent);
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.A_STAR_ALT;
    }
}