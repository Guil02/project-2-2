package group.seven.gui;

import group.seven.Main;
import group.seven.logic.simulation.Simulator;
import group.seven.model.agents.Guard;
import group.seven.model.agents.Intruder;
import group.seven.model.environment.Scenario;
import group.seven.utils.Methods;
import group.seven.utils.Tuple;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

import static group.seven.enums.TileType.GUARD;
import static group.seven.enums.TileType.INTRUDER;
import static group.seven.model.environment.Scenario.TILE_MAP;

public class SimulationScreen extends BorderPane {

    private final View view;
    private boolean running;
    private List<GuardUI> guardsVis;
    private List<IntruderGUI> intruderVis;

    public SimulationScreen() {
        view = new View();
        Methods.loadFXML(this, "/fxml/simulationScreen.fxml");
    }

    public void render() {
        view.update();
        guardsVis.forEach(GuardUI::update);
        intruderVis.forEach(IntruderGUI::update);
    }

    public void updateStats(double elapsedTimeStep, Tuple<Double, Double> coverage) {
        double guardCoverage = coverage.a();
        double intruderCoverage = coverage.b();

        elapsedTime.setText(((int)(elapsedTimeStep * 100) / 100) + " units");
        explorationPercent.setText(String.valueOf((int)(guardCoverage * 100) / 100));

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

        String gameMode = switch (Scenario.GAURD_GAME_MODE) {
            case EXPLORATION -> "Exploration";
            default -> "Intruders";
        };

        gameModeLabel.setText(gameMode);

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

        setUpSidebar();

        setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case Z -> view.zoom(1.05);
                case X -> view.zoom(0.95);
            }
        });
    }

    private void setUpSidebar() {
        guardsVis = Arrays.stream(TILE_MAP.agents)
                .filter(a -> a.getType() == GUARD)
                .map(a -> new GuardUI((Guard) a, view))
                .toList();

        guardsVis.forEach(g -> {
            guardsList.getChildren().add(g);
            g.setOnMouseClicked(e -> g.select());
        });

        intruderVis = Arrays.stream(TILE_MAP.agents)
                .filter(a -> a.getType() == INTRUDER)
                .map(a -> new IntruderGUI((Intruder) a, view))
                .toList();

        intruderVis.forEach(g -> {
            intrudersList.getChildren().add(g);
            g.setOnMouseClicked(e -> g.select());
        });
    }
}
