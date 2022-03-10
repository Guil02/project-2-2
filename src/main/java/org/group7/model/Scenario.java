package org.group7.model;

import org.group7.geometric.Area;
import org.group7.geometric.Tuple;
import org.group7.model.component.Component;
import org.group7.model.component.playerComponents.Guard;
import org.group7.model.component.playerComponents.Intruder;
import org.group7.model.component.playerComponents.PlayerComponent;
import org.group7.model.component.staticComponents.*;
import org.group7.geometric.Point;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class describes the entire map and thus the current game. All the parameters are stored inside of it.
 */
public class Scenario {

    private static boolean printInput = false;

    protected double baseSpeedIntruder;
    protected double sprintSpeedIntruder;
    protected double baseSpeedGuard;
    protected double sprintSpeedGuard;
    protected int numGuards;
    protected int numIntruders;

    protected String filePathString;
    protected Path filePath;
    private final static Charset ENCODING = StandardCharsets.UTF_8;

    protected int gameMode;
    protected String name;
    protected int height;
    protected int width;
    protected double scaling;
    protected double timeStep;
    protected Grid[][] map;
    private boolean initialized = false;

    // all the static components stored separately and in 1 list.
    List<Component> staticComponents;
    List<Wall> walls;
    List<ShadedArea> shadedAreas;
    List<Teleporter> teleporters;
    List<TargetArea> targetAreas;
    List<GuardSpawnArea> guardSpawnAreas;
    List<IntruderSpawnArea> intruderSpawnAreas;

    // all the player components stored separately and in 1 list.
    List<Component> playerComponents;
    List<Guard> guards;
    List<Intruder> intruders;

    public Scenario(File mapFile){
        this();
        System.out.println("File: " + mapFile.getName());
        parseFile(mapFile);
        System.out.println(staticComponents);
        for(int i = 0; i<height; i++){
            for(int j = 0; j<width; j++){
                System.out.print(map[j][i]);
            }
            System.out.println();
        }
    }

