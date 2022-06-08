package group.seven.logic.algorithms.pathfinding;

import group.seven.enums.Action;
import group.seven.enums.AlgorithmType;
import group.seven.enums.Cardinal;
import group.seven.logic.algorithms.Algorithm;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.Tile;
import javafx.geometry.Point2D;

import java.util.*;

import static group.seven.enums.TileType.PORTAL;
import static group.seven.enums.TileType.WALL;
import static group.seven.model.environment.Scenario.TILE_MAP;
import static group.seven.utils.Methods.print;

//TODO: playing around and expirementing. Disregard and doesn't work/is incomplete
public class Astar implements Algorithm {

    Tile[][] map = TILE_MAP.getMap();

    //Manhattan distance heurstic -> good if only 4 directions
    //Euclidean distance (straigtht line) -> maybe good for estimating target based one direction, angle or whatever
    //scared intruder heuristic: s = -distance to nearest agent -> causes scattering
    //boids heuristic -> fiddle with parameters for flocking, pursuit, evasion

    //horizonQueue: stores the frontier nodes in order of distance/direction to target

    //push moves/target in path onto stack, dynamically push new moves onto stack depending on current state
    //pop moves off to get the next destination/targets/moves to target
    Stack<Node> moveStack = new Stack<>();

    //    Queue<Node> openList; //open set, fringe. Order by lowest f cost
//    Set<Node> closedSet;
    //Node goal = new Node(new XY(132, 150));
    Node goal = new Node(new XY(30, 12)); //small map temp close goal

    //Node start;
    Agent agent;
    Heuristic distance = Heuristic.MANHATTAN; //default

    public Astar(Agent agent) {
        this.agent = agent;
    }

    public Astar(Agent agent, Node target) {
        //openList = new PriorityQueue<>(); //orders open list by f-cost. The Node class must implement Comparable
        //closedSet = new HashSet<>(); //hashsets cannot store duplicates. Constant time for all operations
        goal = target;
        //start = new Node(agent.getXY(), 0);
        //openList.add(start);
        this.agent = agent;
    }

    @Override
    public Move getNext() {
        Queue<Node> open = new PriorityQueue<>();
        Set<Node> closed = new HashSet<>();
        LinkedList<Node> path = new LinkedList<>(); // this will store the actual path the agent ought to take

        Node target = goal; //goal node dynamically updated with new information gain to get better estimate
        Node start = new Node(agent.getXY()); // agents position in global coords

        open.add(start);
        path.add(start);
        Node prev = start;
        boolean foundTarget = false;
        int count = 0;
        //while there are still nodes to evaluate...
        while (open.size() > 0 && count < 10) {
            count++;

            Node current = open.poll(); //retrieve next node with lowest f cost and remove from queue
            System.out.println("Getting next... open: " + open.size() + " closed: " + closed.size());
            System.out.print("\t -> " + (current == prev ? "same : " + current : "different! " + current));

            if (current == target) {
                print("Found target : " + target + " from: " + current);
                System.out.println("first: " + path.getFirst() + " last: " + path.getLast());
                Collections.reverse(path);
                System.out.println("reversed first: " + path.getFirst() + " last: " + path.getLast());
                foundTarget = true;
                break; // agent has reached target, so break out of the loop to process the path
            }

            closed.add(current);
            System.out.println("\nclosed set added" + current);
            prev = current;

            for (Node neighbor : current.getNeighbours(current)) {
                //we don't care about evaluated neighbors
                if (closed.contains(neighbor)) continue;

                //check cost to neighbor
                int potential = current.g + g(current, neighbor); // technically just g + 1, but different in dynamic speeding, supposed to be the distance
                System.out.println("\tpotential for " + neighbor + " = " + potential);
                //check if node has been discovered and see if better score found
                if (open.contains(neighbor)) {
                    if (potential >= neighbor.g) continue;      //did not find a better path to neighbor from current
                    print("\tpotential for " + neighbor + " = " + potential + " \t ::: better than current? " + current);
                } else open.add(neighbor);                    //discovered new node, add to openList

                //update with the best path values found so far
                neighbor.parent = current;
                path.add(neighbor);
                neighbor.g = potential;
                neighbor.h = h(neighbor, target);
                neighbor.f = cost(neighbor, target);
                print("Discovered: " + neighbor);
            }
            System.out.println("\tfinished finding neighbors");
        }

        if (foundTarget) {
            int angle = (int) new Point2D(agent.getX(), agent.getY()).angle(path.getLast().x, path.getLast().y);
            System.out.println(angle);
        }

        return new Move(Action.NOTHING, 0, agent);
    }


