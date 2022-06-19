package group.seven.model.environment;

import group.seven.enums.GameMode;
import group.seven.logic.algorithms.GeneticNeuralNetwork.GeneticAlgorithm;
import group.seven.logic.geometric.Rectangle;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Intruder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static group.seven.enums.GameMode.ALL_INTRUDERS_CAUGHT;
import static group.seven.enums.GameMode.ALL_INTRUDER_AT_TARGET;
import static group.seven.enums.TileType.*;
import static group.seven.utils.Methods.print;

public class Scenario {
    public GameMode GUARD_GAME_MODE = ALL_INTRUDERS_CAUGHT;
    public GameMode INTRUDER_GAME_MODE = ALL_INTRUDER_AT_TARGET;
    public String NAME = "untitled map";
    public int WIDTH, HEIGHT;
    public int TILE_SIZE = 10;
    public double INTRUDER_BASE_SPEED, INTRUDER_SPRINT_SPEED;
    public double GUARD_BASE_SPEED, GUARD_SPRINT_SPEED;
    public int NUM_GUARDS, NUM_INTRUDERS, NUM_AGENTS;
    public int VIEW_DISTANCE; //TODO could differ per agent?
    public int NUM_MARKERS;
    public int SMELL_DISTANCE;
    public double TIME_STEP, SCALING;
    public Component targetArea = new Component(new Rectangle(), TARGET, null, null);
    public Component intruderSpawnArea = new Component(new Rectangle(), INTRUDER_SPAWN, null, null);
    public Component guardSpawnArea = new Component(new Rectangle(), GUARD_SPAWN, null, null);

    public int INTRUDERS_CAUGHT = 0;
    public int INTRUDERS_AT_TARGET = 0;

    public final List<Component> walls = new ArrayList<>(20);
    public final List<Component> shadedAreas = new ArrayList<>();
    public final List<Component> portals = new ArrayList<>();

    //replaced the get static component's method with a list containing them instead
    public final List<Component> COMPONENTS = new ArrayList<>(30);
    public final Set<Agent> agents = new HashSet<>();

    public TileMap TILE_MAP;
    private long currentTime;
    public int ID = 0;

    public List<Double> chromosome = new ArrayList<>(GeneticAlgorithm.chromosomeLength);

    public Scenario() {
//        SCENARIO = this;//empty constructor used by ScenarioBuilder
    }

    public void addWall(Rectangle wall) {
        walls.add(new Component(wall, WALL, null, null));
    }

    public void addShaded(Rectangle shaded) {
        shadedAreas.add(new Component(shaded, SHADED, null, null));
    }

    public void addPortals(Component tp) {
        portals.add(tp);
    }

    public TileMap getTileMap() {
        return TILE_MAP;
    }

    public void removeIntruder(Intruder intruder) {
        agents.remove(intruder);
        INTRUDERS_CAUGHT++;
        print("Intruders caught: " + INTRUDERS_CAUGHT + "\t agents left: " + agents.size(), true);
    }

    public long currentTime() {
        return currentTime;
    }

    public List<Double> getChromosome() {
        return chromosome;
    }

    public void setChromosome(List<Double> chromosome) {
        this.chromosome = chromosome;
    }

    public int getId() {
        return ID++;
    }
}
