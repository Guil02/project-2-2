package org.group7.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.group7.Main;
import org.group7.utils.Methods;

public class SimulationScreen extends BorderPane {

    private View view;
    private boolean running;

    public SimulationScreen() {
        view = new View();
        Methods.loadFXML(this, "/fxml/simulationScreen.fxml");
    }

    public void render() {
        view.update();
    }

    public void updateStats() {
        elapsedTime.setText("");
        explorationPercent.setText("");
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
    void initialize() {
        running = true;
        setCenter(view);

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
    }
}
