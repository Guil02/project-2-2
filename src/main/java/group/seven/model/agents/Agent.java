package group.seven.model.agents;

import group.seven.enums.*;
import group.seven.logic.geometric.XY;
import group.seven.model.environment.Marker;
import group.seven.model.environment.Pheromone;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.Tile;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.List;

import static group.seven.enums.Cardinal.*;
import static group.seven.utils.Methods.print;

//TODO the agent structure very much work in progress
public abstract class Agent {
    private static int IDs = 0;

    private double numExplored;
    public final int PHEROMONELIFETIME = 20;
    //Coordinates and Frames:
    public final XY initialPosition; //global spawn position
    protected int x, y;
    public Frame frame; //handles coordinate transforms
    protected Cardinal direction;
    //Type
    public TileType agentType;
    //Frontier
    protected List<Tile> seenTiles = new ArrayList<>(30);
    //Marker and Pheromone
    private final ArrayList<Marker> markers = new ArrayList<>();
    private final ArrayList<Pheromone> pheromones = new ArrayList<>();
    //Internal map
    private TileNode[][] map;

    //Current Speed
    //Strategy

    public Agent(int x, int y) {
        frame = new Frame(new Translate(-x, -y));
        initialPosition = new XY(x, y);

        initializeMap();
    }

    public abstract Move calculateMove();

    public abstract int getID();

    public abstract int getCurrentSpeed();

    //is the distance parameter required here? Seems like it's always 1 based oon CollisionHandler line 46
    public void executeMove(int distance) {
        x += direction.unitVector().x() * distance;
        y += direction.unitVector().y() * distance;
    }

    public void moveTo(XY pos) {
        pos = frame.convertToLocal(pos);

        this.x = pos.x();
        this.y = pos.y();
    }

    public void executeTurn(Move move) {
        switch (move.action()) {
            case TURN_UP -> direction = NORTH;
            case TURN_DOWN -> direction = SOUTH;
            case TURN_LEFT -> direction = WEST;
            case TURN_RIGHT -> direction = EAST;
        }
    }

    public abstract void updateVision();

    public void clearVision() {
        seenTiles.clear();
    }

    public List<Tile> getSeenTiles() {
        return seenTiles;
    }

    protected int newID() {
        return IDs++;
    }

    public int getX() {
        /* convert with frame. This would make callers of the method receive agents coords in global frame
        Point2D globalPosition = frame.convertToGlobal(x, y);
        return (int) globalPosition.getX();
        */
        XY globalPosition = frame.convertToGlobal(x, y);
        return globalPosition.x();
        //return x;
    }

    public void setX(int x) {
        //convert with frame, might be trickier since the affine transforms require 2D coordinate
        this.x = frame.convertToLocal(x, 0).x();
    }

    public int getY() {

        /* convert with frame. This would make callers of the method receive agents coords in global frame
        Point2D globalPosition = frame.convertToGlobal(x, y);
        return (int) globalPosition.getX();
        */
//        XY globalPosition = ;
        return frame.convertToGlobal(x, y).y();
//        return y;
    }

    public void setY(int y) {
        //convert with frame
        //this.y = y;
        this.y = frame.convertToLocal(0, y).y();

    }

    public XY getXY() {
        /* convert with frame. This would make callers of the method receive agents coords in global frame
        Point2D globalPosition = frame.convertToGlobal(x, y);
        return new XY(globalPosition)
        */
        return frame.convertToGlobal(x, y);
    }

    public void setXY(XY newXY) {
        XY local = frame.convertToLocal(newXY);
        x = local.x();
        y = local.y();
    }

    public void setXY(int x, int y) {
        setXY(new XY(x, y));
    }

    public Cardinal getDirection() {
        return direction;
    }

    public void setDirection(Cardinal d) {
        direction = d;
    }

    public TileType getType() {
        return agentType;
    }

    public double getNumExplored() {
        return numExplored;
    }

    public void updateNumExplored() {
        this.numExplored++;
    }

    /*

    Feel free to ignore these methods below, I'm trying to think of a way to make
    updating agents based on their moves more intuitive, and maybe will make these update
    w.r.g the coordinate frame in future
     */

    //perhaps this method overloading could be an approach to update the agent
    public void update() {
        updateVision(); //default thing that always gets updated. Like when agent is not moving
        updateMap(); //updates the map with the content of seen list.
    }

    /**
     * Make sure that only one instance gets stored of a tile during the vision process
     * @param observedTiles the currently observed tiles
     * @param newTiles the new tiles which needs to be checked
     * @return the observedTiles with the not seen newTiles
     */
    public List<Tile> duplicatedTiles(List<Tile> observedTiles, List<Tile> newTiles) {
        for (Tile tile : newTiles)
            if (!(observedTiles.contains(tile)))
                observedTiles.add(tile);
        return observedTiles;
    }

    //update just the direction of agent (and the default, which is updating vision)
    public void update(Action rotation) {
        switch (rotation) {
            case TURN_UP -> direction = NORTH;
            case TURN_DOWN -> direction = SOUTH;
            case TURN_LEFT -> direction = WEST;
            case TURN_RIGHT -> direction = EAST;
        }

        update();
    }

    //update the agents coordinates (and its vision)
    public void update(XY newPosition) {

        newPosition = frame.convertToLocal(newPosition);

        this.x = newPosition.x();
        this.y = newPosition.y();

        update();
    }


    @Override
    public String toString() {
        return "Agent{" +
                "x=" + x +
                ", y=" + y +
                ", direction=" + direction +
                ", agentType=" + agentType +
                '}';
    }

    //
    public void initializeMap() {
        map = new TileNode[Scenario.WIDTH + 1] [Scenario.HEIGHT + 1];
    }

    public void updateMap() {
        for (Tile tile : seenTiles) {
            if (map[tile.getX()][tile.getY()] != null) {
                map[tile.getX()][tile.getY()].update();
            } else map[tile.getX()][tile.getY()] = new TileNode(tile, this);
        }
    }

    public TileNode getMapPosition(int x, int y) {
        XY pos = frame.convertToLocal(x, y);
        try{
            return map[pos.x()][pos.y()];
        }
        catch (IndexOutOfBoundsException e){
            print(e.getMessage());
            return null;
        }
    }

    public TileNode[][] getMap() {
        return map;
    }

    public XY getLocalCoordinate(int x, int y) {
        return new XY(x - initialPosition.x(), y - initialPosition.y());
    }

    public void addMarker(MarkerType type) {
        if (type == MarkerType.VISITED) { //TODO depending on what our agent wants add some properties to the markers in the future
            Marker marker = new Marker(this.getX(), this.getY(), type,getID(),getDirection());
            markers.add(marker);
        }
    }

    public void addPheromone(PheromoneType type) {
        if (type == PheromoneType.TEST) {                                   //TODO depending on what our agent wants add some properties to the pheromones in the future
            Pheromone pheromone = new Pheromone(this.getX(), this.getY(), type, this.PHEROMONELIFETIME);
            pheromones.add(pheromone);
        }
    }

}
