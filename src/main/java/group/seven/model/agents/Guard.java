package group.seven.model.agents;


import group.seven.logic.algorithms.Algorithm;
import group.seven.logic.algorithms.RandomMoves;
import group.seven.logic.algorithms.RandomTest;
import group.seven.logic.vision.RectangleVision;
import group.seven.logic.vision.Vision;
import group.seven.model.environment.Scenario;

import static group.seven.enums.Cardinal.NORTH;
import static group.seven.enums.TileType.GUARD;

public class Guard extends Agent {

    private final int ID;
    public int currentSpeed;
    private final int maxSpeed = (int) Scenario.GUARD_SPRINT_SPEED;
    private Vision vision;
    Algorithm algorithm;

    public Guard(int x, int y, Algorithm algorithm, int startSpeed, Vision vision) {
        this(x, y);
        this.algorithm = algorithm;
        this.currentSpeed = startSpeed;
        this.vision = vision;
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
        currentSpeed = 3;
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
