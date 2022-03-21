package org.group7.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.group7.model.Grid;
import org.group7.model.Scenario;
import org.group7.model.component.playerComponents.Guard;

public class View extends ScrollPane {

    private final GraphicsContext g;

    protected Canvas canvas;
    protected double TILE_SIZE = 10;
    protected int MAP_WIDTH;
    protected int MAP_HEIGHT;

    Scenario scenario;

    public View() {
        scenario = Scenario.getInstance();
        MAP_WIDTH = scenario.getWidth();
        MAP_HEIGHT = scenario.getHeight();

        canvas = new Canvas(MAP_WIDTH * (TILE_SIZE + 1), MAP_HEIGHT * (TILE_SIZE + 1));
        g = canvas.getGraphicsContext2D();

        StackPane container = new StackPane();
        container.minWidthProperty().bind(widthProperty());
        container.minHeightProperty().bind(heightProperty());
        container.maxWidthProperty().bind(canvas.widthProperty());
        container.maxHeightProperty().bind(canvas.heightProperty());

        container.getChildren().add(canvas);

        setPannable(true);
        setContent(container);
        layout();

        setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
    }


    public void update() {
        g.setFill(Color.BLACK);
        g.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        drawMap();
        drawAgents();
        drawPortal();
    }

    private void drawPortal() {
        g.setFill(Color.rgb(155, 89, 182).brighter());
        scenario.getTeleporters().forEach(t -> paintTile(t.getTarget().getX(), t.getTarget().getY()));
    }

    private void drawAgents() {
        g.setFill(Color.rgb(52, 152, 219));
        for (Guard guard : scenario.getGuards()) {
            paintTile(guard.getX(), guard.getY());
//            g.setFill(Color.rgb(241, 196, 15));
//            for (Grid grid : guard.getAgentsCurrentVision()) {
//                paintTile(grid.getX(), grid.getY());
//                System.out.println(grid.getType());
//            }
        }

        g.setFill(Color.rgb(230, 126, 34));
        scenario.getIntruders().forEach(intr -> paintTile(intr.getX(), intr.getY()));
    }

    private void drawMap() {
        for (int y = 0; y < MAP_HEIGHT; y++){
            for(int x = 0; x < MAP_WIDTH; x++) {
                Grid tile = scenario.getMap()[x][y];
                g.setFill(tile.explored ? tile.getType().getColor() : tile.getType().getColor().darker().desaturate());
                paintTile(x, y);

                //doesn't seem to do anything
                if (tile.getPlayerComponent() != null) {
                    g.setFill(Color.YELLOW);
                    paintTile(x, y);
                }
            }
        }
    }

    private void paintTile(int x, int y) {
        g.fillRect(x + (x * TILE_SIZE), y + (y * TILE_SIZE), TILE_SIZE, TILE_SIZE);
    }

    private void paintTile(double x, double y) {
        paintTile((int) x, (int) y);
    }

    protected void zoom(double factor) {
        TILE_SIZE *= factor;

        TILE_SIZE = Math.max(Math.min(TILE_SIZE, 20), 4.5); //TODO: calculate actual ratio for min/max tile size
        canvas.setWidth(MAP_WIDTH * (TILE_SIZE + 1));
        canvas.setHeight(MAP_HEIGHT * (TILE_SIZE + 1));
    }

}
