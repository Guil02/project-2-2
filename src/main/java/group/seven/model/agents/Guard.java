package group.seven.model.agents;


import group.seven.enums.MarkerType;
import group.seven.enums.PheromoneType;
import group.seven.logic.algorithms.Algorithm;
import group.seven.logic.algorithms.RandomMoves;
import group.seven.logic.vision.RectangleVision;
import group.seven.logic.vision.Vision;
import group.seven.model.environment.Marker;
import group.seven.model.environment.Pheromone;
import group.seven.model.environment.Scenario;

import java.util.ArrayList;

import static group.seven.enums.Cardinal.NORTH;
import static group.seven.enums.TileType.GUARD;

public class Guard extends Agent {

    public final int PHEROMONELIFETIME = 20;
    private final int ID;
    private final int maxSpeed = (int) Scenario.GUARD_SPRINT_SPEED;
    public int currentSpeed;
    Algorithm algorithm;
    private Vision vision;
    private ArrayList<Marker> markers = new ArrayList<>();
    private ArrayList<Pheromone> pheromones = new ArrayList<>();

    public Guard(int x, int y, Algorithm algorithm, int startSpeed, Vision vision, ArrayList<Marker> markers) {
        this(x, y);
        this.algorithm = algorithm;
        this.currentSpeed = startSpeed;
        this.vision = vision;
        this.markers = markers;
    }

    public Guard(int x, int y) {
        ID = newID();
        setX(x);
        setY(y);
        agentType = GUARD;
        currentSpeed = 0; //DEFAULT
        direction = NORTH; //DEFAULT
        algorithm = new RandomMoves(this); //DEFAULT
        vision = new RectangleVision(this); //DEFAULT

        //algorithm = new RandomTest(this);
        currentSpeed = 1;
    }

    public void addMarker(MarkerType type) {

        if (type == MarkerType.VISITED) { //TODO depending on what our agent wants add some properties to the markers in the future
            Marker marker = new Marker(this.getX(), this.getY(), type);
            markers.add(marker);
        }


    }

    public void addPheromone(PheromoneType type) {

        if (type == PheromoneType.TEST) {                                   //TODO depending on what our agent wants add some properties to the pheromones in the future
            Pheromone pheromone = new Pheromone(this.getX(), this.getY(), type, this.PHEROMONELIFETIME);
            pheromones.add(pheromone);
        }

    }

    @Override
    public void updateVision() {
        seenTiles.addAll(vision.updateAndGetVisionAgent(this));
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
    public int getCurrentSpeed() {
        return currentSpeed;
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
