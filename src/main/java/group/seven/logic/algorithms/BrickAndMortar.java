package group.seven.logic.algorithms;

import group.seven.enums.Action;
import group.seven.enums.AlgorithmType;
import group.seven.enums.Cardinal;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;
import group.seven.model.agents.TileNode;
import group.seven.model.environment.Adjacent;
import group.seven.model.environment.Marker;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static group.seven.enums.Action.NOTHING;
import static group.seven.enums.Action.*;
import static group.seven.enums.Cardinal.*;
import static group.seven.enums.MarkerType.*;
import static group.seven.enums.TileType.INTRUDER;
import static group.seven.enums.TileType.WALL;

public class BrickAndMortar implements Algorithm {

    private final Agent agent;
    ArrayList<Move> moves = new ArrayList<>();
    private final Cardinal[] orientations = {NORTH, EAST, SOUTH, WEST};


    public BrickAndMortar(Agent agent) {
        moves.add(new Move(getAction(orientations[agent.getID()%4]),0,agent));
        this.agent = agent;
    }

    public static Cardinal tilePosToAgent(int tileX, int tileY, int agentX, int agentY) {

        if (agentX > tileX) {
            return WEST;
        } else if (agentX < tileX) {
            return Cardinal.EAST;
        } else if (agentY > tileY) {
            return Cardinal.NORTH;
        } else if (agentY < tileY) {
            return Cardinal.SOUTH;
        }
        return null;
    }

    /**
     * The function first checks if the moves list is empty. If it is, it calls the multipleDepthFirstSearch() function. If
     * it isn't, it returns the first move in the list
     *
     * @return The next move in the list of moves.
     */
    @Override
    public Move getNext() {
        if(moves.isEmpty()){
            XY target = seeTarget();
            if(target.x()!=-1 && target.y() != -1){
//                System.out.println("path finding to "+target+"");
                AStarPathFinder pf = new AStarPathFinder(agent, target);
                moves.addAll(pf.findPath());
            }
            else{
                BAMWithoutLoopClosure();
            }
        }
        if(moves.isEmpty()){
            return new Move(Action.NOTHING, 0,agent);
        }


        Move nextMove = moves.get(0);
        moves.remove(0);
        return nextMove;
//        if (moves.isEmpty()) {
//            BAMWithoutLoopClosure();
//        }
//       if(moves.isEmpty()){
//            return new Move(NOTHING,0,agent);
//        }
//
//        Move moveToExec = moves.get(0);
//        moves.remove(0);
//        return moveToExec;
    }

    public XY seeTarget(){
        for(Tile t : agent.getSeenTiles()){
            for(Agent a : Scenario.TILE_MAP.agents){
                if(a.getType()== INTRUDER && a.getX()==t.getX() && a.getY() ==t.getY()){
                    return new XY(a.getX(),a.getY());
                }
            }
        }
        return new XY(-1,-1);
    }