    public Scenario() {
        // initialize all the lists
        staticComponents = new ArrayList<>();
        playerComponents = new ArrayList<>();
        walls = new ArrayList<>();
        shadedAreas = new ArrayList<>();
        teleporters = new ArrayList<>();
        targetAreas = new ArrayList<>();
        guardSpawnAreas = new ArrayList<>();
        intruderSpawnAreas = new ArrayList<>();
        guards = new ArrayList<>();
        intruders = new ArrayList<>();
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

            //regions:
            //lots of duplicate code here still, so still room for cleanup
            case "targetArea" -> {
                Tuple<Point, Point> points = parsePoints(value);
                TargetArea component = new TargetArea(points.getA(), points.getB());
                staticComponents.add(component);
                targetAreas.add(component);
                addStaticComponent(component);
            }

            case "spawnAreaIntruders" -> {
                Tuple<Point, Point> points = parsePoints(value);
                IntruderSpawnArea component= new IntruderSpawnArea(points.getA(), points.getB());
                staticComponents.add(component);
                intruderSpawnAreas.add(component);
                addStaticComponent(component);
            }

            case "spawnAreaGuards" -> {
                Tuple<Point, Point> points = parsePoints(value);
                GuardSpawnArea component= new GuardSpawnArea(points.getA(), points.getB());
                staticComponents.add(component);
                guardSpawnAreas.add(component);
                addStaticComponent(component);
            }

            case "wall" -> {
                Tuple<Point, Point> points = parsePoints(value);
                Wall component= new Wall(points.getA(), points.getB());
                staticComponents.add(component);
                walls.add(component);
                addStaticComponent(component);
            }

            case "shaded" -> {
                Tuple<Point, Point> points = parsePoints(value);
                ShadedArea component= new ShadedArea(points.getA(), points.getB());
                staticComponents.add(component);
                shadedAreas.add(component);
                addStaticComponent(component);
            }

            case "teleport" -> {
                Tuple<Point, Point> points = parsePoints(value);
                String[] coords = value.split(" ");
                Point target = new Point(Double.parseDouble(coords[4]), Double.parseDouble(coords[5]));
                Teleporter component = new Teleporter(points.getA(), points.getB(), target);
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

    public List<Component> getStaticComponents() {
        return staticComponents;
    }

    public List<Component> getPlayerComponents() {
        return playerComponents;
    }

    /**
     * a method that spawns guards on random positions inside their spawn area. The guards are then added to the player components list.
     */
    public void spawnGuards(){
        int i = 0;
        Point topLeft = guardSpawnAreas.get(0).getTopLeft();
        Point bottomRight = guardSpawnAreas.get(0).getBottomRight();
        double dx = bottomRight.x - topLeft.x;
        double dy = bottomRight.y - topLeft.y;
        while (i < numGuards){
            Point point = new Point(topLeft.x + dx * Math.random(), topLeft.y + dy * Math.random());
//            Guard player = new Guard(point, point.clone(), (int) (Math.random() * 360));
            Guard player = new Guard(point, point.clone(), Math.random()*2*Math.PI);
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
        double dx = bottomRight.x-topLeft.x;
        double dy = bottomRight.y-topLeft.y;
        while(i<numIntruders){
            Point point = new Point(topLeft.x+dx*Math.random(), topLeft.y+dy*Math.random());
//            Intruder player = new Intruder(point, point.clone(), (int) (Math.random()*360));
            Intruder player = new Intruder(point, point.clone(), Math.random()*2*Math.PI);
            playerComponents.add(player);
            intruders.add(player);
            addPlayerComponent(player);
            i++;
        }
    }

    public void initializeMap(){
        map = new Grid[width][height];
        for(int i = 0; i<width; i++){
            for(int j = 0; j<height; j++){
                map[i][j] = new Grid(i,j);
            }
        }
    }

    public void movePlayerMap(Area a, Area b, PlayerComponent p) {
        int x = (int) a.getTopLeft().x;
        int y = (int) a.getTopLeft().y;

        int tarX = (int) b.getTopLeft().x;
        int tarY = (int) b.getTopLeft().y;

        map[tarX][tarY].setPlayerComponent(p);
        map[x][y].setPlayerComponent(null);
    }

    public void addStaticComponent(StaticComponent c){
        for(int i = (int) c.getTopLeft().x; i<c.getBottomRight().x; i++){
            for(int j = (int) c.getTopLeft().y; j<c.getBottomRight().y; j++){
                map[i][j].setStaticComponent(c);
            }
        }
    }
    public void addPlayerComponent(PlayerComponent c){
        Area a = c.getArea();
        for(int i = (int) a.getTopLeft().x; i<a.getBottomRight().x; i++){
            for(int j = (int) a.getTopLeft().y; j<a.getBottomRight().y; j++){
                map[i][j].setPlayerComponent(c);
            }
        }
    }
    // some getters for some of the private variables

    public int getNumGuards() {
        return numGuards;
    }

    public int getNumIntruders() {
        return numIntruders;
    }

    public Path getFilePath() {
        return filePath;
    }

    public double getScaling() {
        return scaling;
    }

    public double getTimeStep() {
        return timeStep;
    }

    /**
     * this method change the boolean variable that decides whether a print should be done for each line in the parseLine method.
     * @param printInp a boolean for whether a print should be done or not
     */
    public static void setPrintInput(boolean printInp) {
        printInput = printInp;
    }

    public String getName() {
        return name;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<ShadedArea> getShadedAreas() {
        return shadedAreas;
    }

    public List<Teleporter> getTeleporters() {
        return teleporters;
    }

    public List<TargetArea> getTargetAreas() {
        return targetAreas;
    }

    public List<GuardSpawnArea> getGuardSpawnAreas() {
        return guardSpawnAreas;
    }

    public List<IntruderSpawnArea> getIntruderSpawnAreas() {
        return intruderSpawnAreas;
    }

    public List<Guard> getGuards() {
        return guards;
    }

    public List<Intruder> getIntruders() {
        return intruders;
    }

    public int getGameMode() {
        return gameMode;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}

























//package org.group7.model;
//
//import org.group7.model.component.Component;
//import org.group7.model.component.playerComponents.Guard;
//import org.group7.model.component.playerComponents.Intruder;
//import org.group7.model.component.staticComponents.*;
//import org.group7.geometric.Point;
//
//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
///**
// * This class describes the entire map and thus the current game. All the parameters are stored inside of it.
// */
//public class Scenario {
//    private static boolean printInput = false;
//
//    protected double baseSpeedIntruder;
//    protected double sprintSpeedIntruder;
//    protected double baseSpeedGuard;
//    protected int numGuards;
//    protected int numIntruders;
//
//    protected String filePathString;
//    protected Path filePath;
//    private final static Charset ENCODING = StandardCharsets.UTF_8;
//
//    protected int gameMode;
//    protected String name;
//    protected int height;
//    protected int width;
//    protected double scaling;
//
//
//    // all the static components stored separately and in 1 list.
//    List<Component> staticComponents;
//    List<Wall> walls;
//    List<ShadedArea> shadedAreas;
//    List<Teleporter> teleporters;
//    List<TargetArea> targetAreas;
//    List<GuardSpawnArea> guardSpawnAreas;
//    List<IntruderSpawnArea> intruderSpawnAreas;
//
//    // all the player components stored separately and in 1 list.
//    List<Component> playerComponents;
//    List<Guard> guards;
//    List<Intruder> intruders;
//
//    public Scenario(String mapFile){
//        this.filePathString = mapFile;
//
//        // initialize all the lists
//        staticComponents = new ArrayList<>();
//        playerComponents = new ArrayList<>();
//        walls = new ArrayList<>();
//        shadedAreas = new ArrayList<>();
//        teleporters = new ArrayList<>();
//        targetAreas = new ArrayList<>();
//        guardSpawnAreas = new ArrayList<>();
//        intruderSpawnAreas = new ArrayList<>();
//        guards = new ArrayList<>();
//        intruders = new ArrayList<>();
//
//        filePath = Paths.get(filePathString);
//        //filePath = Path.of(getClass().getResource(filePathString).getFile());
//
//        // read in the map and initialize the game.
//        readMap();
//        System.out.println(staticComponents);
//    }
//
//    /**
//     * This method reads from the file that we have given and constructs the entire map.
//     */
//    private void readMap() {
//        // create a scanner and iterate ove all the lines in the file.
////        try {
////            BufferedReader reader = new BufferedReader(new FileReader(filePathString));
////            String line = reader.readLine();
////            while (line!=null){
////                parseLine(line);
////                line = reader.readLine();
////            }
////        } catch (FileNotFoundException e) {
////            e.printStackTrace();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//        try(Scanner scanner = new Scanner(filePath, ENCODING.name())){
//            while(scanner.hasNextLine()){
//                parseLine(scanner.nextLine());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * this method checks the current line and creates the object related to it in the scenario.
//     * @param line the line that is currently being read.
//     */
//    private void parseLine(String line) {
//        try(Scanner scanner = new Scanner(line)) {
//            scanner.useDelimiter("=");
//            if (scanner.hasNext()) {
//                // separate the id and the value from the line.
//                String id = scanner.next();
//                String value = scanner.next();
//
//                id = id.trim();
//                value = value.trim();
//
//                if(printInput){
//                    System.out.println("id: " + id);
//                    System.out.println("value: " + value);
//                }
//
//                // if there are multiple values then split them into separate parts in an array
//                String[] items = value.split(" ");
//
//                // create some useful items.
//                Point topLeft;
//                Point bottomRight;
//                Component component;
//
//                // create the object in the scenario based upon the id provided
//                switch (id) {
//                    case "name":
//                        name = value;
//                        break;
//                    case "gameMode":
//                        gameMode = Integer.parseInt(value); // 0 is exploration, 1 evasion pursuit game
//                        break;
//                    case "scaling":
//                        scaling = Double.parseDouble(value);
//                        break;
//                    case "height":
//                        height = Integer.parseInt(value); // the height of the map
//                        break;
//                    case "width":
//                        width = Integer.parseInt(value); // the width of the map
//                        break;
//                    case "numGuards":
//                        numGuards = Integer.parseInt(value); // the amount of guards
//                        break;
//                    case "numIntruders":
//                        numIntruders = Integer.parseInt(value); // the amount of intruders
//                        break;
//                    case "baseSpeedIntruder":
//                        baseSpeedIntruder = Double.parseDouble(value); // the walking speed of intruders
//                        break;
//                    case "sprintSpeedIntruder":
//                        sprintSpeedIntruder = Double.parseDouble(value); // the sprinting speed of the intruders
//                        break;
//                    case "baseSpeedGuard":
//                        baseSpeedGuard = Double.parseDouble(value); // the base speed of the guard.
//                        break;
//                    case "targetArea": // the area to which the intruders need to get.
//                        topLeft = new Point(Integer.parseInt(items[0]), Integer.parseInt(items[1]));
//                        bottomRight = new Point(Integer.parseInt(items[2]), Integer.parseInt(items[3]));
//                        component = new TargetArea(topLeft, bottomRight);
//                        staticComponents.add(component);
//                        targetAreas.add((TargetArea) component);
//                        break;
//                    case "spawnAreaIntruders": // the area where the intruders spawn onto the map
//                        topLeft = new Point(Integer.parseInt(items[0]), Integer.parseInt(items[1]));
//                        bottomRight = new Point(Integer.parseInt(items[2]), Integer.parseInt(items[3]));
//                        component = new IntruderSpawnArea(topLeft, bottomRight);
//                        staticComponents.add(component);
//                        intruderSpawnAreas.add((IntruderSpawnArea) component);
//                        break;
//                    case "spawnAreaGuards": // the area where the guard spawn onto the map
//                        topLeft = new Point(Integer.parseInt(items[0]), Integer.parseInt(items[1]));
//                        bottomRight = new Point(Integer.parseInt(items[2]), Integer.parseInt(items[3]));
//                        component = new GuardSpawnArea(topLeft, bottomRight);
//                        staticComponents.add(component);
//                        guardSpawnAreas.add((GuardSpawnArea) component);
//                        break;
//                    case "wall": // the walls on the map
//                        topLeft = new Point(Integer.parseInt(items[0]), Integer.parseInt(items[1]));
//                        bottomRight = new Point(Integer.parseInt(items[2]), Integer.parseInt(items[3]));
//                        component = new Wall(topLeft, bottomRight);
//                        staticComponents.add(component);
//                        walls.add((Wall) component);
//                        break;
//                    case "shaded": // the shaded areas on the map where intruders/guards could hide
//                        topLeft = new Point(Integer.parseInt(items[0]), Integer.parseInt(items[1]));
//                        bottomRight = new Point(Integer.parseInt(items[2]), Integer.parseInt(items[3]));
//                        component = new ShadedArea(topLeft, bottomRight);
//                        staticComponents.add(component);
//                        shadedAreas.add((ShadedArea) component);
//                        break;
//                    case "teleport": // the teleporter
//                        topLeft = new Point(Integer.parseInt(items[0]), Integer.parseInt(items[1]));
//                        bottomRight = new Point(Integer.parseInt(items[2]), Integer.parseInt(items[3]));
//                        Point target = new Point(Integer.parseInt(items[4]), Integer.parseInt(items[5]));
//                        component = new Teleporter(topLeft, bottomRight, target);
//                        staticComponents.add(component);
//                        teleporters.add((Teleporter) component);
//                        break;
//                    case "texture":
//                        //TODO still to do. First the coordinates, then an int with texture type and then a double with orientation
//                }
//            }
//        }
//    }
//
//    public List<Component> getStaticComponents() {
//        return staticComponents;
//    }
//
//    public List<Component> getPlayerComponents() {
//        return playerComponents;
//    }
//
//    /**
//     * a method that spawns guards on random positions inside their spawn area. The guards are then added to the player components list.
//     */
//    public void spawnGuards(){
//        int i = 0;
//        Point topLeft = guardSpawnAreas.get(0).getTopLeft();
//        Point bottomRight = guardSpawnAreas.get(0).getBottomRight();
//        double dx = bottomRight.x-topLeft.x;
//        double dy = bottomRight.y-topLeft.y;
//        while(i<numGuards){
//            Point point = new Point(topLeft.x+dx*Math.random(), topLeft.y+dy*Math.random());
//            Guard player = new Guard(point, point.clone(), (int) (Math.random()*360));
//            playerComponents.add(player);
//            guards.add(player);
//            i++;
//        }
//    }
//
//    /**
//     * a method that spawns intruders on random positions inside their spawn area. The intruders are then added to the player components list.
//     */
//    public  void spawnIntruder(){
//        int i = 0;
//        Point topLeft = guardSpawnAreas.get(0).getTopLeft();
//        Point bottomRight = guardSpawnAreas.get(0).getBottomRight();
//        double dx = bottomRight.x-topLeft.x;
//        double dy = bottomRight.y-topLeft.y;
//        while(i<numGuards){
//            Point point = new Point(topLeft.x+dx*Math.random(), topLeft.y+dy*Math.random());
//            Intruder player = new Intruder(point, point.clone(), (int) (Math.random()*360));
//            playerComponents.add(player);
//            intruders.add(player);
//            i++;
//        }
//    }
//
//    // some getters for some of the private variables
//    public int getNumGuards() {
//        return numGuards;
//    }
//
//    public int getNumIntruders() {
//        return numIntruders;
//    }
//
//    public Path getFilePath() {
//        return filePath;
//    }
//
//    public double getScaling() {
//        return scaling;
//    }
//
//    /**
//     * this method change the boolean variable that decides whether a print should be done for each line in the parseLine method.
//     * @param printInput a boolean for whether a print should be done or not
//     */
//    public static void setPrintInput(boolean printInput) {
//        Scenario.printInput = printInput;
//    }
//
//
//    public String getName() {
//        return name;
//    }
//
//    public List<Wall> getWalls() {
//        return walls;
//    }
//
//    public List<ShadedArea> getShadedAreas() {
//        return shadedAreas;
//    }
//
//    public List<Teleporter> getTeleporters() {
//        return teleporters;
//    }
//
//    public List<TargetArea> getTargetAreas() {
//        return targetAreas;
//    }
//
//    public List<GuardSpawnArea> getGuardSpawnAreas() {
//        return guardSpawnAreas;
//    }
//
//    public List<IntruderSpawnArea> getIntruderSpawnAreas() {
//        return intruderSpawnAreas;
//    }
//
//    public List<Guard> getGuards() {
//        return guards;
//    }
//
//    public List<Intruder> getIntruders() {
//        return intruders;
//    }
//}
