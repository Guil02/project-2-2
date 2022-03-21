package org.group7.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import org.group7.Main;
import org.group7.enums.AlgorithmEnum;
import org.group7.model.GameRunner;
import org.group7.model.Scenario;
import org.group7.utils.Config;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainMenu {

    @FXML private ChoiceBox<String> chosenAlgorithm;

    @FXML private Label gameModeLabel;

    @FXML private CheckBox logDataChoice;

    @FXML private ToggleGroup mapChoice;

    @FXML private Label mapNameLabel;

    @FXML private ImageView mapView;

    @FXML private Label messageLabel;

    @FXML private Button startButton;
    @FXML private RadioButton uploadToggle;
    @FXML private RadioButton existingToggle;

    private File scenarioFile;

    private Map<String, Image> mapLibrary;

    @FXML
    void start(ActionEvent event) {
        messageLabel.setText("");    //clear message label
        String algorithm = chosenAlgorithm.getValue();

        if (!algorithm.equals("Choose Algorithm")) {
            Config.ALGORITHM = AlgorithmEnum.getEnum(algorithm);
            Scenario s = new Scenario(scenarioFile);
            GameRunner runner = new GameRunner(s);
            runner.start();
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
            else mapView.setEffect(new Shadow(25, Color.LIGHTGRAY));
        } else {
            messageLabel.setText("No file chosen!");
        }
    }

    private int count = 0;
    private final List<String> maps = new ArrayList<>();

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
        mapView.setFitWidth(200);
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
            mapView.setFitWidth(200);
            mapView.setImage(mapLibrary.get(scenarioFile.getName()));

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        mapNameLabel.setText(scenarioFile.getName().split("\\.")[0]);

        startButton.setOnMouseEntered(event -> startButton.setStyle("-fx-background-color: #27ae60;"));
        startButton.setOnMouseExited(event -> startButton.setStyle("-fx-background-color: #2ecc71;"));

        //define simple on-click events for the radio buttons
        uploadToggle.setOnMouseClicked(event -> uploadMap(new ActionEvent()));     //open file chooser
        existingToggle.setOnMouseClicked(event -> selectExisting(new ActionEvent())); //clear message text

        chosenAlgorithm.getItems().addAll(
                "A*",
                "Wall Following",
                "Flood Fill",
                "Frontier",
                "Random"
        );

        chosenAlgorithm.setValue("Choose Algorithm");
    }

}
