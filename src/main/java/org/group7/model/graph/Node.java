package org.group7.model.graph;

import org.group7.model.component.playerComponents.PlayerComponent;
import org.group7.model.component.staticComponents.StaticComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {
    private final int x;
    private final int y;
    private StaticComponent staticComponent = null;
    private PlayerComponent playerComponent = null;
    List<Edge> edges;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        edges = new ArrayList<>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public StaticComponent getStaticComponent() {
        return staticComponent;
    }

    public PlayerComponent getPlayerComponent() {
        return playerComponent;
    }

    public void setStaticComponent(StaticComponent staticComponent) {
        this.staticComponent = staticComponent;
    }

    public void setPlayerComponent(PlayerComponent playerComponent) {
        this.playerComponent = playerComponent;
    }

    public void addEdge(Edge edge){
        edges.add(edge);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y && Objects.equals(staticComponent, node.staticComponent) && Objects.equals(playerComponent, node.playerComponent) && Objects.equals(edges, node.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, staticComponent, playerComponent, edges);
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
