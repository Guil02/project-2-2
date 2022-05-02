package group.seven.model.environment;

import java.util.ArrayList;
import java.util.List;

public class Marker {
    private static final int SIZE = 0; //TODO : think of making an area of a markers not a single grid of marker
    private int markerType;
    private int xCoordinate;
    private int yCoordinate;

    public Marker(int xCoordinate, int yCoordinate, int markerType) {

        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.markerType = markerType;

    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public int getType() {
        return markerType;
    }

    public void setType(int type) {
        this.markerType = type;
    }


}
