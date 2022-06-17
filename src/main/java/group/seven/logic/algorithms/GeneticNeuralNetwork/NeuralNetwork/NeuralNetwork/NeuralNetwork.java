package group.seven.logic.algorithms.GeneticNeuralNetwork.NeuralNetwork.NeuralNetwork;

import group.seven.utils.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NeuralNetwork {
    private Matrix weight_hidden;
    private Matrix weight_output;
    private Matrix bias_hidden;
    private Matrix bias_output;

    public NeuralNetwork(int input, int hidden, int output) {
        weight_hidden = new Matrix(input, hidden);
        weight_output = new Matrix(hidden, output);
        bias_hidden = new Matrix(1, hidden);
        bias_output = new Matrix(1, output);
    }

    public static NeuralNetwork readInNetwork(String filename) {
        return null;
    }

    public Matrix pass_forward(Matrix input) {
        Matrix output = input.dot(weight_hidden).add(bias_hidden).sigmoid();
        output = output.dot(weight_output).add(bias_output).sigmoid();
        return output;
    }

    public Matrix getWeight_hidden() {
        return weight_hidden;
    }

    public void setWeight_hidden(Matrix weight_hidden) {
        this.weight_hidden = weight_hidden;
    }

    public Matrix getWeight_output() {
        return weight_output;
    }

    public void setWeight_output(Matrix weight_output) {
        this.weight_output = weight_output;
    }

    public Matrix getBias_hidden() {
        return bias_hidden;
    }

    public void setBias_hidden(Matrix bias_hidden) {
        this.bias_hidden = bias_hidden;
    }

    public Matrix getBias_output() {
        return bias_output;
    }

    public void setBias_output(Matrix bias_output) {
        this.bias_output = bias_output;
    }

    public List<Double> getAllWeights() {
        List<Double> weights = new ArrayList<>();
        Double[] weight_hidden = Arrays.stream(this.weight_hidden.toArray()).boxed().toArray(Double[]::new);
        Double[] bias_hidden = Arrays.stream(this.bias_hidden.toArray()).boxed().toArray(Double[]::new);
        Double[] weight_output = Arrays.stream(this.weight_output.toArray()).boxed().toArray(Double[]::new);
        Double[] bias_output = Arrays.stream(this.bias_output.toArray()).boxed().toArray(Double[]::new);
        weights.addAll(Arrays.asList(weight_hidden));
        weights.addAll(Arrays.asList(bias_hidden));
        weights.addAll(Arrays.asList(weight_output));
        weights.addAll(Arrays.asList(bias_output));
        return weights;
    }

    public void setAllWeights(List<Double> weights) {
        int index = 0;
        weight_hidden.setData(setWeight(weight_hidden.rows, weight_hidden.columns, weights, index));
        index += weight_hidden.rows * weight_hidden.columns;
        bias_hidden.setData(setWeight(bias_hidden.rows, bias_hidden.columns, weights, index));
        index += bias_hidden.rows * bias_hidden.columns;
        weight_output.setData(setWeight(weight_output.rows, weight_output.columns, weights, index));
        index += weight_output.rows * weight_output.columns;
        bias_output.setData(setWeight(bias_output.rows, bias_output.columns, weights, index));
    }

    private double[][] setWeight(int row, int col, List<Double> weights, int startIndex) {
        double[][] newWeights = new double[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                newWeights[i][j] = weights.get(startIndex++);
            }
        }
        return newWeights;
    }

    @Override
    public String toString() {
        return "NeuralNetwork{" +
                "weight_hidden=" + weight_hidden +
                ", weight_output=" + weight_output +
                ", bias_hidden=" + bias_hidden +
                ", bias_output=" + bias_output +
                '}';
    }
}
