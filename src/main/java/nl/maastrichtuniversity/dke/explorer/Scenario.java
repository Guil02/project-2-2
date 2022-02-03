/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.maastrichtuniversity.dke.explorer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author joel
 */
public class Scenario {
    
    protected double baseSpeedIntruder;
    protected double sprintSpeedIntruder;
    protected double baseSpeedGuard;
    
    protected String mapDoc;
    protected int gameMode;
    private final Path filePath;
    private final static Charset ENCODING = StandardCharsets.UTF_8;
    
    protected String name;
    protected String gameFile;
    protected int mapHeight;
    protected int mapWidth;
    protected double scaling;
    protected int numIntruders;
    protected int numGuards;
    protected Area spawnAreaIntruders;
    protected Area spawnAreaGuards;
    protected Area targetArea;
    protected ArrayList<Area> walls;
    protected ArrayList<TelePortal> teleports;
    protected ArrayList<Area> shaded;
    
    public Scenario(String mapFile){
        // set parameters
        mapDoc=mapFile;
                
        // initialize variables
         walls = new ArrayList<>(); // create list of walls
         shaded = new ArrayList<>(); // create list of low-visability areas
         teleports = new ArrayList<>(); // create list of teleports e.g. stairs
        
        // read scenario
        filePath = Paths.get(mapDoc); // get path
        System.out.println(filePath);
        readMap();
    }
    
    public void readMap(){ 
        try (Scanner scanner =  new Scanner(filePath, ENCODING.name())){
            while (scanner.hasNextLine()){
                parseLine(scanner.nextLine());
            }
        }
        catch(Exception e)
        {
        }
    }
    
    /*
        
     */    
    protected void parseLine(String line){
        //use a second Scanner to parse the content of each line 
        try(Scanner scanner = new Scanner(line)){
            scanner.useDelimiter("=");
            if (scanner.hasNext()){
                // read id value pair
                String id = scanner.next();
                String value = scanner.next();
                // trim excess spaces
                value=value.trim();
                id=id.trim();
                // in case multiple parameters
                String[] items=value.split(" ");
                Area tmp;
                switch(id)
                {
                    case "name":
                        name = value;
                        break;
                    case "gameFile":
                        gameFile = value;
                        break;
                    case "gameMode":
                        gameMode = Integer.parseInt(value); // 0 is exploration, 1 evasion pursuit game
                        break;
                    case "scaling":
                        scaling = Double.parseDouble(value);
                        break;
                    case "height":
                        mapHeight = Integer.parseInt(value);
                        break;
                    case "width":
                        mapWidth = Integer.parseInt(value);
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
                        targetArea = new Area(Integer.parseInt(items[0]),Integer.parseInt(items[1]),Integer.parseInt(items[2]),Integer.parseInt(items[3]));
                        break;
                    case "spawnAreaIntruders":
                        spawnAreaIntruders = new Area(Integer.parseInt(items[0]),Integer.parseInt(items[1]),Integer.parseInt(items[2]),Integer.parseInt(items[3]));
                        break;
                    case "spawnAreaGuards":
                        spawnAreaGuards = new Area(Integer.parseInt(items[0]),Integer.parseInt(items[1]),Integer.parseInt(items[2]),Integer.parseInt(items[3]));
                        break;
                    case "wall":
                        tmp = new Area(Integer.parseInt(items[0]),Integer.parseInt(items[1]),Integer.parseInt(items[2]),Integer.parseInt(items[3]));
                        walls.add(tmp);
                        break;
                    case "shaded":
                        tmp = new Area(Integer.parseInt(items[0]),Integer.parseInt(items[1]),Integer.parseInt(items[2]),Integer.parseInt(items[3]));
                        shaded.add(tmp);
                        break;
                    case "teleport":
                        TelePortal teletmp = new TelePortal(Integer.parseInt(items[0]),Integer.parseInt(items[1]),Integer.parseInt(items[2]),Integer.parseInt(items[3]),Integer.parseInt(items[4]),Integer.parseInt(items[5]),Double.parseDouble(items[6]));
                        teleports.add(teletmp);
                        break;
                    case "texture":
                        // still to do. First the coordinates, then an int with texture type and then a double with orientation
                }
            }
        }
    }  
    
    public ArrayList<Area> getWalls(){
        return walls;
    }

    public boolean inWall(double x, double y){
        boolean tmp = false;
        for(int j=0;j<walls.size();j++){
            if(walls.get(j).isHit(x,y)){
                tmp=true;
            }
        }
        return(tmp);
    }
    
    public ArrayList<Area> getShaded(){
        return shaded;
    }
    
    public ArrayList<TelePortal> getTeleportals(){
        return teleports;
    }
    
    public Area getTargetArea(){
        return targetArea;
    }
    
    public double[][] spawnGuards(){
        double[][] tmp = new double[numGuards][4];
        double dx=spawnAreaGuards.rightBoundary-spawnAreaGuards.rightBoundary;
        double dy=spawnAreaGuards.topBoundary-spawnAreaGuards.bottomBoundary;
        for(int i=0; i<numGuards; i++){
            tmp[i][0]=spawnAreaGuards.leftBoundary+Math.random()*dx;
            tmp[i][1]=spawnAreaGuards.bottomBoundary+Math.random()*dy;
            tmp[i][2]=Math.random()*2*Math.PI;
        }
        return tmp;
    }

    public double[][] spawnIntruders(){
        double[][] tmp = new double[numIntruders][4];
        double dx=spawnAreaIntruders.rightBoundary-spawnAreaIntruders.rightBoundary;
        double dy=spawnAreaIntruders.topBoundary-spawnAreaIntruders.bottomBoundary;
        for(int i=0; i<numIntruders; i++){
            tmp[i][0]=spawnAreaIntruders.leftBoundary+Math.random()*dx;
            tmp[i][1]=spawnAreaIntruders.bottomBoundary+Math.random()*dy;
            tmp[i][2]=Math.random()*2*Math.PI;
        }
        return tmp;
    }
    
    public int getNumGuards(){
        return numGuards;
    }
    
    public String getGameFile(){
        return gameFile;
    }

    public String getMapDoc(){
        return mapDoc;
    }
    
    public double getScaling(){
        return scaling;
    }
}
