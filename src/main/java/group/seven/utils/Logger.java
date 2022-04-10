package group.seven.utils;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.opencsv.ICSVWriter.*;

public class Logger {
    private static final String[] HEADER = {"TIME_STEP", "COVERAGE"};

    /**
     * Log time step and coverage to a csv file
     * @param input String[] of [TimeStep, Coverage] to be logged
     * @param name name of the file/map/scenario
     */
    public static void log(String[] input, String name) {
        try {
            File file = new File("experiment_" + name.replaceAll("\\s", "_") +".csv");
            if (file.createNewFile()) log(HEADER, name);

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
}
