package group.seven.logic.algorithms.GeneticNeuralNetwork;

import group.seven.utils.Methods;

import java.util.ArrayList;
import java.util.List;

public class Individual {
    final int chromosomeLength;
    List<Double> chromosome;
    double fitness;
    int timeTakenToFinish;

    public Individual(List<Double> chromosome, int chromosomeLength) {
        this.chromosome = chromosome;
        this.chromosomeLength = chromosomeLength;
    }

    public Individual(int chromosomeLength) {
        chromosome = new ArrayList<>();
        this.chromosomeLength = chromosomeLength;
        initializeRandomWeights();
    }

    private void initializeRandomWeights() {
        for (int i = 0; i < chromosomeLength; i++) {
            chromosome.add(Methods.randomNumber(-1, 1));
        }
    }


    public List<Double> getChromosome() {
        return chromosome;
    }

    public void setChromosome(List<Double> chromosome) {
        this.chromosome = chromosome;
    }

    /**
     * @return current fitness value
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * Sets the current fitness value.
     *
     * @param fitness new fitness
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public void calculateFitness() {
        fitness = 1 - (((double) timeTakenToFinish) / GeneticAlgorithm.maxTime);
    }

    /**
     * Mutates some weights of the agent randomly.
     */
    public void mutateChromosome() {
        for (int i = 0; i < chromosome.size(); i++) {
            chromosome.set(i, mutateChromosome(chromosome.get(i)));
        }
    }

    /**
     * Mutates a single chromosome.
     *
     * @param weight old weight
     * @return new weight (after mutation)
     */
    private double mutateChromosome(double weight) {
        double r = Math.random();
        if (r < GeneticAlgorithm.mutationRate) {
            if (Math.random() < 0.5) {
                return weight + weight * Methods.randomNumber(-0.1, 0.1);
            } else return weight - weight * Methods.randomNumber(-0.1, 0.1);
        }
        return weight;
    }
}