    /**
     * f(s, t) cost
     * Total cost to get from s to t given path from start node to s
     *
     * @param node current node to evaluate
     * @param goal target node to reach
     * @return total cost where f = g + n -> sum of the distance to s + heuristic(s) estimate to target node
     */
    private int cost(Node node, Node goal) {
        return g(node, goal) + h(node, goal);
    }

    /**
     * g cost
     * total tiles (or moves) needed to get from this node to the next
     *
     * @param node current node
     * @param next next node it wants to reach
     * @return cost ot get form this node to next
     */
    private int g(Node node, Node next) {
        //in case of dynamic speeding return manhattan distance between node and next
//        return node.g + 1;
        return Math.abs(distance.getValue(node, next));
        //return Math.abs(node.x - next.x) + Math.abs(node.y - next.y); //node = start, next = current
    }

    /**
     * Heuristic estimate function h(u, v) = cost estimate of getting from node u to node v
     * Generally u is the current agent's node position or am arbitrary start node it's evaluating
     * Generally v is the current agent's node target/goal or am arbitrary start node it's evaluating
     * It for the case of intruders it may evaluate its cost from its position to a local goal
     * and it could evaluate its position to the target area, and use it as a factor in the heuristic function
     *
     * @param node   Start node
     * @param target Goal node
     * @return estimated h cost from start node to target
     */
    private int h(Node node, Node target) {
        return distance.getValue(node, target);
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.A_STAR_ALT;
    }

    //================= utility classes =====================//

    enum Heuristic {
        MANHATTAN, STRAIGHT_LINE;

        public int getValue(Node a, Node b) {
            return switch (this) {
                case MANHATTAN -> Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
                case STRAIGHT_LINE -> (int) new Point2D(a.x, a.y).distance(b.x, b.y);
            };
        }
    }

    record Occupancy(boolean occupied, Agent agent) {
    }

    class Node implements Comparable<Node> {
        Point2D pos;
        int x, y;
        int f;
        int h;
        int g;
        Node parent;
        List<Node> neighbours;

        public Node(XY xy, Node previous) {
            x = xy.x();
            y = xy.y();
            pos = new Point2D(x, y);
            parent = previous;
            //neighbours = getNeighbours();
        }

        public Node(XY xy) {
            x = xy.x();
            y = xy.y();
            pos = new Point2D(x, y);

            //neighbours = getNeighbours();
        }


        public List<Node> getNeighbours(Node current) {
            print("getting neighbours... : " + current);
            List<Node> adjacent = new ArrayList<>(5);

            for (int d = 0; d < 4; d++) {
                XY nextPos = Cardinal.values()[d].unitVector.add(x, y); //maybe add offset for dynamic speeding

                Tile t = TILE_MAP.getTile(nextPos.x(), nextPos.y());
                if (t != null) {
                    if (t.getType() != WALL) {
                        Node adj = new Node(nextPos, this);
                        //                 adj = new Node(nextPos);
                        Occupancy occupancy = occupied(adj);

                        if (!occupancy.occupied) {
                            adjacent.add(adj);
                        } else {
                            Agent other = occupancy.agent;
                            //define behaviour or record agent info
                        }

                        if (t.getType() == PORTAL) { //assumes there's just one portal;
                            adjacent.add(new Node(Scenario.portals.get(0).exit(), this));
                        }
                    }
                }
            }
//            XY exit = null;
//            if (t.getType() == TileType.PORTAL) {
//                exit = Scenario.portals.get(0).exit();
//
            return adjacent;
        }

        private Occupancy occupied(Node other) {
            //need to test this
            for (Agent a : TILE_MAP.agents)
                if (a.getX() == other.x && a.getY() == other.y && a != agent)
                    return new Occupancy(true, a);

            return new Occupancy(false, null);
        }

        @Override
        public int compareTo(Node o) {
            //TODO tie-breaking heuristic
            //e.g. distance to nearest obstacle or target or agent or whatever
            //prolly better to keep moving in same direction
            return this.f - o.f;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node node)) return false;
            if (x != node.x) return false;
            if (y != node.y) return false;
            if (f != node.f) return false;
            if (h != node.h) return false;
            if (g != node.g) return false;
            if (parent == null && node.parent == null) return true;
            else {
                assert parent != null;
                return parent.equals(node.parent);
            }
            //return parent.equals(node.parent);
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            result = 31 * result + f;
            result = 31 * result + h;
            result = 31 * result + g;
//            result = 31 * result + parent.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "pos=" + pos +
                    ", x=" + x +
                    ", y=" + y +
                    ", f=" + f +
                    ", h=" + h +
                    ", g=" + g +
                    '}';
        }
    }

}
