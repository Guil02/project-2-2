package org.group7.model.algorithms;

import java.util.Objects;

public class AStarNode {
    private final int x; // the x coordinate on our map
    private final int y; // the y coordinate on our map
    private final AStar aStar; // the instance of the A* algorithm that this node is a part of

    private int gCost = Integer.MAX_VALUE; // distance from starting position. Manhattan distance.
    private int hCost = Integer.MAX_VALUE; // heuristic cost aka distance from target.
//    private int dCost = Integer.MAX_VALUE; // (this is custom) distance from current node. I envision this as being the amount of turns needed to get to this node. (so including turns needed to turn 90 degrees)
    private int fCost = Integer.MAX_VALUE; // total cost.

    public AStarNode(int x, int y, AStar aStar) {
        this.x = x;
        this.y = y;
        this.aStar = aStar;
    }

    public void updateCost(){
        updateGCost();
        updateHCost();
        updateFCost();
    }

    private void updateGCost(){
        gCost = aStar.gCost(this.x, this.y);
    }

    private void updateHCost(){
        hCost = aStar.hCost(this.x, this.y);
    }

    private void updateFCost(){
        fCost = gCost+hCost;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getfCost() {
        return fCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AStarNode aStarNode = (AStarNode) o;
        return x == aStarNode.x && y == aStarNode.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, aStar);
    }
}
