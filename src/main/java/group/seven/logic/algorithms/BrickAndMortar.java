package group.seven.logic.algorithms;

import group.seven.enums.Action;
import group.seven.enums.AlgorithmType;
import group.seven.enums.Cardinal;
import group.seven.enums.MarkerType;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;
import group.seven.model.environment.Marker;
import group.seven.model.environment.Scenario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BrickAndMortar implements Algorithm {

    private final Agent agent;
    ArrayList<Move> moves = new ArrayList<>();


    public BrickAndMortar(Agent agent) {
        this.agent = agent;
    }

    public static Cardinal tilePosToAgent(int tileX, int tileY, int agentX, int agentY) {

        if (agentX > tileX) {
            return Cardinal.WEST;
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

        if (moves.isEmpty()) {
            multipleDepthFirstSearch();
        }
//       if(moves.isEmpty()){
//            return new Move(Action.NOTHING,0,agent);
//        }

        Move moveToExec = moves.get(0);
        moves.remove(0);
        return moveToExec;
    }

    /**
     * If there are unexplored cells around, then go to one of them randomly. If the current cell is marked with your ID,
     * then mark it as visited and go to the parent cell. Else, go to one of the explored cells randomly
     */
    protected void multipleDepthFirstSearch() {


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


    }

    /**
     * This method generates the next move that needs to be done according to the Brick and Mortar without loop
     * closure. The moves are added to the move list {@link BrickAndMortar#moves}
     */
    public void BAMWithoutLoopClosure() {

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

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.BRICK_AND_MORTAR;
    }
}
