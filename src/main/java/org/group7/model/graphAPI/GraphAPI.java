package org.group7.model.graphAPI;

import java.util.Set;

public interface GraphAPI<V> {

    /**
     * Test whether there is an edge from Vertex v to Vertex u
     * @param v first vertex
     * @param u second vertex
     * @return true if there exists an edge between these vertices
     */
    boolean isAdjacent(V v, V u);

    /**
     * Retrieve set of all adjacent vertices, that are adjacent to vertex v
     * @param v the vertex whose neighbors to return
     * @return a set of Vertices, or an empty set if there are no neighbors to v
     */
    Set<V> neighbors(V v);

    /**
     * Adds vertex v to the graph if it doesn't already exist
     * @param v to be added to the graph
     */
    void addVertex(V v);

    /**
     * Removes vertex v from graph if it exists
     * @param v to be remoed to the graph
     */
    void removeVertex(V v);

    /**
     * Creates an edge between vertex V and vertex U, if it doesn't already exist
     * @param v
     * @param u
     */
    void addEdge(V v, V u);

    /**
     * removes edge between Vertex V and vertex U, if exists in graph
     * @param v first vertex
     * @param u second vertex
     */
    void removeEdge(V v, V u);



}
