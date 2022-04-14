package group.seven.model.environment;

import group.seven.logic.geometric.Rectangle;
import group.seven.logic.geometric.XY;
import group.seven.utils.Config;
import javafx.util.Builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import static group.seven.enums.Cardinal.NORTH;
import static group.seven.enums.GameMode.EXPLORATION;
import static group.seven.enums.GameMode.SINGLE_INTRUDER;
import static group.seven.enums.TileType.*;
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
        parseFile(mapFile, scenario);
        initMap(scenario);
        fillMap(scenario);
        return scenario;
    }

    private void parseFile(File map, Scenario scenario) {
        try (Scanner sc = new Scanner(map)) {
            while (sc.hasNextLine()) {
                String[] property = sc.nextLine()                                //get next line in file
                        .replaceAll("([\\s]*(//)+)+(.)*", "")   //remove any comments in file line
                        .split(" = ");      //split line into its (key, value) pair as a String array

                parseValue(property[0], property[1], scenario); // key, value
            }
        } catch (FileNotFoundException e) {e.printStackTrace();}
    }

    private void parseValue(String property, String value, Scenario s) {
        switch (property) {
            //simple properties:
            case "name"                 -> s.NAME                   = value;
            case "gameMode"             -> s.GAME_MODE              = parseInt(value) == 1 ? SINGLE_INTRUDER : EXPLORATION;
            case "height"               -> s.HEIGHT                 = parseInt(value);    // the height of the map
            case "width"                -> s.WIDTH                  = parseInt(value);    // the width of the map
            case "numGuards"            -> s.NUM_GUARDS             = parseInt(value);    // the amount of guards
            case "numIntruders"         -> s.NUM_INTRUDERS          = parseInt(value);    // the amount of intruders
            case "baseSpeedIntruder"    -> s.INTRUDER_BASE_SPEED    = parseDouble(value); // the walking speed of intruders
            case "sprintSpeedIntruder"  -> s.INTRUDER_SPRINT_SPEED  = parseDouble(value); // the sprinting speed of the intruders
            case "baseSpeedGuard"       -> s.GUARD_BASE_SPEED       = parseDouble(value); // the base speed of the guard.
            case "sprintSpeedGuard"     -> s.GUARD_SPRINT_SPEED     = parseDouble(value); // the sprinting speed of the intruders
            case "distanceViewing"      -> s.VIEW_DISTANCE          = parseInt(value);
            case "numberMarkers"        -> s.NUM_MARKERS            = parseInt(value);
            case "smellingDistance"     -> s.SMELL_DISTANCE         = parseInt(value);

            // not sure if these should be included
            case "tileSize"             -> s.TILE_SIZE              = parseInt(value);
            case "scaling"              -> s.SCALING                = parseDouble(value);
            case "timeStep"             -> s.TIME_STEP = parseDouble(value);

            //regions:
            case "targetArea"           -> s.targetArea             = new Component(parsePoints(value), TARGET);
            case "spawnAreaIntruders"   -> s.intruderSpawnArea      = new Component(parsePoints(value), INTRUDER_SPAWN);
            case "spawnAreaGuards"      -> s.guardSpawnArea         = new Component(parsePoints(value), GUARD_SPAWN);

            case "wall"                 -> s.addWall(parsePoints(value));
            case "shaded"               -> s.addShaded(parsePoints(value));
            case "texture"              -> print("Texture not implemented yet");

            case "teleport" -> {
                String[] coords = value.split(" ");
                XY target = new XY(parseInt(coords[4]), parseInt(coords[5]));
                s.addPortals(new Portal(parsePoints(value), target, NORTH)); //TODO: randomly change their orientation
            }

            default -> print("Unrecognized Property: " + property);
        }

        s.NUM_AGENTS = s.NUM_GUARDS + s.NUM_INTRUDERS;
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

    private void initMap(Scenario s) {
        TileMap tileMap = new TileMap(s);
        for(int x = 0; x <= s.WIDTH; x++)
            for (int y = 0; y <= s.HEIGHT; y++)
                tileMap.setTile(x, y, new Tile(x, y));

        s.TILE_MAP = tileMap;
    }

    //TODO: needs to be remade to consider portals and such
    private void fillMap(Scenario s) {
        s.getStaticAreas().forEach(a -> {
            for(int x = a.area().getX(); x < a.area().getMaxIntX(); x++){
                for(int y = a.area().getY(); y < a.area().getMaxIntY(); y++) {
                    s.TILE_MAP.setType(x, y, a.type());
                }
            }
        });
    }

    //private record MetaProperties(GameMode gameMode, String mapName, int width, int height){}
    //private record GuardProperties(int baseSpeed, int maxSpeed, int amount, int numMarkers){}
    //private record IntruderProperties(int baseSpeed, int maxSpeed, int amount){}
    //private record EnvironmentProperties(int viewDistance, int soundDistance, int smellDistance){}

}
