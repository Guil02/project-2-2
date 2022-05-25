package group.seven.model.environment;

import group.seven.enums.GameMode;
import group.seven.logic.geometric.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static group.seven.enums.GameMode.ALL_INTRUDERS_CAUGHT;
import static group.seven.enums.GameMode.ALL_INTRUDER_AT_TARGET;
import static group.seven.enums.TileType.*;

public class Scenario {
    private static Scenario SCENARIO;
    //singleton
    public static Scenario get() {
        return SCENARIO;
    }

    public static GameMode GAURD_GAME_MODE = ALL_INTRUDERS_CAUGHT;
    public static GameMode INTRUDER_GAME_MODE = ALL_INTRUDER_AT_TARGET;
    public static String NAME = "untitled map";
    public static int WIDTH, HEIGHT;
    public static int TILE_SIZE = 10;
    public static double INTRUDER_BASE_SPEED, INTRUDER_SPRINT_SPEED;
    public static double GUARD_BASE_SPEED, GUARD_SPRINT_SPEED;
    public static int NUM_GUARDS, NUM_INTRUDERS, NUM_AGENTS;
    public static int VIEW_DISTANCE; //TODO could differ per agent?
    public static int NUM_MARKERS;
    public static int SMELL_DISTANCE;
    public static double TIME_STEP, SCALING;
    public static Component targetArea = new Component(new Rectangle(), TARGET, null, null);
    public static Component intruderSpawnArea = new Component(new Rectangle(), INTRUDER_SPAWN, null, null);
    public static Component guardSpawnArea = new Component(new Rectangle(), GUARD_SPAWN, null, null);

    public static int INTRUDERS_CAUGHT = 0;
    public static int INTRUDERS_AT_TARGET = 0;

    public static final List<Component> walls = new ArrayList<>(20);
    public static final List<Component> shadedAreas = new ArrayList<>();
    public static final List<Component> portals = new ArrayList<>();

    //replaced the get static component's method with a list containing them instead
    public static final List<Component> COMPONENTS = new ArrayList<>(30);

    public static TileMap TILE_MAP;

    public Scenario() {
         SCENARIO = this;//empty constructor used by ScenarioBuilder
    }

    public static void addWall(Rectangle wall) {
        walls.add(new Component(wall, WALL, null, null));
    }

    public static void addShaded(Rectangle shaded) {
        shadedAreas.add(new Component(shaded, SHADED,null, null));
    }

    public static void addPortals(Component tp) {
        portals.add(tp);
    }
}
