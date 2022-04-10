package group.seven.model.agents;

import group.seven.enums.Cardinal;
import group.seven.enums.TileType;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Agent {

    //Pose
    protected int x, y;
    protected Cardinal direction;
    private IntegerProperty xProp = new SimpleIntegerProperty();
    private IntegerProperty yProp = new SimpleIntegerProperty();

    //Type
    private TileType type;

    //FOV

    //Frontier
    //Current Speed
    //Strategy

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
        return type;
    }
}
