package group.seven.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;
import java.util.ArrayList;

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

    /**
     * Static import this method as a shorthand for System.out.println()
     * if debug disabled, then it won't print
     *
     * @param t   the text, number, object or other to be printed
     * @param <T> generic parameter to enable printing of anything
     */
    public static <T> void print(T t) {
        print(t, Config.DEBUG_MODE);
    }

    /**
     * Static import this method as a shorthand for System.out.println()
     * Set debug flag to override Config.DEBUG_MODE value
     *
     * @param t     the object or primitive to be printed
     * @param debug if true then prints, else won't print.
     * @param <T>   generic parameter to enable printing of anything
     */
    public static <T> void print(T t, boolean debug) {
        if (debug) System.out.println(t);
    }


    /**
     * a function to generate random numbers
     *
     * @param min lower bound for random number
     * @param max upper bound for random number
     * @return the random number generated
     */
    public static double randomNumber(double min, double max) {
        double interval = max - min;
        return Math.random() * interval + min;
    }
    //TODO implement

    /**
     * This method will read in the stored weights of all the individuals in the genetic algorithm's population
     * in the current generation of training.
     *
     * @param fileName the path to the file containing the weights of the GA
     * @return a list containing the chromosome for each individual
     */
    public static ArrayList<ArrayList<Double>> readGAWeights(String fileName) {
        throw new UnsupportedOperationException("Operation not implemented yet");
    }
}
