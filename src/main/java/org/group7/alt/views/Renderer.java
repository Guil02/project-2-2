package org.group7.alt.views;


import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.group7.Main;
import org.group7.alt.model.map.Map;
import org.group7.alt.model.map.Tile;

public class Renderer extends ScrollPane {

    Canvas canvas;
    Map map;

    public Renderer(Map map) {
        this.map = map;

        StackPane container = new StackPane();
        container.setStyle("-fx-background-color: #d0d0d0;");
        canvas = new Canvas(Map.WIDTH * (Map.TILE_SIZE + 1), Map.HEIGHT * (Map.TILE_SIZE + 1));

        container.minWidthProperty().bind(canvas.widthProperty());
        container.minHeightProperty().bind(canvas.heightProperty());
        container.getChildren().add(canvas);

        update();

        setContent(container);
        layout();
        setPannable(true);

        Main.stage.setScene(new Scene(this, 800, 600));

    }

    public void update() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.BLACK);
        g.clearRect(0,0, canvas.getWidth(), canvas.getHeight());

        for (int i = 0; i < Map.HEIGHT; i++){
            for(int j = 0; j < Map.WIDTH; j++){
                Tile tile = map.tileMap[j][i];
                g.setFill(tile.getType().getColor());
                g.fillRect(j + (j * Map.TILE_SIZE), i + (i * Map.TILE_SIZE), Map.TILE_SIZE, Map.TILE_SIZE);
            }
        }
    }

}
