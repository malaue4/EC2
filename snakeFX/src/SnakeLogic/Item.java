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

	public int getX() {
		return position.x;
	}

	public int getY() {
		return position.y;
	}

	@Override
	public void update() {

	}

	public void draw(GraphicsContext g, double fieldWidth, double fieldHeight, long now) {
		g.save();
		float t = (float)((now+phaseOffset) % phase)/phase;
		g.translate(position.x * fieldWidth+fieldWidth/2, position.y * fieldHeight+fieldHeight/2);
		g.scale(fieldWidth/25.0, fieldWidth/25.0);

		g.setFill(Color.DARKSLATEGRAY);
		double sSize = 3+sin(t*PI*2);
		g.fillOval(-sSize*1.1, -sSize, sSize*2.2, sSize*2);

		g.setFill(color);
		g.fillOval(-5, -10+sin(t*PI*2)*2, 10, 10);
		g.restore();
	}
}
