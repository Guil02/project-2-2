package group.seven.logic.algorithms.GeneticNeuralNetwork;

import group.seven.utils.Config;
import group.seven.utils.Methods;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static group.seven.logic.algorithms.GeneticNeuralNetwork.GeneticAlgorithm.fileName2;

public class Population {
    private final static int numberOfStrongest = 40;
    private final static int numberOfSurvivors = 20;
    private final static String fileName = "TBD";
    private final int chromosomeLength;
    List<Individual> population;
    int populationSize;
    boolean isSorted = false;

    public Population(int populationSize, int chromosomeLength, boolean randomWeights) {
        this.chromosomeLength = chromosomeLength;
        population = new ArrayList<>();
        this.populationSize = populationSize;
        for (int i = 0; i < populationSize; i++) {
            population.add(new Individual(chromosomeLength));
        }

        if (!randomWeights) {
            readInWeights();
        }
    }

    private void readInWeights() {
        if (Config.DEBUG_MODE) {
            System.out.println("started reading weights");
        }
        List<List<Double>> weights = Methods.readGAWeights(GeneticAlgorithm.fileName);
        if (Config.DEBUG_MODE) {
            System.out.println("finished reading weights");
        }
        int index = 0;
        for (Individual i : population) {
            i.setChromosome(weights.get(index++));
        }
    }

    /**
     * Updates the population to a new generation. (Combination of consecutive methods)
     */
    public void updateGeneration() {
//        updateFitness();
        for (Individual i : population) {
            i.setCurrentScenario(null);
        }
        steadyStateSelection();
        mutatePopulation();
    }

    private void updateFitness() {
        for (Individual i : population) {
            i.calculateFitness();
        }
        isSorted = false;
    }

    private void steadyStateSelection() {
        if (!isSorted) {
            quickSort(population, 0, population.size() - 1);
            Collections.reverse(population);
            isSorted = true;
        }
        System.out.println("Best performance: " + population.get(0).getFitness());
        birth();
    }

    private void mutatePopulation() {
        for (Individual i : population) {
            i.mutateChromosome();
        }
    }

    private void birth() {
        for (int i = numberOfSurvivors; i < population.size(); i = i + 2) {
            int motherIndex = (int) Methods.randomNumber(0, numberOfStrongest - 1);
            Individual mother = population.get(motherIndex);
            int fatherIndex = (int) Methods.randomNumber(0, numberOfStrongest - 1);
            Individual father = population.get(fatherIndex);
            crossOver(mother, father, population.get(i), population.get(i + 1));
        }
    }

    private void addToList(List<Double> list, List<Double> target, int startIndex, int endIndex) {
        for (int i = startIndex; i < endIndex; i++) {
            target.add(list.get(i));
        }
    }

    private void crossOver(Individual mother, Individual father, Individual child1, Individual child2) {
        int[] crossoverPoints = generateCrossOverPoints();

        List<Double> geneMother = mother.getChromosome();
        List<Double> geneFather = father.getChromosome();
        List<Double> geneChild1 = createCrossOver(geneMother, geneFather, crossoverPoints, true);
        List<Double> geneChild2 = createCrossOver(geneMother, geneFather, crossoverPoints, false);
        child1.setChromosome(geneChild1);
        child2.setChromosome(geneChild2);
    }

    private List<Double> createCrossOver(List<Double> geneMother, List<Double> geneFather, int[] crossoverPoints, boolean motherFirst) {
        List<Double> gene = new ArrayList<>();
        if (motherFirst) {
            addToList(geneMother, gene, 0, crossoverPoints[0]);
            addToList(geneFather, gene, crossoverPoints[0], crossoverPoints[1]);
            addToList(geneMother, gene, crossoverPoints[1], chromosomeLength);
        } else {
            addToList(geneFather, gene, 0, crossoverPoints[0]);
            addToList(geneMother, gene, crossoverPoints[0], crossoverPoints[1]);
            addToList(geneFather, gene, crossoverPoints[1], chromosomeLength);
        }

        return gene;
    }

    private int[] generateCrossOverPoints() {
        int firstCrossOverPoint = (int) Methods.randomNumber(0, chromosomeLength);
        int secondCrossOverPoint = (int) Methods.randomNumber(0, chromosomeLength);
        if (secondCrossOverPoint < firstCrossOverPoint) {
            int temp = firstCrossOverPoint;
            firstCrossOverPoint = secondCrossOverPoint;
            secondCrossOverPoint = temp;
        }
        return new int[]{firstCrossOverPoint, secondCrossOverPoint};
    }

    public void quickSort(List<Individual> agents, int low, int high) {
        if (low < high) {
            int part = partition(agents, low, high);
            quickSort(agents, low, part - 1);
            quickSort(agents, part + 1, high);
        }
    }

    private int partition(List<Individual> agents, int low, int high) {
        double pivot = agents.get(high).getFitness();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (agents.get(j).getFitness() < pivot) {
                i++;
                Individual tempAgent = agents.get(i);
                agents.set(i, agents.get(j));
                agents.set(j, tempAgent);
            }
        }
        Individual tempAgent = agents.get(i + 1);
        agents.set(i + 1, agents.get(high));
        agents.set(high, tempAgent);
        return i + 1;
    }


    public void storeWeights() {
        List<List<Double>> list = new ArrayList<>();
        for (int i = 0; i < GeneticAlgorithm.amountToStore; i++) {
            list.add(population.get(i).getChromosome());
        }
        try {
            Methods.writeWeights(list, Paths.get(getClass().getResource(fileName2).toURI()).toFile());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
//        throw new UnsupportedOperationException("Operation not implemented yet");
    }
}
