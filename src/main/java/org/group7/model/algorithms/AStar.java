package org.group7.model.algorithms;

import org.group7.model.Grid;
import org.group7.model.enums.ComponentEnum;

import java.util.ArrayList;
import java.util.List;

public class AStar implements Algorithm{
    private final int initialX;
    private final int initialY;
    private final Grid[][] map;
    private AStarNode current;

    private static final boolean TEMP = true;//TODO REMOVE HCOST METHOD AND this or just this.

    List<AStarNode> open;

    public AStar(int initialX, int initialY, Grid[][] map) {
        this.initialX = initialX;
        this.initialY = initialY;
        open = new ArrayList<>();
        this.map = map;
        current = new AStarNode(initialX, initialY, this);
    }

    @Override
    public Grid calculateMovement() {
        return null;//TODO implement
    }

    //***********************************************//
    // methods for all the costs in the A* algorithm //
    //***********************************************//
    public int gCost(int x, int y) {
        return Math.abs(initialX - x) + Math.abs(initialY - y);
    }

    public int hCost() {
        int explored = 0;
        int walls = 0;
        int total = map.length * map[0].length;
        for (Grid[] grids : map) {
            for (Grid grid : grids) {
                if (grid.getStaticComponent().getComponentEnum() == ComponentEnum.WALL) {
                    walls++;
                } else if (grid.explored) {
                    explored++;
                }
            }
        }
        if(TEMP){
            return 0;
        }
        return ((total-walls-explored)*100) / (total - walls);
    }

    public int dCost(int x, int y){
        return Math.abs(current.getX() - x) + Math.abs(current.getY() - y);
    }
}
