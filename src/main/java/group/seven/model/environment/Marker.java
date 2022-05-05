package group.seven.model.environment;

import group.seven.enums.MarkerType;
import group.seven.logic.geometric.XY;

public class Marker {
    private static final int SIZE = 0; //TODO : think of making an area of a markers not a single grid of marker
    private final MarkerType markerType;
    private final int xCoordinate;
    private final int yCoordinate;

    public Marker(int xCoordinate, int yCoordinate, MarkerType markerType) {

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

    public MarkerType getType() {
        return markerType;
    }

    public XY getXY(){
        return new XY(this.xCoordinate,this.yCoordinate);
    }


}
