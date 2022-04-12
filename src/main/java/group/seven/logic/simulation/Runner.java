package group.seven.logic.simulation;

import group.seven.enums.GameMode;
import group.seven.gui.TempView;
import group.seven.model.agents.Guard;
import group.seven.model.environment.Scenario;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;

import java.util.Random;

public class Runner extends AnimationTimer {
    Scenario scenario;
    //Timeline timeline;
    TempView view;

    public Runner(Scenario scenario) {
        this.scenario = scenario;
        view = new TempView(scenario);

        //timeline = new Timeline(new KeyFrame(Duration.millis(1000), this::render));
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
        long previous = prev;
        System.out.print("\r fps: " +  (now - previous) / 1_000_000_000.0);
        count++;
        Platform.runLater(() -> view.update((int) (now - previous) / 1000000));
        //view.update((int) (now - previous) / 1000000);

        if (count > 100000) stop();
        prev = System.nanoTime();
    }

    private void spawnAgents(GameMode gameMode) {
        switch (gameMode) {
            case EXPLORATION -> {
                for (int i = 0; i < scenario.NUM_GUARDS; i++) {
                    //spawn explorers in spawn area
                    Random r = new Random();
                    r.nextInt(scenario.guardSpawnArea.area().width);
                    //int x = scenario.guardSpawnArea.area().getWidth();

                    //XY spawnPoint = Environment.GUARD_SPAWN_GRIDS.get((int) (Math.random() * Environment.GUARD_SPAWN_GRIDS.size()));

                    Guard agent = new Guard();

                    scenario.TILE_MAP.addAgent(agent);
                }
            }

            case SINGLE_INTRUDER, MULTI_INTRUDER -> {
                for (int i = 0; i < scenario.NUM_GUARDS; i++) {
                    //spawn guards in spawn area
                    //add to agents list

                }

                for (int i = 0; i < scenario.NUM_INTRUDERS; i++) {
                    //spawn intruders in spawn area
                    //add to agents list
                }
            }
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
}

