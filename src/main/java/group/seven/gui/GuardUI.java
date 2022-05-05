//package group.seven.gui;
//import group.seven.model.component.playerComponents.PlayerComponent;
//import group.seven.utils.Methods;
//import javafx.fxml.FXML;
//import javafx.scene.control.Label;
//import javafx.scene.layout.AnchorPane;
//
//public class GuardUI extends AnchorPane {
//
//    protected static int selected = 0;
//    protected static GuardUI selectedGuard = null;
//    protected static double mapSize;
//
//    @FXML private Label agentID;
//    @FXML private Label coverage;
//    @FXML private Label orientation;
//
//    protected final PlayerComponent guard;
//    protected final int ID;
//
//    public GuardUI(PlayerComponent guard) {
//        this.guard = guard;
//        ID = guard.getId();
//        mapSize = Scenario.getInstance().getWidth() * Scenario.getInstance().getHeight();
//        Methods.loadFXML(this, "/fxml/guardAlt.fxml");
//    }
//
//    protected void update() {
//        coverage.setText((int)((Grid.numGridsSeenBy[ID] / mapSize) * 100) + "%");
//        //orientation.setText(guard.getOrientation().toString());
//    }
//
//    @FXML void initialize() {
//        agentID.setText("#"+ (ID + 1));
//        coverage.setText("0%");
//        orientation.setText(guard.getOrientation().toString());
//
//    }
//
//    public void select() {
//        if (selected != ID + 1) {
//            setStyle("-fx-background-color: #e1b12c; -fx-background-radius: 25;");
//            selected = ID + 1;
//            if (selectedGuard != null) selectedGuard.setStyle("-fx-background-color: #34495e;");
//            selectedGuard = this;
//        } else {
//            selected = 0;
//            selectedGuard = null;
//            setStyle("-fx-background-color: #34495e;");
//        }
//    }
//}