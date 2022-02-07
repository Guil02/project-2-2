package org.group7.model;

import org.group7.model.component.Component;
import org.group7.model.component.playerComponents.Guard;
import org.group7.model.component.playerComponents.Intruder;
import org.group7.model.component.staticComponents.*;
import org.group7.geometric.Point;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Scenario {
    private static boolean printInput = false;

    protected double baseSpeedIntruder;
    protected double sprintSpeedIntruder;
    protected double baseSpeedGuard;
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

    List<Component> staticComponents;
    List<Wall> walls;
    List<ShadedArea> shadedAreas;
    List<Teleporter> teleporters;
    List<TargetArea> targetAreas;
    List<GuardSpawnArea> guardSpawnAreas;
    List<IntruderSpawnArea> intruderSpawnAreas;

    List<Component> playerComponents;
    List<Guard> guards;
    List<Intruder> intruders;

    public Scenario(String mapFile){
        this.filePathString = mapFile;

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

        filePath = Paths.get(filePathString);
        readMap();
    }

    private void readMap() {
        try(Scanner scanner = new Scanner(filePath, ENCODING.name())){
            while(scanner.hasNextLine()){
                parseLine(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseLine(String line) {
        try(Scanner scanner = new Scanner(line)) {
            scanner.useDelimiter("=");
            if (scanner.hasNext()) {
                String id = scanner.next();
                String value = scanner.next();

                id = id.trim();
                value = value.trim();

                if(printInput){
                    System.out.println("id: " + id);
                    System.out.println("value: " + value);
                }

                String[] items = value.split(" ");
                Point topLeft;
                Point bottomRight;
                Component component;
                switch (id) {
                    case "name":
                        name = value;
                        break;
                    case "gameMode":
                        gameMode = Integer.parseInt(value); // 0 is exploration, 1 evasion pursuit game
                        break;
                    case "scaling":
                        scaling = Double.parseDouble(value);
                        break;
                    case "height":
                        height = Integer.parseInt(value);
                        break;
                    case "width":
                        width = Integer.parseInt(value);
                        break;
                    case "numGuards":
                        numGuards = Integer.parseInt(value);
                        break;
                    case "numIntruders":
                        numIntruders = Integer.parseInt(value);
                        break;
                    case "baseSpeedIntruder":
                        baseSpeedIntruder = Double.parseDouble(value);
                        break;
                    case "sprintSpeedIntruder":
                        sprintSpeedIntruder = Double.parseDouble(value);
                        break;
                    case "baseSpeedGuard":
                        baseSpeedGuard = Double.parseDouble(value);
                        break;
                    case "targetArea":
                        topLeft = new Point(Integer.parseInt(items[0]), Integer.parseInt(items[1]));
                        bottomRight = new Point(Integer.parseInt(items[2]), Integer.parseInt(items[3]));
                        component = new TargetArea(topLeft, bottomRight);
                        staticComponents.add(component);
                        targetAreas.add((TargetArea) component);
                        break;
                    case "spawnAreaIntruders":
                        topLeft = new Point(Integer.parseInt(items[0]), Integer.parseInt(items[1]));
                        bottomRight = new Point(Integer.parseInt(items[2]), Integer.parseInt(items[3]));
                        component = new IntruderSpawnArea(topLeft, bottomRight);
                        staticComponents.add(component);
                        intruderSpawnAreas.add((IntruderSpawnArea) component);
                        break;
                    case "spawnAreaGuards":
                        topLeft = new Point(Integer.parseInt(items[0]), Integer.parseInt(items[1]));
                        bottomRight = new Point(Integer.parseInt(items[2]), Integer.parseInt(items[3]));
                        component = new GuardSpawnArea(topLeft, bottomRight);
                        staticComponents.add(component);
                        guardSpawnAreas.add((GuardSpawnArea) component);
                        break;
                    case "wall":
                        topLeft = new Point(Integer.parseInt(items[0]), Integer.parseInt(items[1]));
                        bottomRight = new Point(Integer.parseInt(items[2]), Integer.parseInt(items[3]));
                        component = new Wall(topLeft, bottomRight);
                        staticComponents.add(component);
                        walls.add((Wall) component);
                        break;
                    case "shaded":
                        topLeft = new Point(Integer.parseInt(items[0]), Integer.parseInt(items[1]));
                        bottomRight = new Point(Integer.parseInt(items[2]), Integer.parseInt(items[3]));
                        component = new ShadedArea(topLeft, bottomRight);
                        staticComponents.add(component);
                        shadedAreas.add((ShadedArea) component);
                        break;
                    case "teleport":
                        topLeft = new Point(Integer.parseInt(items[0]), Integer.parseInt(items[1]));
                        bottomRight = new Point(Integer.parseInt(items[2]), Integer.parseInt(items[3]));
                        Point target = new Point(Integer.parseInt(items[4]), Integer.parseInt(items[5]));
                        component = new Teleporter(topLeft, bottomRight, target);
                        staticComponents.add(component);
                        teleporters.add((Teleporter) component);
                        break;
                    case "texture":
                        // still to do. First the coordinates, then an int with texture type and then a double with orientation
                }
            }
        }
    }

    public List<Component> getStaticComponents() {
        return staticComponents;
    }

    public List<Component> getPlayerComponents() {
        return playerComponents;
    }

    public void spawnGuards(){
        int i = 0;
        Point topLeft = guardSpawnAreas.get(0).getTopLeft();
        Point bottomRight = guardSpawnAreas.get(0).getBottomRight();
        double dx = bottomRight.x-topLeft.x;
        double dy = bottomRight.y-topLeft.y;
        while(i<numGuards){
            Guard player = new Guard(topLeft.x+dx*Math.random(), topLeft.y+dy*Math.random());
            playerComponents.add(player);
            guards.add(player);
            i++;
        }
    }

    public  void spawnIntruder(){
        int i = 0;
        Point topLeft = guardSpawnAreas.get(0).getTopLeft();
        Point bottomRight = guardSpawnAreas.get(0).getBottomRight();
        double dx = bottomRight.x-topLeft.x;
        double dy = bottomRight.y-topLeft.y;
        while(i<numGuards){
            Intruder player = new Intruder(topLeft.x+dx*Math.random(), topLeft.y+dy*Math.random());
            playerComponents.add(player);
            intruders.add(player);
            i++;
        }
    }

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

    public static void setPrintInput(boolean printInput) {
        Scenario.printInput = printInput;
    }
}
