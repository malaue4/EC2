package SnakeGUI;

import SnakeLogic.GameObject;
import javafx.scene.paint.Color;

/**
 * Created by Ebbe Vang on 19-01-2017.
 */
public class Item implements GameObject{
    private Color Color;
    private int x;
    private int y;

    public Item(Color color, int x, int y) {
        Color = color;
        this.x = x;
        this.y = y;
    }

    public Color getColor() {
        return Color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void update() {

    }
}
