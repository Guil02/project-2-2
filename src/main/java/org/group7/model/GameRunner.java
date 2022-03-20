package org.group7.model;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import org.group7.Main;
import org.group7.enums.Actions;
import org.group7.enums.Orientation;
import org.group7.geometric.Point;
import org.group7.gui.*;
import org.group7.model.algorithms.ActionTuple;
import org.group7.model.component.playerComponents.Guard;
import org.group7.model.component.playerComponents.PlayerComponent;
import org.group7.model.component.staticComponents.Teleporter;
import org.group7.utils.Config;
import static org.group7.enums.ComponentEnum.TELEPORTER;


import java.util.LinkedList;
import java.util.List;



public class GameRunner extends AnimationTimer {
    private List<State> states;
    private Scenario scenario;
    State currentState;
    GameScreen gameScreen;

    SimulationScreen display;

    double timeStep;
    double elapsedTimeStep; //total time
    int count = 0;

    public GameRunner(Scenario scenario) {
        this.scenario = scenario;
        this.states = new LinkedList<>();
        this.elapsedTimeStep = 0;
        this.timeStep = Config.TIME_STEP;

        if (scenario.numGuards > 0){
            scenario.spawnGuards();
        }

        if (scenario.numIntruders > 0){
            scenario.spawnIntruder();
        }

        display = new SimulationScreen();
        Main.stage.setScene(new Scene(display));
        Main.stage.centerOnScreen();

//        currentState = new State(scenario.guards, scenario.intruders);
//        states.add(currentState);

//        gameScreen = new GameScreen(new Canvas(scenario.width, scenario.height));
        /*
        Renderer renderer = new ExplorationSim(scenario.width, scenario.height);
        gameScreen = new GameScreen(renderer, scenario);
        Main.stage.setScene(new Scene(gameScreen));

        Main.stage.centerOnScreen();

        renderer.res = 1.05 * Math.max(scenario.width / renderer.getViewportBounds().getWidth(), scenario.height / renderer.getViewportBounds().getHeight());
        */

        setInitialVision();
        display.render();
    }

//    @Override
//    public void handle(long now) {
//        //where update the game
//        updatePlayers();
//        gameScreen.render(scenario);
//        elapsedTimeStep += timeStep;
////        elapsedTimeStep += 1;
////        if(elapsedTimeStep % 5 == 0) {
//        if (elapsedTimeStep>count*5){
//            count++;
//            double coverage = calculateCoverage();
//            System.out.print("\rTotal Coverage: " + coverage + " elapsed Time: " + elapsedTimeStep);
//            //TODO: ask SAM --> break break if coverage is > 80
//        }
//    }

    @Override
    public void handle(long now) {
        //where update the game
        updatePlayers();
        display.render();

        elapsedTimeStep += timeStep;

        if (elapsedTimeStep>count*5){
            count++;
            double coverage = calculateCoverage();
            //System.out.print("\rTotal Coverage: " + coverage + " elapsed Time: " + elapsedTimeStep);
            display.updateStats(elapsedTimeStep, coverage);
        }
    }

    private void createState() {
        states.add(new State(scenario.guards, scenario.intruders));
    }

    private void setInitialVision(){
        for(int i = 0; i< scenario.guards.size(); i++){
            this.scenario = scenario.guards.get(i).updateVision();
        }
        for(int i = 0; i< scenario.intruders.size(); i++){
            this.scenario = scenario.guards.get(i).updateVision();
        }
    }

    private void updatePlayers(){
        for(int i = 0; i< scenario.guards.size(); i++){
            Point startingPoint= scenario.guards.get(i).getCoordinates().clone();
            ActionTuple moveAction = scenario.guards.get(i).calculateMove();
            //if the action is a move forward we want to take into account our base speed and move the number of base speed units
            if (moveAction.getAction() == Actions.MOVE_FORWARD) {
                for (int j=0; j<moveAction.getDistance(); j++) {
                    //updates god grids and if agent visited grid also checks for vision validity ie.walls in the way
                    this.scenario = scenario.guards.get(i).updateVision();
                    //check collision with agent for the next move (if there is a next move) --> break loop
                    if (checkCollisionAgentWall(scenario.guards.get(i),moveAction.getDistance(),j)) {
                        break;
                    }
                    //check collision with teleporter for the current cell/grid and if so do teleportation after teleportation your turn is over --> break loop
                    if (checkCollisionTeleporter(scenario.guards.get(i))) {
                        //TODO: ask GIO --> move the agent to new position
                        Guard player = scenario.guards.get(i);
                        Point currentPosition = player.getCoordinates();
                        int x = (int)currentPosition.getX();
                        int y = (int)currentPosition.getY();

                        Grid grid = scenario.map[x][y];
                        Teleporter teleporter = (Teleporter) grid.getStaticComponent();
                        player.teleport(teleporter.getTarget());
                        updateGrid(currentPosition,teleporter.getTarget());
                        break;
                    }
                    //if no collision applyAction
                    scenario.guards.get(i).applyAction(moveAction.getAction());
                    updateGrid(startingPoint, scenario.guards.get(i).getCoordinates());
                }
            }else {// the agent turns
                //apply turn
                scenario.guards.get(i).applyAction(moveAction.getAction());
                //if the action is only a change in direction we still have to update our god grid and the players vision
                scenario.guards.get(i).updateVision();
            }
            //updateGrid(startingPoint, scenario.guards.get(i).getCoordinates());
        }

        for(int i = 0; i< scenario.intruders.size(); i++){
            Point startingPoint= scenario.intruders.get(i).getCoordinates().clone();
            ActionTuple moveAction = scenario.intruders.get(i).calculateMove();
            //if the action is a move forward we want to take into account our base speed and move the number of base speed units
            if (moveAction.getAction() == Actions.MOVE_FORWARD) {
                for (int j=0; j<moveAction.getDistance(); j++) {
                    //updates god grids and if agent visited grid also checks for vision validity ie.walls in the way
                    this.scenario = scenario.intruders.get(i).updateVision();
                    //check collision with agent for the next move (if there is a next move) --> break loop
                    if (checkCollisionAgentWall(scenario.intruders.get(i),moveAction.getDistance(),j)) {
                        break;
                    }
                    //check collision with teleporter for the current cell/grid and if so do teleportation after teleportation your turn is over --> break loop
                    if (checkCollisionTeleporter(scenario.intruders.get(i))) {
                        //TODO: ask GIO --> move the agent to new position
                        break;
                    }
                    //if no collision applyAction
                    scenario.intruders.get(i).applyAction(moveAction.getAction());
                    updateGrid(startingPoint, scenario.intruders.get(i).getCoordinates());
                }
            }else {// the agent turns
                //apply turn
                scenario.intruders.get(i).applyAction(moveAction.getAction());
                //if the action is only a change in direction we still have to update our god grid and the players vision
                scenario.intruders.get(i).updateVision();
            }
            //updateGrid(startingPoint, scenario.intruders.get(i).getCoordinates());
        }

    }

