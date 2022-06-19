package group.seven.logic.algorithms.GeneticNeuralNetwork;

import group.seven.enums.AlgorithmType;
import group.seven.logic.simulation.Simulator;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.ScenarioBuilder;
import group.seven.utils.Config;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithm {
    public static final String fileName = "GeneticAlgorithm/GAWeights.txt";
    public static final String fileName2 = "/GeneticAlgorithm/GAWeights.txt";
    public static final int inputSize = 7;
    public static final int hiddenSize = 5;
    public static final int outputSize = 9;
    public static final int chromosomeLength = inputSize * hiddenSize + hiddenSize + hiddenSize * outputSize + outputSize;
    static final int populationSize = 200;
    static final double mutationRate = 0.1;
    static final int amountToStore = populationSize;
    static final int amountOfGenerations = 10000;
    static final int maxTime = Config.MAX_GAME_LENGTH;
    private final Population population;

    public GeneticAlgorithm() {
        System.out.println("started ga");
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
        population.isSorted = false;
//        List<Scenario> scenarios = createScenario();
//        ExecutorService executor = Executors.newCachedThreadPool();
        System.out.println("started threading");
        int count = 0;
        ScenarioBuilder sc = new ScenarioBuilder();
        long startTime = System.currentTimeMillis();

        for (Individual i : population.population) {
            Scenario s = sc.build();
            s.setChromosome(i.getChromosome());
            i.setCurrentScenario(s);
            new Simulator(s);
            i.calculateFitness();
            i.setCurrentScenario(null);
            count++;
        }

//        for (Scenario scenario : scenarios) {
////            Task<Integer> t = new Task<>() {
////                @Override
////                protected Integer call() throws Exception {
////                    new Simulator(scenario);
////                    return 0;
////                }
////            };
////            executor.submit(t);
//            new Simulator(scenario);
//
//            count++;
////            System.out.print("number: " + count++ + "\r");
//        }
        System.out.println("time taken: " + (System.currentTimeMillis() - startTime) + "ms");
//        System.gc();
//        executor.shutdown();
//        try {
//            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        scenarios.clear();
        System.out.println("finished threading");
    }

    private List<Scenario> createScenario() {
        List<Scenario> list = new ArrayList<>(populationSize);
        //TODO CHANGE PARAMETER OF THE SCENARIO BUILDER TO THE STRING OF THE MAP USED
        ScenarioBuilder sc = new ScenarioBuilder();
        for (Individual i : population.population) {
            Scenario a = sc.build();
            i.setCurrentScenario(a);
            a.setChromosome(i.getChromosome());
            Config.ALGORITHM_INTRUDER = AlgorithmType.GENETIC_NEURAL_NETWORK;
            list.add(a);
        }
        return list;
    }

}
