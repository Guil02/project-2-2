package org.group7.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.group7.Main;
import org.group7.model.Scenario;
import org.group7.model.component.Component;
import org.group7.utils.Methods;

public class GameScreen extends AnchorPane {

    public Renderer renderer;
    private Scenario scenario;

    public GameScreen(Renderer renderer, Scenario scenario) {
        this.renderer = renderer;
        this.scenario = scenario;
        Methods.loadFXML(this, "/fxml/gameScreen.fxml");
    }

    @FXML private Button pauseBtn;
    @FXML private Button playBtn;
    @FXML private VBox renderContainer;
    @FXML private Button resetBtn;
    @FXML private Button slowDownBtn;
    @FXML private Button speedUpBtn;
    @FXML private AnchorPane renderBox;

    @FXML
    void resetSimulation(ActionEvent event) {
        ((ExplorationSim)renderer).reset(); //doesn't actual reset the simulation, it just restores the default view
    }

    public void render(Scenario scenario) {
        renderer.render(scenario);
    }

//    public void render(Component component) {
//        renderer.render();
//        //GraphicsContext g = canvas.getGraphicsContext2D();
//        //g.setFill(Color.RED);
//        //g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
//        g.setFill(Color.GREEN);
//        g.fillRect(component.getArea().getTopLeft().x, component.getArea().getTopLeft().y, component.getArea().getWidth(), component.getArea().getHeight());
//    }

    @FXML
    void initialize() {
        renderBox.getChildren().add(renderer);

        renderBox.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                renderer.res = 1.05 * Math.max(scenario.getWidth() / renderer.getViewportBounds().getWidth(), scenario.getHeight() / renderer.getViewportBounds().getHeight());
            }
        });
    }

}
