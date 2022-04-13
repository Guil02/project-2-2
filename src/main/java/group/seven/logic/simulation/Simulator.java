package group.seven.logic.simulation;

import group.seven.enums.Action;
import group.seven.enums.GameMode;
import group.seven.gui.TempView;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Guard;
import group.seven.model.agents.Intruder;
import group.seven.model.agents.Move;
import group.seven.model.environment.Scenario;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import static group.seven.utils.Methods.print;

public class Simulator extends AnimationTimer {
    Scenario scenario;
    //Timeline timeline;
    TempView view;

    public Simulator(Scenario scenario) {
        this.scenario = scenario;
        spawnAgents(scenario.GAME_MODE);
        view = new TempView(scenario);

        //timeline = new Timeline(new KeyFrame(Duration.millis(100), this::render));
        //timeline.setCycleCount(Animation.INDEFINITE);
        //timeline.play();

        prev = System.nanoTime();
        start();
    }

    protected void render(ActionEvent actionEvent) {
        //Platform.runLater(view::update);
        //view.update(count);
        //view.update();
    }

    int count = 0;
    long prev;

    @Override
    public void handle(long now) {
//        long previous = prev;
//        System.out.print("\r fps: " +  (now - previous) / 1_000_000_000.0);
        count++;
        update();
        Platform.runLater(() -> view.update());
        view.update();
        if (count > 100000) stop();
        prev = System.nanoTime();
    }

    protected void update() {
        for (Agent agent : scenario.TILE_MAP.agents) {
            if (agent != null) {
                Move move = agent.calculateMove();
                agent.setDirection(move.action() == Action.FLIP ? move.direction().flip() : move.direction());
                agent.setDirection(agent.getDirection().flip());

                //print(move);
            }
        }
//        Arrays.stream(scenario.TILE_MAP.agents).forEach(Agent::calculateMove);
    }

    private void spawnAgents(GameMode gameMode) {
        print(gameMode);
        switch (gameMode) {
            case EXPLORATION -> {
                for (int i = 0; i < scenario.NUM_GUARDS; i++) {
                    Guard agent = new Guard(5, 10);
                    scenario.TILE_MAP.addAgent(agent);
                }
            }

            case SINGLE_INTRUDER, MULTI_INTRUDER -> {
                Random rand = new Random();
                for (int i = 0; i < scenario.NUM_GUARDS; i++) {
                    Guard agent = new Guard(5 + rand.nextInt(10), 10 + rand.nextInt(10));
                    scenario.TILE_MAP.addAgent(agent);
                    print("added guard : " + agent.getID());
                }

                for (int i = 0; i < scenario.NUM_INTRUDERS; i++) {
                    Intruder agent = new Intruder(10, 30);
                    scenario.TILE_MAP.addAgent(agent);
                    print("added intruder : " + agent.getID());
                }
            }

        }
        print(Arrays.stream(scenario.TILE_MAP.agents).filter(Objects::nonNull).map(Agent::getID).toList());
    }


//    Task<Boolean> simulator;
//    public void start() {
//        simulator = new Task<>() {
//            boolean simulating = true;
//            int count = 0;
//            @Override
//            protected Boolean call() {
//                int i = 0; int j = 0;
//                while (simulating) {
//                    int x = i++;
//                    int y = j;
////                    for (int x = i; x < scenario.WIDTH; x++) {
////                        for (int y = j; y < scenario.HEIGHT; y++) {
//                            scenario.TILE_MAP.setType(x, y, TileType.WALL);
////                        }
////                    }
//
//                    j = y + 1;
//                    //update model
//                    //int finalCount = count;
//                    //Platform.runLater(() -> view.update(finalCount));
//                    if (count > 100000) simulating = false;
//                    count++;
//                }
//                return true;
//            }
//        };
//        timeline.play();
//        new Thread(simulator).start();
//        if (simulator.isDone()) {
//            boolean caughtIntruder = simulator.getValue();
//            timeline.stop();
//        }
}

