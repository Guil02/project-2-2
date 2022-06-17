package group.seven.model.agents;


import group.seven.enums.AlgorithmType;
import group.seven.enums.Cardinal;
import group.seven.logic.algorithms.*;
import group.seven.logic.vision.ConeVision;
import group.seven.logic.vision.RectangleVision;
import group.seven.logic.vision.Vision;
import group.seven.model.environment.Marker;
import group.seven.model.environment.Pheromone;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.Tile;
import group.seven.utils.Config;

import java.util.ArrayList;
import java.util.List;

import static group.seven.enums.TileType.GUARD;

public class Guard extends Agent {

    public final int PHEROMONE_LIFETIME = 20;
    private final int ID;
    private final int maxSpeed;
    private final ArrayList<Pheromone> pheromones = new ArrayList<>();
    public int currentSpeed;
    Algorithm algorithm;
    private Vision vision;
    private ArrayList<Marker> markers = new ArrayList<>();

    public Guard(int x, int y, Scenario s, Algorithm algorithm, int startSpeed, Vision vision, ArrayList<Marker> markers) {
        this(x, y, s);
        this.algorithm = algorithm;
        this.currentSpeed = startSpeed;
        this.vision = vision;
        this.markers = markers;
    }

    public Guard(int x, int y, Scenario s, AlgorithmType algorithmType) {
        this(x, y, s);
        algorithm = initAlgo(algorithmType);
//        System.out.println(algorithmType);
    }


    public Guard(int x, int y, Scenario s) {
        super(x, y, s);
        ID = s.getId();
        agentType = GUARD;
        currentSpeed = 3; //DEFAULT //TODO base speed?
        direction = Cardinal.randomDirection();
        algorithm = initAlgo(Config.ALGORITHM_GUARD); //DEFAULT
        vision = new ConeVision(this); //DEFAULT
        maxSpeed = (int) scenario.GUARD_SPRINT_SPEED;

        // algorithm = new RandomTest(this);
    }

    public Algorithm initAlgo(AlgorithmType type) {
        return switch (type) {
            case BRICK_AND_MORTAR   -> new BrickAndMortar(this);
            case ANT_PURSUIT        -> new AntsPursuit(this);
            case ANT                -> new Ant(this);
            case EVAW               -> new EVAW(this);
            case FISH               -> new FishSwarm(this);
            default                 -> new RandomAlt(this);
        };
    }

    @Override
    public void updateVision() {
        seenTiles.clear();
        List<Tile> newTiles = vision.updateAndGetVisionAgent(this);
        seenTiles = duplicatedTiles(seenTiles, newTiles);
        //print(seenTiles);
    }

    @Override
    public Move calculateMove() {
        return algorithm.getNext();
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public int getSpeed() {
        return currentSpeed;
    }

    @Override
    public void setSpeed(int speed) {
        this.currentSpeed = speed;
    }

    @Override
    public String toString() {
        return "Guard{" +
                "ID=" + ID +
                ", x=" + x +
                ", y=" + y +
                ", direction=" + direction +
                ", speed=" + currentSpeed +
                ", algorithm=" + algorithm.getType() +
                '}';
    }
}
