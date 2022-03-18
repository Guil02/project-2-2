package org.group7.alt.views;


import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Translate;
import org.group7.Main;
import org.group7.alt.enums.Cardinal;
import org.group7.alt.logic.util.CoordinateMapper;
import org.group7.alt.model.ai.Agents.Agent;
import org.group7.alt.model.map.Environment;
import org.group7.alt.model.map.Tile;

import java.awt.*;
import java.awt.geom.Line2D;

public class Renderer extends ScrollPane {

    Canvas canvas;
    Environment environment;
    double TILE_SIZE;

    public Renderer(Environment environment) {
        this.environment = environment;

        StackPane container = new StackPane();
        ///container.setStyle("-fx-background-color: #d0d0d0;");

        TILE_SIZE = Environment.TILE_SIZE;
        canvas = new Canvas(Environment.WIDTH * (TILE_SIZE + 1), Environment.HEIGHT * (TILE_SIZE + 1));

        //container at least as big as scollpane and maximally as big as canvas
        container.minWidthProperty().bind(widthProperty());
        container.minHeightProperty().bind(heightProperty());
        container.maxWidthProperty().bind(canvas.widthProperty());
        container.maxHeightProperty().bind(canvas.heightProperty());

        container.getChildren().add(canvas);

        update();

        setContent(container);
        layout();
        setPannable(true);

        Main.stage.setScene(new Scene(this, 1080, 640));

        //for zooming and eventually more
        setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case X -> TILE_SIZE *= 0.95;
                case Z -> TILE_SIZE *= 1.05;
            }

            //TODO: calculate actual ratio
            TILE_SIZE = Math.max(Math.min(TILE_SIZE, 20), 4.5); //min max tile size values found experimental

            //TODO use bindings to automatically resize canvas when zooming
            //TODO use scale transforms or clipping region for canvas outside of viewport
            canvas.setWidth(Environment.WIDTH * (TILE_SIZE + 1));
            canvas.setHeight(Environment.HEIGHT * (TILE_SIZE + 1));
        });
    }

    public void update() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.BLACK);
        g.clearRect(0,0, canvas.getWidth(), canvas.getHeight());

        for (int y = 0; y < Environment.HEIGHT; y++){
            for(int x = 0; x < Environment.WIDTH; x++) {
                Tile tile = environment.getTileMap().getTile(x, y);
                g.setFill(tile.getColorTexture());
                g.fillRect(x + (x * TILE_SIZE), y + (y * TILE_SIZE), TILE_SIZE, TILE_SIZE);
            }
        }

        for (Agent agent : environment.getTileMap().getAgentList()) {
            g.setFill(agent.getType().getColor());
            Point p = CoordinateMapper.convertLocalToGlobal(environment.getTileMap().getSpawn(agent), agent.getPose().getPosition());
            g.fillRect(p.x + (p.x * TILE_SIZE), p.y + (p.y * TILE_SIZE), TILE_SIZE, TILE_SIZE);

            //g.setFill(Color.color(0.8,0.3,1, 0.5));
            //g.fillOval(p.x + (p.x * TILE_SIZE), p.y + ((p.y * TILE_SIZE)), TILE_SIZE, TILE_SIZE);

            g.setStroke(Color.TOMATO);

            Cardinal cardinal = agent.getPose().getDirection();

            //g.setStroke(Color.YELLOW.darker());
            //g.moveTo(p.x + (p.x * TILE_SIZE), p.y + (p.y * TILE_SIZE));
            //g.lineTo(direction.getEndX(), direction.getEndY());
            //g.stroke();

            g.setStroke(Color.color(0.8,0.3,1, 0.5));
            g.setLineWidth(3);
            int viewDistance = 10; //10 cells

            Affine reset = g.getTransform();
            double startX = p.x + (p.x * TILE_SIZE) + TILE_SIZE / 2;
            double startY = p.y + (p.y * TILE_SIZE) + TILE_SIZE / 2;

            double endX = startX + (TILE_SIZE * viewDistance * cardinal.relativeOffset().x);
            double endY = startY + (TILE_SIZE * viewDistance * cardinal.relativeOffset().y);

            g.rotate(cardinal.rotation());

            g.strokeLine(startX, startY, endX, endY);

            //g.translate(cardinal.relativeOffset().x + TILE_SIZE, cardinal.relativeOffset().y + TILE_SIZE);

            //g.strokeLine(startX, startY, endX, endY);
            g.setTransform(reset);
        }
    }
}
