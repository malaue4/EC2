package SnakeLogic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.Random;

import static java.lang.Math.PI;
import static java.lang.Math.sin;


public class Item implements GameObject {
	private Color color;
	private Point position;
	int phase;
	int phaseOffset;
	boolean dead = false;

	public Item(Color color, int x, int y) {
		this.color = color;
		position = new Point(x, y);

		Random random = new Random();
		phase = (800 * 1000000) + random.nextInt(500);
		phaseOffset = random.nextInt(phase);
	}

	public Color getColor() {
		return color;
	}

	public Point getPosition(){
		return position;
	}

	@Override
	public void update() {

	}

	@Override
	public void die() {
		dead = true;
	}

	public boolean isDead(){
		return dead;
	}

	public void draw(GraphicsContext g, long now) {
		g.save();
		float t = (float)((now+phaseOffset) % phase)/phase;
		g.translate(position.x + 0.5, position.y + 0.5);

		g.setFill(Color.DARKSLATEGRAY);
		double sSize = 0.12 + 0.04 *sin(t*PI*2);
		g.fillOval(-sSize*1.1, -sSize, sSize*2.2, sSize*2);

		g.setFill(color);
		g.fillOval(-0.2, -0.4 +sin(t*PI*2)*0.08, 0.4, 0.4);
		g.restore();
	}
}
