package org.group7.alt.model.map;

import org.group7.alt.enums.Component;
import org.group7.alt.enums.GameMode;
import org.group7.geometric.Area;
import org.group7.model.Scenario;
import org.group7.model.component.playerComponents.PlayerComponent;
import org.group7.model.component.staticComponents.StaticComponent;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Map {

    public static GameMode GAME_MODE;
    public static String NAME;
    public static int WIDTH;
    public static int HEIGHT;

    public static double INTRUDER_BASE_SPEED;
    public static double INTRUDER_SPRINT_SPEED;
    public static double GUARD_BASE_SPEED;
    public static double GUARD_SPRINT_SPEED;

    public static int NUM_GAURDS;
    public static int NUM_INTRUDERS;

    public static double SCALING;
    public static double TIME_STEP;

    public Tile[][] tileMap;

    public Map(Scenario s) {
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

        initializeMap();

        List<org.group7.model.component.Component> components = new LinkedList<>();
        components.addAll(s.getStaticComponents());
        components.addAll(s.getPlayerComponents());

        for (org.group7.model.component.Component c : s.getStaticComponents()) {
            StaticComponent sc = (StaticComponent) c;
            switch (sc.getComponentEnum()) {
                case WALL -> addStaticComponent(sc, Component.WALL);
                case GUARD_SPAWN_AREA -> addStaticComponent(sc, Component.GUARD_SPAWN);
                case INTRUDER_SPAWN_AREA -> addStaticComponent(sc, Component.INTRUDER_SPAWN);
                case TELEPORTER -> addStaticComponent(sc, Component.TELEPORTER); //TODO add teleport location
                case SHADED_AREA -> addStaticComponent(sc, Component.SHADED);
                case TARGET_AREA -> addStaticComponent(sc, Component.TARGET);
            }
        }

        //TODO: not sure if agents have even been spawned yet
        for (org.group7.model.component.Component c : s.getPlayerComponents()) {
            PlayerComponent p = (PlayerComponent) c;
            switch (p.getComponentEnum()) {
                case GUARD -> addPlayerComponent(p, Component.GUARD);
                case INTRUDER -> addPlayerComponent(p, Component.INTRUDER);
            }
        }

    }

    public void initializeMap(){
        tileMap = new Tile[WIDTH][HEIGHT];
        for(int i = 0; i < WIDTH; i++){
            for(int j = 0; j < HEIGHT; j++){
                tileMap[i][j] = new Tile(Component.EMPTY, i, j);
            }
        }

    }

    public void addStaticComponent(StaticComponent c, Component type) {
        for(int i = (int) c.getTopLeft().x; i < c.getBottomRight().x; i++){
            for(int j = (int) c.getTopLeft().y; j < c.getBottomRight().y; j++){
                tileMap[i][j] = new Tile(type, i, j);
            }
        }
    }
    public void addPlayerComponent(PlayerComponent c, Component type){
        Area a = c.getArea();
        for(int i = (int) a.getTopLeft().x; i < a.getBottomRight().x; i++){
            for(int j = (int) a.getTopLeft().y; j<a.getBottomRight().y; j++){
                tileMap[i][j] = new Tile(type,i, j);
            }
        }
    }

    @Override
    public String toString() {
        for(int i = 0; i < HEIGHT; i++){
            for(int j = 0; j < WIDTH; j++){
                System.out.print(tileMap[j][i]);
            }
            System.out.println();
        }
        return "Map{" +
                "\nName: " + NAME +
                "\nDimensions: " + WIDTH + ", " + HEIGHT +
                "\nGame Mode: " + GAME_MODE +
                "\nNum Guards: " + NUM_GAURDS +
//                "\ntileMap:" + Arrays.deepToString(tileMap) +
                '}';
    }
}
