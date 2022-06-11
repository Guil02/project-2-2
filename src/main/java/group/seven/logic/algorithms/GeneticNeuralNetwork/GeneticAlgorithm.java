package group.seven.logic.algorithms.GeneticNeuralNetwork;

public class GeneticAlgorithm {
    static final int populationSize = 20;
    static final String fileName = "build/classes/java/main/model/GeneticAlgorithm/weights";
    static final double mutationRate = 0.05;
    static final int amountOfGenerations = 1000;
    static final int inputSize = 20;
    static final int hiddenSize = 5;
    static final int outputSize = 6;
    static final int chromosomeLength = inputSize * hiddenSize + hiddenSize + hiddenSize * outputSize + outputSize;
    static final int maxTime = 1000;
    private Population population;

    public GeneticAlgorithm() {
        population = new Population(populationSize, chromosomeLength, true);

        train();
    }

    public void train() {
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

    }

}
