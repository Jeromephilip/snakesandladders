package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

    public Tile (int x, int y) {
        setStroke(Color.BLACK);
        setWidth(Main.tileSize);
        setHeight(Main.tileSize);
        setFill(Color.YELLOW);
    }
}

