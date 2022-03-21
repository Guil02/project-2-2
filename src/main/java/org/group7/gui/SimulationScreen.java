package org.group7.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.group7.Main;
import org.group7.model.Scenario;
import org.group7.utils.Methods;

public class SimulationScreen extends BorderPane {

    private final View view;
    private boolean running;

    public SimulationScreen() {
        view = new View();
        Methods.loadFXML(this, "/fxml/simulationScreen.fxml");
    }

    public void render() {
        view.update();
    }

    public void updateStats(double elapsedTimeStep, double coverage) {
        elapsedTime.setText(String.valueOf((int)(elapsedTimeStep * 100) / 100) + " units");
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

        String gameMode = switch (Scenario.getInstance().getGameMode()) {
            case 0 -> "Exploration";
            case 1 -> "Intruder";
            default -> "Unknown Game Mode";
        };

        gameModeLabel.setText(gameMode);

        pauseButton.setOnAction(event -> {
            //gameRunner.stop();
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
