/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.maastrichtuniversity.dke.explorer;

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
public class ExGamePlayer extends FileWatcher {
    
    protected int time;
    protected Scenario scenario;
    
    // This is just the quick and dirty way to keep the game state what we at 
    // minimum need to run something; no way to do this eventually I'd say
    protected double[][] guardPositions;
    protected int[] guardStates;
    protected Scenario s;
    private final static Charset ENCODING = StandardCharsets.UTF_8;
    private final Path filePath;
    
    ExGamePlayer(Scenario s){
        super(s.getGameFile());
        scenario=s;
        time=0;
        filePath = Paths.get(scenario.getGameFile()); // get path
        guardPositions = new double[scenario.getNumGuards()][4];
        guardStates = new int[scenario.getNumGuards()];
    }
    
    protected void writeGameFile(){
        try{
            FileWriter write = new FileWriter(scenario.getGameFile(),false);
            PrintWriter prtln = new PrintWriter(write);
            prtln.println("signal = 0"); // semaphore code   0-> game controller, 1-> guards, 2-> intruder
            prtln.println("scenario = " + scenario.getMapDoc());
                        for(int i=0;i<scenario.getNumGuards();i++){
                // write for each guard
                // guard = <number> <status> <x> <y> <angle> <countdownTimer>
                prtln.println("guard = "+String.valueOf(i)+" "+String.valueOf(guardStates[i])+" "+String.valueOf(guardPositions[i][0])+" "+String.valueOf(guardPositions[i][1])+" "+String.valueOf(guardPositions[i][2])+" "+String.valueOf(guardPositions[i][3]));
            } 
            prtln.close();
            System.out.println("WHAAAAAAAT");
        }
        catch(Exception e){
            // we ar in trouble
        }
    }
    
    protected void waitForTurn(){

    }
    
    public void start(){
        // first try to move
        onModified();
        // then keep watching
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
            ourturn=false;
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
                if(guardStates[i]==4){
                    // if the guard made an illegal move, we change direction; otherwise just let him continue
                    guardPositions[i][2]=Math.random()*2*Math.PI;
                    guardStates[i]=1;
                }
                if(guardStates[i]==0){
                    // if the guard is standing still, let him/her move
                    guardPositions[i][2]=Math.random()*2*Math.PI;
                    guardStates[i]=1;
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
                        if("1".equals(value)){
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
        return ourturn;
    }  
}
