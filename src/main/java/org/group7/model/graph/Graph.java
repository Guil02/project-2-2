package org.group7.model.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    List<Node> nodes;
    public Graph() {
        nodes = new ArrayList<>();
    }

    public void addNode(Node a){
        if(!nodes.contains(a)){
            nodes.add(a);
        }
        else{
            System.out.println("Graph already contains node: "+a);
        }
    }

    public void addAllNode(Node... a){
        for(Node i : a){

            if(!nodes.contains(i)){
                nodes.add(i);
            }
            else{
                System.out.println("Graph already contains node: "+i);
            }
        }
    }

    public boolean hasNode(Node a){
        return nodes.contains(a);
    }

    public void addEdge(Node a, Node b){
        Edge e = new Edge(a,b);
        if(!nodes.contains(a)||!nodes.contains(b)){
            throw new RuntimeException("node does not exist inside the graph. so The edge can't be created");
        }
        if(!a.edges.contains(e)){
            a.addEdge(e);
            b.addEdge(e);
        }
        else{
            System.out.println("an edge from "+a+" to "+b+" already exists.");
        }
    }

    public boolean hasEdge(Node a, Node b){
        return a.edges.contains(new Edge(a, b));
    }
}
