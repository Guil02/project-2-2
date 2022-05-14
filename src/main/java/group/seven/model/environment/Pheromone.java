package group.seven.model.environment;

import group.seven.enums.PheromoneType;

public class Pheromone {
    public static final double maxStrength = 100;
    private final PheromoneType phermoneType;
    private final int xCoordinate;
    private final int yCoordinate;
    /**
     * the strength of the pheromone. i.e. it indicates how recent the pheromone has been placed.
     */
    private double strength;

    public Pheromone(int xCoordinate, int yCoordinate, PheromoneType phermoneType, double strength) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.strength = strength;
        this.phermoneType = phermoneType;
    }

    /**
     * Method that updates the value of the {@link Pheromone#strength} of the pheromone by multiplying it's value by a value between 0 and 1 to represent
     * the dissipation of the smell. This way of dissipating the strength is based upon the paper:
     * Theoretical Study of ant-based Algorithms for Multi-Agent Patrolling. by Arnaud Glad, Olivier Simonin,
     * Olivier Buffet and François Charpillet.
     */
    public void update() {
        strength = Math.random()*strength;
//        strength -= alpha;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public PheromoneType getType() {
        return phermoneType;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }
}
