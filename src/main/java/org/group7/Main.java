package org.group7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.group7.gui.Menu;

import java.io.IOException;

public class Main extends Application {

    public static Stage stage;  //so we can access the stage easily from other (GUI) classes

    public static void main(String[] args) {launch();}

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;

        //load the Main Menu
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/menu.fxml"));
        Scene scene = new Scene(root);

//        Menu menu = new Menu();
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/promotionPane.fxml"));
//        loader.setController(menu);
//        loader.setRoot(menu);
//        loader.load();
        //if we want an icon for the application:
        //stage.getIcons().add(new Image("/img/icon.png"));

        stage.setTitle("Surveillance System");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}