    /**
     * If there are unexplored cells around, then go to one of them randomly. If the current cell is marked with your ID,
     * then mark it as visited and go to the parent cell. Else, go to one of the explored cells randomly
     */
    protected void multipleDepthFirstSearch() {
/*

        ArrayList<Marker> markers = Scenario.TILE_MAP.getMarkers();

        List<Marker> currentCellMarkers = Scenario.TILE_MAP.getTile(agent.getX(), agent.getY()).guard_marker;


        List<Marker> eastCellMarkers = Scenario.TILE_MAP.getTile(agent.getX() + 1, agent.getY()).guard_marker;
        List<Marker> westCellMarkers = Scenario.TILE_MAP.getTile(agent.getX() - 1, agent.getY()).guard_marker;
        List<Marker> northCellMarkers = Scenario.TILE_MAP.getTile(agent.getX(), agent.getY() + 1).guard_marker;
        List<Marker> southCellMarkers = Scenario.TILE_MAP.getTile(agent.getX(), agent.getY() - 1).guard_marker;

        //if the current cell is unexplored then
        //2: mark it as explored
        //3: annotate the cell with your ID
        //4: annotate the cell with the direction of the previous cell

        boolean flag1 = true;

        for (Marker currentCellMarker :
                currentCellMarkers) {
            if (currentCellMarker.getType() == MarkerType.EXPLORED || currentCellMarker.getType() == MarkerType.VISITED ) {
                flag1 = false;
                break;

            }
        }
        if (flag1) {
            Scenario.TILE_MAP.getTile(agent.getX(), agent.getY()).guard_marker.get(agent.getID()).setType(MarkerType.EXPLORED);
        }

        //if there are unexplored cells around
        // then
        //go to one of them randomly

        ArrayList<List<Marker>> adjacentMarkers = new ArrayList<>();
        adjacentMarkers.add(northCellMarkers);
        adjacentMarkers.add(southCellMarkers);
        adjacentMarkers.add(eastCellMarkers);
        adjacentMarkers.add(westCellMarkers);
        Collections.shuffle(adjacentMarkers);

        label:
        for (List<Marker> adjacentMarker : adjacentMarkers) {

            boolean bool = true;

            for (Marker m : adjacentMarker) {
                if (m.getType() == MarkerType.EXPLORED || m.getType() == MarkerType.VISITED) {
                    bool = false;
                    break;
                }
            }
            if (bool) {
                for (Marker m : adjacentMarker) {
                    if (tilePosToAgent(m.getXCoordinate(), m.getYCoordinate(), agent.getX(), agent.getY()) == Cardinal.NORTH) {
                        Move move1 = new Move(Action.TURN_UP, 0, this.agent);
                        Move move2 = new Move(Action.MOVE_FORWARD, 1, this.agent);
                        moves.add(move1);
                        moves.add(move2);

                    } else if (tilePosToAgent(m.getXCoordinate(), m.getYCoordinate(), agent.getX(), agent.getY()) == Cardinal.SOUTH) {
                        Move move1 = new Move(Action.TURN_DOWN, 0, this.agent);
                        Move move2 = new Move(Action.MOVE_FORWARD, 1, this.agent);
                        moves.add(move1);
                        moves.add(move2);
                    } else if (tilePosToAgent(m.getXCoordinate(), m.getYCoordinate(), agent.getX(), agent.getY()) == Cardinal.WEST) {
                        Move move1 = new Move(Action.TURN_LEFT, 0, this.agent);
                        Move move2 = new Move(Action.MOVE_FORWARD, 1, this.agent);
                        moves.add(move1);
                        moves.add(move2);
                    } else if (tilePosToAgent(m.getXCoordinate(), m.getYCoordinate(), agent.getX(), agent.getY()) == Cardinal.EAST) {
                        Move move1 = new Move(Action.TURN_RIGHT, 0, this.agent);
                        Move move2 = new Move(Action.MOVE_FORWARD, 1, this.agent);
                        moves.add(move1);
                        moves.add(move2);
                    }
                    break label;
                }
            }

        }
        //if the current cell is marked with your ID then
        //10: mark it as visited
        //11: go to the parent cell


        boolean flag2 = false;
        Marker cm = null;
        for (Marker currentCellMarker :
                currentCellMarkers) {
            if (currentCellMarker.getType() == MarkerType.EXPLORED && currentCellMarker.getId() == agent.getID()) {
                flag2 = true;
                cm = currentCellMarker;
            }
        }
        if (flag2) {
            Scenario.TILE_MAP.getTile(cm.getXCoordinate(), cm.getYCoordinate()).guard_marker.get(agent.getID()).setType(MarkerType.VISITED);

            if (cm.getCardinal() == Cardinal.NORTH) {
                Move move1 = new Move(Action.TURN_UP, 0, this.agent);
                Move move2 = new Move(Action.MOVE_FORWARD, 1, this.agent);
                moves.add(move1);
                moves.add(move2);

            } else if (cm.getCardinal() == Cardinal.SOUTH) {
                Move move1 = new Move(Action.TURN_DOWN, 0, this.agent);
                Move move2 = new Move(Action.MOVE_FORWARD, 1, this.agent);
                moves.add(move1);
                moves.add(move2);

            } else if (cm.getCardinal() == Cardinal.WEST) {
                Move move1 = new Move(Action.TURN_LEFT, 0, this.agent);
                Move move2 = new Move(Action.MOVE_FORWARD, 1, this.agent);
                moves.add(move1);
                moves.add(move2);

            } else if (cm.getCardinal() == Cardinal.EAST) {
                Move move1 = new Move(Action.TURN_RIGHT, 0, this.agent);
                Move move2 = new Move(Action.MOVE_FORWARD, 1, this.agent);
                moves.add(move1);
                moves.add(move2);

            }

        }


        //else
        //13: go to one of the explored cells randomly


        ArrayList<Marker> exploredTiles = new ArrayList<>();

        for (Marker marker : agent.getMarkers()) {
            if (marker.getType() == MarkerType.EXPLORED) {
                exploredTiles.add(marker);
            }
        }


        for (int i = 0; i < Scenario.TILE_MAP.getMarkers().size(); i++) {
            if (Scenario.TILE_MAP.getMarkers().get(i).getType() == MarkerType.EXPLORED) {
                exploredTiles.add(Scenario.TILE_MAP.getMarkers().get(i));
            }
        }

        Collections.shuffle(exploredTiles);

        if (!exploredTiles.isEmpty()) {
            AStarPathFinder aStarPathFinder = new AStarPathFinder(this.agent, exploredTiles.get(0).getXY());
            ArrayList<Move> movesAStar = new ArrayList<>(aStarPathFinder.findPath());
            moves.addAll(movesAStar);
        }

*/
    }

