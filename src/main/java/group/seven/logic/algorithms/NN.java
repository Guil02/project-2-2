package group.seven.logic.algorithms;

import group.seven.enums.Action;
import group.seven.enums.AlgorithmType;
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
            Action chosenAction = getBestAction(outputValues);
            if (chosenAction != null) {
                moves.add(new Move(chosenAction, 1, agent));
            }
        }
        if (moves.isEmpty()) {
            return new Move(Action.NOTHING, 0, agent);
        }
//        System.out.println(moves.get(0) + "\n");
        return moves.poll();
    }

    private Action getBestAction(double[] outputValues) {
        int bestIndex = 0;
        for (int i = 0; i < outputValues.length; i++) {
            if (outputValues[i] > outputValues[bestIndex]) {
                bestIndex = i;
            }
        }
        return switch (bestIndex) {
            case 0 -> MOVE_FORWARD;
            case 1 -> TURN_UP;
            case 2 -> TURN_RIGHT;
            case 3 -> TURN_DOWN;
            case 4 -> TURN_LEFT;
            case 5 -> NOTHING;
            default -> throw new RuntimeException("impossible value found in output index");
        };
    }

    private Matrix getVisionAnalysis() {
        List<Tile> seenTiles = agent.getSeenTiles();
        Matrix input = new Matrix(1, GeneticAlgorithm.inputSize);

        input.set(0, 0, VisionAnalysis.numStaticComponent(seenTiles, WALL));
        input.set(0, 1, VisionAnalysis.numStaticComponent(seenTiles, TARGET));
        input.set(0, 2, VisionAnalysis.numStaticComponent(seenTiles, PORTAL));
        input.set(0, 3, VisionAnalysis.numStaticComponent(seenTiles, EMPTY));
        input.set(0, 4, VisionAnalysis.numIntruders(agent.scenario, seenTiles));
        input.set(0, 5, VisionAnalysis.numGuards(agent.scenario, seenTiles));

        return input;
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.GENETIC_NEURAL_NETWORK;
    }
}
