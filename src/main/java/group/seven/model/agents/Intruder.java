package group.seven.model.agents;


import group.seven.enums.Action;
import group.seven.enums.AlgorithmType;
import group.seven.enums.Cardinal;
import group.seven.logic.algorithms.AStarGoal;
import group.seven.logic.algorithms.Algorithm;
import group.seven.logic.algorithms.NN;
import group.seven.logic.algorithms.RandomAlt;
import group.seven.logic.algorithms.intruders.GraphAstar;
import group.seven.logic.geometric.Pythagoras;
import group.seven.logic.geometric.Rectangle;
import group.seven.logic.geometric.XY;
import group.seven.logic.vision.ConeVision;
import group.seven.logic.vision.RectangleVision;
import group.seven.logic.vision.Vision;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.Tile;
import group.seven.utils.Config;

import java.util.List;

import static group.seven.enums.TileType.INTRUDER;
import static group.seven.utils.Methods.print;

public class Intruder extends Agent {

    private final int ID;
    private final int maxSpeed;
    protected int currentSpeed;
    Algorithm algorithm;
    private Vision vision;
    private Cardinal orientationToGoal;
    private double angleToGoal;  // in degrees
    private int inTargetArea = 0;

    private boolean firstTimeInTargetArea = true;
    private boolean alive = true;

    public Intruder(int x, int y, Scenario s, AlgorithmType algorithm) {
        this(x, y, s);
        this.algorithm = initAlgo(algorithm);
        updateOrientationToGoal();

    }

    public Intruder(int x, int y, Scenario s) {
        super(x, y, s);
        ID = s.getId();
        agentType = INTRUDER;
        direction = Cardinal.randomDirection();      //DEFAULT
        algorithm = initAlgo(Config.ALGORITHM_INTRUDER); //DEFAULT
        vision = new ConeVision(this); //DEFAULT
        updateOrientationToGoal();
        currentSpeed = 3; //TODO base speed?
        maxSpeed = (int) scenario.INTRUDER_SPRINT_SPEED;
    }

    //Builder methods, just experimenting, feel free to ignore. Would want to use for easy customization
    //Methods here all return an instance to the object, so you can chain methods together
    public static Intruder create(int x, int y, Scenario s) {
        return new Intruder(x, y, s);
    }

    public Algorithm initAlgo(AlgorithmType type) {
        return switch (type) {
            case A_STAR -> new AStarGoal(this);
            case A_STAR_ALT -> new GraphAstar(this);
            case GENETIC_NEURAL_NETWORK -> new NN(this);
            default -> new RandomAlt(this);
        };
    }

    public void updateOrientationToGoal() {
        Rectangle goalLocationArea = scenario.targetArea.area();
        double heightMediumPoint = goalLocationArea.getHeight() / 2;
        double widthMediumPoint = goalLocationArea.getWidth() / 2;
        int x = (int) (goalLocationArea.getX() + widthMediumPoint);
        int y = (int) (goalLocationArea.getY() + heightMediumPoint);
        XY agentGlobal = getXY();
//        double angle = Pythagoras.angleFromAgentToTarget(new XY(x,y), new XY(this.x, this.y));
//        double angle = Pythagoras.angleFromAgentToTarget(new XY(x, y), new XY(this.getX(), this.getY())); //todo changed so frames match
        double angle = Pythagoras.angleFromAgentToTarget(new XY(x, y), agentGlobal); //todo changed so frames match
        //double angle = Pythagoras.getAnglePythagoras(this.x,this.y,x,y);
        //Update angle to goal, which is in degrees
        this.angleToGoal = angle;

//       this.orientationToGoal = Pythagoras.fromAngleToCardinal(angle, this.getX() ,this.getY(), x, y); //todo changed to match frame
        //TODO: investtigate ->I think the parameter order might have been wrong
        this.orientationToGoal = Pythagoras.fromAngleToCardinal(angle, agentGlobal.x(), x, agentGlobal.y(), y); //todo changed to match frame
    }

    public Cardinal getOrientationToGoal() {
        return this.orientationToGoal;
    }

    public double getAngleToGoal() {
        return this.angleToGoal;
    }

    @Override
    public void updateVision() {
        List<Tile> newTiles = vision.updateAndGetVisionAgent(this);
        seenTiles = duplicatedTiles(seenTiles, newTiles);
        /*
        Could also use the below if the vision stores an instance of the agent.
        Otherwise, would recommend making vision methods static and any have vision status effects stored in agent

        seenTiles.addAll(vision.updateAndGetVisionAgent());
         */
    }

    @Override
    public Move calculateMove() {
        //Check if Intruder is in the target area
        if (scenario.targetArea.contains(getXY())) {
//        if (Scenario.targetArea.area().contains(this.getX(),this.getY())) {
            if (firstTimeInTargetArea) {
                print("Intruder " + getID() + " made it to target");
                //TODO handle leaving and returning to target area
                scenario.INTRUDERS_AT_TARGET++;
                firstTimeInTargetArea = false;
                return algorithm.getNext();
            } else {
                //TODO: make intruder leave target area if guard nearby or to make room for other intruders
                return new Move(Action.NOTHING, 0, this);
            }
        } //Check if Intruder is alive = is not caught yet
        else if (alive) {
            return algorithm.getNext();
        } else {
            return new Move(Action.NOTHING, 0, this);
        }
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
        currentSpeed = speed;
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
            case A_STAR -> new AStarGoal(this);
            case A_STAR_ALT -> new GraphAstar(this);
            default -> new RandomAlt(this);
        };

        return this;
    }

    //intruder builder method
    public Intruder algorithm(AlgorithmType algorithm) {
        this.algorithm = switch (algorithm) {
            case A_STAR -> new AStarGoal(this);
            case A_STAR_ALT -> new GraphAstar(this);
            default -> new RandomAlt(this);
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

    public int intruderInTargetArea() {
        print("Intruder " + ID + " has got the goods");
        inTargetArea += 1;
        return inTargetArea;
    }

    public void killIntruder() {
        if (alive) {
            //scenario.INTRUDERS_CAUGHT++;
            alive = false;
            System.out.println("\nIntruder " + ID + " just got shot");
            scenario.removeIntruder(this);
        }
    }

    public void intruderNotInTargetArea() {
        inTargetArea = 0;
    }

    @Override
    public String toString() {
        return "Intruder{" +
                "ID=" + ID +
                ", x=" + x +
                ", y=" + y +
                ", globalX=" + getX() +
                ", globalY=" + getY() +
                ", direction=" + direction +
                ", speed=" + currentSpeed +
                ", algorithm=" + algorithm.getType() +
                '}';
    }
}
