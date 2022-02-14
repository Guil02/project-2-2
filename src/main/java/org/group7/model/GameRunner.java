package org.group7.model;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Window;
import org.group7.Main;
import org.group7.gui.ExplorationSim;
import org.group7.gui.Game;
import org.group7.gui.GameScreen;
import org.group7.gui.Renderer;
import org.group7.model.component.Component;

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
        createState();

        gameScreen.render(scenario);

        counter++;
    }

    private void createState() {
        states.add(new State(scenario.guards, scenario.intruders));
    }

    private void updatePlayers(){
        for(int i = 0; i< scenario.guards.size(); i++){
            scenario.guards.get(i).move(Math.random(), Math.random());
        }

        for(int i = 0; i< scenario.intruders.size(); i++){
            scenario.intruders.get(i).move(Math.random(), Math.random());
        }
    }
}
