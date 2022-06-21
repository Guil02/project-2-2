package group.seven.logic.algorithms.pathfinding;

import group.seven.enums.Action;
import group.seven.enums.AlgorithmType;
import group.seven.enums.Cardinal;
import group.seven.enums.TileType;
import group.seven.logic.algorithms.Algorithm;
import group.seven.logic.geometric.Vector;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Guard;
import group.seven.model.agents.Move;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.Tile;
import group.seven.model.environment.TileMap;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import java.util.List;
import java.util.Random;

import static group.seven.enums.Action.MOVE_FORWARD;
import static group.seven.enums.TileType.UNKNOWN;
import static group.seven.utils.Methods.print;

public class Predator implements Algorithm {

    public class Node implements Comparable<Node> {
        public TileType type = UNKNOWN;
        public XY xy;
        public int visitCount = 0;
        public double pathPheromoneStrength = 0;
        public double lastSeenGuard = 0;
        public double lastSeenIntruder = 0;
        public double h = Integer.MAX_VALUE; //distance to target
        public double g = 1;
        public double f;

        public Node(XY xy) {
            this.xy = xy;
            h = xy.manhattan(globalTargetPoint);
            f();
        }

        protected double f() {
            f = (h = h * (pathPheromoneStrength / (1 + visitCount))) + g;
            return f;
        }

        @Override
        public int compareTo(Node o) {
            return Double.compare(f, o.f);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o instanceof Predator.Node node)
                return xy.equals(node.xy);
            else return false;
        }

        @Override
        public int hashCode() {
            return xy.hashCode();
        }

        @Override
        public String toString() {
            return "Node(" +
                    "type=" + type.name() +
                    ", xy=" + xy +
                    ", visitCount=" + visitCount +
                    ", smell=" + pathPheromoneStrength +
                    ", f=" + f +
                    ')';
        }
    }

    record Pose(XY xy, Cardinal direction, int speed) {
    }

    Random rand = new Random();

    Agent agent;
    XY currentXY;
    Graph<Pose, DefaultEdge> pathTaken;
    //DefaultDirectedGraph<XY, DefaultEdge> djkstra = new DefaultDirectedGraph<>(DefaultEdge.class);
    Pose pose;
    Scenario scenario;
    TileMap tileMap;
    Vector globalTargetPoint;
    double currentDistanceToTarget;
    Graph<Predator.Node, DefaultWeightedEdge> g;

    public Predator(Agent agent) {
        this.agent = agent;
        currentXY = agent.getXY();
        scenario = agent.scenario;
        tileMap = scenario.getTileMap();
        int xt = scenario.targetArea.area().getX() + rand.nextInt(scenario.targetArea.area().getIntWidth());
        int yt = scenario.targetArea.area().getY() + rand.nextInt(scenario.targetArea.area().getIntHeight());
        globalTargetPoint = new Vector(xt, yt);
        currentDistanceToTarget = globalTargetPoint.manhattan(currentXY);

        pathTaken = new DefaultDirectedGraph<>(DefaultEdge.class);
        pathTaken.addVertex(pose = new Pose(currentXY, agent.getDirection(), agent.getSpeed()));
        print(pathTaken.toString());

        g = GraphTypeBuilder.undirected()
                .allowingSelfLoops(true)
                .weighted(true)
                .vertexClass(Node.class)
                .edgeClass(DefaultWeightedEdge.class)
                .buildGraph();

        Predator.Node current = new Node(currentXY);
        g.addVertex(current);
        System.out.println(g);

    }

    @Override
    public Move getNext() {
        agent.updateVision();
        currentXY = agent.getXY();
        currentDistanceToTarget = globalTargetPoint.manhattan(currentXY);

        Node n = new Node(currentXY);
        n.visitCount++;
        n.pathPheromoneStrength += scenario.SMELL_DISTANCE;
        n.type = tileMap.getType(currentXY);
        n.f();

        g.addVertex(n);

        List<Tile> fov = agent.getSeenTiles();
        for (Tile t : fov) {
            Node inFOV = new Node(t.getXY());
            inFOV.type = t.getType();
            g.addVertex(inFOV);
            t.getAdjacent().toList().forEach(tile -> {
                Node adj = new Node(tile.getXY());
                if (!g.containsVertex(adj)) {
                    g.addVertex(adj);
                }
                g.addEdge(inFOV, adj);
            });
        }

        for (Agent agent : tileMap.agents) {
            if (agent instanceof Guard g) {
                if (fov.stream().map(Tile::getXY).toList().contains(g.getXY())) {
                    n.lastSeenGuard = agent.getTime();
                    System.out.println("Spotted a guard");
                }
            }
        }


        //pathTaken.addVertex(newPose);
        //pathTaken.addEdge(pose, newPose);


        Action action = rand.nextDouble() < 0.3 ? Action.values()[rand.nextInt(4)] : MOVE_FORWARD;
        if (action == MOVE_FORWARD) {

        }
        //agent.setSpeed(rand.nextDouble() < 0.5 ? agent.getSpeed() + 1 : agent.getSpeed() - 1);
        return new Move(action, (action == MOVE_FORWARD ? agent.getSpeed() : 0), agent);
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.A_STAR_ALT;
    }

}
