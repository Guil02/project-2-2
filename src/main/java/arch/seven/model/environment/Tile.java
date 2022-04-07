package arch.seven.model.environment;

import arch.constants.TileType;

import arch.seven.model.agents.Agent;
import arch.seven.model.markers.Marker;

import java.util.List;

import static org.group7.utils.Methods.print;

record Observed(Agent agent, boolean seen, double atTime){}

public class Tile {
    TileType type;
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

    public static void main(String[] args) {
        Agent agent = new Agent();
        Observed obs = new Observed(agent, false, 0);
        Observed obsOther = new Observed(agent, false, 0);


        print(obs == obsOther);
        print(obs.equals(obsOther));
        print(obs.hashCode() == obsOther.hashCode());
    }
}

