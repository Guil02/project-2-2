package group.seven.logic.algorithms;

import group.seven.enums.AlgorithmType;
import group.seven.logic.geometric.Pythagoras;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Intruder;
import group.seven.model.agents.Move;
import group.seven.model.agents.TileNode;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.Tile;

import java.util.ArrayList;
import java.util.List;

import static group.seven.enums.Action.NOTHING;
import static group.seven.enums.AlgorithmType.A_STAR;
import static group.seven.enums.TileType.*;

public class AStarAlgorithm implements Algorithm {

    private final int initialX;
    private final int initialY;
    private final Tile[][] map;
    private final TileNode[][] playerMap;
    private AStarNode current;
    private AStarNode target;
    private Intruder player;
    List<Move> movesLeft;
    List<AStarNode> open;
    List<AStarNode> closed;
    int[][] additions = {{1,0},{-1,0},{0,1},{0,-1}}; //TODO: maybe remove



    public AStarAlgorithm(Intruder player) {
        this.initialX = player.initialPosition.x();
        this.initialY =  player.initialPosition.y();;
        open = new ArrayList<>();
        closed = new ArrayList<>();
        movesLeft = new ArrayList<>();
        this.map = Scenario.TILE_MAP.getMap();
        current = new AStarNode(player.getXY(),  this);
        open.add(current);
        this.player = player;
        this.playerMap = player.getMap();
        //playerMap = new Grid[map.length][map[0].length];
        //playerMap[initialX][initialY]=map[initialX][initialY];
    }



    public void updateOpen(){
        List<AStarNode> toBeRemoved = new ArrayList<>();
        for(AStarNode node: open){
            List<AStarNode> neighbours = neighbours(node);
            int count = 0;
            for(AStarNode neighbour : neighbours){
                if(playerMap[neighbour.getX()][neighbour.getY()]!=null){
                    count++;
                }
            }
            if(count>=4){
                toBeRemoved.add(node);
            }
        }
        open.removeIf(toBeRemoved::contains);
        closed.addAll(toBeRemoved);
    }

    public List<AStarNode> neighbours(AStarNode node){
        int x = node.getX();
        int y = node.getY();
        List<AStarNode> neighbours = new ArrayList<>();
        for(int i = 0; i<4; i++){
            AStarNode neighbor = new AStarNode(new XY(x+additions[i][0],y+additions[i][1]),this);
            if (!outOfBounds(neighbor.getX(), neighbor.getY()) && playerMap[neighbor.getX()][neighbor.getY()] != null) {
                neighbours.add(neighbor);
            }
        }
        return neighbours;
    }
    public boolean outOfBounds(int x, int y){
        return x < 0  || x >= playerMap.length || y < 0 || y >= playerMap[0].length;
    }

    public AStarNode findTarget(){
        updateOpen();
        open.remove(current);
        if(!closed.contains(current))
            closed.add(current);

        for(Tile[] grids: map){
            for(Tile grid: grids){
                if(grid.getSeen().get(player.getID())){
                    AStarNode node = new AStarNode(grid.getXy(),  this);
                    if(open.contains(node) || closed.contains(node) || (grid.getType()!=EMPTY && grid.getType()==WALL) || (player.getIgnorePortal() && grid.getType()==PORTAL)){
                        continue;
                    }
                    open.add(node);
                    if((grid.getType()!=EMPTY  && grid.getType()==PORTAL)){ // makes the teleporter be adjacent to the new location. So the agent is aware of where it has been.
                        Tile exit = grid.getAdjacent().targetLocation();
                        playerMap[grid.getX()][grid.getY()]= new TileNode(grid,player);
                        playerMap[exit.getX()][exit.getY()] = new TileNode(map[exit.getX()][exit.getY()],player);
                    }
                    else{
                        playerMap[grid.getX()][grid.getY()]= new TileNode(grid,player);
                    }
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
            else if(node.getfCost()==lowestValue){
                if(node.gethCost()< current.gethCost()){
                    lowestValue = node.getfCost();
                    currentTarget = node;
                }
            }
        }
        return currentTarget;
    }


    public int gCost(XY xy) {
        return (Math.abs(initialX - xy.x()) + Math.abs(initialY - xy.y()));
    }


    public int hCost(XY xy ){
        return (Math.abs(current.getX() - xy.x()) + Math.abs(current.getY() - xy.y()));
    }


    public int rCost (XY xy){ //xy = current frontier node tested
        double angleCurrentNode = Pythagoras.getAnglePythagoras(xy.x(), xy.y(), current.getX(), current.getY());
        double angleOrientation = player.getAngleToGoal();
        return (int)Math.round((Math.abs(angleOrientation- angleCurrentNode)));
    }

    @Override
    public Move getNext() {

        if(player.getIsTeleported()){
            movesLeft.clear();
            player.setTeleported(false);
            playerMap[current.getX()][current.getY()] = new TileNode(map[current.getX()][current.getY()], player);
        }
        if(movesLeft.isEmpty()){
            if(target!=null) {
                current = target;
            }

            target = findTarget();
            System.out.println(target.getX());
            System.out.println(target.getY());

            if(target == null){
                movesLeft.add(new Move(NOTHING,0, player));
            }
            else{
                AStarPathFinder aStarPathFinder = new AStarPathFinder(player, target.getCoordinate());
                movesLeft = aStarPathFinder.findPath();
            }
        }
        Move move = new Move(NOTHING, 0,player);
        System.out.println("move return "+move);
        try{
            move = movesLeft.get(0);
        }
        catch(IndexOutOfBoundsException ignore){
        }
        if(!movesLeft.isEmpty())
            movesLeft.remove(0);

        return move;
    }

    @Override
    public AlgorithmType getType() {
        return A_STAR;
    }

}
