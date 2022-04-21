package group.seven.model.agents;


import group.seven.enums.Cardinal;
import group.seven.enums.TileType;
import group.seven.logic.algorithms.Algorithm;
import group.seven.logic.algorithms.RandomMoves;
import group.seven.model.environment.Scenario;

public class Intruder extends Agent {

    private final int ID;
    protected int currentSpeed;
    private final int maxSpeed = (int) Scenario.INTRUDER_SPRINT_SPEED;;

    Algorithm algorithm;

    public Intruder(int x, int y, Algorithm algorithm) {
        this(x, y);
        this.algorithm = algorithm;
    }

    public Intruder(int x, int y) {
        ID = newID();
        setX(x);
        setY(y);
        currentSpeed = 0;                       //DEFAULT
        agentType = TileType.INTRUDER;
        direction = Cardinal.SOUTH;             //DEFAULT
        algorithm = new RandomMoves(this); //DEFAULT
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
