package org.group7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    public static Stage stage;  //so we can access the stage easily from other (GUI) classes

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;

        //load the Main Menu
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainMenu.fxml"));
        Scene scene = new Scene(root);

        //if we want an icon for the application:
        stage.getIcons().add(new Image("/img/icon.png"));

        stage.setTitle("Surveillance System");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}