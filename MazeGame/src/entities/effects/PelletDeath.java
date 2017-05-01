package entities.effects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static java.lang.Long.max;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by Martin on 16-04-2017.
 */
public class PelletDeath extends Effect {

	private Color color;
	public PelletDeath(long now, Color color) {
		super(now);
		this.color = color;
	}

	@Override
	public void draw(GraphicsContext g, long now) {
		g.save();
		g.setFill(color);
		g.translate(getPosition().x+0.5, getPosition().y+0.5);
		float t = ((float)(max(now, spawnTime)%spawnTime))/lifespan;

		double sSize = 0.3*sin(t*PI);
		double rad = 0.5*sin(t*PI/2);
		g.rotate(t*360);
		g.fillOval(-sSize+rad*0.3, -sSize+rad*0.1, sSize*2, sSize*2);
		g.fillOval(-sSize-rad*0.25, -sSize-rad*0.2, sSize*2, sSize*2);
		g.fillOval(-sSize-rad*0.05, -sSize+rad*0.5, sSize*2, sSize*2);
		g.restore();
	}
}