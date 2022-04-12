package group.seven.logic.graphs;

import java.util.*;

//Undirected Simple Graph. Works I think, but I don't like the inheritance structure
public abstract class AbstractGraph<V> implements GraphAPI<V>{
    //tester Node representation object, will delete
    record Node(String label, int x, int y) {}

    private static final int EDGE_CAPACITY = 5;     //I think this is technically true? portals have 5 edges

    Map<V, Set<V>> graph; //adjacency set: such that there are no duplicates

    /**
     * Initializes empty implemented with a LinkedHashMap
     * of generic nodes that map to a HashSet adjacency set
     */
    public AbstractGraph() {
        graph = new LinkedHashMap<>();
        //also consider using SortedHashMap or just normal HashMap, not sure
    }

    /**
     * Initializes the graph data structure with single node entry
     * mapping to empty adjacency list
     * @param origin initial object in graph
     */
    public AbstractGraph(V origin) {
        this();
        graph.put(origin, new HashSet<>(EDGE_CAPACITY));
    }

    public void addAll(V... v) {
        Arrays.stream(v).forEach(this::addVertex);
    }

    public String print() {
        StringBuilder map = new StringBuilder();
        graph.forEach((key, value) -> map.append(key).append(" -> ").append(value).append("\n"));
        return map.toString();
    }
}


