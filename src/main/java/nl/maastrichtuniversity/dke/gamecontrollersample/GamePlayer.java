/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.maastrichtuniversity.dke.gamecontrollersample;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 *
 * @author joel
 */
public class GamePlayer extends FileWatcher {
    
    protected int time;
    protected Scenario scenario;
    
    // This is just the quick and dirty way to keep the game state what we at 
    // minimum need to run something; no way to do this eventually I'd say
    protected double[][] guardPositions;
    protected int[] guardStates;
    protected Scenario s;
    private final static Charset ENCODING = StandardCharsets.UTF_8;
    private final Path filePath;
    
    GamePlayer(Scenario s){
        super(s.getGameFile());
        scenario=s;
        time=0;
        filePath = Paths.get(scenario.getGameFile()); // get path
    }
    
    protected void writeGameFile(){
        try{
            FileWriter write = new FileWriter(scenario.getGameFile(),false);
            PrintWriter prtln = new PrintWriter(write);
            prtln.println("signal = 1"); // semaphore code   0-> game controller, 1-> guards, 2-> intruder
            prtln.println("scenario = " + scenario.getMapDoc());
                        for(int i=0;i<scenario.getNumGuards();i++){
                // write for each guard
                // guard = <number> <status> <x> <y> <angle> <countdownTimer>
                prtln.println("guard = "+String.valueOf(i)+" "+String.valueOf(guardStates[i])+" "+String.valueOf(guardPositions[i][0])+" "+String.valueOf(guardPositions[i][1])+" "+String.valueOf(guardPositions[i][2])+" "+String.valueOf(0.0));
            } 
            prtln.close();
        }
        catch(Exception e){
            // we ar in trouble
        }
    }
    
    public void setup(){
        // we assume that the game mode is 0. That is all for which there is a
        // minimum implementation
        guardPositions = scenario.spawnGuards();
        guardStates = new int[scenario.getNumGuards()]; // should be initialized to 0 by default
        writeGameFile();
    }
    
    protected void waitForTurn(){

    }
    
    public void start(){
        try{
            this.watchFile();
        }
        catch(Exception e){
            // ai
        }
    }
    
    @Override
    public void onModified(){
        boolean ourturn=true;
        try (Scanner scanner =  new Scanner(filePath, ENCODING.name())){
            while (scanner.hasNextLine()){
                if(!parseLine(scanner.nextLine())){
                    ourturn = false;
                    break;
                }
            }
        }
        catch(Exception e)
        {
        }
        if(ourturn){
            // process changes
            for(int i=0;i<guardStates.length;i++){
                // if the guard is walking
                // state 0 standing still
                // state 1 walking
                // state 2 sprinting
                // state 3 dazed/climbing
                // state 4 illegal move attempted
                // probably we need some more
                if(guardStates[i]==1){
                    double x = guardPositions[i][0]+Math.cos(guardPositions[i][2])*scenario.baseSpeedGuard*scenario.getScaling();
                    double y = guardPositions[i][1]+Math.sin(guardPositions[i][2])*scenario.baseSpeedGuard*scenario.getScaling();
                    // check whether he is gonna hit a wall
                    if(!scenario.inWall(x, y)){
                        guardPositions[i][0]=x;
                        guardPositions[i][1]=y;
                        System.out.println("Time: " + time + ", Guard: " + i + ", Position: (" + x + "," + y +")");
                    }
                    else{
                        guardStates[i]=4;
                        System.out.println("Time: " + time + ", Guard: " + i + " Illegal move");
                    }
                 }
            }
            writeGameFile();
        }
    }
    
    protected boolean parseLine(String line){
        //use a second Scanner to parse the content of each line 
        boolean ourturn = true;
        try(Scanner scanner = new Scanner(line)){
            scanner.useDelimiter("=");
            if (scanner.hasNext()&ourturn){
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
                    case "signal":
                        if("0".equals(value)){
                            // it is good to go
                            // if signal is not the first line; this will obviously break
                        }
                        else{
                            ourturn=false;
                        }    
                        break;
                    case "scenario":
                        // this shouldn't change
                        break;
                    case "guard":
                        //<number> <status> <x> <y> <angle> <countdownTimer>
                        int number = Integer.parseInt(items[0]); // id of guard
                        int status = Integer.parseInt(items[1]); // status of guard
                        double x = Double.parseDouble(items[2]); 
                        double y = Double.parseDouble(items[3]); 
                        double angl = Double.parseDouble(items[4]); 
                        double tm = Double.parseDouble(items[5]);
                        guardStates[number]=status;
                        guardPositions[number][0]=x;
                        guardPositions[number][1]=y;
                        guardPositions[number][2]=angl;
                        guardPositions[number][3]=tm;
                 }
            }
        }
        if(ourturn){
            return true;
        }
        else{
            return false;
        }
    }  
}
