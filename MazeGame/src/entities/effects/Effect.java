package entities.effects;

import entities.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.Point;

import static java.lang.Long.max;

/**
 * Created by Martin on 16-04-2017.
 */
public class Effect implements GameObject {

	final long spawnTime;
	final long lifespan = 1000L*1000000L;
	private Point position = new Point();
	private boolean dead = false;

	public Effect(long now){
		spawnTime = now;
	}

	@Override
	public void update(long now){
		if(spawnTime+lifespan < now){
			die();
		}

	}

	@Override
	public void die() {
		dead = true;
	}

	@Override
	public boolean isDead() {
		return dead;
	}

	@Override
	public void draw(GraphicsContext g, long now) {
		g.save();
		g.setFill(Color.ORANGE);
		g.translate(getPosition().x, getPosition().y);
		float t = ((float)(max(now, spawnTime)%spawnTime))/lifespan;
		g.fillRect(0.35-t/2, 0.35-t/2, .3+t,.3+t);
		g.restore();
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Point getPosition() {
		return position;
	}
}
