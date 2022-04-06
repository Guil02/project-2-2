package org.group7.utils;

import com.opencsv.CSVWriter;
import org.group7.model.Scenario;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    private static final String[] HEADER = {"TIME_STEP", "COVERAGE"};


    public static void log(String[] input) {
        try {
            File file = new File("experiment_"+ Scenario.getInstance().getName().replaceAll("\\s", "_") +".csv");
            if (file.createNewFile()) log(HEADER);

            FileWriter writer;
            CSVWriter csvWriter;

            writer = new FileWriter(file, true);
            csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
            csvWriter.writeNext(input);
            csvWriter.close();

        } catch (IOException e) {
            System.out.println("what");
            e.printStackTrace();
        }
    }
}
