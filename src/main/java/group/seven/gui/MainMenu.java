package group.seven.gui;

import group.seven.enums.AlgorithmType;
import group.seven.enums.GameMode;
import group.seven.logic.simulation.Simulator;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.ScenarioBuilder;
import group.seven.utils.Config;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import group.seven.Main;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainMenu {

    @FXML private ChoiceBox<String> chosenAlgorithm;
    @FXML private ChoiceBox<String> chosenIntruder;
    @FXML private ChoiceBox<String> chosenGamemode;
    @FXML private Label gameModeLabel;
    @FXML private CheckBox logDataChoice;
    @FXML private ToggleGroup mapChoice;
    @FXML private Label mapNameLabel;
    @FXML private ImageView mapView;
    @FXML private Label messageLabel;
    @FXML private Label algorithmLabel0;
    @FXML private Label algorithmLabel1;
    @FXML private Label algorithmLabel2;
    @FXML private Button startButton;
    @FXML private RadioButton uploadToggle;
    @FXML private RadioButton existingToggle;

    private File scenarioFile;
    private Map<String, Image> mapLibrary;

    @FXML
    void start(ActionEvent event) {
        messageLabel.setText("");    //clear message label
        String algorithm = chosenAlgorithm.getValue();      //guard value
        String algorithm2 = chosenIntruder.getValue();      //intruder value
        String gamemode = chosenGamemode.getValue();

        if (!algorithm.equals("Choose Guard") && !algorithm2.equals(("Choose Intruder")) && !gamemode.equals("Choose Gamemode")) {
            Config.ALGORITHM_GUARD = AlgorithmType.getEnum(algorithm);
            Config.ALGORITHM_INTRUDER = AlgorithmType.getEnum(algorithm2);
            Config.GAMEMODE = GameMode.getEnum(gamemode);
            Scenario scenario = new ScenarioBuilder(scenarioFile).build();
            //Runner runner =
            new Simulator(scenario);
            //runner.start();

        } else {
            messageLabel.setText("Please Select an Algorithm");
        }
    }

    @FXML
    void uploadMap(ActionEvent event) {
        mapChoice.selectToggle(uploadToggle);   //select the radio button for the start() method
        messageLabel.setText("");               //clear message text
        startButton.setDisable(true);
        //open new FileChooser dialog. Only allow .txt files.
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File chosen = fileChooser.showOpenDialog(Main.stage);

        //make sure user actually selected a file
        if (chosen != null) {
            scenarioFile = chosen;
            mapNameLabel.setText(scenarioFile.getName().split("\\.")[0]);
            startButton.setDisable(false);
            if (mapLibrary.containsKey(scenarioFile.getName())) mapView.setImage(mapLibrary.get(scenarioFile.getName()));
            else mapView.setEffect(new ColorAdjust(0,0,0.5,0.0));
        } else {
            messageLabel.setText("No file chosen!");
        }
    }

    private int count = 1;
    private final List<String> maps = new ArrayList<>();
    FadeTransition fade;

    @FXML
    void nextMap(ActionEvent event) {
        if (count >= maps.size() || count < 0) count = 0;
        String mapFile = maps.get(count++);
        browse(mapFile);
    }

    @FXML
    void prevMap(ActionEvent event) {
        if (count < 0 || count >= maps.size()) count = maps.size() - 1;
        String mapFile = maps.get(count--);
        browse(mapFile);
    }

    private void browse(String mapFile) {
        try {
            scenarioFile = new File(getClass().getResource("/scenarios/"+mapFile).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        mapView.setImage(mapLibrary.get(mapFile));
        fade.play();

        selectExisting(new ActionEvent());
    }

    @FXML
    void selectExisting(ActionEvent event) {
        mapChoice.selectToggle(existingToggle);
        messageLabel.setText("");
        startButton.setDisable(false);
        mapNameLabel.setText(scenarioFile.getName().split("\\.")[0]);
        mapView.setEffect(null);
    }

    @FXML
    void initialize() {
        messageLabel.setText("");
        logDataChoice.setOnAction(event -> Config.LOG_DATA = !Config.LOG_DATA);

        mapLibrary = new LinkedHashMap<>(3);
        mapLibrary.put("biggerTestMap.txt", new Image("/img/biggerTestMap.png"));
        mapLibrary.put("testmap.txt", new Image("/img/testmap.png"));
        mapLibrary.put("examinermap_phase1.txt", new Image("/img/examinermap_phase1.png"));

        maps.add("biggerTestMap.txt");
        maps.add("testmap.txt");
        maps.add("examinermap_phase1.txt");

        try {
            //initialize scenarioFile to default
            scenarioFile = new File(getClass().getResource(Config.DEFAULT_MAP_PATH).toURI());
            mapView.setImage(mapLibrary.get(scenarioFile.getName()));

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        mapNameLabel.setText(scenarioFile.getName().split("\\.")[0]);

        chosenAlgorithm.getItems().addAll(
                "Ant Pursuit",
                "Brick And Mortar",
                "Random"        //RandomTest.java file, not RandomMoves
        );

        chosenAlgorithm.setValue("Choose Guard");
        chosenAlgorithm.setOnHidden(event -> algorithmLabel0.setText(chosenAlgorithm.getValue()));
        chosenAlgorithm.setStyle("-fx-font-size: 16; -fx-background-color: #f5f6fa;");

        chosenIntruder.getItems().addAll(
                "A*",
                "Random"
        );

        chosenIntruder.setValue("Choose Intruder");
        chosenIntruder.setOnHidden(event -> algorithmLabel1.setText(chosenIntruder.getValue()));
        chosenIntruder.setStyle("-fx-font-size: 16; -fx-background-color: #f5f6fa;");

        chosenGamemode.getItems().addAll(
            "Single Intruder Caught",
            "All intruders Caught"
        );

        chosenGamemode.setValue("Choose Gamemode");
        chosenGamemode.setOnHidden(event -> algorithmLabel2.setText(chosenGamemode.getValue()));
        chosenGamemode.setStyle("-fx-font-size: 16; -fx-background-color: #f5f6fa;");

        startButton.setOnMouseEntered(event -> startButton.setStyle("-fx-background-color: #27ae60;"));
        startButton.setOnMouseExited(event -> startButton.setStyle("-fx-background-color: #2ecc71;"));

        uploadToggle.setOnMouseClicked(event -> uploadMap(new ActionEvent()));     //open file chooser
        existingToggle.setOnMouseClicked(event -> selectExisting(new ActionEvent())); //clear message text

        fade = new FadeTransition(Duration.millis(200), mapView);
        fade.setFromValue(0.1);
        fade.setToValue(1);
        fade.setRate(0.2);

    }

}
