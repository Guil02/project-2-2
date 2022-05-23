package group.seven.model.agents;

import group.seven.enums.*;
import group.seven.logic.geometric.XY;
import group.seven.model.environment.*;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.List;

import static group.seven.enums.Cardinal.*;
import static group.seven.model.environment.Scenario.TILE_MAP;
import static group.seven.utils.Methods.print;

//TODO the agent structure very much work in progress
public abstract class Agent {
    private static int IDs = 0;

    private double numExplored;
    //Coordinates and Frames:
    public final XY globalSpawn; //global spawn position
    protected int x, y;
    public final Frame frame; //handles coordinate transforms
    protected Cardinal direction;
    //Type
    public TileType agentType;
    //Frontier
    protected List<Tile> seenTiles = new ArrayList<>(30);
    //Marker and Pheromone
    public int PHEROMONELIFETIME = 20;
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
        frame = new Frame(new Translate(-x, -y));
        globalSpawn = new XY(x, y);

        map = new TileNode[Scenario.WIDTH + 1] [Scenario.HEIGHT + 1];
        setXY(x, y);
        //initializeMap();
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
        /* convert with frame. This would make callers of the method receive agents coords in global frame*/
        return frame.convertToGlobal(x, y).x();
        //return x;
    }

    public void setX(int x) {
        //convert with frame, might be trickier since the affine transforms require 2D coordinate
        this.x = frame.convertToLocal(x, 0).x();
    }

    public int getY() {
        return frame.convertToGlobal(x, y).y();
//        return y;
    }

    public void setY(int y) {
        //this.y = y;
        this.y = frame.convertToLocal(0, y).y();

    }

    public XY getXY() {
        return frame.convertToGlobal(x, y);
    }

    public void setXY(XY newXY) {
        setXY(newXY.x(), newXY.y());
    }

    public void setXY(int x, int y) {
        XY local = frame.convertToLocal(x, y);
        this.x = local.x();
        this.y = local.y();
    }

    public Cardinal getDirection() {
        return direction;
    }

    public XY getGlobalSpawn() {
        return globalSpawn;
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
     *
     * @param observedTiles the currently observed tiles
     * @param newTiles      the new tiles which needs to be checked
     * @return the observedTiles with the not seen newTiles
     */
    public List<Tile> duplicatedTiles(List<Tile> observedTiles, List<Tile> newTiles) {
        for (Tile tile : newTiles) {
            if (observedTiles.contains(tile)) {
                continue;
            }
            observedTiles.add(tile);
        }
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

    //
    public void initializeMap() {
        map = new TileNode[Scenario.WIDTH + 1] [Scenario.HEIGHT + 1];
    }

    //I think this just gets called once upon spawning
    public void initializeInitialTile(){
        try {
            map[globalSpawn.x()][globalSpawn.y()] = new TileNode(TILE_MAP.getTile(globalSpawn),this);
        } catch (Exception e){
            System.err.println("An error occurred in the initialization of the initial tile in the agent class");
            e.printStackTrace();
        }
    }

    public void updateMap() {
        for (Tile tile : seenTiles) {
            int tx = tile.getX();
            int ty = tile.getY();

            //because the map is initialized as null
            if (map[tx][ty] != null) {
                map[tx][ty].update();
            } else map[tx][ty] = new TileNode(tile, this);
        }


//        for(TileNode[] tiles : map){
//            for(TileNode tile: tiles){
//                if(tile != null) tile.updateAdjacent();
//            }
//        }
    }

    //parameters are in global
    public TileNode getMapPosition(int x, int y) {
        try{
            return map[x][y];
        }
        catch (IndexOutOfBoundsException e){
            print(e.getMessage());
            return null;
        }
    }

    public TileNode[][] getMap() {
        return map;
    }

//    public XY getLocalCoordinate(int x, int y) {
//        return new XY(x - globalSpawn.x(), y - globalSpawn.y());
//    }


    public void setTeleported(boolean isTeleported) {
        this.isTeleported = isTeleported;
    }

    public boolean getIsTeleported() {
        return this.isTeleported;
    }

    @Override
    public String toString() {
        XY global = getXY();
        return "Agent{" +
                "x=" + x +
                ", y=" + y +
                ", globalX=" +  global.x() +
                ", globalY=" + global.y() +
                ", direction=" + direction +
                ", agentType=" + agentType +
                '}';
    }


    //#################### Unused methods: ##########################//

    /**
     * This function adds a marker to the list of markers.
     *
     * @param type The type of marker to add.
     */
    public void addMarker(MarkerType type) {
        //TODO coordinate-deconversion?
        Marker marker = new Marker(this.getX(), this.getY(), type, getID(), getDirection());
        markers.add(marker);
    }

    /**
     * > This function creates a new pheromone object and adds it to the list of pheromones
     *
     * @param type The type of pheromone that is being added.
     */
    public void addPheromone(PheromoneType type) {
        //TODO transform?
        Pheromone pheromone = new Pheromone(this.getX(), this.getY(), type, this.PHEROMONELIFETIME);
        pheromones.add(pheromone);                             //TODO depending on what our agent wants add some properties to the pheromones in the future
    }

    public ArrayList<Marker> getMarkers() {
        return this.markers;
    }

}
