package group.seven.model.agents;

import group.seven.enums.*;
import group.seven.logic.geometric.XY;
import group.seven.model.environment.Marker;
import group.seven.model.environment.Pheromone;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.Tile;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.List;

import static group.seven.enums.Cardinal.*;

//TODO the agent structure very much work in progress
public abstract class Agent {
    private static int IDs = 0;
    public final int PHEROMONELIFETIME = 20;
    //Type
    public final XY initialPosition; //spawining
    private final IntegerProperty xProp = new SimpleIntegerProperty();
    private final IntegerProperty yProp = new SimpleIntegerProperty();
    //Pose
    public int x, y; //You can use this for updated coordinates
    //Type
    public TileType agentType;
    protected Cardinal direction;
    //Frontier
    protected List<Tile> seenTiles = new ArrayList<>(30);
    //Marker and Pheromone
    private final ArrayList<Marker> markers = new ArrayList<>();
    private final ArrayList<Pheromone> pheromones = new ArrayList<>();
    //Internal map
    private TileNode[][] map;
    //Type
    boolean ignorePortal = false;
    boolean isTeleported = false;

    //Current Speed
    //Strategy

    public Agent(int x, int y) {
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
        this.x = pos.x();
        this.y = pos.y();
    }

    public void setIgnorePortal(boolean ignorePortal){  // TODO: handle by simulator
        this.ignorePortal = ignorePortal;
    }

    public boolean getIgnorePortal() {
        return this.ignorePortal;
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
        //convert with frame
//        return xProp.get();
        return x;
    }

    public int getY() {
        //convert with frame
//        return yProp.get();
        return y;
    }

    public void setX(int x) {
        //convert with frame
        this.x = x;
        xProp.set(x);
    }

    public void setY(int y) {
        //convert with frame
        this.y = y;
        yProp.set(y);
    }

    public XY getXY() {
        return new XY(x, y);
    }


    public IntegerProperty xProperty() {
        return xProp;
    }

    public IntegerProperty yProperty() {
        return yProp;
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
        //TODO: consider using a Set data structure, like HashSet. It ensures there are no duplicates
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
                ", xProp=" + xProp +
                ", yProp=" + yProp +
                ", agentType=" + agentType +
                '}';
    }

    //
    public void initializeMap() {
        map = new TileNode[Scenario.WIDTH][Scenario.HEIGHT];
    }

    public void updateMap() {
        for (Tile tile : seenTiles) {
            if (map[tile.getX()][tile.getY()] != null) {
                map[tile.getX()][tile.getY()].update();
            } else map[tile.getX()][tile.getY()] = new TileNode(tile, this);
        }
    }

    public TileNode getMapPosition(int x, int y) {
        try{
            return map[x][y];
        }
        catch (Exception e){
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

    public void setTeleported(boolean isTeleported) {
        this.isTeleported = isTeleported;
    }

    public boolean getIsTeleported() {
        return this.isTeleported;
    }

}
