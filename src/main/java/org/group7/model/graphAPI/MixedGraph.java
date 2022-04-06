package org.group7.model.graphAPI;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

record Cell(int x, int y, boolean obstacle, boolean directed){}
record Edge(Cell cell, double cost){}

public class MixedGraph<V> implements GraphAPI<V> {

    private static final int EDGE_CAPACITY = 5;

    Map<Cell, HashSet<Edge>> graph;

    //Map<V, HashSet<V>> graph; //adjacency set
    Cell origin;

    public MixedGraph(Cell root) {
        origin = root;
        graph = new HashMap<>(Map.of(root, new HashSet<>(5)));
    }

    public MixedGraph() {
        graph = new HashMap<>();
    }

    @Override
    public boolean isAdjacent(V v, V u) {
        return graph.get(v).contains(u);
    }

    @Override
    public Set<V> neighbors(V v) {
        return null;
    }

    @Override
    public void addVertex(V v) {

    }

    @Override
    public void removeVertex(V v) {

    }

    @Override
    public void addEdge(V v, V u) {

    }

    @Override
    public void removeEdge(V v, V u) {

    }
}
