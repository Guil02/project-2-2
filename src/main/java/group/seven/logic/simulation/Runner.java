package group.seven.logic.simulation;

import group.seven.gui.TempView;
import group.seven.model.environment.Scenario;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;

public class Runner extends AnimationTimer {
    Scenario scenario;
    Timeline timeline;
    TempView view;

    public Runner(Scenario scenario) {
        this.scenario = scenario;

        //timeline = new Timeline(new KeyFrame(Duration.millis(1000), this::render));
        //timeline.setCycleCount(Animation.INDEFINITE);
        view = new TempView(scenario);
        prev = System.nanoTime();
        //timeline.play();
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
        Platform.runLater(() -> view.update((int) (now - previous) / 1000000));
        if (count > 100000) stop();
        count++;
        prev = System.nanoTime();
    }

    //step
    //updateModel
    //updateGUI
    //gameOver
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

