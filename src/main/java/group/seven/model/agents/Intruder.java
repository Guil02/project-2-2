package group.seven.model.agents;


import group.seven.enums.AlgorithmType;
import group.seven.enums.Cardinal;
import group.seven.logic.algorithms.Algorithm;
import group.seven.logic.algorithms.BrickAndMortar;
import group.seven.logic.algorithms.RandomMoves;
import group.seven.logic.algorithms.RandomTest;
import group.seven.logic.geometric.Rectangle;
import group.seven.logic.vision.ConeVision;
import group.seven.logic.vision.RectangleVision;
import group.seven.logic.vision.Vision;
import group.seven.model.environment.Scenario;
import javafx.geometry.Orientation;

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
        algorithm = new RandomMoves(this); //DEFAULT
        vision = new RectangleVision(this); //DEFAULT
        updateOrientationToGoal();
        currentSpeed = 3;
    }

    public void updateOrientationToGoal (){
        Rectangle goalLocationArea = Scenario.targetArea.area();
        double heightMediumPoint =  goalLocationArea.getHeight()/2;
        double widthMediumPoint = goalLocationArea.getWidth()/2;
        double x = goalLocationArea.getX() + widthMediumPoint;
        double y = goalLocationArea.getY() + heightMediumPoint;
        double angle = 0;
        if (x != this.x && y != this.y) { //checking so that no division by 0 happens
            double adjacent = Math.abs(x- this.x);
            double opposite = Math.abs(y- this.y);
            angle = Math.atan(opposite/adjacent);
            angle = Math.toDegrees(angle);
        }
        //Update angle to goal, which is in degrees
        this.angleToGoal= angle;

        if (angle == 0){  // Checking if goal is in front of agent
           if (x > this.x && y == this.y){
               this.orientationToGoal = EAST;
           } else if (x < this.x && y == this.y){
               this.orientationToGoal = WEST;
           } else if (y < this.y && x == this.x){
               this.orientationToGoal = NORTH;
           } else {
               this.orientationToGoal = SOUTH;
           }
       }else {
           if ((angle<45 && angle>0) || (angle>315 && angle<360 )){
               this.orientationToGoal = EAST;
           } else if ((angle>45 && angle<135)) {
               this.orientationToGoal = NORTH;
           }else if ((angle>135 && angle<225)) {
               this.orientationToGoal = WEST;
           } else {
               this.orientationToGoal = SOUTH;
           }
       }

    }

    public Cardinal getOrientationToGoal(){
        return this.orientationToGoal;
    }

    public double getAngleToGoal(){
        return this.angleToGoal;
    }

    @Override
    public void updateVision() {
        seenTiles.addAll(vision.updateAndGetVisionAgent(this));
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
