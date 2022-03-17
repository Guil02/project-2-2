package org.group7.alt.model.map;

import org.group7.alt.enums.Cell;
import org.group7.alt.enums.GameMode;
import org.group7.model.Scenario;
import org.group7.model.component.Component;
import org.group7.model.component.staticComponents.StaticComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Environment {

    public static GameMode GAME_MODE;
    public static String NAME;
    public static int WIDTH;
    public static int HEIGHT;
    public static int TILE_SIZE = 10;

    public static double INTRUDER_BASE_SPEED;
    public static double INTRUDER_SPRINT_SPEED;
    public static double GUARD_BASE_SPEED;
    public static double GUARD_SPRINT_SPEED;

    public static int NUM_GAURDS;
    public static int NUM_INTRUDERS;

    public static double SCALING;
    public static double TIME_STEP;

    public static List<Point> INTRUDER_SPAWN_GRIDS;
    public static List<Point> GUARD_SPAWN_GRIDS;

    public TileMap tileMap;

    public Environment(Scenario s) {
        parseScenario(s);

        INTRUDER_SPAWN_GRIDS = new ArrayList<>();
        GUARD_SPAWN_GRIDS = new ArrayList<>();

        initializeMap();

        for (Component c : s.getStaticComponents()) {
            StaticComponent sc = (StaticComponent) c;
            switch (sc.getComponentEnum()) {
                case WALL -> fillMap(sc, Cell.WALL);
                case GUARD_SPAWN_AREA -> fillMap(sc, Cell.GUARD_SPAWN);
                case INTRUDER_SPAWN_AREA -> fillMap(sc, Cell.INTRUDER_SPAWN);
                case TELEPORTER -> fillMap(sc, Cell.TELEPORTER); //TODO add teleport location
                case SHADED_AREA -> fillMap(sc, Cell.SHADED);
                case TARGET_AREA -> fillMap(sc, Cell.TARGET);
            }
        }
    }

    private void parseScenario(Scenario s) {
        GAME_MODE = s.getGameMode() == 0 ? GameMode.EXPLORATION : GameMode.SINGLE_INTRUDER;
        NAME = s.getName();
        WIDTH = s.getWidth();
        HEIGHT = s.getHeight();
        INTRUDER_BASE_SPEED = s.getBaseSpeedIntruder();
        INTRUDER_SPRINT_SPEED = s.getSprintSpeedIntruder();
        GUARD_BASE_SPEED = s.getBaseSpeedGuard();
        GUARD_SPRINT_SPEED = s.getSprintSpeedGuard();
        NUM_GAURDS = s.getNumGuards();
        NUM_INTRUDERS = s.getNumIntruders();
        SCALING = s.getScaling();
        TIME_STEP = s.getTimeStep();
    }

    public void initializeMap(){
        tileMap = new TileMap();

        for(int x = 0; x <= WIDTH; x++)
            for (int y = 0; y <= HEIGHT; y++)
                tileMap.setTile(new Point(x, y), new Tile());
    }

    public void fillMap(StaticComponent c, Cell type) {
        for(int i = (int) c.getTopLeft().x; i < c.getBottomRight().x; i++){
            for(int j = (int) c.getTopLeft().y; j < c.getBottomRight().y; j++) {
                switch (type) {
                    case GUARD_SPAWN -> GUARD_SPAWN_GRIDS.add(new Point(i, j));
                    case INTRUDER_SPAWN -> INTRUDER_SPAWN_GRIDS.add(new Point(i, j));
                }

                tileMap.setTile(new Point(i, j), new Tile(type));
            }
        }
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    @Override
    public String toString() {
        return "Map{" +
                "\nName: " + NAME +
                "\nDimensions: " + WIDTH + ", " + HEIGHT +
                "\nGame Mode: " + GAME_MODE +
                "\nNum Guards: " + NUM_GAURDS +
                '}';
    }
}
