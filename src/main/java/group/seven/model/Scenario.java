package group.seven.model;

import group.seven.enums.Tile;
import group.seven.logic.records.Records;
import group.seven.logic.records.XY;
import group.seven.model.agents.Agent;
import group.seven.model.environment.Cell;
import org.group7.geometric.Area;
import org.group7.geometric.Point;
import org.group7.geometric.Tuple;
import org.group7.model.Grid;
import org.group7.model.component.Component;
import org.group7.model.component.playerComponents.Guard;
import org.group7.model.component.playerComponents.Intruder;
import org.group7.model.component.playerComponents.PlayerComponent;
import org.group7.model.component.staticComponents.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static group.seven.enums.Tile.GUARD;
import static group.seven.enums.Tile.INTRUDER;

/**
 * This class describes the entire map and thus the current game. All the parameters are stored inside of it.
 */
public class Scenario {

    public static Scenario ENVIRONMENT;

    public static double baseSpeedIntruder;
    public static double sprintSpeedIntruder;
    public static double baseSpeedGuard;
    public static double sprintSpeedGuard;
    public static int numGuards;
    public static int numIntruders;
    public static int distanceViewing;
    public static int numberMarkers;
    public static int smellingDistance;

    public static int gameMode;
    public static String name = "map";
    public static int height; // y "rows"
    public static int width; // x "columns"

    protected Cell[][] map;

    protected double scaling;
    protected double timeStep;
    private boolean initialized = false;

    // all the static components stored separately and in 1 list.
    List<Tile> walls;
    List<Tile> shadedAreas;
    List<Tile> teleporters;
    List<XY> targetAreas;
    List<XY> guardSpawnAreas;
    List<XY> intruderSpawnAreas;

    public static List<Agent> agents;

