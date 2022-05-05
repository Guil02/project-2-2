package group.seven.model.environment;

import group.seven.enums.PheromoneType;

public class Pheromone {
    private final PheromoneType phermoneType;
    private final int xCoordinate;
    private final int yCoordinate;
    private int lifeTime;

    public Pheromone(int xCoordinate, int yCoordinate, PheromoneType phermoneType, int lifeTime) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.lifeTime = lifeTime;
        this.phermoneType = phermoneType;
    }

    public void update(int alpha) {

        lifeTime -= alpha;
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
}
