package group.seven.model.agents;

import group.seven.enums.Cardinal;
import group.seven.enums.TileType;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

//TODO the agent structure very much work in progress
public abstract class Agent{

    //Pose
    //protected int x, y;
    protected Cardinal direction;
    private final IntegerProperty xProp = new SimpleIntegerProperty();
    private final IntegerProperty yProp = new SimpleIntegerProperty();

    //Type
    private TileType agentType;

    //FOV

    //Frontier
    //Current Speed
    //Strategy

    public abstract void updateVision();

    public int getX() {
        //convert with frame
        return xProp.get();
    }

    public int getY() {
        //convert with frame
        return yProp.get();
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

    public TileType getType() {
        return agentType;
    }


}
