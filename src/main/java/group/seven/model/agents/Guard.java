package group.seven.model.agents;


import group.seven.enums.Cardinal;
import group.seven.enums.TileType;
import group.seven.logic.algorithms.Algorithm;
import group.seven.logic.algorithms.RandomMoves;
import group.seven.logic.vision.RectangleVision;
import group.seven.logic.vision.Vision;
import group.seven.model.environment.Scenario;

public class Guard extends Agent {

    private final int ID;
    public int currentSpeed;
    private final int maxSpeed = (int) Scenario.GUARD_SPRINT_SPEED;
    Vision vision;
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
        currentSpeed = 0;
        agentType = TileType.GUARD;
        direction = Cardinal.NORTH; //DEFAULT
        algorithm = new RandomMoves(this); //DEFAULT
        vision = new RectangleVision(); //DEFAULT
    }

    @Override
    public void updateVision() {

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
}
