package org.group7.alt.logic.graph;

import org.group7.alt.enums.Component;

import java.awt.*;
import java.util.List;

public class Node {

    List<Node> neighbourhood;

    Component type;
    Point coordinates;

    boolean obstacle;

    public Node(){}

    public Node(Component c, Point coords) {
        type = c;
        coordinates = coords;
    }

}
