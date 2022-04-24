package group.seven.gui;

import group.seven.Main;
import group.seven.logic.simulation.Simulator;
import group.seven.model.environment.Scenario;
import group.seven.utils.Methods;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SimulationScreen extends BorderPane {

    private final View view;
    private boolean running;
    //private List<GuardUI> guardsVis;

    public SimulationScreen() {
        view = new View();
        Methods.loadFXML(this, "/fxml/simulationScreen.fxml");
    }

    public void render() {
        view.update();
        //guardsVis.forEach(GuardUI::update);
    }

    public void updateStats(double elapsedTimeStep, double coverage) {
        elapsedTime.setText(((int)(elapsedTimeStep * 100) / 100) + " units");
        explorationPercent.setText(String.valueOf((int)(coverage * 100) / 100));
    }

    @FXML private Label elapsedTime;
    @FXML private Label explorationPercent;
    @FXML private Label gameModeLabel;
    @FXML private VBox guardsList;
    @FXML private VBox intrudersList;
    @FXML private Button pauseButton;
    @FXML private Button quitButton;
    @FXML private Button resetButton;

    @FXML
    void zoomIn(Event event) {
        view.zoom(1.05);
    }

    @FXML
    void zoomOut(Event event) {
        view.zoom(0.95);
    }

    @FXML
    void initialize() {
        running = true;
        setCenter(view);
        pauseButton.setText(!running ? "Play" : "Pause");

        String gameMode = switch (Scenario.GAME_MODE) {
            case EXPLORATION -> "Exploration";
            case SINGLE_INTRUDER -> "Intruder";
            case MULTI_INTRUDER -> "Multi Intruder";
        };

        gameModeLabel.setText(gameMode);

//        guardsVis = Scenario.getInstance().getGuards().stream()
//                        .map(GuardUI::new)
//                        .toList();
//
//        guardsVis.forEach(g -> {
//            guardsList.getChildren().add(g);
//            g.setOnMouseClicked(e -> g.select());
//        });
//
        pauseButton.setOnAction(event -> {
            if (running) Simulator.pause();
            else Simulator.sim.start();

            pauseButton.setText(running ? "Play" : "Pause");
            running = !running;
        });

        quitButton.setOnAction(event -> {
            Main.stage.close();
            Platform.exit();
            System.exit(0);
        });

        resetButton.setDisable(true);

        setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case Z -> view.zoom(1.05);
                case X -> view.zoom(0.95);
            }
        });
    }
}
