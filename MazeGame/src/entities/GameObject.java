package entities;

import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

/**
 *
 */
public interface GameObject {

	void update(long now);

	void draw(GraphicsContext g, long now);

	void die();

	boolean isDead();

}
