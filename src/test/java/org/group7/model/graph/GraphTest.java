package org.group7.model.graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    public void addNodeTest(){
        Node a = new Node(1,2);
        Graph g = new Graph();
        g.addNode(a);
        assertTrue(g.hasNode(a));
    }

    // Tests what happens when an edge is added while one of the nodes is not in the graph.
    @Test
    public void addEdgeTest1(){
        Graph g = new Graph();
        Node a = new Node(1,2);
        Node b = new Node(2,2);

        // test with only node a present
        g.addAllNode(a);
        RuntimeException thrown1 = assertThrows(RuntimeException.class, () -> g.addEdge(a,b));
        RuntimeException thrown2 = assertThrows(RuntimeException.class, () -> g.addEdge(b,a));
        String expected = "node does not exist inside the graph. so The edge can't be created";
        assertEquals(expected, thrown1.getMessage());
        assertEquals(expected, thrown2.getMessage());

        // test with only node b present
        Graph h = new Graph();
        h.addAllNode(b);
        RuntimeException thrown3 = assertThrows(RuntimeException.class, () -> h.addEdge(a,b));
        RuntimeException thrown4 = assertThrows(RuntimeException.class, () -> h.addEdge(b,a));
        assertEquals(expected, thrown3.getMessage());
        assertEquals(expected, thrown4.getMessage());
    }

    @Test
    public void addEdgeTest2(){
        Graph g = new Graph();
        Node a = new Node(1,2);
        Node b = new Node(2,2);
        Node c = new Node(2,2);
        Node d = new Node(4,2);

        g.addAllNode(a,b);
        g.addEdge(a,b);
        assertTrue(g.hasEdge(a,b));
        assertFalse(g.hasEdge(a,c));
        assertFalse(g.hasEdge(a,d));
    }

}