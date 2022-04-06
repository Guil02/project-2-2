package org.group7.model.graphAPI;

import java.util.*;

//Biderectional Simple Graph
public abstract class AbstractGraph<V> implements GraphAPI<V>{

    private static final int EDGE_CAPACITY = 5;

    Map<V, Set<V>> graph; //adjacency set
    V origin;

    public AbstractGraph() {
        graph = new LinkedHashMap<>();
    }

    public AbstractGraph(V root) {
        origin = root;
        graph = new HashMap<>(Map.of(root, new HashSet<>(EDGE_CAPACITY)));
    }

    public void addAll(V... v) {
        Arrays.stream(v).forEach(this::addVertex);
    }

    //    @Override
    public String print() {
        StringBuilder map = new StringBuilder();
        graph.forEach((key, value) -> map.append(key).append(" -> ").append(value).append("\n"));
        return map.toString();
    }


}

record Node(String label, int x, int y) {}