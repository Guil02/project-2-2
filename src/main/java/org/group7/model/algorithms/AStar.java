package org.group7.model.algorithms;

import org.group7.enums.Actions;
import org.group7.enums.Orientation;
import org.group7.geometric.Point;
import org.group7.model.Grid;
import org.group7.model.component.playerComponents.PlayerComponent;
import org.group7.model.component.staticComponents.Teleporter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.group7.enums.Actions.NOTHING;
import static org.group7.enums.AstarType.ASTAR_PATH;
import static org.group7.enums.AstarType.ASTAR_TARGET;
import static org.group7.enums.ComponentEnum.TELEPORTER;
import static org.group7.enums.ComponentEnum.WALL;

public class AStar implements Algorithm{
    private final int initialX;
    private final int initialY;
    private final Grid[][] map;
    private final Grid[][] playerMap;
    private AStarNode current;
    private AStarNode target;
    private PlayerComponent player;
    int[][] additions = {{1,0},{-1,0},{0,1},{0,-1}}; //to compute neighbors
    List<ActionTuple> movesLeft;
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
        playerMap = new Grid[map.length][map[0].length];
        playerMap[initialX][initialY]=map[initialX][initialY];
    }

    @Override
    public ActionTuple calculateMovement() {
        if(movesLeft.isEmpty()){
            if(target!=null) {
                current = target;
            }
            target = findTarget();
            if(target == null){
                movesLeft.add(new ActionTuple(NOTHING,0));
            }
            else{
                movesLeft = findPath();
            }
        }
        ActionTuple actionTuple = movesLeft.get(0);
        movesLeft.remove(0);
        return actionTuple;
    }

    public AStarNode findTarget(){
        open.remove(current);
        if(!closed.contains(current))
            closed.add(current);
        for(Grid[] grids: map){
            for(Grid grid: grids){
                if(grid.seen.get(player.getId())){
                    AStarNode node = new AStarNode(grid.getX(), grid.getY(), this);

                    if(open.contains(node) || closed.contains(node) || (grid.getStaticComponent()!=null && grid.getStaticComponent().getComponentEnum()==WALL)){
                        continue;
                    }
                    open.add(node);
                    if((grid.getStaticComponent()!=null && grid.getStaticComponent().getComponentEnum()==TELEPORTER)){
                        Point teleportTarget = ((Teleporter)grid.getStaticComponent()).getTarget();
                        playerMap[grid.getX()][grid.getY()] = map[(int) teleportTarget.x][(int) teleportTarget.y];
                    }
                    playerMap[grid.getX()][grid.getY()]=grid;
                }
            }
        }
        int lowestValue = Integer.MAX_VALUE;
        AStarNode currentTarget = null;
        for(AStarNode node: open){
            node.updateCost(ASTAR_TARGET);
            if(node.getfCost()<lowestValue){
                currentTarget = node;
                lowestValue = node.getfCost();
            }
            else if(node.getfCost()==lowestValue){
                if(node.gethCost()< current.gethCost()){
                    lowestValue = node.getfCost();
                    currentTarget = node;
                }
            }
        }
        return currentTarget;
    }

    public List<ActionTuple> findPath(){
        List<AStarNode> openedNodes = new ArrayList<>();
        List<AStarNode> closedNodes = new ArrayList<>();
        openedNodes.add(current);
        current.updateCost(ASTAR_PATH);
        while (!openedNodes.isEmpty()){
            AStarNode node = openedNodes.get(0);
            for(int i = 1; i < openedNodes.size(); i++){
//                node.updateCost(ASTAR_PATH);
                if(openedNodes.get(i).getfCostPath() < node.getfCostPath()) {
                    node= openedNodes.get(i);
                }
                else if(openedNodes.get(i).getfCostPath()==node.getfCostPath()){
                    if(openedNodes.get(i).gethCostPath() < node.gethCostPath()){
                        node = openedNodes.get(i);
                    }
                }
            }
            openedNodes.remove(node);
            closedNodes.add(node);

            if(node.equals(target)){
                target = node;
                return makePath();
            }
            for (AStarNode neighbor: neighbours(node)) {
                if(closedNodes.contains(neighbor)||playerMap[neighbor.getX()][neighbor.getY()]==null){
                    continue;
                }
//                neighbor.updateCost(ASTAR_PATH);
                int lowestCost = node.getgCostPath()+Math.abs(node.getX() - neighbor.getX()) + Math.abs(node.getY() - neighbor.getY());
                if(lowestCost < neighbor.getgCostPath() || !openedNodes.contains(neighbor)){
                    neighbor.updateHCostPath();
                    neighbor.setgCostPath(lowestCost);
                    neighbor.updateFCostPath();
                    neighbor.setParent(node);
                    if(!openedNodes.contains(neighbor)){
                        openedNodes.add(neighbor);
                    }
                }


            }
        }
        return makePath();
    }

    public List<ActionTuple> makePath(){
        List<Actions> actionPath = actionsPath(nodePath());
        List<ActionTuple> path = new ArrayList<>();
        double speed = player.getBaseSpeed();
        for (int i = 0; i < actionPath.size(); i++) {
            if(actionPath.get(i)==Actions.MOVE_FORWARD){
                int count = 1;
                for (int j = i+1; j < speed && j<actionPath.size(); j++) {
                    if(actionPath.get(j)==Actions.MOVE_FORWARD){
                        i++;
                        count++;
                    }else{
                        break;
                    }
                }
                path.add(new ActionTuple(actionPath.get(i), count));
            }else{
                path.add(new ActionTuple(actionPath.get(i), 0));
            }
        }
        return path;
    }

    public List<AStarNode> nodePath() {
        List<AStarNode> nodePath = new ArrayList<>();
        AStarNode node = target;
        while (node!=null && !node.equals(current)) {
            nodePath.add(node);
            node = node.getParent();
        }
        nodePath.add(node);
        Collections.reverse(nodePath);
        return nodePath;
    }
    public List<Actions> actionsPath(List<AStarNode> nodePath){
        List<Actions> actionPath = new ArrayList<>();
        Orientation orientation = player.getOrientation();
        for(int i = 0; i < nodePath.size()-1; i++){
            AStarNode previous = nodePath.get(i);
            AStarNode next = nodePath.get(i+1);
            if(next.getX()>previous.getX() && next.getY()==previous.getY()){
                if(orientation==Orientation.RIGHT)
                    actionPath.add(Actions.MOVE_FORWARD);
                else {
                    actionPath.add(Actions.TURN_RIGHT);
                    actionPath.add(Actions.MOVE_FORWARD);
                    orientation = Orientation.RIGHT;
                }
            }else if(next.getX()<previous.getX() && next.getY()==previous.getY()){
                if(orientation==Orientation.LEFT)
                    actionPath.add(Actions.MOVE_FORWARD);
                else {
                    actionPath.add(Actions.TURN_LEFT);
                    actionPath.add(Actions.MOVE_FORWARD);
                    orientation = Orientation.LEFT;
                }
            }else if(next.getX()==previous.getX() && next.getY()>previous.getY()){
                if(orientation==Orientation.DOWN)
                    actionPath.add(Actions.MOVE_FORWARD);
                else {
                    actionPath.add(Actions.TURN_DOWN);
                    actionPath.add(Actions.MOVE_FORWARD);
                    orientation = Orientation.DOWN;
                }
            }else if(next.getX()==previous.getX() && next.getY()<previous.getY()){
                if(orientation==Orientation.UP)
                    actionPath.add(Actions.MOVE_FORWARD);
                else {
                    actionPath.add(Actions.TURN_UP);
                    actionPath.add(Actions.MOVE_FORWARD);
                    orientation = Orientation.UP;
                }
            }
        }
        return actionPath;
    }

    public boolean contains(List<AStarNode> list, int x, int y){
        for(AStarNode g : list){
            if(g.getX()==x && g.getY() == y){
                return true;
            }
        }
        return false;
    }

    public List<AStarNode> neighbours(AStarNode node){
        int x = node.getX();
        int y = node.getY();
        List<AStarNode> neighbours = new ArrayList<>();
        for(int i = 0; i<4; i++){
            AStarNode neighbor = new AStarNode(x+additions[i][0],y+additions[i][1],this);
            if (playerMap[neighbor.getX()][neighbor.getY()] != null) {
                neighbours.add(neighbor);
            }
        }
        return neighbours;
    }

    //***********************************************//
    // methods for all the costs in the A* algorithm //
    //***********************************************//
    public int gCost(int x, int y) {
        return (Math.abs(initialX - x) + Math.abs(initialY - y));
    }

    public int gCostPath(int x, int y) {
        return Math.abs(current.getX() - x) + Math.abs(current.getY() - y);
    }

    public int hCost(int x, int y){
        return (Math.abs(current.getX() - x) + Math.abs(current.getY() - y));
    }

    public int hCostPath(int x, int y){
        return Math.abs(target.getX() - x) + Math.abs(target.getY() - y);
    }



}
