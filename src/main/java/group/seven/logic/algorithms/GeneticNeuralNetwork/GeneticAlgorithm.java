package group.seven.logic.algorithms.GeneticNeuralNetwork;

import group.seven.enums.AlgorithmType;
import group.seven.logic.geometric.XY;
import group.seven.logic.simulation.Simulator;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.ScenarioBuilder;
import group.seven.model.environment.Tile;
import group.seven.utils.Config;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static group.seven.enums.TileType.WALL;

public class GeneticAlgorithm {
    public static final String fileName = "GeneticAlgorithm/GAWeights.txt";
    public static final String fileName2 = "/GeneticAlgorithm/GAWeights.txt";
    public static final int inputSize = 7;
    public static final int hiddenSize = 10;
    public static final int outputSize = 9;
    public static final int chromosomeLength = inputSize * hiddenSize + hiddenSize + hiddenSize * outputSize + outputSize;
    static final int populationSize = 1000;
    static final double mutationRate = 0.20;
    static final int amountToStore = populationSize;
    static final int amountOfGenerations = 10000;
    static final int maxTime = Config.MAX_GAME_LENGTH;
    static int maxDistance = 0;
    static Graph<XY, DefaultWeightedEdge> graph;
    static AStarShortestPath<XY, DefaultWeightedEdge> astar;
    private final Population population;

    public GeneticAlgorithm() {
        System.out.println("started ga");
        graph = createGraph(new ScenarioBuilder().build());
        astar = new AStarShortestPath<>(graph, XY::distance);
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
        System.out.println("started threading");
        int count = 0;
        ScenarioBuilder sc = new ScenarioBuilder();
        long startTime = System.currentTimeMillis();
//        ExecutorService executor = Executors.newFixedThreadPool(10);

//        List<Runnable> tasks = new ArrayList<>();
        for (Individual i : population.population) {
//            Runnable runnableTask = () -> {
//                Scenario s = sc.build();
//                s.setChromosome(i.getChromosome());
//                i.setCurrentScenario(s);
//                new Simulator(s);
//                i.calculateFitness();
//                i.setCurrentScenario(null);
//            };
//            Runnable r = new Runnable() {
//                @Override
//                public void run() {
//                    Scenario s = new ScenarioBuilder().build();
//                    s.setChromosome(i.getChromosome());
//                    i.setCurrentScenario(s);
//                    new Simulator(s);
//                    i.calculateFitness();
//                    i.setCurrentScenario(null);
//                }
//            };
//            Callable<String> callableTask = () -> {
//                Scenario s = sc.build();
//                s.setChromosome(i.getChromosome());
//                i.setCurrentScenario(s);
//                new Simulator(s);
//                i.calculateFitness();
//                i.setCurrentScenario(null);
//                return "Task's execution";
//            };
//            tasks.add(r);
            Scenario s = sc.build();
            s.setChromosome(i.getChromosome());
            i.setCurrentScenario(s);
            new Simulator(s);
            i.calculateFitness();
            i.setCurrentScenario(null);
            count++;
        }

//        for (Runnable r : tasks) {
//            executor.submit(r);
//        }
//
//        executor.shutdown();
//        try {
//            if (!executor.awaitTermination(1000000, TimeUnit.MILLISECONDS)) {
//                executor.shutdownNow();
//            }
//        } catch (InterruptedException e) {
//            executor.shutdownNow();
//        }


        System.out.println("time taken: " + (System.currentTimeMillis() - startTime) + "ms");
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

    public Graph<XY, DefaultWeightedEdge> createGraph(Scenario scenario) {
        maxDistance = scenario.HEIGHT + scenario.WIDTH;
        Graph<XY, DefaultWeightedEdge> g = GraphTypeBuilder.undirected()
                .vertexClass(XY.class)
                .edgeClass(DefaultWeightedEdge.class)
                .weighted(true)
                //.allowingSelfLoops(true)
                .buildGraph();

        List<Tile> walls = new LinkedList<>();
        for (int r = 0; r <= scenario.WIDTH; r++) {
            for (int c = 0; c <= scenario.HEIGHT; c++) {
                Tile t = scenario.TILE_MAP.getTile(r, c);
                XY id = t.getXY();
                g.addVertex(id);
                if (t.getType() == WALL) {
                    walls.add(t);
                }
            }
        }

        for (int r = 0; r < scenario.WIDTH; r++) {
            for (int c = 0; c < scenario.HEIGHT; c++) {
                Tile t = scenario.TILE_MAP.getTile(r, c);
                XY id = t.getXY();
                List<Tile> adj = t.getAdjacent().toList();
                adj.forEach(n -> {
                    g.addEdge(id, n.getXY());
                    if (walls.contains(t) || walls.contains(n))
                        g.setEdgeWeight(id, n.getXY(), Integer.MAX_VALUE);
                    else g.setEdgeWeight(id, n.getXY(), 1);
                });
            }
        }

        return g;
    }

}
