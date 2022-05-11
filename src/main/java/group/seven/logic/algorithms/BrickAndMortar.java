package group.seven.logic.algorithms;

import group.seven.enums.Action;
import group.seven.enums.AlgorithmType;
import group.seven.enums.Cardinal;
import group.seven.enums.MarkerType;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;
import group.seven.model.environment.Marker;

import java.util.ArrayList;
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
        ArrayList<Marker> markers = agent.getMarkers();


        if (!moves.isEmpty()) {

            Move moveToExec = moves.get(0);
            moves.remove(0);
            return moveToExec;

        }

        //if the current cell is unexplored then
        //2: mark it as explored
        //3: annotate the cell with your ID
        //4: annotate the cell with the direction of the previous cell
        for (int i = 0; i < agent.getMarkers().size(); i++) {
            if (markers.get(i).getXCoordinate() == agent.getX() && markers.get(i).getYCoordinate() == agent.getY()) {
                if (!(markers.get(i).getType() == MarkerType.EXPLORED || markers.get(i).getType() == MarkerType.VISITED)) {
                    agent.addMarker(MarkerType.EXPLORED);
                }
            }
        }


        //if there are unexplored cells around
        // then
        //go to one of them randomly

        for (int i = 0; i < agent.getMarkers().size(); i++) {


            if (markers.get(i).getXCoordinate() == agent.getX() + 1 && markers.get(i).getYCoordinate() == agent.getY()) {

                if (!(markers.get(i).getType() == MarkerType.EXPLORED || markers.get(i).getType() == MarkerType.VISITED)) {


                    Move move1 = new Move(Action.TURN_RIGHT, 0, this.agent);
                    Move move2 = new Move(Action.MOVE_FORWARD, 1, this.agent);
                    moves.add(move1);
                    moves.add(move2);

                    Move moveToExec = moves.get(0);
                    moves.remove(0);
                    return moveToExec;
                }

            }

            if (markers.get(i).getXCoordinate() == agent.getX() - 1 && markers.get(i).getYCoordinate() == agent.getY()) {

                if (!(markers.get(i).getType() == MarkerType.EXPLORED || markers.get(i).getType() == MarkerType.VISITED)) {

                    Move move1 = new Move(Action.TURN_LEFT, 0, this.agent);
                    Move move2 = new Move(Action.MOVE_FORWARD, 1, this.agent);
                    moves.add(move1);
                    moves.add(move2);

                    Move moveToExec = moves.get(0);
                    moves.remove(0);
                    return moveToExec;
                }
            }

            if (markers.get(i).getXCoordinate() == agent.getX() && markers.get(i).getYCoordinate() == agent.getY() + 1) {


                if (!(markers.get(i).getType() == MarkerType.EXPLORED || markers.get(i).getType() == MarkerType.VISITED)) {

                    Move move1 = new Move(Action.TURN_UP, 0, this.agent);
                    Move move2 = new Move(Action.MOVE_FORWARD, 1, this.agent);
                    moves.add(move1);
                    moves.add(move2);

                    Move moveToExec = moves.get(0);
                    moves.remove(0);
                    return moveToExec;
                }
            }

            if (markers.get(i).getXCoordinate() == agent.getX() && markers.get(i).getYCoordinate() == agent.getY() - 1) {


                if (!(markers.get(i).getType() == MarkerType.EXPLORED || markers.get(i).getType() == MarkerType.VISITED)) {

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

        for (int i = 0; i < agent.getMarkers().size(); i++) {
            if (markers.get(i).getXCoordinate() == agent.getX() && markers.get(i).getYCoordinate() == agent.getY()) {
                if (markers.get(i).getId() == agent.getID()) {
                    markers.get(i).setType(MarkerType.VISITED);
                    if (markers.get(i).getCardinal() == Cardinal.NORTH) {
                        Move move1 = new Move(Action.TURN_UP, 0, this.agent);
                        Move move2 = new Move(Action.MOVE_FORWARD, 1, this.agent);
                        moves.add(move1);
                        moves.add(move2);

                        Move moveToExec = moves.get(0);
                        moves.remove(0);
                        return moveToExec;
                    } else if (markers.get(i).getCardinal() == Cardinal.SOUTH) {

                        Move move1 = new Move(Action.TURN_DOWN, 0, this.agent);
                        Move move2 = new Move(Action.MOVE_FORWARD, 1, this.agent);
                        moves.add(move1);
                        moves.add(move2);

                        Move moveToExec = moves.get(0);
                        moves.remove(0);
                        return moveToExec;
                    } else if (markers.get(i).getCardinal() == Cardinal.WEST) {

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
        }

        //else
        //13: go to one of the explored cells randomly

        for (int i = 0; i < agent.getMarkers().size(); i++) {
            if (markers.get(i).getType() == MarkerType.EXPLORED) {

                //TODO think about it using A* probably

            }
        }


        return null;
    }


    /**
     * A method to return a marker located at a specified (x,y) position
     *
     * @param markers a list of markers
     * @param x the x coordinate where you want to check for a marker
     * @param y the y coordinate where you want to check for a marker
     * @return the marker at the given (x,y) position. Will return null if there is no marker.
     */
    public Marker getMarker(List<Marker> markers, int x, int y){
        for(Marker marker : markers){
            if(marker.getXY().x()==x && marker.getXY().y()==y){
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

//TODO optimize 9-11,5,7