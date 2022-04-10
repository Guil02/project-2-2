package group.seven.model.agents;

import group.seven.enums.TileType;
import group.seven.model.environment.Tile;
import javafx.geometry.Point2D;

import static group.seven.enums.TileType.UNKNOWN;

public class TileNode extends Tile {

    //from agent's perspective
    int x, y;
    //Or Point2D?

    TileType type;

    public TileNode(int localX, int localY) {
        x = localX;
        y = localY;
        type = UNKNOWN;
    }

    public TileNode(TileType type, int localX, int localY) {
       this(localX, localY);
       this.type = type;
    }

    public int getDistance(TileNode other) {
        Point2D position = new Point2D(x, y);
        return (int) position.distance(other.x, other.y);
    }
}
