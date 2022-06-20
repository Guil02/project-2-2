package group.seven.logic.simulation.experimentation;

import group.seven.enums.AlgorithmType;
import group.seven.logic.simulation.Simulator;
import group.seven.logic.vision.Vision;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Guard;
import group.seven.model.agents.Intruder;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.ScenarioBuilder;
import group.seven.utils.Config;
import group.seven.utils.Tuple;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.List;

//TODO: third draft
public class Measures {

    int n = 50; //default
    String name;

    DoubleSummaryStatistics guardSpeed = new DoubleSummaryStatistics();
    DoubleSummaryStatistics kills = new DoubleSummaryStatistics();
    DoubleSummaryStatistics guardFOVSize = new DoubleSummaryStatistics();
    DoubleSummaryStatistics guardCoverage = new DoubleSummaryStatistics();
    DoubleSummaryStatistics guardTilesTraversed = new DoubleSummaryStatistics();
    IntSummaryStatistics guardWins = new IntSummaryStatistics();

    IntSummaryStatistics intruderSpeed = new IntSummaryStatistics();
    DoubleSummaryStatistics targetReached = new DoubleSummaryStatistics();
    DoubleSummaryStatistics intruderCoverage = new DoubleSummaryStatistics();
    DoubleSummaryStatistics intruderFOVSize = new DoubleSummaryStatistics();
    DoubleSummaryStatistics intruderTilesTraversed = new DoubleSummaryStatistics();
    IntSummaryStatistics intruderWins = new IntSummaryStatistics();
    DoubleSummaryStatistics distanceToTarget = new DoubleSummaryStatistics();

    DoubleSummaryStatistics endTime = new DoubleSummaryStatistics();

    List<Double> gSpeeds = new ArrayList<>(n);
    List<Double> iSpeeds = new ArrayList<>(n);
    List<Double> captures = new ArrayList<>(n);
    List<Double> inTargets = new ArrayList<>(n);
    List<Double> gFOVs = new ArrayList<>(n);
    List<Double> iFOVs = new ArrayList<>(n);
    List<Double> gTraveled = new ArrayList<>(n);
    List<Double> iTraveled = new ArrayList<>(n);
    List<Double> gCoverage = new ArrayList<>(n);
    List<Double> iCoverage = new ArrayList<>(n);
    List<Double> endTimes = new ArrayList<>(n);
    List<Integer> gWins = new ArrayList<>(n);
    List<Integer> iWins = new ArrayList<>(n);
    List<Double> targetDistance = new ArrayList<>(n);

    File scenarioFile = new File(getClass().getResource(Config.DEFAULT_MAP_PATH).toURI());

    double mapDensity;
    AlgorithmType guardAlgorithm = Config.ALGORITHM_GUARD;
    AlgorithmType intruderAlgorithm = Config.ALGORITHM_INTRUDER;
    Vision.Type visionType = Vision.Type.CONE;

    public Measures(String experimentName) throws URISyntaxException {
        name = experimentName;
    }

    public Measures setScenarioFile(File filePath) {
        scenarioFile = filePath;
        return this;
    }

    public Measures setAgentVision(Vision.Type visionType) {
        this.visionType = visionType;
        return this;
    }

    public Measures setGuardAlgorithm(AlgorithmType guardAlgorithm) {
        this.guardAlgorithm = guardAlgorithm;
        return this;
    }

    public Measures setIntruderAlgorithm(AlgorithmType intruderAlgorithm) {
        this.intruderAlgorithm = intruderAlgorithm;
        return this;
    }

    public Measures setNumRuns(int numRuns) {
        n = numRuns;
        return this;
    }

    boolean built = false;

    public Measures build() {
        built = true;
        Config.GUI_ON = false;
        //TODO create Header for CSV data Logger
        return this;
    }

    public void start() {
        if (built) {
            //start simulating
            for (int i = 0; i < n; i++) {
                Scenario s = new ScenarioBuilder(scenarioFile).build();
                Runner runner = new Runner(s);
                runner.begin();
            }
        } else {
            System.out.println("Please build() the experiment before starting it!");
        }
    }

    class Runner extends Simulator {

        public Runner(Scenario scenario) {
            super(scenario, true);
        }

        public void begin() {
            boolean running = true;
            while (running) {
                count++;
                update();           //update model

                elapsedTimeSteps += timeStep; //update elapsed time steps
                for (Agent a : scenario.agents)
                    a.setTime(elapsedTimeSteps);

                //TODO: implement GameOver condition checking
                if (count > 100000) running = false;
            }
            double timeOver = elapsedTimeSteps;
        }

        public void collect() {
            Tuple<Double, Double> coverage = calculateCoverage();
            gCoverage.add(coverage.a());
            iCoverage.add(coverage.b());

            for (Agent a : scenario.agents) {
                if (a instanceof Guard g) {
                    gSpeeds.add((double) g.getSpeed());
                    gFOVs.add((double) g.getSeenTiles().size());
                } else if (a instanceof Intruder i) {
                    iSpeeds.add((double) i.getSpeed());
                    iFOVs.add((double) i.getSeenTiles().size());
                }
            }
        }
    }
}
