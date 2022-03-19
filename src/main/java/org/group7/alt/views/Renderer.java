package org.group7.alt.views;


import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.group7.Main;
import org.group7.alt.enums.Cardinal;
import org.group7.alt.logic.algorithms.DefaultExploreStategy;
import org.group7.alt.logic.simulation.VisionHandler;
import org.group7.alt.model.ai.agents.Agent;
import org.group7.alt.model.map.Environment;
import org.group7.alt.model.map.Tile;

import static org.group7.alt.enums.Cardinal.*;
import static org.group7.alt.model.map.Environment.TILE_MAP;

public class Renderer extends ScrollPane {

    Canvas canvas;
    double TILE_SIZE;

    public Renderer() {
        StackPane container = new StackPane();

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

                //I think this is broken now
                case W -> TILE_MAP.getAgentList().forEach(agent -> agent.setPose(agent.getPose().rotate(NORTH)));
                case A -> TILE_MAP.getAgentList().forEach(agent -> agent.setPose(agent.getPose().rotate(WEST)));
                case S -> TILE_MAP.getAgentList().forEach(agent -> agent.setPose(agent.getPose().rotate(SOUTH)));
                case D -> TILE_MAP.getAgentList().forEach(agent -> agent.setPose(agent.getPose().rotate(EAST)));
                case M -> TILE_MAP.getAgentList().forEach(agent -> agent.setPose(agent.getPose().stepFoward()));
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

        drawMap(g);
        drawFOV(g);
    }

    private void drawMap(GraphicsContext g) {
        for (int y = 0; y < Environment.HEIGHT; y++){
            for(int x = 0; x < Environment.WIDTH; x++) {
                Tile tile = TILE_MAP.getTile(x, y);
                g.setFill(tile.isExplored() ? tile.getColorTexture() : tile.getColorTexture().darker().desaturate());
                g.fillRect(x + (x * TILE_SIZE), y + (y * TILE_SIZE), TILE_SIZE, TILE_SIZE);
            }
        }
    }

    private void drawFOV(GraphicsContext g) {
        for (Agent agent : TILE_MAP.getAgentList()) {
            g.setFill(agent.getType().getColor());

            Point2D p = TILE_MAP.getLocalFrame(agent).convertLocal(new Point2D(agent.getPose().getX(), agent.getPose().getY()));

            int agentX = (int) p.getX();
            int agentY = (int) p.getY();

            g.fillRect(p.getX() + (agentX * TILE_SIZE), p.getY() + (agentY * TILE_SIZE), TILE_SIZE, TILE_SIZE);

            Cardinal cardinal = agent.getPose().getDirection();

            double startX = p.getX() + (agentX * TILE_SIZE) + TILE_SIZE / 2;
            double startY = p.getY() + (agentY * TILE_SIZE) + TILE_SIZE / 2;

            double endX = startX + (TILE_SIZE * VisionHandler.VIEW_DISTANCE) * cardinal.unitVector.x();
            double endY = startY + (TILE_SIZE * VisionHandler.VIEW_DISTANCE) * cardinal.unitVector.y();

            g.setLineWidth(3);
            g.setStroke(Color.TOMATO);

            g.strokeLine(startX, startY, endX, endY);

        }
    }
}
