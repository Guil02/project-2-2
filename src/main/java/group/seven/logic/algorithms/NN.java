package group.seven.logic.algorithms;

import group.seven.enums.Action;
import group.seven.enums.AlgorithmType;
import group.seven.enums.Cardinal;
import group.seven.logic.algorithms.GeneticNeuralNetwork.GeneticAlgorithm;
import group.seven.logic.algorithms.GeneticNeuralNetwork.NeuralNetwork.NeuralNetwork.NeuralNetwork;
import group.seven.logic.algorithms.GeneticNeuralNetwork.VisionAnalysis;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;
import group.seven.model.environment.Tile;
import group.seven.utils.Matrix;

import java.util.LinkedList;
import java.util.List;

import static group.seven.enums.Action.*;
import static group.seven.enums.TileType.*;

public class NN implements Algorithm {

    private final LinkedList<Move> moves = new LinkedList<>();
    private final NeuralNetwork nn;
    Agent agent;

    public NN(Agent agent) {
        this.agent = agent;
        nn = new NeuralNetwork(GeneticAlgorithm.inputSize, GeneticAlgorithm.hiddenSize, GeneticAlgorithm.outputSize);
        nn.setAllWeights(agent.scenario.getChromosome());
    }

    @Override
    public Move getNext() {
        if (moves.isEmpty()) {
            Matrix input = getVisionAnalysis();
            Matrix output = nn.pass_forward(input);
            double[] outputValues = output.toArray();
//            System.out.println(Arrays.toString(outputValues));
            Move chosenAction = getBestAction(outputValues);
            if (chosenAction != null) {
                moves.add(chosenAction);
            }
        }
        if (moves.isEmpty()) {
            return new Move(Action.NOTHING, 0, agent);
        }
//        System.out.println(moves.get(0) + "\n");
        return moves.poll();
    }

    private Move getBestAction(double[] outputValues) {
        int bestIndex = 0;
        for (int i = 0; i < outputValues.length; i++) {
            if (outputValues[i] > outputValues[bestIndex]) {
                bestIndex = i;
            }
        }
        return switch (bestIndex) {
            case 0 -> new Move(MOVE_FORWARD, 1, agent);
            case 1 -> new Move(MOVE_FORWARD, 2, agent);
            case 2 -> new Move(MOVE_FORWARD, 3, agent);
            case 3 -> new Move(MOVE_FORWARD, 4, agent);
            case 4 -> new Move(TURN_UP, 0, agent);
            case 5 -> new Move(TURN_RIGHT, 0, agent);
            case 6 -> new Move(TURN_DOWN, 0, agent);
            case 7 -> new Move(TURN_LEFT, 0, agent);
            case 8 -> new Move(NOTHING, 0, agent);
            default -> throw new RuntimeException("impossible value found in output index");
        };
    }

    private Matrix getVisionAnalysis() {
        List<Tile> seenTiles = agent.getSeenTiles();
        Matrix input = new Matrix(1, GeneticAlgorithm.inputSize);

        input.set(0, 0, VisionAnalysis.numStaticComponent(seenTiles, WALL) * (-0.5 + Math.random()) * 0.1);
        input.set(0, 1, VisionAnalysis.numStaticComponent(seenTiles, TARGET) * (-0.5 + Math.random()) * 0.1);
        input.set(0, 2, VisionAnalysis.numStaticComponent(seenTiles, PORTAL) * (-0.5 + Math.random()) * 0.1);
        input.set(0, 3, VisionAnalysis.numStaticComponent(seenTiles, EMPTY) * (-0.5 + Math.random()) * 0.1);
        input.set(0, 4, VisionAnalysis.numIntruders(agent.scenario, seenTiles) * (-0.5 + Math.random()) * 0.1);
        input.set(0, 5, VisionAnalysis.numGuards(agent.scenario, seenTiles) * (-0.5 + Math.random()) * 0.1);
        input.set(0, 6, directionToNumber(agent.getDirection()));

        return input;
    }

    private int directionToNumber(Cardinal c) {
        return switch (c) {

            case NORTH -> 0;
            case SOUTH -> 1;
            case EAST -> 2;
            case WEST -> 3;
            default -> 0;
        };
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.GENETIC_NEURAL_NETWORK;
    }
}
