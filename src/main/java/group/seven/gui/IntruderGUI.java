package group.seven.gui;

import group.seven.model.agents.Intruder;
import group.seven.utils.Methods;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class IntruderGUI extends AnchorPane {

    protected static int selected = 0;
    protected static IntruderGUI selectedIntruder = null;

    @FXML
    private Label agentID;
    @FXML
    private Label coverage;
    @FXML
    private Label orientation;

    protected final Intruder intruder;
    protected final int ID;
    View view;

    public IntruderGUI(Intruder intruder, View view) {
        this.intruder = intruder;
        this.view = view;
        ID = intruder.getID();
        Methods.loadFXML(this, "/fxml/intruderGUI.fxml");
    }

    protected void update() {
        coverage.setText((int) ((intruder.getNumExplored() / view.s.TILE_MAP.NUM_TILES) * 100) + "%");
        //orientation.setText(guard.getOrientation().toString());
    }

    @FXML
    void initialize() {
        agentID.setText("#" + (ID + 1));
        coverage.setText("0%");
        orientation.setText(intruder.getDirection().toString());
    }

    public void select() {
        if (selected != ID + 1) {
            setStyle("-fx-background-color: #e1b12c; -fx-background-radius: 25;");
            selected = ID + 1;
            if (selectedIntruder != null) selectedIntruder.setStyle("-fx-background-color: #34495e;");
            selectedIntruder = this;
        } else {
            selected = 0;
            selectedIntruder = null;
            setStyle("-fx-background-color: #34495e;");
        }
    }
}