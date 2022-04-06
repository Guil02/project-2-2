package org.group7.model.graphAPI;

import java.util.*;

//So far just supports undirected graphs
public class Graph<V> {

    Map<V, Set<V>> graph; //adjacency set
    V root;

    public Graph() {
        //if order matters, use LinkedHashMap
        //experiment with initial capacity
        graph = new HashMap<>(100);
    }

    public Graph(V root) {
        this();
        this.root = root;
        graph.put(root, new HashSet<>(5)); //intial capacity 5, because tiles generally have 4 edges, except teleporters
    }

    public void addVertex(V vertex) {
        if (root == null) root = vertex;

        if (!graph.containsKey(vertex)) {
            //add vertex to graph with empty adjacency list
            graph.put(vertex, new HashSet<>(5));
        } else {
            //need to figure out behavior for what to do if graph already contains V
            System.out.println("Vertex " + vertex + " already exists in graph");
        }
    }

    /**
     * Adds an edge between Vertex v, u. If one or neither exist in the graph, they will be created and then
     * the edge will be added
     * @param v first vertex
     * @param u second vertex
     */
    public void addEdge(V v, V u) {
        //put u in adjecency set of v. Otherwise, put v in graph with adjacent u
        if (root == null) root = v;

        if (graph.containsKey(v)) {
            graph.get(v).add(u);
        } else {
            graph.put(v, new HashSet<>(Set.of(u)));
        }

        //same same with u. update adj list if u in map, otherwise create new entry in map
        if (graph.containsKey(u)) {
            graph.get(u).add(v);
        } else {
            graph.put(u, new HashSet<>(Set.of(v)));
        }
    }

    public V getRoot() {
        return root;
    }

    public Set<V> getAdjacent(V v) {
        return graph.getOrDefault(v, new HashSet<>(0)); //returns set of neighbours or empty set
    }

    /**
     * Removes edge between vertices v and u. Both will still remain in the graph
     * @param v
     * @param u
     */
    public void removeEdge(V v, V u) {
        if (!graph.containsKey(v) || !graph.containsKey(u)) {
            //guard clause
            System.out.println("One of these vertices is not included in the graph");
            return;
        }

        graph.get(v).remove(u);
        graph.get(u).remove(v);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Root: " + root.toString() + "\n");
        graph.forEach((key, value) -> s.append(key).append(" -> ").append(value).append("\n"));
        return s.toString();
    }

    public static void main(String[] args) {
        Graph<String> graph = new Graph<>();
//        graph.addVertex("A");
//        graph.addVertex("B");
//        graph.addVertex("C");

        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("C", "D");

        System.out.println(graph);

        graph.removeEdge("C", "B");

        System.out.println(graph);

    }
}
