package group.seven.gui;

import group.seven.model.environment.Scenario;
import group.seven.model.environment.Tile;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.Arrays;

import static group.seven.gui.GuardUI.selectedGuard;
import static group.seven.gui.IntruderGUI.selectedIntruder;
import static javafx.scene.paint.Color.BLACK;

public class View extends ScrollPane {

    private final GraphicsContext g;

    protected Canvas canvas;
    protected double TILE_SIZE = 10;
    protected int MAP_WIDTH;
    protected int MAP_HEIGHT;
    protected Scenario s;

    public View(SimulationScreen simulationScreen) {
        s = simulationScreen.scenario;
        MAP_WIDTH = s.WIDTH;
        MAP_HEIGHT = s.HEIGHT;

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
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawMap();
        drawAgents();
    }

//    private void drawPortal() {
//        g.setFill(Color.rgb(155, 89, 182).brighter());
//        //scenario.getTeleporters().forEach(t -> paintTile(t.getTarget().getX(), t.getTarget().getY()));
//    }

    private void drawAgents() {
        Arrays.stream(s.TILE_MAP.agents)
                .forEach(a -> {
                    g.setFill(a.getType().getColor());
                    paintTile(a.getX(), a.getY());
                });

        if (GuardUI.selected != 0) {
            g.setFill(Color.rgb(23, 129, 118));
            paintTile(selectedGuard.guard.getX(), selectedGuard.guard.getY());
        }

        if (IntruderGUI.selected != 0) {
            g.setFill(Color.rgb(192, 57, 43));
            paintTile(selectedIntruder.intruder.getX(), selectedIntruder.intruder.getY());
        }

        g.setFill(BLACK);
    }

    private void drawMap() {
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                Tile tile = s.TILE_MAP.map[x][y];

                //TODO: change exploration colors for guards and intruders just for fun
                if (tile.getExploredGuard()) g.setFill(tile.getType().getColor());
                else if (tile.getExploredIntruder()) g.setFill(tile.getType().getColor());
                else g.setFill(tile.getType().getColor().darker().desaturate());

//                if (!tile.getExploredGuard() || !tile.getExploredIntruder()) g.setFill(tile.getType() == WALL ? Color.gray(0.4) : tile.getType().getColor().darker().desaturate());
//                else g.setFill(tile.getType().getColor());

                paintTile(x, y);
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

//        double min = getViewportBounds().getWidth() / canvas.getWidth();
//        double max = getViewportBounds().getWidth() / canvas.getWidth();

        TILE_SIZE = Math.max(Math.min(TILE_SIZE, 20), 4.5); //TODO: calculate actual ratio for min/max tile size
//        TILE_SIZE = Math.max(Math.min(TILE_SIZE, max), min); //TODO: calculate actual ratio for min/max tile size
        canvas.setWidth(MAP_WIDTH * (TILE_SIZE + 1));
        canvas.setHeight(MAP_HEIGHT * (TILE_SIZE + 1));
    }

}
