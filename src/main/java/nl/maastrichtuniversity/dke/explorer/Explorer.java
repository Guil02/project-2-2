/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.maastrichtuniversity.dke.explorer;

/**
 *
 * @author joel
 */
public class Explorer {
    
    protected String mapDoc;
    protected Scenario scenario;
    
    ExGamePlayer p;   
    
    public static void main(String[] args){
        // the mapscenario should be passed as a parameter
        String mapD="/Users/joel/surfdrive/Education/Project2-2/2020/GameControllerSample/testmap.txt";
        Explorer game = new Explorer(mapD);
        //game.writeGameFile();
        game.p.start();
    }

    public Explorer(String scn){
        mapDoc=scn;
        scenario = new Scenario(mapDoc);
        p = new ExGamePlayer(scenario);
    }
}
