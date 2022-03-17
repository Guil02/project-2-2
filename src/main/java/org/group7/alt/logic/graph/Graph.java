package org.group7.alt.logic.graph;

import java.awt.*;
import java.util.*;
import java.util.List;
public class Graph {

    public Map<Point, Node> graph;
    public Node origin;

    public List<Node> nodes;
    public List<Edge> edges;

    public Graph() {
        nodes = new LinkedList<>();
        edges = new LinkedList<>();
        graph = new HashMap<>();
    }

    public Graph(Collection<Node> nodeList, Collection<Edge> edgeList) {
        nodes = new LinkedList<>(List.copyOf(nodeList));
        edges = new LinkedList<>(List.copyOf(edgeList));
    }

    public Node getNodeAt(Point pos) {
        return graph.getOrDefault(pos, new Node());
    }

    public void addNode(Point pos, Node node) {
        //or graph.putIfAbsent(pos, node)
        if (graph.containsKey(pos)) {
            graph.replace(pos, node);
        } else {
            graph.put(pos, node);
        }
    }

    public void addNode(Node node) {
        graph.putIfAbsent(node.coordinate, node);
    }

    public Node getOrigin() {
        return origin;
    }

    public void setOrigin(Node node) {
        origin = node;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getOrder() {
        return nodes.size();
    }

    public int size() {
        return edges.size();
    }
}
