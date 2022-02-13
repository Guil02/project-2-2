package org.group7.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import org.group7.geometric.Point;
import org.group7.model.Scenario;
import org.group7.model.component.Component;
import org.group7.model.component.playerComponents.Guard;
import org.group7.model.component.playerComponents.Intruder;
import org.group7.model.component.staticComponents.Teleporter;
import org.group7.utils.Config;

import static javafx.scene.paint.Color.*;

public class ExplorationSim extends Renderer {

    double mapWidth, mapHeight;

    public ExplorationSim(double width, double height) {
        mapWidth = width;
        mapHeight = height;
        WIDTH = 3000; //need to figure out a better way to define canvas size
        HEIGHT = 3000;
        canvas = new Canvas(WIDTH, HEIGHT);
        initRenderView();
        requestFocus(); //GRRR why does this not work
    }

    @Override
        public void render(Component component) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.fillRect(WIDTH / 2 + toCoord(component.getArea().getTopLeft().x) - toCoord(mapWidth / 2), HEIGHT / 2 + toCoord(component.getArea().getTopLeft().y) - toCoord(mapHeight / 2), toCoord(component.getArea().getWidth()), toCoord(component.getArea().getHeight()));
    }

    @Override
    public void render(Scenario scenario) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.clearRect(0,0,WIDTH,HEIGHT);

        //depending on how we handle textures, rendering each component type can be done in one loop
        //either way, it'll still be O(n) time, unless we filter out components based on zoom level; but I doubt we'll have to worry about that

        //List<Component> walls = staticComponents.stream().filter(c -> c.getComponentEnum() == WALL).toList();
        g.setFill(BLACK);
        for (Component w : scenario.getWalls()) {
            paintRect(w, g);
        }

        g.setFill(AQUAMARINE);
        for (Component gS : scenario.getGuardSpawnAreas()) {
            paintRect(gS, g);
        }

        g.setFill(TOMATO);
        for (Component iS : scenario.getIntruderSpawnAreas()) {
            paintRect(iS, g);
        }

        g.setFill(LIGHTGRAY);
        for (Component c : scenario.getShadedAreas()) {
            paintRect(c, g);
        }

        g.setFill(PURPLE);
        for (Teleporter t : scenario.getTeleporters()) {
            paintRect(t, g);
            g.setFill(PINK);
            paintPoint(t.getTarget(), g);
        }

        g.setFill(GREEN);
        for (Component c : scenario.getTargetAreas()) {
            paintRect(c, g);
        }

        g.setFill(ORANGE);
        for (Intruder intruder : scenario.getIntruders()) {
            paintPoint(intruder.getCoordinates(), g);
        }

        g.setFill(BLUE);
        for (Guard guard : scenario.getGuards()) {
            paintPoint(guard.getCoordinates(), g);
        }
    }

    //kinda janky but works for now
    protected void paintRect(Component c, GraphicsContext g) {
        g.fillRect(WIDTH / 2 + toCoord(c.getArea().getTopLeft().x) - toCoord(mapWidth / 2), HEIGHT / 2 + toCoord(c.getArea().getTopLeft().y) - toCoord(mapHeight / 2), toCoord(c.getArea().getWidth()), toCoord(c.getArea().getHeight()));
    }

    protected void paintPoint(Point p, GraphicsContext g) {
        //TODO: change alpha/transparency level
        g.fillOval(WIDTH / 2 + toCoord(p.x) - toCoord(mapWidth / 2) - toCoord(2.5), HEIGHT / 2 + toCoord(p.y) - toCoord(mapHeight / 2) - toCoord(2.5), toCoord(2.5), toCoord(2.5));
    }

    @Override
    public void addKeyHandler() {
        //key commands for now bc I am lazy. Have to click on the canvas first though to request focus, otherwise it ignores you
        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.Z || event.getCode() == KeyCode.Y) {
                scale(1.05);
            } else if (event.getCode() == KeyCode.X) {
                scale(0.95);
            } else if (event.getCode() == KeyCode.C) {
                setHvalue(0.5);
                setVvalue(0.5);
            } else if (event.getCode() == KeyCode.R) {
                reset();
            }
        });
    }

    public void reset() {
        res = Config.DEFAULT_RENDER_RESOLUTION;
        setHvalue(0.5);
        setVvalue(0.5);
    }
}
