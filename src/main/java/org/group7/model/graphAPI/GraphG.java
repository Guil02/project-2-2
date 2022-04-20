package org.group7.model.graphAPI;

import org.w3c.dom.ls.LSOutput;

import java.util.HashSet;
import java.util.Set;

public class GraphG<V> extends AbstractGraph<V>{

    public GraphG(V origin) {
        super(origin);
    }

    @Override
    public boolean isAdjacent(V v, V u) {
        return graph.get(v).contains(u) || graph.get(u).contains(v);
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
        if (origin == null) origin = v;

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

    @Override
    public void removeEdge(V v, V u) {
        if (!graph.containsKey(v) || !graph.containsKey(u)) {
            System.out.println("One of these vertices is not included in the graph");
            return;
        }

        graph.get(v).remove(u);
        graph.get(u).remove(v);
    }

    public static void main(String[] args) {
        GraphG<String> g = new GraphG<>("Origin");
        g.addAll("A","B","C","E","F","G","Marie");
        System.out.println(g.print());
        g.addEdge("Origin", "Marie");
        g.addEdge("A", "B");
        g.addEdge("A", "B");
        g.addEdge("A", "C");
        g.addEdge("B", "D");
        g.addEdge("B", "B");
        System.out.println(g.print());
        System.out.println(g.isAdjacent("Marie", "Origin"));
        System.out.println(g.neighbors("A"));

        Node o = new Node("Origin", 0, 0);
        GraphG<Node> g1 = new GraphG<>(o);
        g1.addVertex(o);
        //g1.addAll(new Node("Left", -1, 0, true), new Node("Right", 1, 0, true), new Node("Up", 0, 1, true), new Node("Down", 0, 0, false));
        System.out.println(g1.print());
        g1.addEdge(o, new Node("Left", -1, 0));
        g1.addEdge(o, new Node("Right", 1, 0));
        g1.addEdge(o, new Node("Up", 0, 1));
        g1.addEdge(o, new Node("Down", 0, 1));
        System.out.println(g1.print());
        System.out.println(g1.neighbors(o));
    }

}
