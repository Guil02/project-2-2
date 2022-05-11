package group.seven.logic.algorithms;

import group.seven.enums.Action;
import group.seven.enums.AlgorithmType;
import group.seven.enums.Cardinal;
import group.seven.enums.MarkerType;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;
import group.seven.model.agents.TileNode;
import group.seven.model.environment.Marker;
import group.seven.model.environment.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BrickAndMortar implements Algorithm {

    ArrayList<Move> moves = new ArrayList<>();
    private Agent agent;
    private boolean flag = false;

    public BrickAndMortar(Agent agent) {
        this.agent = agent;
    }


    @Override
    public Move getNext() {

        //Tile tile = new Tile(agent.getX(), agent.getY());
        //TileNode tileNode = new TileNode(tile);

        if (!moves.isEmpty()) {

            Move moveToExec = moves.get(0);
            moves.remove(0);
            return moveToExec;

        }

        return MultipleDepthFirstSearch();
    }

    protected Move MultipleDepthFirstSearch() {


        ArrayList<Marker> markers = agent.getMarkers();

        Marker currentCellMarker = getMarker(agent.getMarkers(), agent.getX(), agent.getY());
        Marker eastCellMarker = getMarker(agent.getMarkers(), agent.getX() + 1, agent.getY());
        Marker westCellMarker = getMarker(agent.getMarkers(), agent.getX() - 1, agent.getY());
        Marker northCellMarker = getMarker(agent.getMarkers(), agent.getX(), agent.getY() + 1);
        Marker southCellMarker = getMarker(agent.getMarkers(), agent.getX(), agent.getY() - 1);

        //if the current cell is unexplored then
        //2: mark it as explored
        //3: annotate the cell with your ID
        //4: annotate the cell with the direction of the previous cell
        if (currentCellMarker != null && (!(currentCellMarker.getType() == MarkerType.EXPLORED || currentCellMarker.getType() == MarkerType.VISITED))) {

            agent.addMarker(MarkerType.EXPLORED);

        }


        //if there are unexplored cells around
        // then
        //go to one of them randomly

        ArrayList<Marker> adjacentMarkers = new ArrayList<>();
        adjacentMarkers.add(northCellMarker);
        adjacentMarkers.add(southCellMarker);
        adjacentMarkers.add(westCellMarker);
        adjacentMarkers.add(eastCellMarker);
        Collections.shuffle(adjacentMarkers);


        for (Marker adjacentMarker : adjacentMarkers) {

            if (eastCellMarker != null && adjacentMarker == eastCellMarker) {

                if (!(eastCellMarker.getType() == MarkerType.EXPLORED || eastCellMarker.getType() == MarkerType.VISITED)) {


                    Move move1 = new Move(Action.TURN_RIGHT, 0, this.agent);
                    Move move2 = new Move(Action.MOVE_FORWARD, 1, this.agent);
                    moves.add(move1);
                    moves.add(move2);

                    Move moveToExec = moves.get(0);
                    moves.remove(0);
                    return moveToExec;
                }

            }

            if (westCellMarker != null && adjacentMarker == westCellMarker) {


                if (!(westCellMarker.getType() == MarkerType.EXPLORED || westCellMarker.getType() == MarkerType.VISITED)) {

                    Move move1 = new Move(Action.TURN_LEFT, 0, this.agent);
                    Move move2 = new Move(Action.MOVE_FORWARD, 1, this.agent);
                    moves.add(move1);
                    moves.add(move2);

                    Move moveToExec = moves.get(0);
                    moves.remove(0);
                    return moveToExec;
                }
            }


            if (northCellMarker != null && adjacentMarker == northCellMarker) {


                if (!(northCellMarker.getType() == MarkerType.EXPLORED || northCellMarker.getType() == MarkerType.VISITED)) {

                    Move move1 = new Move(Action.TURN_UP, 0, this.agent);
                    Move move2 = new Move(Action.MOVE_FORWARD, 1, this.agent);
                    moves.add(move1);
                    moves.add(move2);

                    Move moveToExec = moves.get(0);
                    moves.remove(0);
                    return moveToExec;
                }
            }

            if (southCellMarker != null && adjacentMarker == southCellMarker) {


                if (!(southCellMarker.getType() == MarkerType.EXPLORED || southCellMarker.getType() == MarkerType.VISITED)) {

                    Move move1 = new Move(Action.TURN_DOWN, 0, this.agent);
                    Move move2 = new Move(Action.MOVE_FORWARD, 1, this.agent);
                    moves.add(move1);
                    moves.add(move2);

                    Move moveToExec = moves.get(0);
                    moves.remove(0);
                    return moveToExec;
                }
            }

        }


        //if the current cell is marked with your ID then
        //10: mark it as visited
        //11: go to the parent cell


        if (currentCellMarker != null) {
            if (currentCellMarker.getId() == agent.getID()) {
                currentCellMarker.setType(MarkerType.VISITED);
                if (currentCellMarker.getCardinal() == Cardinal.NORTH) {
                    Move move1 = new Move(Action.TURN_UP, 0, this.agent);
                    Move move2 = new Move(Action.MOVE_FORWARD, 1, this.agent);
                    moves.add(move1);
                    moves.add(move2);

                    Move moveToExec = moves.get(0);
                    moves.remove(0);
                    return moveToExec;
                } else if (currentCellMarker.getCardinal() == Cardinal.SOUTH) {

                    Move move1 = new Move(Action.TURN_DOWN, 0, this.agent);
                    Move move2 = new Move(Action.MOVE_FORWARD, 1, this.agent);
                    moves.add(move1);
                    moves.add(move2);

                    Move moveToExec = moves.get(0);
                    moves.remove(0);
                    return moveToExec;
                } else if (currentCellMarker.getCardinal() == Cardinal.WEST) {

                    Move move1 = new Move(Action.TURN_LEFT, 0, this.agent);
                    Move move2 = new Move(Action.MOVE_FORWARD, 1, this.agent);
                    moves.add(move1);
                    moves.add(move2);

                    Move moveToExec = moves.get(0);
                    moves.remove(0);
                    return moveToExec;
                } else {

                    Move move1 = new Move(Action.TURN_RIGHT, 0, this.agent);
                    Move move2 = new Move(Action.MOVE_FORWARD, 1, this.agent);
                    moves.add(move1);
                    moves.add(move2);

                    Move moveToExec = moves.get(0);
                    moves.remove(0);
                    return moveToExec;
                }

            }
        }


        //else
        //13: go to one of the explored cells randomly

        RandomMoves randomMoves = new RandomMoves(this.agent); //while a* is not done just make a random move
        return randomMoves.getNext();

//        for (int i = 0; i < agent.getMarkers().size(); i++) {
//            if (markers.get(i).getType() == MarkerType.EXPLORED) {
//
//                //TODO think about it using A* probably
//
//            }
//        }

    //    return null;
    }

    /**
     * A method to return a marker located at a specified (x,y) position
     *
     * @param markers a list of markers
     * @param x       the x coordinate where you want to check for a marker
     * @param y       the y coordinate where you want to check for a marker
     * @return the marker at the given (x,y) position. Will return null if there is no marker.
     */
    public Marker getMarker(List<Marker> markers, int x, int y) {
        for (Marker marker : markers) {
            if (marker.getXY().x() == x && marker.getXY().y() == y) {
                return marker;
            }
        }

        // returns null if there is no marker at given x and  y
        return null;
    }


    @Override
    public AlgorithmType getType() {
        return AlgorithmType.BRICK_AND_MORTAR;
    }
}
