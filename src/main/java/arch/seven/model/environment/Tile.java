package arch.seven.model.environment;


import arch.seven.model.markers.Marker;
import group.seven.model.agents.Agent;
import group.seven.model.environment.Cell;

import java.util.List;

import static org.group7.utils.Methods.print;

record Observed(Agent agent, boolean seen, double atTime){}

public class Tile {
    Cell type;
    int x, y;
    //Point p
    //XY xy

    //guard, intruder
    boolean[] explored = new boolean[2]; //or use tuple/record

    //or array or map
    List<Observed> seen;
    List<Marker> markers;

    List<Tile> adjacent;

    /*
    Graph approaches
        * Simple adjacent Tile list
        * Nested Node class
        * Node super type
        * Node subtype
        * Selective visibility of fields and methods
        * Node record
            - update nodes lazily using != or hashcodes

     */
}

