package group.seven;

import group.seven.logic.algorithms.GeneticNeuralNetwork.GeneticAlgorithm;
import group.seven.utils.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static Stage stage;  //so we can access the stage easily from other (GUI) classes

    public static void main(String[] args) {
        if (Config.GUI_ON) {
            launch();
        } else if (Config.GA_ON) {
            GeneticAlgorithm ga = new GeneticAlgorithm();
        } else throw new RuntimeException("no system mode specified");
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