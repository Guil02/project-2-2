package org.group7.model;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import org.group7.Main;
import org.group7.enums.Actions;
import org.group7.enums.Orientation;
import org.group7.geometric.Point;
import org.group7.gui.*;
import org.group7.model.algorithms.ActionTuple;
import org.group7.model.component.playerComponents.PlayerComponent;
import org.group7.model.component.staticComponents.Teleporter;
import org.group7.utils.Config;
import static org.group7.enums.ComponentEnum.TELEPORTER;
import static org.group7.enums.ComponentEnum.WALL;


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
        for(PlayerComponent player : scenario.playerComponents){
            Point startingPoint = player.getCoordinates().clone();
            ActionTuple moveAction = player.calculateMove();
            //if the action is a move forward we want to take into account our base speed and move the number of base speed units
            if(moveAction.getAction() == Actions.MOVE_FORWARD){
                for(int i = 0; i<moveAction.getDistance(); i++){
                    Point targetPoint = startingPoint.clone();
                    movePoint(player,targetPoint,i+1);
                    if(i<player.getBaseSpeed() && !isOutOfBounds(targetPoint)){
                        if(wallCollision(targetPoint)){
                            break;
                        }
                        else if(playerCollision(player, targetPoint)){
                            break;
                        }
                        else if(teleporterCollision(targetPoint)){
                            Teleporter teleporter = (Teleporter) scenario.map[(int) targetPoint.getX()][(int) targetPoint.getY()].getStaticComponent();
                            Point teleportTarget = teleporter.getTarget();
                            player.teleport(teleportTarget);
                            player.updateVision();
                            updateGrid(startingPoint, targetPoint);
                            break;
                        }
                        else{
                            player.moveAgentForward(moveAction.getAction());
                            player.updateVision();
                            updateGrid(startingPoint, targetPoint);
                        }
                    }
                    else{
                        break;
                    }
                }
            }
            else{
                player.applyAction(moveAction.getAction());
                player.updateVision();
            }
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

    private boolean teleporterCollision(Point targetPoint) {
        return scenario.map[(int) targetPoint.getX()][(int) targetPoint.getY()].getStaticCompE()==TELEPORTER;
    }

    private boolean playerCollision(PlayerComponent player, Point targetPoint) {
        PlayerComponent other = scenario.map[(int) targetPoint.getX()][(int) targetPoint.getY()].getPlayerComponent();
        return other !=null && player !=other;
    }

    private boolean wallCollision(Point targetPoint) {
        return scenario.map[(int) targetPoint.getX()][(int) targetPoint.getY()].getStaticCompE() == WALL;
    }

    private boolean isOutOfBounds(Point point) {
        return point.x < 0 || point.x >= scenario.getWidth() || point.y < 0 || point.y >= scenario.getHeight();
    }

    private void movePoint(PlayerComponent player, Point point, int distance){
        switch (player.getOrientation()){
            case UP -> point.y-=distance;
            case DOWN -> point.y+=distance;
            case LEFT -> point.x-=distance;
            case RIGHT -> point.x+=distance;
        }
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
