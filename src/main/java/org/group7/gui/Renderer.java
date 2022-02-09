package org.group7.gui;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

//TODO: IGNORE THIS CLASS
public abstract class Renderer extends ScrollPane {

    double WIDTH, HEIGHT; //Not sure if we need them or if we just let javafx use calculated size

    Canvas canvas;
    AnimationTimer renderer;

    public Renderer() {

        renderer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                render();
            }
        };

        setPannable(true);
        setHvalue(0.5);
        setVvalue(0.5);

        setContent(canvas);
        addKeyHandler();

        //this.setPrefSize(1200, 800);
        //this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        //this.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public void start() {
        renderer.start();
    }

    public void stop() {
        renderer.stop();
    }

    public abstract void addKeyHandler();
    protected abstract void render();
}
