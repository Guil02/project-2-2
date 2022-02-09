package org.group7.gui;

import javafx.scene.canvas.Canvas;

public class ExplorationSim extends Renderer {

    double MAP_WIDTH;
    double MAP_HEIGHT;

    public ExplorationSim(double width, double height) {
        super();
        MAP_WIDTH = width;
        MAP_HEIGHT = height;

        //probably want to scale canvas so that small maps are still visible
        canvas = new Canvas(MAP_WIDTH, MAP_HEIGHT);
    }

    @Override
    protected void render() {

    }

    @Override
    public void addKeyHandler() {

    }
}
