package group.seven.logic.algorithms;

import group.seven.logic.geometric.XY;

import java.util.Objects;

public class AStarNode {
    private final XY coordinate;
    private int gCost = Integer.MAX_VALUE;
    private int fCost = Integer.MAX_VALUE;
    private int hCost = Integer.MAX_VALUE;
    private int rCost = Integer.MAX_VALUE;
    private AStarNode parent;
    private AStarPathFinder aStarPath;
    private AStarAlgorithm aStarGoal;

    // TODO: if bugs, check this
    public AStarNode(XY xy, AStarPathFinder aStarPath) {
        this.coordinate = xy;
        this.parent = null;
        this.aStarPath = aStarPath;
    }
    public AStarNode(XY xy, AStarAlgorithm aStarGoal) {
        this.coordinate = xy;
        this.parent = null;
        this.aStarGoal = aStarGoal;
    }


    public void updateCost(){
        updateGCost();
        updateHCost();
        updateFCost();
    }

    public void updateHCost() {
        if (aStarGoal == null ){
            hCost = aStarPath.hCost(this.coordinate);
        } else {
            hCost = aStarGoal.hCost(this.coordinate);

        }
    }

    public void updateRCost(){
        rCost= aStarGoal.rCost(this.coordinate);
    }

    public void updateGCost(){
        if (aStarGoal == null ){
            gCost = aStarPath.gCost(this.coordinate);
        } else {
            gCost = aStarGoal.gCost(this.coordinate);

        }

    }


    public void updateFCost(){
        if (aStarGoal == null ){
            fCost = gCost+hCost - rCost;
        } else {
            fCost = gCost+hCost;
        }
    }

    public XY getCoordinate() {
        return coordinate;
    }



    public int getfCost() {
        return fCost;
    }

    public double getrCost(){
        return rCost;
    }

    public int getgCost() {
        return gCost;
    }

    public int gethCost() {
        return hCost;
    }



    public void setgCost(int gCost){ this.gCost = gCost; }

    public int getX (){
        return this.coordinate.x();
    }

    public int getY (){
        return this.coordinate.y();
    }
    public AStarNode getParent(){ return parent; }

    public void setParent(AStarNode parent) { this.parent = parent; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AStarNode aStarNode = (AStarNode) o;
        return Objects.equals(coordinate, aStarNode.coordinate);
    }


    @Override
    public String toString() {
        return "AStarNode{" +
                "coordinate=" + coordinate +
                '}';
    }
}
