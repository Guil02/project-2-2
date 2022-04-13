package group.seven.logic.graphs;

import java.util.HashSet;
import java.util.Set;

public class MapGraph<V> extends AbstractGraph<V>{

    V origin;

    public MapGraph(V origin) {
        super(origin);
        this.origin = origin;
    }

    @Override
    public boolean isAdjacent(V v, V u) {
        return graph.getOrDefault(v, new HashSet<>(0)).contains(u)
                || graph.getOrDefault(u, new HashSet<>(0)).contains(v);
    }

    @Override
    public Set<V> neighbors(V v) {
        return graph.get(v);
    }

    @Override
    public void addVertex(V v) {
        graph.put(v, new HashSet<>(5));
    }

    @Override
    public void removeVertex(V v) {
        graph.remove(v);
    }

    @Override
    public void addEdge(V v, V u) {
        //probably a better way to do this

        if (graph.containsKey(v)) graph.get(v).add(u);
        else graph.put(v, new HashSet<>(Set.of(u)));

        //same with vertex u; update adj-set if u in map, otherwise create new entry in map
        if (graph.containsKey(u)) graph.get(u).add(v);
        else graph.put(u, new HashSet<>(Set.of(v)));
    }

    @Override
    public void removeEdge(V v, V u) {
        if (!graph.containsKey(v) || !graph.containsKey(u)) {
            System.out.println("One of these vertices is not included in the graph");
            return;
        }

        graph.get(v).remove(u);
        graph.get(u).remove(v);
    }

    //Example functionality/usage. TODO: delete
    public static void main(String[] args) {
        MapGraph<String> g = new MapGraph<>("Origin");
        g.addAll("A","B","C","E","F","G","O");
        System.out.println("Initial Graph 1:\n"+g.print());

        g.addEdge("Origin", "O");
        g.addEdge("A", "B");
        g.addEdge("A", "B");
        g.addEdge("A", "C");
        g.addEdge("B", "D");
        g.addEdge("B", "B");

        System.out.println("Graph 1:\n"+g.print());
        System.out.println(g.isAdjacent("Foo", "Origin"));
        System.out.println("Neighbors of A:\n" +g.neighbors("A"));

        Node o = new Node("Origin", 0, 0);
        MapGraph<Node> g1 = new MapGraph<>(o);
        //g1.addAll(new Node("Left", -1, 0, true), new Node("Right", 1, 0, true), new Node("Up", 0, 1, true), new Node("Down", 0, 0, false));

        System.out.println("Initial Graph 2:\n" +g1.print());
        g1.addEdge(o, new Node("Left", -1, 0));
        g1.addEdge(o, new Node("Right", 1, 0));
        g1.addEdge(o, new Node("Up", 0, -1));
        g1.addEdge(o, new Node("Down", 0, 1));
        System.out.println("Graph 2:\n"+g1.print());
        System.out.println("Neighbors of " + o + " :\n" + g1.neighbors(o));
    }

}
