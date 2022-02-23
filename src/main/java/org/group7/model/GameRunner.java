package org.group7.model;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import org.group7.Main;
import org.group7.geometric.Area;
import org.group7.geometric.Point;
import org.group7.gui.ExplorationSim;
import org.group7.gui.GameScreen;
import org.group7.gui.Renderer;
import org.group7.model.component.Component;
import org.group7.model.component.ComponentEnum;
import org.group7.model.component.playerComponents.PlayerComponent;
import org.group7.utils.Methods;

import java.util.ArrayList;
import java.util.List;

public class GameRunner extends AnimationTimer {
    private List<State> states;
    private Scenario scenario;
    State currentState;
    int counter = 0;
    GameScreen gameScreen;

    public GameRunner(Scenario scenario) {
        this.scenario = scenario;
        this.states = new ArrayList<>();

        scenario.spawnGuards();
        scenario.spawnIntruder();
        currentState = new State(scenario.guards, scenario.intruders);
        states.add(currentState);

//        gameScreen = new GameScreen(new Canvas(scenario.width, scenario.height));
        Renderer renderer = new ExplorationSim(scenario.width, scenario.height);
        gameScreen = new GameScreen(renderer, scenario);
        Main.stage.setScene(new Scene(gameScreen));
        //Main.stage.setFullScreen(true);
        Main.stage.centerOnScreen();

        renderer.res = 1.05 * Math.max(scenario.width / renderer.getViewportBounds().getWidth(), scenario.height / renderer.getViewportBounds().getHeight());

    }

    @Override
    public void handle(long now) {
        //where update the game
        //createState();
        updatePlayers();

        gameScreen.render(scenario);

        counter++;
    }

    private void createState() {
        states.add(new State(scenario.guards, scenario.intruders));
    }

    private void updatePlayers(){
        for(int i = 0; i< scenario.guards.size(); i++){
            doMovement(scenario.guards.get(i));
        }

        for(int i = 0; i< scenario.intruders.size(); i++){
            doMovement(scenario.intruders.get(i));
        }
    }

    /**
     * method that does the movement for a provided player component. Is currently random can be modified to fit to the algorithms.
     * @param p a player component you want to move
     */
    private void doMovement(PlayerComponent p){
        double mul = 0.3;
        double sub = mul/2;
        double distance = getSpeed(p)*scenario.getTimeStep();
//        distance = 0.1;
        p.turn(Math.random()*mul-sub);
        if(checkWallCollision(p, distance)){
            if(Math.random()>0.5){
                p.turn(0.5*Math.PI);
            }
            else{
                p.turn(-0.5*Math.PI);
            }
        }
        else if(checkTeleporterCollision(p, distance)){
            doTeleport(p, distance);
        }
        else if(p.getComponentEnum() == ComponentEnum.INTRUDER && checkTargetCollision(p, distance)){
            stop(); //TODO implement game over screen
        }
        else{
            Area a = p.getArea().clone();
            p.move(distance);
            scenario.movePlayerMap(a, p.getArea(), p);
        }
    }

    public double getSpeed(PlayerComponent p){
        switch (p.getComponentEnum()){
            case GUARD -> {
                return scenario.baseSpeedGuard;
            }
            case INTRUDER -> {
                return scenario.baseSpeedIntruder;
            }
            default -> {
                return 0;
            }

        }
    }

    private boolean checkTargetCollision(PlayerComponent p, double distance) {
        // check if the agent collides with a wall.
        for(int i = 0; i<scenario.targetAreas.size(); i++){
            if(p.collision(scenario.targetAreas.get(i), distance)){
                return true;
            }
        }
        return false;
    }

    private void doTeleport(PlayerComponent p, double distance) {
        for(int i = 0; i<scenario.teleporters.size(); i++){
            if(p.collision(scenario.teleporters.get(i), distance)){
                Point target = scenario.teleporters.get(i).getTarget();
                p.setPosition(target.clone());
            }
        }
    }

    private boolean checkTeleporterCollision(PlayerComponent p, double distance) {
        for(int i = 0; i<scenario.teleporters.size(); i++){
            if(p.collision(scenario.teleporters.get(i), distance)){
                return true;
            }
        }
        return false;
    }

    private boolean checkWallCollision(PlayerComponent p, double distance) {
        for(int i = 0; i<scenario.walls.size(); i++){
            if(p.collision(scenario.walls.get(i), distance)){
                return true;
            }
        }
        return false;
    }

    private boolean checkCollision(PlayerComponent p, List<Component> list, double distance){
        for (Component component : list) {
            if (p.collision(component, distance)) {
                return true;
            }
        }
        return false;
    }
}
