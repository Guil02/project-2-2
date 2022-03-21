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
import org.group7.utils.Logger;

import static org.group7.enums.ComponentEnum.TELEPORTER;
import static org.group7.enums.ComponentEnum.WALL;


import java.util.LinkedList;
import java.util.List;



public class GameRunner extends AnimationTimer {
    private List<State> states;
    private Scenario scenario;
    State currentState;

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

        setInitialVision();
        display.render();
    }

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

            //TODO: Maybe add Config setting for toggling logging or not
            int step = ((int) (elapsedTimeStep * 100) / 100);   //in integer steps of 5
            int cov = ((int) (coverage * 100) / 100);           //in integer percent

            Logger.log(new String[]{String.valueOf(step), String.valueOf(cov)});
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
                for(int i = 0; i < moveAction.getDistance(); i++){
                    Point targetPoint = startingPoint.clone();
                    movePoint(player,targetPoint,i + 1);

                    if(i < player.getBaseSpeed() && !isOutOfBounds(targetPoint)){
                        if(wallCollision(targetPoint)) {
                            break;
                        } else if(playerCollision(player, targetPoint)){
                            break;
                        } else if(teleporterCollision(targetPoint)){
                            Teleporter teleporter = (Teleporter) scenario.map[(int) targetPoint.getX()][(int) targetPoint.getY()].getStaticComponent();
                            Point teleportTarget = teleporter.getTarget();
                            player.teleport(teleportTarget);
                            player.updateVision();
                            updateGrid(startingPoint, targetPoint);
                            break;
                        } else {
                            player.moveAgentForward(moveAction.getAction());
                            player.updateVision();
                            updateGrid(startingPoint, targetPoint);
                        }
                    } else {
                        break;
                    }
                }
            } else{
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

    public double calculateCoverage() {
        int seenGrids = 0;
        for (int i = 0; i < scenario.getWidth(); i++) {
            for (int j=0; j < scenario.getHeight(); j++) {
                if(scenario.map[i][j].explored) {
                    seenGrids++;
                }
            }
        }
        int totalSize = scenario.getWidth() * scenario.getHeight();
        double d1 = seenGrids;
        double d2 = totalSize;
//        return (seenGrids/totalSize)*100;
        return (d1/d2)*100;
    }

}
