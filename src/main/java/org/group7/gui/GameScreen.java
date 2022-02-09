package org.group7.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.group7.model.component.Component;

public class GameScreen extends AnchorPane {

    Canvas canvas;

    public GameScreen(Canvas canvas){
        this.canvas = canvas;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gameScreen.fxml"));
        loader.setController(this); //this class is the controller for the FXML view that the FXMLLoader is loading
        loader.setRoot(this);       //this class is also the Parent node of the FXML view
        try {
            loader.load();              //this is the method that actually does the loading. It's non-static version of FXMLLoader.load()
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void render(Component component) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        //g.setFill(Color.RED);
        //g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g.setFill(Color.GREEN);
        g.fillRect(component.getArea().getPoint1().x, component.getArea().getPoint1().y, component.getArea().getWidth(), component.getArea().getHeight());
    }

    @FXML
    void initialize() {
        renderContainer.getChildren().add(canvas);
    }

}