    /**
     * This method generates the next move that needs to be done according to the Brick and Mortar without loop
     * closure. The moves are added to the move list {@link BrickAndMortar#moves}
     */
    public void BAMWithoutLoopClosure() {
        TileNode currentTile = agent.getMapPosition(agent.x, agent.y);
        Adjacent<TileNode> neighbours = currentTile.getAdjacent();
        D d = countD(neighbours);

        // check if removing this cell does not block the path between two explored / unexplored cells
        if ((d.exploredTiles +d.unexploredTiles)< 2) {
            currentTile.setExploreType(VISITED);
        } else {
            currentTile.setExploreType(EXPLORED);
        }

        if(d.unexploredTiles>0) {
            TileNode chosen = d.unexplored.get(0);
            List<TileNode> choices = new ArrayList<>();
            choices.add(chosen);
            int amount = countVisitedAndWalls(chosen);
            for (TileNode t : d.unexplored) {
                int temp = countVisitedAndWalls(t);
                if (temp > amount) {
                    chosen = t;
                    amount = temp;
                    choices.clear();
                    choices.add(t);
                }
                else if(temp==amount){
                    choices.add(t);
                }
            }
            Random rand = new Random();
            chosen = choices.get(rand.nextInt(0,choices.size()));
            AStarPathFinder pf = new AStarPathFinder(agent,new XY(chosen.getX(), chosen.getY()));
            moves.addAll(pf.findPath());
        }
        else if(d.exploredTiles>0){
//            choose first direction based upon agent id so not all agent go same direction
            boolean selectSame = true;
            if(d.exploredTiles > 1)
                selectSame = false;
            int index = agent.getID()%4;
            TileNode target = chooseTarget(currentTile,index, selectSame);
            AStarPathFinder pf = new AStarPathFinder(agent,new XY(target.getX(), target.getY()));
            moves.addAll(pf.findPath());
        }
        else{
            moves.add(new Move(NOTHING,0,agent));
        }
    }

