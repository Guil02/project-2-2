package org.group7.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import org.group7.model.Scenario;
import org.group7.model.component.Component;
import org.group7.model.component.markerComponent.MarkerComponent;
import org.group7.model.component.playerComponents.PlayerComponent;
import org.group7.model.component.staticComponents.StaticComponent;
import org.group7.utils.Config;

import java.util.List;

public abstract class Renderer extends ScrollPane {

    double WIDTH, HEIGHT; //Not sure if we need them or if we just let javafx use calculated size
    public double res = Config.DEFAULT_RENDER_RESOLUTION;
    Canvas canvas;

    private int agentFocus;

    List<Component> components;
    List<PlayerComponent> agents;
    List<MarkerComponent> markers;
    List<StaticComponent> staticComponents;

    public void scale(double factor) {
        res *= factor;
    }

    //convert x,y values relative to scaling
    protected double toCoord(double val) {
        return (val / res);
    }

    //X position of component relative to focused agent
    public double calcX(double d) {
        double x = WIDTH / 2 - toCoord(agents.get(agentFocus).getX()); //get x position relative to agent focused on
        return x + toCoord(d);
    }

    //Y position of component relative to focused agent
    public double calcY(double d) {
        double y = HEIGHT / 2 - toCoord(agents.get(agentFocus).getY());
        return y + toCoord(d);
    }

    public void setFocus(int f) {
        if (agentFocus == f)
            agentFocus++; //switch to next agent
        else if (agentFocus >= agents.size())
            agentFocus = 0;
        else agentFocus = f;

        setHvalue(0.5);
        setVvalue(0.5);
    }

    public void initRenderView() {
        requestFocus(); //idk why it doesn't owrk
        setPannable(true);
        setHvalue(0.5);
        setVvalue(0.5);
        AnchorPane.setBottomAnchor(this, 0.0);
        AnchorPane.setRightAnchor(this, 0.0);
        AnchorPane.setLeftAnchor(this, 0.0);
        AnchorPane.setTopAnchor(this, 0.0);
        setContent(canvas);
        addKeyHandler();

        //this.setPrefSize(1200, 800);
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollBarPolicy.NEVER);
    }


    public abstract void addKeyHandler();

    public abstract void render(Component c);

    public abstract void render(Scenario scenario);
}
