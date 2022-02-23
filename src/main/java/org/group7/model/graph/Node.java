package org.group7.model.graph;

import org.group7.model.component.playerComponents.PlayerComponent;
import org.group7.model.component.staticComponents.StaticComponent;

import java.util.ArrayList;
import java.util.List;

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
}
