package entities;

import MazeLogic.Game;
import entities.effects.Effect;
import entities.effects.PelletDeath;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.Random;

import static java.lang.Math.PI;
import static java.lang.Math.sin;

/**
 * Created by Martin on 09-04-2017.
 */
public class Pellet implements GameObject {
	private javafx.scene.paint.Color color;
	private Point position;
	private int phase;
	private int phaseOffset;
	private boolean dead = false;
	double size;

	public Pellet(Color color, int x, int y) {
		this.color = color;
		position = new Point(x, y);

		Random random = new Random();
		phase = (800 * 1000000) + random.nextInt(500);
		phaseOffset = random.nextInt(phase);
		size = 0.2;
	}

	public javafx.scene.paint.Color getColor() {
		return color;
	}

	public Point getPosition(){
		return position;
	}

	@Override
	public void update(long now) {

	}

	@Override
	public void die() {
		dead = true;
		Game.getInstance().spawnEffect(position, new PelletDeath(Game.getInstance().getNow(), color));
	}

	public boolean isDead(){
		return dead;
	}

	public void draw(GraphicsContext g, long now) {
		g.save();
		float t = (float)((now+phaseOffset) % phase)/phase;
		g.translate(position.x + 0.5, position.y + 0.5);

		g.setFill(javafx.scene.paint.Color.DARKSLATEGRAY);
		double sSize = size/3 + size/9 *sin(t*PI*2);
		g.fillOval(-sSize*1.1, -sSize, sSize*2.2, sSize*2);

		g.setFill(color);
		g.fillOval(-size/2, -size +sin(t*PI*2)*(size/2), size, size);
		g.restore();
	}
}
