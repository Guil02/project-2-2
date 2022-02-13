package org.group7.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import org.group7.model.component.Component;

import java.util.List;

public class ExplorationSim extends Renderer {

    double mapWidth, mapHeight;

    public ExplorationSim(double width, double height) {
        mapWidth = width;
        mapHeight = height;
        WIDTH = 3000;
        HEIGHT = 3000;
        canvas = new Canvas(WIDTH, HEIGHT);
        initRenderView();

        //probably want to scale canvas so that small maps are still visible

    }

    @Override
    protected void render() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.clearRect(0,0,WIDTH,HEIGHT);
    }

    @Override
        public void render(Component component) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        //g.setFill(Color.RED);
        //g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g.setFill(Color.GREEN);
        g.fillRect(WIDTH / 2 + toCoord(component.getArea().getTopLeft().x) - toCoord(mapWidth / 2), HEIGHT / 2 + toCoord(component.getArea().getTopLeft().y) - toCoord(mapHeight / 2), toCoord(component.getArea().getWidth()), toCoord(component.getArea().getHeight()));
    }

    @Override
    public void render(List<Component> staticComponents) {
        render();
        for (Component c : staticComponents)
            render(c);
    }

    @Override
    public void addKeyHandler() {
        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.Z || event.getCode() == KeyCode.Y) {
                scale(1.05);
            } else if (event.getCode() == KeyCode.X) {
                scale(0.95);
            } else if (event.getCode() == KeyCode.C) {
                setHvalue(0.5);
                setVvalue(0.5);
            }
        });
    }
}
