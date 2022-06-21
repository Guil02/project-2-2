package group.seven.logic.algorithms.intruders;

public class IntruderMap {
    /*
    Representations:
     - Frontier list of unknown nodes
     - Graph:
        - with walls
        - without walls
        - tree from current positions
     - visited map
     - nd-array of tile features
     - constant heuristic costs
     - observed tile map array
        - teleporters?
     - observed tile hashmap
     - edge cost table
     - tiletype map
        - as enums
        - as integers
     - pairs path map

     Algorithm
        - needs to add penalty for rotation in path planning.
          -> should expand nodes in the direction traveled since rotating is a time costs.
          -> If rotating necessary the idea is to prioritize rotations
            - when adding neighbors to frontier the g-cost should
                be incremented to account for rotation.
            - can be weighted by angle to target
            - and by max distance traversable -- if known
     */
}
