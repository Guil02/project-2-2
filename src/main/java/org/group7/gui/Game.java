package org.group7.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class Game {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Canvas canvas;

    @FXML
    private Button pauseButton;

    @FXML
    private Button resetButton;

    @FXML
    private Label timeLabel;

    @FXML
    void pause(MouseEvent event) {

    }

    @FXML
    void reset(MouseEvent event) {

    }

    @FXML
    void initialize() {
        // When the pause button is pressed, the pause method is run.
        pauseButton.setOnMouseClicked(this::pause);

        // When the reset button is pressed the reset method is run.
        resetButton.setOnMouseClicked(this::reset);

    }

}
