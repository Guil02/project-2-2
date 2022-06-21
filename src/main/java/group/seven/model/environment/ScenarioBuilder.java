package group.seven.model.environment;

import group.seven.enums.GameMode;
import group.seven.logic.algorithms.GeneticNeuralNetwork.GeneticAlgorithm;
import group.seven.logic.geometric.Rectangle;
import group.seven.logic.geometric.XY;
import group.seven.logic.simulation.CollisionHandler;
import group.seven.utils.Config;
import group.seven.utils.Methods;
import javafx.util.Builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static group.seven.enums.Cardinal.NORTH;
import static group.seven.enums.TileType.*;
import static group.seven.utils.Methods.print;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class ScenarioBuilder implements Builder<Scenario> {

    private File mapFile;
    private Scenario scenario;

    /**
     * Creates a new Builder the scenario given in the map file.
     * Does not build the Scenario class until build() is called
     *
     * @param mapFile map file for which to build a Scenario object from.
     */
    public ScenarioBuilder(File mapFile) {
        this.mapFile = mapFile;
    }

    /**
     * Initializes the Scenario with the contents from the default mapFile as defined
     * in the Config class
     */
    public ScenarioBuilder() {
//        this(new File(Config.DEFAULT_MAP_PATH));

        try {
            if (Config.GUI_ON) {

                this.mapFile = Paths.get(ScenarioBuilder.class.getClassLoader().getResource(Config.DEFAULT_MAP_PATH).toURI()).toFile();
            } else {

                this.mapFile = Paths.get(ScenarioBuilder.class.getClassLoader().getResource(Config.DEFAULT_MAP_PATH_GUI_OFF).toURI()).toFile();
            }
        } catch (URISyntaxException e) {

            e.printStackTrace();
        }

    }

    public ScenarioBuilder(boolean o) {
        this(new File(Config.DEFAULT_MAP_PATH));
    }

    /**
     * This method must be called in order to actually assemble the Scenario object
     *
     * @return a new Scenario object with all its fields populated by the map file
     */
    @Override
    public Scenario build() {
        //TODO: provide support for configuring file-based scenarios
        //TODO: provide support for building custom scenarios not based on map files
        scenario = new Scenario();
        parseFile(mapFile);
        compileComponents();
        initMap();
        fillMap();
        setAdjacent();
        if (!(Config.GA_ON && !Config.GUI_ON)) {
            scenario.setChromosome(Methods.readGAWeights(GeneticAlgorithm.fileName).get(0));
        }
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void parseValue(String property, String value) {
        switch (property) {
            //simple properties:
            case "name" -> scenario.NAME = value;
//            case "gameMode"             -> Scenario.GAURD_GAME_MODE = parseInt(value) == 1 ? ALL_INTRUDER_AT_TARGET : EXPLORATION;
            case "gameMode" -> {
                if (parseInt(value) > 2) scenario.INTRUDER_GAME_MODE = GameMode.values()[parseInt(value)];
                else scenario.GUARD_GAME_MODE = GameMode.values()[parseInt(value)];
            }
            case "height" -> scenario.HEIGHT = parseInt(value);    // the height of the map
            case "width" -> scenario.WIDTH = parseInt(value);    // the width of the map
            case "numGuards" -> scenario.NUM_GUARDS = parseInt(value);    // the amount of guards
            case "numIntruders" -> scenario.NUM_INTRUDERS = parseInt(value);    // the amount of intruders
            case "baseSpeedIntruder" -> scenario.INTRUDER_BASE_SPEED = parseDouble(value); // the walking speed of intruders
            case "sprintSpeedIntruder" -> scenario.INTRUDER_SPRINT_SPEED = parseDouble(value); // the sprinting speed of the intruders
            case "baseSpeedGuard" -> scenario.GUARD_BASE_SPEED = parseDouble(value); // the base speed of the guard.
            case "sprintSpeedGuard" -> scenario.GUARD_SPRINT_SPEED = parseDouble(value); // the sprinting speed of the intruders
            case "distanceViewing" -> scenario.VIEW_DISTANCE = parseInt(value);
            case "numberMarkers" -> scenario.NUM_MARKERS = parseInt(value);
            case "smellingDistance" -> scenario.SMELL_DISTANCE = parseInt(value);

            // not sure if these should be included
            case "tileSize" -> scenario.TILE_SIZE = parseInt(value);
            case "scaling" -> scenario.SCALING = parseDouble(value);
            case "timeStep" -> scenario.TIME_STEP = parseDouble(value);

            //regions:
            case "targetArea" -> scenario.targetArea = new Component(parsePoints(value), TARGET, null, null);
            case "spawnAreaIntruders" -> scenario.intruderSpawnArea = new Component(parsePoints(value), INTRUDER_SPAWN, null, null);
            case "spawnAreaGuards" -> scenario.guardSpawnArea = new Component(parsePoints(value), GUARD_SPAWN, null, null);

            case "wall" -> scenario.addWall(parsePoints(value));
            case "shaded" -> scenario.addShaded(parsePoints(value));
            case "texture" -> print("Texture not implemented yet");

            case "teleport" -> {
                String[] coords = value.split(" ");
                XY target = new XY(parseInt(coords[4]), parseInt(coords[5]));
                scenario.addPortals(new Component(parsePoints(value), PORTAL, target, NORTH));
            }

            default -> print("Unrecognized Property: " + property);
        }

        scenario.NUM_AGENTS = scenario.NUM_GUARDS + scenario.NUM_INTRUDERS;
    }

    /**
     * Converts the string of four corners that define area
     * into our Rectangle class which returns ints instead of doubles
     *
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
        scenario.COMPONENTS.addAll(List.of(scenario.targetArea, scenario.intruderSpawnArea, scenario.guardSpawnArea));
        scenario.COMPONENTS.addAll(scenario.walls);
        scenario.COMPONENTS.addAll(scenario.shadedAreas);
        scenario.COMPONENTS.addAll(scenario.portals);
    }

    private void initMap() {
        TileMap tileMap = new TileMap(scenario);
        scenario.TILE_MAP = tileMap;
        for (int x = 0; x <= scenario.WIDTH; x++)
            for (int y = 0; y <= scenario.HEIGHT; y++)
                tileMap.setTile(x, y, new Tile(x, y, scenario));

    }

    private void setAdjacent() {
        TileMap t = scenario.TILE_MAP;
        for (int x = 0; x < scenario.WIDTH; x++) {
            for (int y = 0; y < scenario.HEIGHT; y++) {
                if (t.getTile(x, y) != null) {
                    t.getTile(x, y).adjacent = createAdjacent(t, x, y);
                }
            }
        }
    }

    private Adjacent<Tile> createAdjacent(TileMap t, int x, int y) {
        Tile NORTH = null, EAST = null, SOUTH = null, WEST = null, TARGET = null;
        if (y > 0) {
            NORTH = t.getTile(x, y - 1);
        }

        if (x < scenario.WIDTH) {
            EAST = t.getTile(x + 1, y);
        }

        if (x < scenario.HEIGHT) {
            SOUTH = t.getTile(x, y + 1);
        }

        if (x > 0) {
            WEST = t.getTile(x - 1, y);
        }

        if (t.getType(x, y) == PORTAL) {
            XY xy = new XY(x, y);

            Component portal = CollisionHandler.getComponent(xy, PORTAL, scenario);
            if (portal != null) {
                XY exit = portal.exit();
                TARGET = t.getTile(exit.x(), exit.y());
            }
        }
        return new Adjacent<>(NORTH, EAST, SOUTH, WEST, TARGET);
    }

    private void fillMap() {
        scenario.COMPONENTS.forEach(c -> {
            for (int x = c.area().getX(); x < c.area().getMaxIntX(); x++) {
                for (int y = c.area().getY(); y < c.area().getMaxIntY(); y++) {
                    scenario.TILE_MAP.setType(x, y, c.type());
                }
            }
        });

        scenario.portals.forEach(p -> scenario.TILE_MAP.setType(p.exit().x(), p.exit().y(), EXIT_PORTAL));

        int totalGrids = 0;
        for (int i = 0; i <= scenario.WIDTH; i++)
            for (int j = 0; j <= scenario.HEIGHT; j++)
                if (scenario.TILE_MAP.map[i][j].getType() != WALL)
                    totalGrids++;

        scenario.TILE_MAP.NUM_TILES = totalGrids;

        print("Total Grids: " + totalGrids);
        print("Num Tiles: " + ((scenario.WIDTH + 1) * (scenario.HEIGHT + 1)));
    }

    //private record MetaProperties(GameMode gameMode, String mapName, int width, int height){}
    //private record GuardProperties(int baseSpeed, int maxSpeed, int amount, int numMarkers){}
    //private record IntruderProperties(int baseSpeed, int maxSpeed, int amount){}
    //private record EnvironmentProperties(int viewDistance, int soundDistance, int smellDistance){}

}
