package group.seven.logic.simulation.experimentation;

import group.seven.logic.simulation.Simulator;
import group.seven.model.agents.Agent;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.ScenarioBuilder;
import group.seven.utils.Tuple;

import java.io.File;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import static group.seven.logic.simulation.experimentation.Experimentation.loadMapFiles;

//TODO: Ignore, 2nd draft
public class Experimenter extends Simulator {

    public File[] mapFiles;

    int numRuns;
    DoubleSummaryStatistics stats;
    Logger dataLogger;
    String experimentName;
    boolean append;
    //time, speed, num_rotations, avg_intruders_caught, avg_intruder_success
    //time, FOV, num_rotations, avg_intruders_caught, avg_intruder_success
    //time, ratio, num_guards, num_intruders, num_rotations, avg_intruder_caught, avg_intruder_success

    public Experimenter(Scenario scenario) {
        super(scenario, true);
        List<String> maps = new ArrayList<>(List.of(
                "biggerTestMap.txt",
                "mazeMap.txt",
                "small_map.txt",
                "tiny.txt"
        ));

        //load each of the mapFiles from the passed list of map file names.
        mapFiles = loadMapFiles(maps).toArray(new File[maps.size()]);
        Scenario mazeMap = new ScenarioBuilder(mapFiles[1]).build();
        System.out.println(mazeMap.NAME);
    }

    public void run(Scenario s) {
        for (int i = 0; i < numRuns; i++) {
            runExperiment();
        }

    }

    private void runExperiment() {
        //Simulator sim = new Simulator();
        boolean running = true;
        while (running && count < 100000) {
            long now = System.nanoTime();
            count++;
            update();           //update model
            //elapsedTimeSteps += timeStep; //update elapsed time steps
            // System.out.print("\rElapsed Time Steps: " + elapsedTimeSteps + "\t framerate: " + ((double) now - prev) / 1e9);
            Tuple<Double, Double> coverage = calculateCoverage();
            for (Agent a : scenario.agents)
                //a.setTime(elapsedTimeSteps);
                prev = System.nanoTime();
            running = false;
        }
    }


}
