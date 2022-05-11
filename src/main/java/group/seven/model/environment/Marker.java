package group.seven.model.environment;

import group.seven.enums.Cardinal;
import group.seven.enums.MarkerType;
import group.seven.logic.geometric.XY;

public class Marker {
    private static final int SIZE = 0; //TODO : think of making an area of a markers not a single grid of marker
    private final int xCoordinate;
    private final int yCoordinate;
    private final int Id;
    private MarkerType markerType;
    private Cardinal cardinal;

    public Marker(int xCoordinate, int yCoordinate, MarkerType markerType, int Id, Cardinal cardinal) {

        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.markerType = markerType;
        this.Id = Id;
        this.cardinal = cardinal;
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

    public void setType(MarkerType type) {
        this.markerType = type;
    }

    public XY getXY() {
        return new XY(this.xCoordinate, this.yCoordinate);
    }

    public int getId() {

        return Id;

    }

    public void setDirection(Cardinal card) {
        this.cardinal = card;
    }

    public Cardinal getCardinal() {
        return cardinal;
    }

}
