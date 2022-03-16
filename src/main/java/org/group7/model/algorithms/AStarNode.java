package org.group7.model.algorithms;

import org.group7.enums.AstarType;

import java.util.Objects;

public class AStarNode {
    private final int x; // the x coordinate on our map
    private final int y; // the y coordinate on our map
    private final AStar aStar; // the instance of the A* algorithm that this node is a part of

    private int gCost = Integer.MAX_VALUE; // distance from starting position. Manhattan distance.
    private int hCost = Integer.MAX_VALUE; // heuristic cost aka distance from target.
    private int hCostPath = Integer.MAX_VALUE;
    private int gCostPath = Integer.MAX_VALUE;
//    private int dCost = Integer.MAX_VALUE; // (this is custom) distance from current node. I envision this as being the amount of turns needed to get to this node. (so including turns needed to turn 90 degrees)
    private int fCost = Integer.MAX_VALUE; // total cost.
    private int fCostPath = Integer.MAX_VALUE; // total cost.
    private AStarNode parent;

    public AStarNode(int x, int y, AStar aStar) {
        this.x = x;
        this.y = y;
        this.aStar = aStar;
        this.parent = null;
    }

    public void updateCost(AstarType type){
        if (type == AstarType.ASTAR_TARGET) {
            updateGCost();
            updateHCost();
            updateFCost();
        } else if(type == AstarType.ASTAR_PATH){
            updateGCostPath();
            updateHCostPath();
            updateFCostPath();
        }
    }

    public void updateHCostPath() {
        hCostPath = aStar.hCostPath(this.x, this.y);
    }

    private void updateGCost(){
        gCost = aStar.gCost(this.x, this.y);
    }

    public void updateGCostPath(){
        gCostPath = aStar.gCostPath(this.x, this.y);
    }

    private void updateHCost(){
        hCost = aStar.hCost(this.x, this.y);
    }

    private void updateFCost(){
        fCost = gCost+hCost;
    }

    public void updateFCostPath(){
        fCostPath = gCostPath+hCostPath;
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

    public int getfCostPath() {
        return fCostPath;
    }

    public int getgCostPath() {
        return gCostPath;
    }

    public void setgCostPath(int gCostPath){ this.gCostPath = gCostPath; }

    public int gethCostPath(){ return hCostPath; }

    public AStarNode getParent(){ return parent; }

    public void setParent(AStarNode parent) { this.parent = parent; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AStarNode aStarNode = (AStarNode) o;
        return x == aStarNode.x && y == aStarNode.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
