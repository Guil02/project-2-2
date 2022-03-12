package org.group7.alt.logic.graph;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Graph {

    List<Node> nodes;
    List<Edge> edges;

    public Graph() {
        nodes = new LinkedList<>();
        edges = new LinkedList<>();
    }

    public Graph(Collection<Node> nodeList, Collection<Edge> edgeList) {
        nodes = new LinkedList<>(List.copyOf(nodeList));
        edges = new LinkedList<>(List.copyOf(edgeList));
    }

    public int getOrder() {
        return nodes.size();
    }

    public int size() {
        return edges.size();
    }
}
