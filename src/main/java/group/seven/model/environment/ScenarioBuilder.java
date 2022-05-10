package group.seven.model.environment;

import group.seven.logic.geometric.Rectangle;
import group.seven.logic.geometric.XY;
import group.seven.utils.Config;
import javafx.util.Builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static group.seven.enums.Cardinal.NORTH;
import static group.seven.enums.GameMode.EXPLORATION;
import static group.seven.enums.GameMode.SINGLE_INTRUDER;
import static group.seven.enums.TileType.*;
import static group.seven.model.environment.Scenario.*;
import static group.seven.utils.Methods.print;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class ScenarioBuilder implements Builder<Scenario> {

    private final File mapFile;

    /**
     * Creates a new Builder the scenario given in the map file.
     * Does not build the Scenario class until build() is called
     * @param mapFile map file for which to build a Scenario object from.
     */
    public ScenarioBuilder(File mapFile) {
        this.mapFile = mapFile;
    }

    /**
     * Initializes the Scenario with the contents from the default mapFile as defined
     * in the Config class
     */
    public ScenarioBuilder(){
        this(new File(Config.DEFAULT_MAP_PATH));
    }

    /**
     * This method must be called in order to actually assemble the Scenario object
     * @return a new Scenario object with all its fields populated by the map file
     */
    @Override
    public Scenario build() {
        //TODO: provide support for configuring file-based scenarios
        //TODO: provide support for building custom scenarios not based on map files
        Scenario scenario = new Scenario();
        parseFile(mapFile);
        compileComponents();
        initMap();
        fillMap();
        setAdjacent();
        return scenario;
    }

    private void parseFile(File map) {
        try (Scanner sc = new Scanner(map)) {
            while (sc.hasNextLine()) {
                String[] property = sc.nextLine()                                //get next line in file
                        .replaceAll("([\\s]*(//)+)+(.)*", "")   //remove any comments in file line
                        .split(" = ");      //split line into its (key, value) pair as a String array

                parseValue(property[0], property[1]); // key, value
            }
        } catch (FileNotFoundException e) {e.printStackTrace();}
    }

    private void parseValue(String property, String value) {
        switch (property) {
            //simple properties:
            case "name"                 -> Scenario.NAME = value;
            case "gameMode"             -> Scenario.GAME_MODE = parseInt(value) == 1 ? SINGLE_INTRUDER : EXPLORATION;
            case "height"               -> Scenario.HEIGHT = parseInt(value);    // the height of the map
            case "width"                -> Scenario.WIDTH = parseInt(value);    // the width of the map
            case "numGuards"            -> Scenario.NUM_GUARDS = parseInt(value);    // the amount of guards
            case "numIntruders"         -> Scenario.NUM_INTRUDERS = parseInt(value);    // the amount of intruders
            case "baseSpeedIntruder"    -> Scenario.INTRUDER_BASE_SPEED = parseDouble(value); // the walking speed of intruders
            case "sprintSpeedIntruder"  -> Scenario.INTRUDER_SPRINT_SPEED = parseDouble(value); // the sprinting speed of the intruders
            case "baseSpeedGuard"       -> Scenario.GUARD_BASE_SPEED = parseDouble(value); // the base speed of the guard.
            case "sprintSpeedGuard"     -> Scenario.GUARD_SPRINT_SPEED = parseDouble(value); // the sprinting speed of the intruders
            case "distanceViewing"      -> Scenario.VIEW_DISTANCE = parseInt(value);
            case "numberMarkers"        -> Scenario.NUM_MARKERS = parseInt(value);
            case "smellingDistance"     -> Scenario.SMELL_DISTANCE = parseInt(value);

            // not sure if these should be included
            case "tileSize"             -> Scenario.TILE_SIZE = parseInt(value);
            case "scaling"              -> Scenario.SCALING = parseDouble(value);
            case "timeStep"             -> Scenario.TIME_STEP = parseDouble(value);

            //regions:
            case "targetArea"           -> targetArea             = new Component(parsePoints(value), TARGET, null, null);
            case "spawnAreaIntruders"   -> intruderSpawnArea      = new Component(parsePoints(value), INTRUDER_SPAWN,null, null);
            case "spawnAreaGuards"      -> guardSpawnArea         = new Component(parsePoints(value), GUARD_SPAWN,null, null);

            case "wall"                 -> Scenario.addWall(parsePoints(value));
            case "shaded"               -> Scenario.addShaded(parsePoints(value));
            case "texture"              -> print("Texture not implemented yet");

            case "teleport" -> {
                String[] coords = value.split(" ");
                XY target = new XY(parseInt(coords[4]), parseInt(coords[5]));
                Scenario.addPortals(new Component(parsePoints(value), PORTAL, target, NORTH));
            }

            default -> print("Unrecognized Property: " + property);
        }

        Scenario.NUM_AGENTS = Scenario.NUM_GUARDS + Scenario.NUM_INTRUDERS;
    }

    /**
     * Converts the string of four corners that define area
     * into our Rectangle class which returns ints instead of doubles
     * @param value String of corner points ([0] = x1, [1] = y1, [2] = x2, [3] = y2)
     * @return Rectangle enclosing the area
     */
    private Rectangle parsePoints(String value) {
        int[] points = Arrays.stream(value.split(" "))
                .mapToInt(p -> (int) Double.parseDouble(p)).toArray();
        int width = Math.abs(points[2] - points[0]);    //not sure if Math.abs is necessary
        int height = Math.abs(points[3] - points[1]);
        return new Rectangle(points[0], points[1], width, height);
    }

    private void compileComponents() {
        COMPONENTS.addAll(List.of(targetArea, intruderSpawnArea, guardSpawnArea));
        COMPONENTS.addAll(walls);
        COMPONENTS.addAll(shadedAreas);
        COMPONENTS.addAll(portals);
    }

    private void initMap() {
        TileMap tileMap = new TileMap();
        for(int x = 0; x <= Scenario.WIDTH; x++)
            for (int y = 0; y <= Scenario.HEIGHT; y++)
                tileMap.setTile(x, y, new Tile(x, y));

        Scenario.TILE_MAP = tileMap;
    }

    private void setAdjacent(){
        TileMap t = TILE_MAP;
        for(int x = 0; x < Scenario.WIDTH; x++){
            for (int y = 0; y < Scenario.HEIGHT; y++) {
                if(t.getTile(x,y)!=null){
                    t.getTile(x,y).adjacent = createAdjacent(t,x,y);
                }
            }
        }
    }

    private Adjacent createAdjacent(TileMap t, int x, int y) {
        Tile NORTH = null, EAST = null, SOUTH = null, WEST = null, TARGET = null;
        if(y>0){
            NORTH = t.getTile(x,y-1);
        }

        if(x< WIDTH){
            EAST = t.getTile(x+1,y);
        }

        if(x< HEIGHT){
            SOUTH = t.getTile(x,y+1);
        }

        if(x>0){
            WEST = t.getTile(x-1,y);
        }

        if(t.getType(x,y)==PORTAL){
            Component portal = null;
            for (Component component : Scenario.portals) {
                if (component.getX() == x && component.getY() == y) {
                    portal = component;
                    break;
                }
            }
            if(portal != null){
                XY exit = portal.exit();
                TARGET = t.getTile(exit.x(),exit.y());
            }
        }
        return new Adjacent(NORTH,EAST,SOUTH,WEST,TARGET);
    }

    private void fillMap() {
        COMPONENTS.forEach(c -> {
            for(int x = c.area().getX(); x < c.area().getMaxIntX(); x++){
                for(int y = c.area().getY(); y < c.area().getMaxIntY(); y++) {
                    Scenario.TILE_MAP.setType(x, y, c.type());
                }
            }
        });

        Scenario.portals.forEach(p -> Scenario.TILE_MAP.setType(p.exit().x(), p.exit().y(), EXIT_PORTAL));
    }

    //private record MetaProperties(GameMode gameMode, String mapName, int width, int height){}
    //private record GuardProperties(int baseSpeed, int maxSpeed, int amount, int numMarkers){}
    //private record IntruderProperties(int baseSpeed, int maxSpeed, int amount){}
    //private record EnvironmentProperties(int viewDistance, int soundDistance, int smellDistance){}

}
