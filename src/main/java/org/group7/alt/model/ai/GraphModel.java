package org.group7.alt.model.ai;

import org.group7.alt.logic.graph.Graph;
import org.group7.alt.logic.graph.Node;
import org.group7.alt.model.ai.Agents.Agent;

import java.util.LinkedList;
import java.util.List;

public class GraphModel extends Graph {

    Node currentLocation;
    List<Node> unexplored;
    List<Node> explored;

    public GraphModel(Agent agent) {
        unexplored = new LinkedList<>();
        explored = new LinkedList<>();

        Node agentOrigin = new Node(agent.getType(), agent.getPose().getPosition());
        setOrigin(agentOrigin);
        //addNode(agentOrigin);

        currentLocation = agentOrigin;
        //currentLocation.setExplored(true);
        explored.add(currentLocation);
    }

    public void update() {
        //get move from algorithm
    }

    public void updateGraph(Node... nodes) {

    }

    public void updateGraph(List<Node> nodes) {
        for (Node n : nodes) {
            addNode(n);
            explored.add(n);
        }
    }

}
