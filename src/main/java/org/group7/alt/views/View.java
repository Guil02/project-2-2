package org.group7.alt.views;

public class View {
//    Tile[][] tileMap;
//    Pane pane;
//    List<Rectangle> tiles;
//    Canvas canvas;
//
//    public View(Tile[][] tileMap) {
//        this.tileMap = tileMap;
//        pane = new Pane();
//        ScrollPane scrollPane = new ScrollPane();
//
//        pane.setPrefWidth(Map.WIDTH * Map.TILE_SIZE);
//        pane.setPrefHeight(Map.HEIGHT * Map.TILE_SIZE);
//
//        StackPane container = new StackPane();
//        canvas = new Canvas();
//        container.prefWidthProperty().bind(canvas.widthProperty());
//        container.prefHeightProperty().bind(canvas.heightProperty());
//        container.getChildren().add(canvas);
//
//        tiles = new LinkedList<>();
//
//        for (int x = 0; x < Map.WIDTH; x++) {
//            for (int y = 0; y < Map.HEIGHT; y++) {
//                Rectangle tile = new Rectangle();
//                tile.setFill(tileMap[x][y].getType().getColor());
//                tile.setWidth(Map.TILE_SIZE);
//                tile.setHeight(Map.TILE_SIZE);
//                tile.setX(x + x * Map.TILE_SIZE);
//                tile.setY(y + y * Map.TILE_SIZE);
//                tiles.add(tile);
//            }
//        }
//        pane.getChildren().addAll(tiles);
//        pane.layout();
//        scrollPane.setContent(pane);
//
//        Main.stage.setScene(new Scene(scrollPane, 800, 600));
//    }
//
//    public void update() {
//        int i = 0;
//        for (int x = 0; x < Map.WIDTH; x++) {
//            for (int y = 0; y < Map.HEIGHT; y++, i++) {
//                Tile cell = tileMap[x][y];
//                Rectangle tile = tiles.get(i);
//                tile.setFill(cell.getType().getColor());
//            }
//        }
//    }
//
//    public void update(boolean bool) {
//        GraphicsContext g = canvas.getGraphicsContext2D();
//        g.setFill(Color.valueOf("#fafafa"));
//        g.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
//
//        for (int i = 0; i < Map.HEIGHT; i++){
//            for(int j = 0; j < Map.WIDTH; j++){
//                Tile tile = tileMap[j][i];
//            }
//        }
//    }
}
