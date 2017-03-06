package SnakeLogic;

import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

/**
 *
 */
public interface GameObject {

	void update();

	void die();

	boolean isDead();

	Point getPosition();

	void draw(GraphicsContext g, long now);

}
