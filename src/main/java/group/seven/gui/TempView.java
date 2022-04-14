package group.seven.gui;


import group.seven.Main;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.Tile;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.util.Arrays;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.BLUE;


public class TempView extends ScrollPane {

    private final Label label;
    Canvas canvas;
    double TILE_SIZE;
    Scenario s;
    GraphicsContext g;

    public TempView(Scenario s) {
        this.s = s;
        StackPane container = new StackPane();
        TILE_SIZE = s.TILE_SIZE;
        canvas = new Canvas(s.WIDTH * (TILE_SIZE + 1), s.HEIGHT * (TILE_SIZE + 1));
        g = canvas.getGraphicsContext2D();
        //container at least as big as ScollPane and maximally as big as canvas
        container.minWidthProperty().bind(widthProperty());
        container.minHeightProperty().bind(heightProperty());
        container.maxWidthProperty().bind(canvas.widthProperty());
        container.maxHeightProperty().bind(canvas.heightProperty());


        container.getChildren().add(canvas);
        setContent(container);
        layout();
        setPannable(true);
        //for zooming and eventually more
        setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case X -> zoom(0.95);
                case Z -> zoom(1.05);
            }
        });

        label = new Label("Not started");
        label.setFont(Font.font(120));
        update();

        container.getChildren().add(label);
        Main.stage.setScene(new Scene(this, 1080, 640));

    }

    int c = 0;

    public void update() {
        //print("Value.." + c);
        label.setText(c++ + " ");
        g.setFill(BLUE);
        g.clearRect(0,0, canvas.getWidth(), canvas.getHeight());

        g.setStroke(BLACK);
        drawMap();
        drawDirection();
        drawAgents();
        drawFOV();
    }

    private void drawMap() {
        for (int y = 0; y < s.HEIGHT; y++){
            for(int x = 0; x < s.WIDTH; x++) {
                Tile tile = s.TILE_MAP.getTile(x, y);
                //g.setFill(tile.isExplored() ? tile.getColorTexture() : tile.getColorTexture().darker().desaturate());
                g.setFill(tile.getType().getColor());
                g.fillRect(x + (x * TILE_SIZE), y + (y * TILE_SIZE), TILE_SIZE, TILE_SIZE);
            }
        }
    }

    private void drawAgents() {
        Arrays.stream(Scenario.get().TILE_MAP.agents)
                .forEach(a -> {
                    g.setFill(a.getType().getColor());
                    paintTile(a.getX(), a.getY());
                });

        g.setFill(BLACK);
    }

    private void drawDirection() {
        Arrays.stream(Scenario.get().TILE_MAP.agents)
                .forEach(a -> {
                    //TODO
                });
    }

    private void drawFOV() {
//        for (Agent agent : s.TILE_MAP.getAgentList()) {
//            g.setFill(agent.getType().getColor());
//
//            Point2D p = s.TILE_MAP.getLocalFrame(agent).convertLocal(agent.getX(), agent.getY());
//
//            int agentX = (int) p.getX();
//            int agentY = (int) p.getY();
//
//            g.fillRect(p.getX() + (agentX * TILE_SIZE), p.getY() + (agentY * TILE_SIZE), TILE_SIZE, TILE_SIZE);
//
//            Cardinal cardinal = agent.getDirection();
//
//            double startX = p.getX() + (agentX * TILE_SIZE) + TILE_SIZE / 2;
//            double startY = p.getY() + (agentY * TILE_SIZE) + TILE_SIZE / 2;
//
//            double endX = startX + (TILE_SIZE * VisionHandler.VIEW_DISTANCE) * cardinal.unitVector.x();
//            double endY = startY + (TILE_SIZE * VisionHandler.VIEW_DISTANCE) * cardinal.unitVector.y();
//
//            g.setLineWidth(3);
//            g.setStroke(Color.TOMATO);
//
//            g.strokeLine(startX, startY, endX, endY);
//
//        }
    }

    private void paintTile(int x, int y) {
        g.fillRect(x + (x * TILE_SIZE), y + (y * TILE_SIZE), TILE_SIZE, TILE_SIZE);
    }

    private void paintLine(int x, int y, int endX, int endY) {
        g.fillRect(x + (x * TILE_SIZE), y + (y * TILE_SIZE), TILE_SIZE, TILE_SIZE + (endY*TILE_SIZE));
    }

    protected void zoom(double factor) {
        TILE_SIZE *= factor;

        TILE_SIZE = Math.max(Math.min(TILE_SIZE, 20), 4.5); //TODO: calculate actual ratio for min/max tile size
        canvas.setWidth(s.WIDTH * (TILE_SIZE + 1));
        canvas.setHeight(s.HEIGHT * (TILE_SIZE + 1));
        update();
    }

    public void update(int count) {
        c = count;
        update();
    }
}
