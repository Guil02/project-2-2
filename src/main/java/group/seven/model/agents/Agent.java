package group.seven.model.agents;

import group.seven.enums.Cardinal;
import group.seven.enums.TileType;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

//TODO the agent structure very much work in progress
public abstract class Agent {
    private static int IDs = 0;

    //Pose
    protected int x, y; //You can use this if the property stuff confuses
    protected Cardinal direction;
    private final IntegerProperty xProp = new SimpleIntegerProperty();
    private final IntegerProperty yProp = new SimpleIntegerProperty();

    //Type
    public TileType agentType;

    //FOV
    //Frontier
    //Current Speed
    //Strategy

    public abstract void updateVision();
    public abstract Move calculateMove();
    public abstract int getID();
    public abstract int getCurrentSpeed();

    public void executeMove(int distance){
        x = x+direction.getUnitVector().x()*distance;
        y = y+direction.getUnitVector().y()*distance;
    }

    protected int newID() {
        return IDs++;
    }

    public int getX() {
        //convert with frame
        return xProp.get();
    }

    public int getY() {
        //convert with frame
        return yProp.get();
    }

    public void setX(int x) {
        //convert with frame
        this.xProp.set(x);
    }

    public void setY(int y) {
        //convert with frame
        this.yProp.set(y);
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

}
