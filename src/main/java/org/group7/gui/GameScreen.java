package org.group7.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class GameScreen extends AnchorPane {

    public GameScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gameScreen.fxml"));
        loader.setController(this); //this class is the controller for the FXML view that the FXMLLoader is loading
        loader.setRoot(this);       //this class is also the Parent node of the FXML view
        loader.load();              //this is the method that actually does the loading. It's non-static version of FXMLLoader.load()
    }

    @FXML private ResourceBundle resources;

    @FXML private URL location;

    @FXML private Button pauseBtn;

    @FXML private Button playBtn;

    @FXML private VBox renderContainer;

    @FXML private Button resetBtn;

    @FXML private Button slowDownBtn;

    @FXML private Button speedUpBtn;

    @FXML
    void resetSimulation(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}
