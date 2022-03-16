package org.group7.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import org.group7.geometric.DistanceAngleTuple;
import org.group7.geometric.Point;
import org.group7.geometric.Vector2D;
import org.group7.model.Scenario;
import org.group7.model.component.Component;
import org.group7.model.component.playerComponents.Guard;
import org.group7.model.component.playerComponents.Intruder;
import org.group7.model.component.playerComponents.PlayerComponent;
import org.group7.model.component.staticComponents.Teleporter;
import org.group7.utils.Config;

import java.util.ArrayList;
import java.util.HashMap;

import static javafx.scene.paint.Color.*;

public class ExplorationSim extends Renderer {

    double mapWidth, mapHeight;

    public ExplorationSim(double width, double height) {
        mapWidth = width;
        mapHeight = height;
        //tile size = screenwidth / mapwidth
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
            //g.beginPath();
            paintRect(w, g);
            //g.closePath();
            //g.clip();

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
            paintAgent(intruder, g);
            g.setStroke(DARKMAGENTA);
            drawDirection(intruder, g);
            g.setStroke(AQUA);
            drawFOV(intruder, g);
            g.setFill(ORANGE);
            //drawRay(intruder, g, scenario);
        }

        g.setFill(BLUE);
        for (Guard guard : scenario.getGuards()) {
            paintAgent(guard, g);
            g.setStroke(DARKBLUE);
            drawDirection(guard, g);
            g.setStroke(RED);
            drawFOV(guard, g);
            g.setFill(BLUE);
           // drawRay(guard, g, scenario);
        }
    }

    /*
    protected void drawRay(PlayerComponent p, GraphicsContext g, Scenario scenario){
        double x = WIDTH / 2 + toCoord(p.getX()) - toCoord(mapWidth / 2);
        double y = HEIGHT / 2 + toCoord(p.getY()) - toCoord(mapHeight / 2);

        g.setLineWidth(2);
        ArrayList<Component> list = (ArrayList<Component>) scenario.getStaticComponents();
        list.addAll(scenario.getPlayerComponents());
        HashMap<Integer, ArrayList<DistanceAngleTuple<Double, Vector2D>>> rayMap = p.getRay().getVisualField(list);
        for(Integer name: rayMap.keySet()){
            ArrayList<DistanceAngleTuple<Double,Vector2D>> subSet = rayMap.get(name);
            double distance = p.getViewFieldLength();
            double dx = 0;
            double dy = 0;
            for (DistanceAngleTuple<Double, Vector2D> ray : subSet) {
                if(ray.getDistance()<distance){
                    distance = ray.getDistance();
                    double angle = ray.getAngle().getAngle();
                    dx = Math.cos(angle)*distance;
                    dy = Math.sin(angle)*distance;
                    g.strokeLine(x, y, x + toCoord(dx), y + toCoord(dy));
                }

            }
            g.strokeLine(x, y, x + toCoord(dx), y + toCoord(dy));
        }
    }*/

    protected void drawFOV(PlayerComponent p, GraphicsContext g) {
        double x = WIDTH / 2 + toCoord(p.getX()) - toCoord(mapWidth / 2);
        double y = HEIGHT / 2 + toCoord(p.getY()) - toCoord(mapHeight / 2);

        g.setLineWidth(2);
        //g.strokeLine(x, y, x + toCoord(Math.cos(p.direction.getRotatedBy(p.getViewFieldAngle() / 2).getAngle()) * p.getViewFieldLength() - 10), y + toCoord(Math.sin(p.direction.getRotatedBy(p.getViewFieldAngle() / 2).getAngle()) * p.getViewFieldLength()));
        //g.strokeLine(x, y, x + toCoord(Math.cos(p.direction.getRotatedBy(-(p.getViewFieldAngle() / 2)).getAngle()) * p.getViewFieldLength() - 10), y + toCoord(Math.sin(-(p.direction.getRotatedBy(p.getViewFieldAngle() / 2)).getAngle()) * p.getViewFieldLength()));

        //g.strokeLine(x, y, x + toCoord(Math.cos(p.direction.getAngle() - (p.getViewFieldLength() / 2))) * p.getViewFieldLength(), y + toCoord(Math.sin(p.direction.getAngle() - (p.getViewFieldLength() / 2))) * p.getViewFieldLength());
        //g.strokeLine(x, y, x + toCoord(Math.cos(p.direction.getAngle() + (p.getViewFieldLength() / 2))) * p.getViewFieldLength(), y + toCoord(Math.sin(p.direction.getAngle() + (p.getViewFieldLength() / 2))) * p.getViewFieldLength());

        g.strokeLine(x, y, x + toCoord(Math.cos(p.direction.getRotatedBy(p.getViewFieldAngle() / 2).getAngle())) * p.getViewFieldLength(), y + toCoord(Math.sin(p.direction.getRotatedBy(p.getViewFieldAngle() / 2).getAngle())) * p.getViewFieldLength());
        g.strokeLine(x, y, x + toCoord(Math.cos(p.direction.getRotatedBy((-1 * p.getViewFieldAngle()) / 2).getAngle())) * p.getViewFieldLength(), y + toCoord(Math.sin(p.direction.getRotatedBy((-1 * p.getViewFieldAngle()) / 2).getAngle())) * p.getViewFieldLength());

        g.setStroke(BLACK);
//        g.strokeLine(x, y, x + toCoord(Math.cos(new Vector2D(Math.toRadians(90)).getRotatedBy(Math.toRadians(45) / 2).getAngle()) * p.getViewFieldLength()), y + toCoord(Math.sin(new Vector2D(Math.toRadians(90)).getRotatedBy(Math.toRadians(45) / 2).getAngle()) * p.getViewFieldLength()));
//        g.strokeLine(x, y, x + toCoord(Math.cos(new Vector2D(Math.toRadians(90)).getRotatedBy(Math.toRadians(-45) / 2).getAngle()) * p.getViewFieldLength()), y + toCoord(Math.sin(new Vector2D(Math.toRadians(90)).getRotatedBy(Math.toRadians(-45) / 2).getAngle()) * p.getViewFieldLength()));


        g.setLineWidth(1);
    }

    protected void drawDirection(PlayerComponent p, GraphicsContext g) {
        double x = WIDTH / 2 + toCoord(p.getX()) - toCoord(mapWidth / 2);
        double y = HEIGHT / 2 + toCoord(p.getY()) - toCoord(mapHeight / 2);

        g.strokeLine(x, y, x + toCoord(Math.cos(p.direction.getAngle())) * p.getViewFieldLength(), y + toCoord(Math.sin(p.direction.getAngle()))* p.getViewFieldLength());
        g.setStroke(GREEN);
//        g.strokeLine(x, y, x + toCoord(Math.cos(Math.toRadians(90)) * p.getViewFieldLength()), y + toCoord(Math.sin(Math.toRadians(90)) * p.getViewFieldLength()));
    }

    protected void paintAgent(PlayerComponent p, GraphicsContext g) {
        double x = WIDTH / 2 + toCoord(p.getX()) - toCoord(mapWidth / 2) - toCoord(2.5);
        double y = HEIGHT / 2 + toCoord(p.getY()) - toCoord(mapHeight / 2) - toCoord(2.5);

        g.fillOval(x, y, toCoord(5), toCoord(5));
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
