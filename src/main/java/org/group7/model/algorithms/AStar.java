package org.group7.model.algorithms;

import org.group7.enums.Actions;
import org.group7.model.Grid;
import org.group7.model.component.playerComponents.PlayerComponent;

import java.util.ArrayList;
import java.util.List;

import static org.group7.enums.ComponentEnum.WALL;

public class AStar implements Algorithm{
    private final int initialX;
    private final int initialY;
    private final Grid[][] map;
    private AStarNode current;
    private PlayerComponent player;

    private static final boolean TEMP = true;//TODO REMOVE HCOST METHOD AND this or just this.
    List<Actions> movesLeft;

    List<AStarNode> open;
    List<AStarNode> closed;

    public AStar(int initialX, int initialY, Grid[][] map, PlayerComponent player) {
        this.initialX = initialX;
        this.initialY = initialY;
        open = new ArrayList<>();
        closed = new ArrayList<>();
        movesLeft = new ArrayList<>();
        this.map = map;
        current = new AStarNode(initialX, initialY, this);
        open.add(current);
        this.player = player;
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
        open.remove(current);
        closed.add(current);
        for(Grid[] grids: map){
            for(Grid grid: grids){
                if(grid.seen.get(player.getId())){
                    AStarNode node = new AStarNode(grid.getX(), grid.getY(), this);
                    if(closed.contains(node)){
                        continue;
                    }
                    if(grid.getStaticComponent().getComponentEnum()==WALL){
                        continue;
                    }
                    open.add(node);
                }
            }
        }
        int lowestValue = Integer.MAX_VALUE;
        AStarNode currentTarget = null;
        for(AStarNode node: open){
            node.updateCost();
            if(node.getfCost()<lowestValue){
                currentTarget = node;
                lowestValue = node.getfCost();
            }
        }

        return currentTarget;

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
    int[][] additions = {{1,0},{-1,0},{0,1},{0,-1}};
    public List<AStarNode> neighbours(AStarNode node){
        int x = node.getX();
        int y = node.getY();
        List<AStarNode> neighbours = new ArrayList<>();
        for(int i = 0; i<4; i++){
            neighbours.add(new AStarNode(x+additions[i][0],y+additions[i][1],this));
        }
        return neighbours;
    }
}
