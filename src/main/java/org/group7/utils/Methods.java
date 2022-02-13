package org.group7.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class Methods {

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
}
