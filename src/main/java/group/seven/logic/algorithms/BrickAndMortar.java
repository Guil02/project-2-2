package group.seven.logic.algorithms;

import group.seven.enums.Action;
import group.seven.enums.AlgorithmType;
import group.seven.enums.Cardinal;
import group.seven.enums.MarkerType;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;
import group.seven.model.environment.Marker;

import java.util.ArrayList;

public class BrickAndMortar implements Algorithm {

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

                    Move move = new Move();
                    return move;
                }

            }

            if (markers.get(i).getXCoordinate() == agent.getX() - 1 && markers.get(i).getYCoordinate() == agent.getY()) {

                if (!(markers.get(i).getType() == MarkerType.EXPLORED || markers.get(i).getType() == MarkerType.VISITED)) {

                    Move move = new Move();
                    return move;
                }
            }

            if (markers.get(i).getXCoordinate() == agent.getX() && markers.get(i).getYCoordinate() == agent.getY() + 1) {


                if (!(markers.get(i).getType() == MarkerType.EXPLORED || markers.get(i).getType() == MarkerType.VISITED)) {

                    Move move = new Move();
                    return move;
                }
            }

            if (markers.get(i).getXCoordinate() == agent.getX() && markers.get(i).getYCoordinate() == agent.getY() - 1) {


                if (!(markers.get(i).getType() == MarkerType.EXPLORED || markers.get(i).getType() == MarkerType.VISITED)) {

                    Move move = new Move();
                    return move;
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
                        return new Move(Action.TURN_DOWN, 1, this.agent);
                    } else if (markers.get(i).getCardinal() == Cardinal.SOUTH) {

                        return new Move(Action.TURN_UP, 1, this.agent);
                    } else if (markers.get(i).getCardinal() == Cardinal.WEST) {

                        return new Move(Action.TURN_RIGHT, 1, this.agent);
                    } else {

                        return new Move(Action.TURN_LEFT, 1, this.agent);
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

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.BRICK_AND_MORTAR;
    }
}
