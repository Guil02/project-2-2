package group.seven.logic.graphs;

import group.seven.enums.Action;
import group.seven.enums.Cardinal;
import group.seven.enums.TileType;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Agent;
import group.seven.model.environment.Adjacent;
import group.seven.model.environment.Marker;
import group.seven.model.environment.Pheromone;

import java.util.*;

public class EnviroGraph {
    Agent agent; //or maybe just its ID

    List<Edge<?>> edges = new ArrayList<>();

    Map<?, Set<?>> graph; //adjacency set: such that there are no duplicates

    public EnviroGraph(Agent agent) {
        this.agent = agent;
        graph = new LinkedHashMap<>();
    }

    record Edge<V>(Cardinal cardinal, V from, V to, double score, boolean directed){}
    record PathNode(Cell node, double score, Action action){}

    public static class Cell {
        TileType type;
        Adjacent<Cell> neighborhood; // or as local fields/components
        //boolean occupied;
        XY xy;
        List<Marker> markers;
        List<Pheromone> pheromones;

        public Cell(TileType type, Adjacent<Cell> neighborhood, XY xy, List<Marker> markers, List<Pheromone> pheromones) {
            this.type = type;
            this.neighborhood = neighborhood;
            //this.occupied = false;
            this.xy = xy;
            this.markers = markers;
            this.pheromones = pheromones;
        }
    }

    static class GridPath {
        Cell start;
        Cell goal;
        Queue<PathNode> path;

    }

    /*
    grid graph nodes
    edges have cost
    edges can be directional
    edges (could) have a cardinal direction

    nodes have grid adjacent vertices
    nodes have an XY coordinate
    nodes store tile type
    nodes store time-stamp discovered
    nodes store if they have been traversed
    nodes can have markers/pheromones
    nodes can be of type unknown

    algorithmc nodes:
      path nodes
          movement, rotations
      ant nodes
      agent tracking
      graph partitioning

     */
}
