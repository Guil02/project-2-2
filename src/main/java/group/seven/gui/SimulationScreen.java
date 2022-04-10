package group.seven.gui;

import group.seven.Main;
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

    public SimulationScreen(Scenario s) {
        view = new View(s);
        Methods.loadFXML(this, "/fxml/simulationScreen.fxml");
    }

    public void render() {
        view.update();
        //guardsVis.forEach(GuardUI::update);
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
        pauseButton.setText(!running ? "Play" : "Pause");

//        String gameMode = switch (Scenario.getInstance().getGameMode()) {
//            case 0 -> "Exploration";
//            case 1 -> "Intruder";
//            default -> "Unknown Game Mode";
//        };

        //gameModeLabel.setText(gameMode);

//        guardsVis = Scenario.getInstance().getGuards().stream()
//                        .map(GuardUI::new)
//                        .toList();
//
//        guardsVis.forEach(g -> {
//            guardsList.getChildren().add(g);
//            g.setOnMouseClicked(e -> g.select());
//        });
//
//        pauseButton.setOnAction(event -> {
//            if (running) GameRunner.gameRunner.stop();
//            else GameRunner.gameRunner.start();
//
//            pauseButton.setText(running ? "Play" : "Pause");
//            running = !running;
//        });

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
