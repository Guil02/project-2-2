package org.group7.utils;

import group.seven.enums.Status;
import group.seven.logic.records.XY;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.group7.model.State;
import org.group7.model.component.markerComponent.CrossMarkerComponent;
import org.group7.model.component.playerComponents.Guard;
import org.group7.model.graph.Graph;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Methods {

    /**
     * Utility method for injecting fxml file into a custom JavaFX Node
     * Call this method in the constructor of Node's controller class in order to inject fxml fields
     * Controller class must have all annoted @FXML fields the fxml file expects!
     * @param controller Object that extends from fxml root Node type
     * @param url "/path/from/resources root/to/file.fxml"
     */
    public static void loadFXML(Node controller, String url) {
        FXMLLoader loader = new FXMLLoader(Methods.class.getResource(url));
        loader.setController(controller); //this class is the controller for the FXML view that the FXMLLoader is loading
        loader.setRoot(controller);       //this class is also the Parent node of the FXML view
        try {
            loader.load();              //this is the method that actually does the loading. It's non-static version of FXMLLoader.load()
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Static import this method as a shorthand for System.out.println()
     * if debug disabled, then it won't print
     * @param t the text, number, object or other to be printed
     * @param <T> generic parameter to enable printing of anything
     */
    public static <T> void print(T t) {
        print(t, Config.DEBUG_MODE);
    }

    /**
     * Static import this method as a shorthand for System.out.println()
     * Set debug flag to override Config.DEBUG_MODE value
     * @param t the object or primitive to be printed
     * @param debug if true then prints, else won't print.
     * @param <T> generic parameter to enable printing of anything
     */
    public static <T> void print(T t, boolean debug) {
        if (debug) System.out.println(t);
    }
}