    private TileNode chooseTarget(TileNode currentTile, int startingIndex, boolean selectSame) {
        for(int i = startingIndex; i<orientations.length; i++){
            TileNode adjacent = currentTile.getAdjacent().getAdjacent(orientations[i]);
            if(adjacent!=null && adjacent.getType()!=WALL && adjacent.getExploreType()==EXPLORED){
                if(agent.getDirection()!=orientations[i].flip() || (agent.getDirection()==orientations[i].flip()&&selectSame)) {
                    return adjacent;
                }
            }
        }
        for(int i = 0; i<startingIndex; i++){
            TileNode adjacent = currentTile.getAdjacent().getAdjacent(orientations[i]);
            if(adjacent!=null && adjacent.getType()!=WALL && adjacent.getExploreType()==EXPLORED){
                if(agent.getDirection()!=orientations[i].flip() || (agent.getDirection()==orientations[i].flip()&&selectSame)) {
                    return adjacent;
                }
            }
        }
        throw new IllegalStateException("could not find a target");
    }

    /**
     * A method made to iterate over the 4 adjacent tiles of a tile and check how many of them are marked as
     * unexplored or explored.
     *
     * @param a The adjacent nodes for a tile
     * @return the inner class {@link D}
     */
    public D countD(Adjacent<TileNode> a) {
        D d = new D();

        addCount(d, a.north());
        addCount(d, a.east());
        addCount(d, a.south());
        addCount(d, a.west());

        return d;
    }

    /**
     * This method is used by {@link BrickAndMortar#countD(Adjacent)} to check if a tile is explored/unexplored
     *
     * @param d a variable {@link D} where the information should be stored
     * @param t The specific tile to be checked out.
     */
    public void addCount(D d, TileNode t) {
        if (t != null && t.getType()==WALL) {
        } else if (t == null || t.getExploreType() == UNEXPLORED) {
            if(t!=null){
                d.unexplored.add(t);
            }
            d.unexploredTiles++;
        } else if (t.getExploreType() == EXPLORED) {
            d.exploredTiles++;
        }
    }

    /**
     * a method that checks how many adjacent tiles are visited or are a wall.
     * @param t the node for which needs to be checked
     * @return the amount of adjacent tiles that are visited or a wall
     */
    public int countVisitedAndWalls(TileNode t){
        Adjacent<TileNode> a = t.getAdjacent();
        int count = 0;
        if(a.north()!=null && (a.north().getType()==WALL||a.north().getExploreType()==VISITED))
            count++;
        if(a.east()!=null && (a.east().getType()==WALL||a.east().getExploreType()==VISITED))
            count++;
        if(a.south()!=null && (a.south().getType()==WALL||a.south().getExploreType()==VISITED))
            count++;
        if(a.west()!=null && (a.west().getType()==WALL||a.west().getExploreType()==VISITED))
            count++;
        return count;
    }

    /**
     * an inner class made to store some information about the tile and its neighbours
     */
    static class D {
        //        the amount of explored + unexplored neighbours it has
        int exploredTiles = 0;
        //        the amount of unexplored
        int unexploredTiles = 0;
        //        a list containing all the unexplored tiles in the neighbourhood
        List<TileNode> unexplored = new ArrayList<>();
    }

    /**
     * A method to return a marker located at a specified (x,y) position
     *
     * @param markers a list of markers
     * @param x       the x coordinate where you want to check for a marker
     * @param y       the y coordinate where you want to check for a marker
     * @return the marker at the given (x,y) position. Will return null if there is no marker.
     */
    public List<Marker> getMarker(List<Marker> markers, int x, int y) {

        List<Marker> allMarkers = new ArrayList<>();

        for (Marker marker : markers) {
            if (marker.getXY().x() == x && marker.getXY().y() == y) {
                allMarkers.add(marker);
            }
        }

        // returns null if there is no marker at given x and  y
        return allMarkers;
    }

    private Action getAction(Cardinal c){
        switch (c){

            case NORTH -> {
                return TURN_UP;
            }
            case SOUTH -> {
                return TURN_DOWN;
            }
            case EAST -> {
                return TURN_RIGHT;
            }
            case WEST -> {
                return TURN_LEFT;
            }
        }
        throw new IllegalArgumentException(c+" is not a valid orientation for this method");
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.BRICK_AND_MORTAR;
    }
}
