package group.seven.model.environment;

public class Pheromone extends Marker {
    private int lifeTime;

    public Pheromone(int xCoordinate, int yCoordinate, int markerType, int lifeTime) {
        super(xCoordinate, yCoordinate, markerType);
        this.lifeTime = lifeTime;
    }

    public void update(int alpha) {

        lifeTime -= alpha;
    }
}
