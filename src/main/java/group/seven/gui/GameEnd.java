package group.seven.gui;

import group.seven.Main;
import group.seven.utils.Methods;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class GameEnd extends BorderPane {

    private final View view;

    public GameEnd() {
        view = new View();
        Methods.loadFXML(this, "/fxml/GameEnd.fxml");
    }

    public void render() {
        view.update();
    }

    @FXML private Button QuitGame;
    @FXML private Button BackToMainMenu;

    @FXML
    void initialize() {
        setCenter(view);
        QuitGame.setOnMouseEntered(event -> QuitGame.setStyle("-fx-background-color: #27ae60;"));
        QuitGame.setOnMouseExited(event -> QuitGame.setStyle("-fx-background-color: #2ecc71;"));
        QuitGame.setOnAction(event -> System.exit(0));

        BackToMainMenu.setOnMouseEntered(event -> BackToMainMenu.setStyle("-fx-background-color: #27ae60;"));
        BackToMainMenu.setOnMouseExited(event -> BackToMainMenu.setStyle("-fx-background-color: #2ecc71;"));
        BackToMainMenu.setOnAction(event -> {
            try {
                Main.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/fxml/mainMenu.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

}
