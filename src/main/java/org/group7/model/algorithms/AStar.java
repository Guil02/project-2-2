package org.group7.model.algorithms;

import org.group7.enums.Actions;
import org.group7.model.Grid;

import java.util.ArrayList;
import java.util.List;

public class AStar implements Algorithm{
    private final int initialX;
    private final int initialY;
    private final Grid[][] map;
    private AStarNode current;

    private static final boolean TEMP = true;//TODO REMOVE HCOST METHOD AND this or just this.
    List<Actions> movesLeft;

    List<AStarNode> open;
    List<AStarNode> closed;

    public AStar(int initialX, int initialY, Grid[][] map) {
        this.initialX = initialX;
        this.initialY = initialY;
        open = new ArrayList<>();
        closed = new ArrayList<>();
        movesLeft = new ArrayList<>();
        this.map = map;
        current = new AStarNode(initialX, initialY, this);
        open.add(current);
    }

    @Override
    public Actions calculateMovement() {
        if(movesLeft.isEmpty()){
            AStarNode target = findTarget();
            //TODO shortest path here
        }
        return movesLeft.get(movesLeft.size()-1);
    }

    public AStarNode findTarget(){
        return null;
    }

    //***********************************************//
    // methods for all the costs in the A* algorithm //
    //***********************************************//
    public int gCost(int x, int y) {
        return Math.abs(initialX - x) + Math.abs(initialY - y);
    }

    public int hCost(int x, int y){
        return Math.abs(current.getX() - x) + Math.abs(current.getY() - y);
    }

    public boolean contains(List<AStarNode> list, int x, int y){
        for(AStarNode g : list){
            if(g.getX()==x && g.getY() == y){
                return true;
            }
        }
        return false;
    }
}
