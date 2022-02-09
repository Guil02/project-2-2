package org.group7.gui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.group7.Main;
import org.group7.model.GameRunner;
import org.group7.model.Scenario;
import org.group7.utils.Config;

//import static org.group7.utils.Config.DEFAULT_MAP_PATH;

/**
 * View controller for the Main Menu. Loaded statically from the Main class.
 */
public class Menu {

    @FXML private ImageView defaultMapView;

    @FXML private RadioButton defaultChoice;

    @FXML private RadioButton uploadChoice;

    @FXML private ToggleGroup mapChoice;

    @FXML private Label message;

    @FXML private Button startButton;

    @FXML private Label uploadFileName;

    @FXML private Button uploadScenarioButton;

    private File scenario;

    @FXML
    void start(ActionEvent event) {
        message.setText("");    //clear message label

        if (mapChoice.getSelectedToggle().equals(defaultChoice)) {
            //user selected default scenario
            scenario = new File(getClass().getResource(Config.DEFAULT_MAP_PATH).getFile());

            //switch scene
            //Stage stage = Main.stage;
            //Scene nextScene = new Scene(), etc...
            //GameRunner runner = new GameRunner(new Scenario(scenario.getPath()));
            GameRunner runner = new GameRunner(new Scenario(getClass().getResource(Config.DEFAULT_MAP_PATH).getFile()));
//            runner.start();

        } else {
            //user uploaded a scenario file
            //should probably parse file and check for validity before continuing

            message.setText("Not implemented yet :(");
        }
    }

    @FXML
    void uploadScenario(ActionEvent event) {

        mapChoice.selectToggle(uploadChoice);   //select the radio button for the start() method
        message.setText("");                    //clear message text
        startButton.setDisable(true);
        //open new FileChooser dialog. Only allow .txt files.
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File chosen = fileChooser.showOpenDialog(Main.stage);

        //make sure user actually selected a file
        if (chosen != null) {
            scenario = chosen;
            uploadFileName.setText(scenario.getName());
            startButton.setDisable(false);
        } else {
            message.setText("No file chosen!");
        }
    }

    @FXML
    void initialize() {
        //automatically called after the FXMLLoader injects the relevant fxml fields into this controller class
        //it's kinda like a constructor for JavaFX/FXML related stuff
        //clear labels
        message.setText("");
        uploadFileName.setText("");

        //define simple on-click events for the radio buttons
        uploadChoice.setOnMouseClicked(event -> uploadScenario(new ActionEvent()));     //open file chooser
        defaultChoice.setOnMouseClicked(event -> {message.setText(""); startButton.setDisable(false);});                  //clear message text

        //define simple hover styling for the start button
        startButton.setOnMouseEntered(event -> startButton.setStyle("-fx-background-color: #27ae60;"));
        startButton.setOnMouseExited(event -> startButton.setStyle("-fx-background-color: #2ecc71;"));

        /*
                I want to eventually add a drop-down menu or something similar for the user to choose between some
            predefined maps we have made instead of just the test map.
                I also want to make a scenario configuration tab to edit existing/chosen scenarios for
            testing/experimentation, without altering the actual file itself (if desired).
                Perhaps also a scenario creator tab for the user (or us) to create custom scenarios (and saving them as
            txt files for later use, if desired) so that you don't have to create a txt file manually and upload it.
         */
    }

    //not sure if we need this stuff, but SceneBuilder suggested it, gonna just leave it for now.
    @FXML private ResourceBundle resources; @FXML private URL location;

}
