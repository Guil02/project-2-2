package group.seven.model.agents;


import group.seven.enums.AlgorithmType;
import group.seven.enums.Cardinal;
import group.seven.logic.algorithms.*;
import group.seven.logic.geometric.Pythagoras;
import group.seven.logic.geometric.Rectangle;
import group.seven.logic.vision.ConeVision;
import group.seven.logic.vision.RectangleVision;
import group.seven.logic.vision.Vision;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.Tile;

import java.util.List;

import static group.seven.enums.Cardinal.*;
import static group.seven.enums.TileType.INTRUDER;

public class Intruder extends Agent {

    private final int ID;
    protected int currentSpeed;
    private final int maxSpeed = (int) Scenario.INTRUDER_SPRINT_SPEED;
    private Vision vision;
    Algorithm algorithm;
    private Cardinal orientationToGoal;
    private double angleToGoal;  // in degrees


    public Intruder(int x, int y, Algorithm algorithm) { //TODO: fix and finish
        this(x, y);
        this.algorithm = algorithm;
        updateOrientationToGoal();

    }

    public Intruder(int x, int y) {
        super(x,y);
        ID = newID();
        setX(x);
        setY(y);
        agentType = INTRUDER;
        currentSpeed = 1;       //DEFAULT
        direction = SOUTH;      //DEFAULT
        algorithm = new AStarGoal(this); //DEFAULT
        vision = new RectangleVision(this); //DEFAULT
        updateOrientationToGoal();
        currentSpeed = 3;
    }

    public void updateOrientationToGoal (){
        Rectangle goalLocationArea = Scenario.targetArea.area();
        double heightMediumPoint =  goalLocationArea.getHeight()/2;
        double widthMediumPoint = goalLocationArea.getWidth()/2;
        int x = (int)(goalLocationArea.getX() + widthMediumPoint);
        int y = (int)(goalLocationArea.getY() + heightMediumPoint);
        double angle = Pythagoras.getAnglePythagoras(x, y, this.x, this.y);
        //Update angle to goal, which is in degrees
        this.angleToGoal= angle;

       this.orientationToGoal = Pythagoras.fromAngleToCardinal(angle, this.x ,this.y, x, y);

    }





    public Cardinal getOrientationToGoal(){
        return this.orientationToGoal;
    }

    public double getAngleToGoal(){
        return this.angleToGoal;
    }

    @Override
    public void updateVision() {
        List<Tile> newTiles = vision.updateAndGetVisionAgent(this);
        seenTiles = duplicatedTiles(seenTiles,newTiles);
        /*
        Could also use the below if the vision stores an instance of the agent.
        Otherwise, would recommend making vision methods static and any have vision status effects stored in agent

        seenTiles.addAll(vision.updateAndGetVisionAgent());
         */
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



    //Builder methods, just experimenting, feel free to ignore. Would want to use for easy customization
    //Methods here all return an instance to the object, so you can chain methods together
    public static Intruder create(int x, int y) {
        return new Intruder(x, y);
    }

    //sets the speed of this intruder and returns the same intruder object back
    public Intruder speed(int speed) {
        currentSpeed = speed;
        return this;
    }

    //sets a new instance of the algorithm for the agent depending on algorithm type
    //Goal: add pre-configured algorithm (e.g. with different constructors), however, currently not implemented
    public Intruder algorithm(Algorithm algorithm) {
        this.algorithm = switch (algorithm.getType()) {
            case RANDOM -> new RandomMoves(this);
            case BRICK_AND_MORTAR -> new BrickAndMortar(this);

            default -> new RandomTest(this);
        };

        return this;
    }

    //intruder builder method
    public Intruder algorithm(AlgorithmType algorithm) {
        this.algorithm = switch (algorithm) {
            case RANDOM -> new RandomMoves(this);
            case BRICK_AND_MORTAR -> new BrickAndMortar(this);

            default -> new RandomTest(this);
        };

        return this;
    }

    //intruder builder method
    public Intruder attachVision(Vision.Type type) {
        this.vision = switch (type) {
            case RECTANGULAR -> new RectangleVision(this);
            case CONE -> new ConeVision(this);
        };
        return this;
    }

    //intruder builder method
    public Intruder direction(Cardinal orientation) {
        direction = orientation;
        return this;
    }

    @Override
    public String toString() {
        return "Intruder{" +
                "ID=" + ID +
                ", x=" + x +
                ", y=" + y +
                ", direction=" + direction +
                ", speed=" + currentSpeed +
                ", algorithm=" + algorithm.getType() +
                '}';
    }
}