    private void updateGrid(Point startingPoint, Point target) {

        int initialX = (int) startingPoint.x;
        int initialY = (int) startingPoint.y;
        int finalX = (int) target.x;
        int finalY = (int) target.y;
        scenario.map[finalX][finalY].setPlayerComponent(scenario.map[initialX][initialY].getPlayerComponent());
        scenario.map[initialX][initialY].setPlayerComponent(null);
    }


    /**
     * method that does the random movement for a provided player component. Is currently random can be modified to fit to the algorithms.
     * @param p a player component you want to move
     * @param type_movement 0= dont turn, 1 = turn right, 2= turn left
     */
    private void doRandomMovement(PlayerComponent p, Actions type_movement){

    }

    public boolean checkCollisionAgentWall(PlayerComponent player, int moveForwardDistance, int step) {
        if (step+1 < moveForwardDistance) {
            Point currentPosition = player.getCoordinates();
            int x = (int)currentPosition.getX();
            int y = (int)currentPosition.getY();
            Orientation orientation = player.getOrientation();
            switch (orientation) {
                case UP -> {
                    if (y-1 >= 0) {
                        if (scenario.map[x][y-1].getPlayerComponent() != null || scenario.map[x][y-1].getType().isObstacle()) {
                            return true;
                        }
                    }
                    return false;
                }
                case DOWN -> {
                    if (y+1 <= scenario.getHeight()) {
                        if (scenario.map[x][y+1].getPlayerComponent() != null || scenario.map[x][y+1].getType().isObstacle()) {
                            return true;
                        }
                    }
                    return false;
                }
                case LEFT -> {
                    if (x-1 >= 0) {
                        if (scenario.map[x-1][y].getPlayerComponent() != null || scenario.map[x-1][y].getType().isObstacle()) {
                            return true;
                        }
                    }
                    return false;
                }
                case RIGHT -> {
                    if (x+1 <= scenario.getWidth()) {
                        if (scenario.map[x+1][y].getPlayerComponent() != null || scenario.map[x+1][y].getType().isObstacle()) {
                            return true;
                        }
                    }
                    return false;
                }
            }
        }
        return false;
    }

    public boolean checkCollisionTeleporter(PlayerComponent player) {
        Point currentPosition = player.getCoordinates();
        int x = (int)currentPosition.getX();
        int y = (int)currentPosition.getY();
        if (scenario.map[x][y].getStaticCompE() == TELEPORTER) {
            return true;
        }
        return false;
    }



    public double calculateCoverage() {
        int seenGrids = 0;
        for (int i=0; i<scenario.getWidth();i++) {
            for (int j=0; j<scenario.getHeight();j++) {
                if(scenario.map[i][j].explored) {
                    seenGrids++;
                }
            }
        }
        int totalSize = scenario.getWidth()*scenario.getHeight();
        double d1 = seenGrids;
        double d2 = totalSize;
//        return (seenGrids/totalSize)*100;
        return (d1/d2)*100;
    }

}

/* OLD CODE - CLEANUP
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
            if (p.collision(component, distance) && !component.equals(p)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkSoundCollision(PlayerComponent p) {
        p.setMovingSound();
        Area soundArea = p.getMovingSound();
        for (Guard guard: currentState.getGuards()) {
            guard.setMovingSound();
            if (soundArea.isHit(guard.getX(), guard.getY())) {
                //make sure agents don't go nuts when they hear themselves
                if (p.getCoordinates() != guard.getCoordinates())
                    return true;
            }
        }
        for (Intruder intruder: currentState.getIntruders()) {
            intruder.setMovingSound();
            if (soundArea.isHit(intruder.getX(), intruder.getY())) {
                //make sure agents don't go nuts when they hear themselves
                if (p.getCoordinates() != intruder.getCoordinates())
                    return true;
            }
        }
        return false;
    }
 */
