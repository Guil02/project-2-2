package group.seven.logic.algorithms.GeneticNeuralNetwork;

import group.seven.logic.geometric.XY;
import group.seven.model.agents.Agent;
import group.seven.model.environment.Scenario;
import group.seven.utils.Methods;

import java.util.ArrayList;
import java.util.List;

import static group.seven.enums.TileType.INTRUDER;

public class Individual {
    final int chromosomeLength;
    List<Double> chromosome;
    double fitness;
    int timeTakenToFinish;

    Scenario currentScenario;

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
//        if (getCurrentScenario().getCount() < Config.MAX_GAME_LENGTH && getCurrentScenario().isIntruderWin()) {
//            fitness = (1 - (((double) getCurrentScenario().getCount()) / GeneticAlgorithm.maxTime)) * 0.5 + 0.5;
//        } else {
        int amountOfAgent = 0;
        double distance = 0;
        for (Agent a : getCurrentScenario().TILE_MAP.agents) {
            if (a.getType() == INTRUDER) {
                distance += calculateDistanceFromTarget(a);
                amountOfAgent++;
            }
        }
        double averageDistance = distance / amountOfAgent;
        double b = getCurrentScenario().HEIGHT;
        b = b * b;
        double c = getCurrentScenario().WIDTH ^ 2;
        c = c * c;
        double maxDistance = Math.sqrt(b + c);
        fitness = 1 - (averageDistance / maxDistance);
        if (Double.isNaN(fitness)) {
            throw new IllegalStateException("fitness value was NAN" + "\naverage distance " + averageDistance + "\n");
        }

//            fitness = 1 - (((double) timeTakenToFinish) / GeneticAlgorithm.maxTime);
//        }
    }

    private double calculateDistanceFromTarget(Agent a) {
        XY target = getCurrentScenario().targetArea.getXY();
        XY currentPosition = a.getXY();
        double b = (currentPosition.x() - target.x());
        b = b * b;
        double c = (currentPosition.y() - target.y());
        c = c * c;
        return Math.sqrt(b + c);
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
                return Methods.randomNumber(-1, 1);
            } else return Methods.randomNumber(-1, 1);
        }
        return weight;
    }

    public Scenario getCurrentScenario() {
        return currentScenario;
    }

    public void setCurrentScenario(Scenario currentScenario) {
        this.currentScenario = currentScenario;
    }

    @Override
    public String toString() {
        return "Individual{" +
                "fitness=" + fitness +
                '}';
    }
}
