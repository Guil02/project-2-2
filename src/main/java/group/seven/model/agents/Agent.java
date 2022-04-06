package group.seven.model.agents;

import group.seven.enums.Orientation;
import group.seven.enums.Tile;
import group.seven.logic.records.XY;
import group.seven.model.Scenario;

import static group.seven.enums.Tile.GUARD;
import static group.seven.enums.Tile.INTRUDER;
import static org.group7.utils.Config.RANDOM;

public class Agent {
    private static int NUM_AGENTS;

    //agent info
    public Tile type;
    XY origin;
    int ID;

    //Pose
    int x, y;
    double speed;
    Orientation direction;

    private Agent(Tile t, XY o, Orientation rotation) {
        type = t;
        origin = o;
        direction = rotation;
        ID = NUM_AGENTS++;

        speed = switch (t) {
            case GUARD -> Scenario.baseSpeedGuard;
            case INTRUDER -> Scenario.baseSpeedIntruder;
            default -> 0;
        };
    }

    public void teleport() {
        //teleporter logic
    }


    public static class Guard extends Agent {

        public Guard(XY spawn, Orientation rotation) {
            super(GUARD, spawn, rotation);

            x = spawn.getX(); //TODO frame conversion
            y = spawn.getY(); // ""
            direction = Orientation.values()[RANDOM.nextInt(4)];
        }
    }

    public static class Intruder extends Agent {

        private Intruder(XY spawn, Orientation rotation) {
            super(INTRUDER, spawn, rotation);

            x = spawn.getX(); //TODO frame conversion
            y = spawn.getY(); // ""
            direction = Orientation.values()[RANDOM.nextInt(4)];
        }
    }

    @Override
    public String toString() {
        return "Agent{" +
                "type=" + type +
                ", origin=" + origin +
                ", ID=" + ID +
                ", x=" + x +
                ", y=" + y +
                ", speed=" + speed +
                ", direction=" + direction +
                '}';
    }
}
