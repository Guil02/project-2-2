package group.seven.logic.simulation.experimentation;

import group.seven.enums.Status;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.ScenarioBuilder;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static group.seven.enums.Status.GAME_OVER;
import static group.seven.enums.Status.RUNNING;
import static group.seven.utils.Methods.print;

public class Experimentation {

    public static File[] mapFiles;

    public static void main(String[] args) {
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


        Experimentation exp = new Experimentation(mazeMap);
        Experiment e = exp.newExperiment();
        e.start();
    }

    /* ------------ */

    class Experiment {
        Status status;
        Scenario scenario;
        double timeStep = 1;
        double elapsedTime = 0;
        long prev = 0L;
        int count = 0;

        public Experiment(Scenario s) {
            scenario = s;
        }

        public void start() {
            boolean running = true;
            status = RUNNING;
            System.out.println("started " + scenario.NAME + "\n");

            while (running) {
                tick();
                //status = status.type;
                if (status.type == GAME_OVER) {
                    running = false;
                    //print(status);
                }
            }

            print(status);
        }

        public void tick() {
            count++;
            //update();           //update model
            elapsedTime += timeStep; //update elapsed time steps
            System.out.print("\rElapsed Time Steps: " + elapsedTime + " --- fps: " + (System.nanoTime() - prev) / 100_000_000.0);
            if (count > 100000) status = GAME_OVER;
            prev = System.nanoTime();
        }
    }

    /*---------------*/

    Scenario current;

    public Experimentation(Scenario s) {
        current = s;
    }

    private Experiment newExperiment() {
        Experiment e = new Experiment(current);
        return e;
    }

    /* ----------------------- */

    public static List<File> loadMapFiles(List<String> maps) {
        List<File> mapFiles = new ArrayList<>();
        try {
            for (String map : maps)
                mapFiles.add(new File(Experimentation.class.getResource("/scenarios/" + map).toURI()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return mapFiles;
    }
}
