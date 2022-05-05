package group.seven.model.agents;

import group.seven.enums.Action;
import group.seven.enums.Cardinal;
import group.seven.enums.TileType;
import group.seven.logic.geometric.XY;
import group.seven.model.environment.Tile;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.List;

import static group.seven.enums.Cardinal.*;

//TODO the agent structure very much work in progress
public abstract class Agent {
    private static int IDs = 0;

    //Pose
    public int x, y; //You can use this if the property stuff confuses
    protected Cardinal direction;
    private final IntegerProperty xProp = new SimpleIntegerProperty();
    private final IntegerProperty yProp = new SimpleIntegerProperty();
    //Frontier
    protected List<Tile> seenTiles = new ArrayList<>(30);
    //Type
    public TileType agentType;

    //Current Speed
    //Strategy

    public abstract Move calculateMove();
    public abstract int getID();
    public abstract int getCurrentSpeed();

    //is the distance parameter required here? Seems like it's always 1 based oon CollisionHandler line 46
    public void executeMove(int distance) {
        x += direction.unitVector().x() * distance;
        y += direction.unitVector().y() * distance;
    }

    public void moveTo(XY pos){
        this.x = pos.x();
        this.y = pos.y();
    }

    public void executeTurn(Move move){
        switch(move.action()){
            case TURN_UP -> direction = NORTH;
            case TURN_DOWN -> direction = SOUTH;
            case TURN_LEFT -> direction = WEST;
            case TURN_RIGHT -> direction = EAST;
        }
    }

    //I still feel like this should be abstract
    public abstract void updateVision();
//    {
//        for (Tile tile : vision.updateAndGetVisionAgent(this)){
//            seenTiles.add(tile);
//        }
//    }

    public void clearVision(){
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

    public XY getXY(){
        return new XY(x,y);
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
    }

    //update just the direction of agent (and the default, which is updating vision)
    public void update(Action rotation) {
        switch(rotation){
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
}
