package org.group7.alt.views;


import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.group7.Main;
import org.group7.alt.logic.util.CoordinateMapper;
import org.group7.alt.model.ai.Agents.Agent;
import org.group7.alt.model.map.Environment;
import org.group7.alt.model.map.Tile;

import java.awt.*;

public class Renderer extends ScrollPane {

    Canvas canvas;
    Environment environment;
    double TILE_SIZE;

    public Renderer(Environment environment) {
        this.environment = environment;

        StackPane container = new StackPane();
        container.setStyle("-fx-background-color: #d0d0d0;");

        TILE_SIZE = Environment.TILE_SIZE;
        canvas = new Canvas(Environment.WIDTH * (TILE_SIZE + 1), Environment.HEIGHT * (TILE_SIZE + 1));
        //container.minWidthProperty().bind(viewportBoundsProperty().get().getWidth() < canvas.widthProperty().get() ? minViewportWidthProperty() : canvas.widthProperty());
        //container.minHeightProperty().bind(viewportBoundsProperty().get().getHeight() < canvas.heightProperty().get() ? minHeightProperty() : canvas.heightProperty());
        container.minWidthProperty().bind(widthProperty());
        container.minHeightProperty().bind(heightProperty());
        container.maxWidthProperty().bind(canvas.widthProperty());
        container.maxHeightProperty().bind(canvas.heightProperty());

        container.getChildren().add(canvas);


        update();

        setContent(container);
        layout();
        setPannable(true);

        Main.stage.setScene(new Scene(this, 800, 600));


        setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case X -> TILE_SIZE *= 0.95;
                case Z -> TILE_SIZE *= 1.05;
            }

            //TODO: calculate actual ratio
            TILE_SIZE = Math.max(Math.min(TILE_SIZE, 20), 4.5);

            //TODO use scale transforms or clipping region for canvas outside of viewport
            canvas.setWidth(Environment.WIDTH * (TILE_SIZE + 1));
            canvas.setHeight(Environment.HEIGHT * (TILE_SIZE + 1));

            //setHvalue(canvas.getWidth() / getViewportBounds().getWidth());
            //setHvalue(0.5);
            //setVvalue(0.5);
        });

    }

    public void update() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.BLACK);
        g.clearRect(0,0, canvas.getWidth(), canvas.getHeight());

        for (int y = 0; y < Environment.HEIGHT; y++){
            for(int x = 0; x < Environment.WIDTH; x++) {
                Tile tile = environment.getTileMap().getTile(x, y);//.getTile(new Point(x, y));
                g.setFill(tile.getColorTexture());
                g.fillRect(x + (x * TILE_SIZE), y + (y * TILE_SIZE), TILE_SIZE, TILE_SIZE);
            }
        }

        for (Agent agent : environment.getTileMap().getAgentList()) {
            g.setFill(agent.getType().getColor());
            Point p = CoordinateMapper.convertLocalToGlobal(environment.getTileMap().getSpawn(agent), agent.getPose().getPosition());
            g.fillRect(p.x + (p.x * TILE_SIZE), p.y + (p.y * TILE_SIZE), TILE_SIZE, TILE_SIZE);
            g.setStroke(Color.TOMATO);
            g.strokeLine(p.x + (p.x * TILE_SIZE), p.y + (p.y * TILE_SIZE), p.x + (p.x * TILE_SIZE) + (5 * agent.getPose().getDirection().relativeOffset().x), p.y + (p.y * TILE_SIZE) + (5 * agent.getPose().getDirection().relativeOffset().y));
        }
    }

}
