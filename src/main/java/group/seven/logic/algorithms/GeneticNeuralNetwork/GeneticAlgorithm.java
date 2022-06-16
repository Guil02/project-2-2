package group.seven.logic.algorithms.GeneticNeuralNetwork;

import group.seven.logic.simulation.Simulator;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.ScenarioBuilder;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GeneticAlgorithm {
    public static final String fileName = "/GeneticAlgorithm/GAWeights.txt";
    public static final int inputSize = 20;
    public static final int hiddenSize = 5;
    public static final int outputSize = 6;
    static final int populationSize = 20;
    static final double mutationRate = 0.05;
    static final int amountOfGenerations = 1000;
    static final int chromosomeLength = inputSize * hiddenSize + hiddenSize + hiddenSize * outputSize + outputSize;
    static final int maxTime = 1000;
    private final Population population;

    public GeneticAlgorithm() {
        population = new Population(populationSize, chromosomeLength, true);

        train();
    }

    private void train() {
        int index = 0;
        while (index < amountOfGenerations) {
            System.out.println("Generation: " + index);
            runSimulations();
            population.updateGeneration();
            population.storeWeights();
            index++;
        }
    }

    private void runSimulations() {
        List<Scenario> scenarios = createScenario();
        ExecutorService executor = Executors.newCachedThreadPool();
        for (Scenario scenario : scenarios) {
            Task<Integer> t = new Task<>() {
                @Override
                protected Integer call() throws Exception {
                    new Simulator(scenario);
                    return 0;
                }
            };
            executor.submit(t);
        }
    }

    private List<Scenario> createScenario() {
        List<Scenario> list = new ArrayList<>(populationSize);
        //TODO CHANGE PARAMETER OF THE SCENARIO BUILDER TO THE STRING OF THE MAP USED
        ScenarioBuilder sc = new ScenarioBuilder();
        for (Individual i : population.population) {
            Scenario a = sc.build();
            a.setChromosome(i.getChromosome());
            list.add(a);
        }
        return list;
    }

}
