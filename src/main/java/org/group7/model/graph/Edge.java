package org.group7.model.graph;

import java.util.Objects;

public class Edge {
    Node a;
    Node b;

    public Edge(Node a, Node b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(a, edge.a) && Objects.equals(b, edge.b) || Objects.equals(a, edge.b) && Objects.equals(b, edge.a);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}
