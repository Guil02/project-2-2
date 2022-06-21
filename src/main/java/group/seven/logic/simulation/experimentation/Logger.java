package group.seven.logic.simulation.experimentation;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import static com.opencsv.ICSVWriter.*;
//TODO not sure what to do with this class yet

/**
 * Default mode is to append to existing files, and to create new file with supplied header
 * if a file with that name does not already exists
 */
public class Logger {

    //for example
    private String[] header = {"TIME_STEP", "COVERAGE"};

    private String experimentName;

    /**
     * This creates a new logger object to log the data points specified in the header
     * Each string in the Header is a column in the CSV file
     *
     * @param header array of column names
     */
    public Logger(String[] header) {
        this.header = header;
        this.experimentName = "Experiment: " + Arrays.toString(header);
    }

    /**
     * Change the name of the csv file from its default
     *
     * @param experimentName the desired name of CSV file
     *                       Remember, if not changed it will append future
     *                       experiments to same file
     * @return this instance of logger for method chaining
     */
    public Logger setExperimentName(String experimentName) {
        this.experimentName = experimentName;
        return this;
    }

    /**
     * Log time step and coverage to a csv file
     *
     * @param input String[] of [TimeStep, Coverage] to be logged
     * @param name  name of the file/map/scenario/experiment
     */
    public void log(String[] input, String name) {
        try {
            File file = new File("experiment_" + name.replaceAll("\\s", "_") + ".csv");
            if (file.createNewFile()) log(header, name);

            FileWriter writer;
            CSVWriter csvWriter;

            writer = new FileWriter(file, true);
            csvWriter = new CSVWriter(writer, DEFAULT_SEPARATOR, NO_QUOTE_CHARACTER,
                    DEFAULT_ESCAPE_CHARACTER, DEFAULT_LINE_END);
            csvWriter.writeNext(input);
            csvWriter.close();
            writer.close();

        } catch (IOException e) {
            System.out.println("what");
            e.printStackTrace();
        }
    }

    /**
     * If you supplied an experimenent name in the setter you would prefer to use
     *
     * @param input String of data points. Try not to add fancy characters I think
     */
    public void log(String[] input) {
        log(input, experimentName);
    }
}
