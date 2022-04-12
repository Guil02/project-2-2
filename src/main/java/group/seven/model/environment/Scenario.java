package group.seven.model.environment;

import group.seven.enums.GameMode;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static group.seven.enums.GameMode.SINGLE_INTRUDER;
import static group.seven.enums.TileType.*;

public class Scenario {

    public GameMode GAME_MODE = SINGLE_INTRUDER;
    public String NAME = "untitled map";
    public int WIDTH;
    public int HEIGHT;
    public int TILE_SIZE = 10;

    public double INTRUDER_BASE_SPEED;
    public double INTRUDER_SPRINT_SPEED;
    public double GUARD_BASE_SPEED;
    public double GUARD_SPRINT_SPEED;
    public int NUM_GUARDS;
    public int NUM_INTRUDERS;
    public int NUM_AGENTS;
    public int VIEW_DISTANCE;
    public int NUM_MARKERS;
    public int SMELL_DISTANCE;
    public double timeStep;
    public double SCALING;

    public Component targetArea;// = new Rectangle(0,0,0,0);
    public Component intruderSpawnArea;// = new Rectangle(0,0,0,0);
    public Component guardSpawnArea;// = new Rectangle(0,0,0,0);

    private final List<Component> walls = new ArrayList<>(20);
    private final List<Component> shadedAreas = new ArrayList<>(5);
    private final List<Portal> portals = new ArrayList<>(5);

    public TileMap TILE_MAP;

    //maybe should be a field if accessed often. Otherwise this lazy approach might be better
    public List<Component> getStaticAreas() {
        List<Component> areas = new LinkedList<>(List.of(targetArea, intruderSpawnArea, guardSpawnArea));
        areas.addAll(walls);
        areas.addAll(shadedAreas);
        //not sure how to handle portals, since they are not Component records

        for (Portal p : portals) {
            //hacky solution for portals, the Component record is just a wrapper for static areas and otherwise has
            //no implement functionality. Eg, the following Components aren't linked in any way.
            //Whereas the Portal record links the portal are to its exit
            Component portalArea = new Component(p.area(), PORTAL);
            Component exitTile = new Component(new Rectangle(p.exit().x(), p.exit().y(), 1, 1), EXIT_PORTAL);
            areas.add(portalArea);
            areas.add(exitTile);
        }
        return areas;
    }

    public List<Portal> getPortals() {
        return portals;
    }

    public Scenario() {
        //empty constructor used by ScenarioBuilder
    }

    public void addWall(Rectangle wall) {
        walls.add(new Component(wall, WALL));
    }

    public void addShaded(Rectangle shaded) {
        shadedAreas.add(new Component(shaded, SHADED));
    }

    public void addPortals(Portal tp) {
        portals.add(tp);
    }

    //public static TileMap TILE_MAP;
}
