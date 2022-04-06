package group.seven.model.environment;

import group.seven.enums.Tile;
import group.seven.logic.records.Records;
import group.seven.logic.records.XY;
import group.seven.model.Scenario;
import group.seven.model.agents.Agent;

import java.util.Arrays;
import java.util.List;

import static group.seven.enums.Tile.*;

public class Cell {

    Tile type;
    public XY xy;
    public boolean explored;

    public Records.Seen[] seenBy;
    List<Marker> markers;

    public Cell(int x, int y) {
        xy = new XY(x, y);
        type = EMPTY;
        seenBy = new Records.Seen[Scenario.numIntruders + Scenario.numGuards];
    }

    public boolean isObstacle() {
        return type == WALL;
    }

    public void onStep(Agent agent) {
        if (type == TELEPORTER) {
            agent.teleport();
        }
    }

    @Override
    public String toString() {
        return "Cell{" +
                "type=" + type +
                ", xy=" + xy +
                ", explored=" + explored +
                ", seenBy=" + Arrays.toString(seenBy) +
                ", markers=" + markers +
                '}';
    }
}
