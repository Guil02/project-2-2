package group.seven.logic.algorithms.GeneticNeuralNetwork;

import group.seven.enums.AlgorithmType;
import group.seven.logic.simulation.Simulator;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.ScenarioBuilder;
import group.seven.utils.Config;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GeneticAlgorithm {
    public static final String fileName = "GeneticAlgorithm/GAWeights.txt";
    public static final String fileName2 = "/GeneticAlgorithm/GAWeights.txt";
    public static final int inputSize = 6;
    public static final int hiddenSize = 5;
    public static final int outputSize = 6;
    public static final int chromosomeLength = inputSize * hiddenSize + hiddenSize + hiddenSize * outputSize + outputSize;
    static final int populationSize = 20;
    static final double mutationRate = 0.05;
    static final int amountOfGenerations = 10;
    static final int maxTime = 1000;
    static final int amountToStore = 1;
    private final Population population;

    public GeneticAlgorithm() {
        System.out.println("started ga");
        population = new Population(populationSize, chromosomeLength, false);
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
        System.out.println("started threading");
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
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private List<Scenario> createScenario() {
        List<Scenario> list = new ArrayList<>(populationSize);
        //TODO CHANGE PARAMETER OF THE SCENARIO BUILDER TO THE STRING OF THE MAP USED
        ScenarioBuilder sc = new ScenarioBuilder();
        for (Individual i : population.population) {
            Scenario a = sc.build();
            a.setChromosome(i.getChromosome());
            Config.ALGORITHM_INTRUDER = AlgorithmType.GENETIC_NEURAL_NETWORK;
            list.add(a);
        }
        return list;
    }

}
