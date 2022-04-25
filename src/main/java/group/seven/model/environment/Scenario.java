package group.seven.model.environment;

import group.seven.enums.GameMode;
import group.seven.logic.geometric.Rectangle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static group.seven.enums.GameMode.SINGLE_INTRUDER;
import static group.seven.enums.TileType.*;

public class Scenario {
    private static Scenario SCENARIO;
    //singleton
    public static Scenario get() {
        return SCENARIO;
    }

    public static GameMode GAME_MODE = SINGLE_INTRUDER;
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
    public static Component targetArea;// = new Rectangle(0,0,0,0);
    public static Component intruderSpawnArea;// = new Rectangle(0,0,0,0);
    public static Component guardSpawnArea;// = new Rectangle(0,0,0,0);

    public static final List<Component> walls = new ArrayList<>(20);
    public static final List<Component> shadedAreas = new ArrayList<>(5);
    public static final List<Component> portals = new ArrayList<>(5);

    public static TileMap TILE_MAP;

    //maybe should be a field if accessed often. Otherwise this lazy approach might be better
    public List<Component> getStaticAreas() {
        List<Component> areas = new LinkedList<>(List.of(targetArea, intruderSpawnArea, guardSpawnArea));
        areas.addAll(walls);
        areas.addAll(shadedAreas);
        areas.addAll(portals);
        //not sure how to handle portals, since they are not Component records

//        for (Component p : portals) {
//            //hacky solution for portals, the Component record is just a wrapper for static areas and otherwise has
//            //no implement functionality. Eg, the following Components aren't linked in any way.
//            //Whereas the Portal record links the portal are to its exit
//
//            Component portalArea = new Component(p.area(), PORTAL);
//            Component exitTile = new Component(new Rectangle(p.exit().x(), p.exit().y(), 1, 1), EXIT_PORTAL);
//            areas.add(portalArea);
//            areas.add(exitTile);
//        }
        return areas;
    }

    public List<Component> getPortals() {
        return portals;
    }

    public Scenario() {
         SCENARIO = this;
        //empty constructor used by ScenarioBuilder
    }

    public void addWall(Rectangle wall) {
        walls.add(new Component(wall, WALL, null, null));
    }

    public void addShaded(Rectangle shaded) {
        shadedAreas.add(new Component(shaded, SHADED,null, null));
    }

    public void addPortals(Component tp) {
        portals.add(tp);
    }

    //public static TileMap TILE_MAP;
}