    public Scenario(File mapFile){
        this();
        System.out.println("File: " + mapFile.getName());

        parseFile(mapFile);

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                int totalPlayers = numGuards + numIntruders;
                map[j][i].seenBy = new Records.Seen[totalPlayers];
                Grid.numGridsSeenBy = new int[totalPlayers];
                for(int k = 0; k < totalPlayers; k++){
                    map[j][i].seen.add(k,false);
                }
            }
        }

        ENVIRONMENT = this;
    }

    public Scenario() {
        // initialize all the lists
        walls = new LinkedList<>();
        shadedAreas = new LinkedList<>();
        teleporters = new LinkedList<>();
        targetAreas = new LinkedList<>();
        guardSpawnAreas = new ArrayList<>();
        intruderSpawnAreas = new ArrayList<>();
    }

    public void parseFile(File map){
        try {
            Scanner sc = new Scanner(map);
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(" = ");
                parseValue(line[0], line[1]); // key, value
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void parseValue(String key, String value) {
        value = value.split(" //")[0];
        switch (key) {
            //simple properties:
            case "name"                 -> name = value;
            case "gameMode"             -> gameMode = Integer.parseInt(value); // 0 is exploration, 1 evasion pursuit game
            case "scaling"              -> scaling = Double.parseDouble(value);
            case "height"               -> height = Integer.parseInt(value); // the height of the map
            case "width"                -> width = Integer.parseInt(value); // the width of the map
            case "numGuards"            -> numGuards = Integer.parseInt(value); // the amount of guards
            case "numIntruders"         -> numIntruders = Integer.parseInt(value); // the amount of intruders
            case "baseSpeedIntruder"    -> baseSpeedIntruder = Double.parseDouble(value); // the walking speed of intruders
            case "sprintSpeedIntruder"  -> sprintSpeedIntruder = Double.parseDouble(value); // the sprinting speed of the intruders
            case "baseSpeedGuard"       -> baseSpeedGuard = Double.parseDouble(value); // the base speed of the guard.
            case "sprintSpeedGuard"     -> sprintSpeedGuard = Double.parseDouble(value); // the sprinting speed of the intruders
            case "timeStep"             -> timeStep = Double.parseDouble(value);
            case "distanceViewing"      -> distanceViewing = Integer.parseInt(value);
            case "numberMarkers"        -> numberMarkers = Integer.parseInt(value);
            case "smellingDistance"     -> smellingDistance = Integer.parseInt(value);

            //regions:
            //lots of duplicate code here still, so still room for cleanup
            case "targetArea" -> {
                Tuple<Point, Point> points = parsePoints(value);
                targetAreas.add(component);
                addStaticComponent(component);
            }

            case "spawnAreaIntruders" -> {
                Tuple<Point, Point> points = parsePoints(value);
                intruderSpawnAreas.add(component);
                addStaticComponent(component);
            }

            case "spawnAreaGuards" -> {
                Tuple<Point, Point> points = parsePoints(value);
                guardSpawnAreas.add(component);
                addStaticComponent(component);
            }

            case "wall" -> {
                Tuple<Point, Point> points = parsePoints(value);
                walls.add(component);
                addStaticComponent(component);
            }

            case "shaded" -> {
                Tuple<Point, Point> points = parsePoints(value);
                shadedAreas.add(component);
                addStaticComponent(component);
            }

            case "teleport" -> {
                Tuple<Point, Point> points = parsePoints(value);
                String[] coords = value.split(" ");
                Point target = new Point(Double.parseDouble(coords[4]), Double.parseDouble(coords[5]));
                Teleporter component = new Teleporter(points.getA(), points.getB(), target, this, Double.parseDouble((coords[6])));
                staticComponents.add(component);
                teleporters.add(component);
                addStaticComponent(component);
            }

            case "texture" -> {
                //TODO
            }

            default -> {
                System.out.println("Unrecognized Property: " + key);
            }
        }
        if(width>0 && height>0 && !initialized){
            initializeMap();
            initialized = true;
        }
    }

    private Tuple<Point, Point> parsePoints(String value) {
        String[] coords = value.split(" ");
        Point topLeft = new Point(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]));
        Point bottomRight = new Point(Double.parseDouble(coords[2]), Double.parseDouble(coords[3]));
        return new Tuple<>(topLeft, bottomRight);
    }

    public void initializeMap(){
        map = new Cell[width][height];
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                map[i][j] = new Cell(i, j);
            }
        }
    }

    public void addStaticComponent(StaticComponent c){
        for(int i = (int) c.getTopLeft().x; i < c.getBottomRight().x; i++){
            for(int j = (int) c.getTopLeft().y; j < c.getBottomRight().y; j++){
                map[i][j].setStaticComponent(c);
            }
        }
    }

    /**
     * a method that spawns guards on random positions inside their spawn area. The guards are then added to the player components list.
     */
    public void spawnGuards(){
        int i = 0;
        Point topLeft = guardSpawnAreas.get(0).getTopLeft();
        Point bottomRight = guardSpawnAreas.get(0).getBottomRight();
        int dx = (int) (bottomRight.x - topLeft.x);
        int dy = (int) (bottomRight.y - topLeft.y);
        while (i < numGuards){
            Point point = new Point(topLeft.x +(int) (dx * Math.random()), topLeft.y + (int)(dy * Math.random()));
            if(map[(int) point.x][(int) point.y].getPlayerComponent()!=null){
                continue;
            }
            Guard player= new Guard(point,point.clone(),  Math.toRadians(0), this,baseSpeedGuard, distanceViewing , smellingDistance);
            if(i<1 && numGuards>1){
                player.setIgnoreTeleport(true);
            }
            playerComponents.add(player);
            guards.add(player);
            addPlayerComponent(player);
            i++;
        }
    }

    /**
     * a method that spawns intruders on random positions inside their spawn area. The intruders are then added to the player components list.
     */
    public void spawnIntruder(){
        int i = 0;
        Point topLeft = intruderSpawnAreas.get(0).getTopLeft();
        Point bottomRight = intruderSpawnAreas.get(0).getBottomRight();
        int dx = (int) (bottomRight.x - topLeft.x);
        int dy = (int) (bottomRight.y - topLeft.y);
        while(i<numIntruders){
            Point point = new Point(topLeft.x +(int) (dx * Math.random()), topLeft.y + (int)(dy * Math.random()));
            Agent player = new Intruder(point,point.clone(),  Math.random()*2*Math.PI, this,baseSpeedGuard, distanceViewing , smellingDistance);
            if(i<1 && numIntruders>1){
                player.setIgnoreTeleport(true);
            }
            agents.add(player);
            intruders.add(player);
            addPlayerComponent(player);
            i++;
        }
    }

    public static List<Agent.Guard> getGuards() {
        return agents.stream()
                .filter(agent -> agent.type == GUARD)
                .map(g -> (Agent.Guard)g).toList();
    }

    public static List<Agent.Intruder> getIntruders() {
        return agents.stream()
                .filter(agent -> agent.type == INTRUDER)
                .map(intr -> (Agent.Intruder)intr).toList();
    }

}