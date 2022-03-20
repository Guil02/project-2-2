package org.group7.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;

public class View extends ScrollPane {

    private GraphicsContext g;

    public View() {
        ScrollPane container = new ScrollPane();
        Canvas canvas = new Canvas();
        g = canvas.getGraphicsContext2D();

        container.setContent(canvas);

        setPannable(true);
        setContent(container);
    }


    public void update() {

    }
}
