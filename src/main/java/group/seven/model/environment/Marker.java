package group.seven.model.environment;

import group.seven.enums.Cardinal;
import group.seven.enums.MarkerType;
import group.seven.logic.geometric.XY;

public class Marker {
    private static final int SIZE = 0; //TODO : think of making an area of a markers not a single grid of marker
//    private final int xCoordinate;
//    private final int yCoordinate;
    private final XY xy;
    private final int Id;
    private MarkerType markerType;
    private Cardinal cardinal;

    public Marker(int xCoordinate, int yCoordinate, MarkerType markerType, int Id, Cardinal cardinal) {
        xy = new XY(xCoordinate, yCoordinate);
//        this.xCoordinate = xCoordinate;
//        this.yCoordinate = yCoordinate;
        this.markerType = markerType;
        this.Id = Id;
        this.cardinal = cardinal;
    }

    public int getXCoordinate() {
        return xy.x();
    }

    public int getYCoordinate() {
        return xy.y();
    }

    public MarkerType getType() {
        return markerType;
    }

    public void setType(MarkerType type) {
        this.markerType = type;
    }

    public XY getXY() {
        return xy;
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
