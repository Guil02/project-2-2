package group.seven.logic.algorithms.pathfinding;

import group.seven.model.environment.Scenario;

import java.util.*;


//TODO: also disregard
public class AX {

    Scenario scenario;

    //TODO: pre-calculate all heuristic values

    // list of all possible neighbors from one cell.
    int[] xDirections = {1, 0, 0, -1, -1, -1, 1, 1};
    int[] yDirections = {0, 1, -1, 0, 1, -1, 1, -1};

    //returns just the nodes in the path
    public List<ANode> getNodePath(ANode currentNode) {
        List<ANode> path = new LinkedList<>();
        while (currentNode != null) {
            path.add(currentNode);
            currentNode = currentNode.parent;
        }
        return path;
    }

    public void aStar(ANode start, ANode target) {
        Queue<ANode> open = new PriorityQueue<>();
        HashSet<ANode> closed = new HashSet<>();

        open.add(start);

        while (open.size() > 0) {
            ANode current = open.poll(); //retrieve next node from open list
            closed.add(current); //mark as evaluated

            if (current.equals(target)) //TODO if current at goal
                break;

            //get neighbours
            for (int i = 0; i < 4; i++) { //TODO check for teleporter as possible neighbor
                //check validity
                int nX = current.x + xDirections[i];
                int nY = current.y + yDirections[i];

                if (nX < 0 || nY < 0 || nX > scenario.TILE_MAP.map.length || nY > scenario.TILE_MAP.map[0].length) {
                    ANode neighbor = new ANode(nX, nY);
                }
            }
        }
    }


}
