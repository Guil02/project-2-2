package group.seven.gui;

import group.seven.Main;
import group.seven.model.environment.Scenario;
import group.seven.utils.Methods;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class ExperimentMenu extends AnchorPane {
    Scenario scenario;

    public ExperimentMenu(Scenario s) {
        scenario = s;
        Methods.loadFXML(this, "/fxml/experimentScreen.fxml");
        Main.stage.setScene(new Scene(this));
        Main.stage.centerOnScreen();
    }

    @FXML
    private RadioButton allCaught;
    @FXML
    private RadioButton allIntruders;
    @FXML
    private Button backBtn;
    @FXML
    private TextField baseSpeedGuard;
    @FXML
    private TextField sprintSpeedGuard;
    @FXML
    private TextField sprintSpeedIntruder;
    @FXML
    private TextField baseSpeedIntruder;
    @FXML
    private ChoiceBox<String> chosenAlgorithm;
    @FXML
    private ChoiceBox<String> chosenGamemode;
    @FXML
    private ChoiceBox<String> chosenIntruder;
    @FXML
    private RadioButton coneVision;
    @FXML
    private Label dataLoggerLabel;
    @FXML
    private ChoiceBox<String> defaultAgentAlgo;
    @FXML
    private ChoiceBox<String> defaultGaurdAlgo;
    @FXML
    private ToggleGroup guardWin;
    @FXML
    private Label guardsLabel;
    @FXML
    private ToggleGroup intruderWin;
    @FXML
    private Label intrudersLabel;
    @FXML
    private CheckBox logDataChoice;
    @FXML
    private ToggleGroup mapChoice;
    @FXML
    private Label mapNameLabel;
    @FXML
    private Label messageLabel;
    @FXML
    private Slider numGuardSlider;
    @FXML
    private Label numGuardVal;
    @FXML
    private Slider numIntruderSlider;
    @FXML
    private Label numIntruderVal;
    @FXML
    private RadioButton oneIntruder;
    @FXML
    private RadioButton rectangleVision;
    @FXML
    private Button runButton;
    @FXML
    private TextField scenarioHeight;
    @FXML
    private TextField scenarioName;
    @FXML
    private TextField scenarioWidth;
    @FXML
    private RadioButton singleCaught;
    @FXML
    private TextField smellDistance;
    @FXML
    private Label terminateLabel;
    @FXML
    private RadioButton uploadToggle;
    @FXML
    private TextField viewDistance;
    @FXML
    private ToggleGroup visionType;
    @FXML
    private Label winConditionLabel;

    @FXML
    void run(ActionEvent event) {

    }

    @FXML
    void initialize() {
        scenarioName.setText(scenario.NAME);
        mapNameLabel.setText(scenario.NAME);
        scenarioWidth.setText(scenario.WIDTH + "");
        scenarioHeight.setText(scenario.HEIGHT + "");
        numGuardSlider.adjustValue(scenario.NUM_GUARDS);
        numGuardVal.textProperty().bind(Bindings.format("%.0f", numGuardSlider.valueProperty()));

        numIntruderSlider.adjustValue(scenario.NUM_INTRUDERS);
        numIntruderVal.textProperty().bind(Bindings.format("%.0f", numIntruderSlider.valueProperty()));

        viewDistance.setText(scenario.VIEW_DISTANCE + "");
        smellDistance.setText(scenario.SMELL_DISTANCE + "");
        visionType.selectToggle(coneVision);
        baseSpeedGuard.setText(scenario.GUARD_BASE_SPEED + "");
        sprintSpeedGuard.setText(scenario.GUARD_SPRINT_SPEED + "");
        baseSpeedIntruder.setText(scenario.INTRUDER_BASE_SPEED + "");
        sprintSpeedIntruder.setText(scenario.INTRUDER_SPRINT_SPEED + "");
    }

    @FXML
    void uploadMap(ActionEvent event) {

    }

    @FXML
    void goBack(ActionEvent event) {

    }

}
