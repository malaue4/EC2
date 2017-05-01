package entities;

import MazeLogic.*;
import MazeLogic.pathfinding.PathFinder;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import utility.Direction;
import utility.Utility;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * Created by martin on 3/30/17.
 */
public class Ghost extends Player {
	private PathFinder pathfinder;
	private List<Level.Field> path;
	private Mood mood = Mood.scramble;
	private Level.Field spawn;
	private Level.Field corner;
	private int blueTimer;

	public void setCorner(Level.Field corner) {
		this.corner = corner;
	}

	public void setMood(Mood mood) {
		this.mood = mood;
		if(mood == Mood.blue){
			blueTimer = 15;
		}
	}

	public enum Mood{
		attack,
		retreat,
		scramble,
		blue
	}

	public Ghost(Level.Field start) {
		super(start);
		pathfinder = new RandomRambler();
		spawn = start;
		corner = start;
	}

	/**
	 * check if the player should move based on current time
	 * @param now the current time
	 */
	public void update(long now){
		if(getNextPosition() == null){
			setMoveTime(450 * 1000000);
			switch (mood){
				case attack:
					path = pathfinder.getPath(getCurrentPosition(), Game.getInstance().getPlayer().getCurrentPosition());
					if(now % 20000000000L > 18000000000L){
						mood = Mood.scramble;
					}
					break;
				case retreat:
					path = pathfinder.getPath(getCurrentPosition(), spawn);
					if(getCurrentPosition().equals(spawn)){
						mood = Mood.attack;
					}
					break;
				case scramble:
					path = pathfinder.getPath(getCurrentPosition(), corner);
					if(getCurrentPosition().equals(corner)){
						mood = Mood.attack;
					}
					break;
				case blue:
					setMoveTime(650 * 1000000);
					path = new RandomRambler().getPath(getCurrentPosition(), spawn);
					if(blueTimer <= 0){
						mood = Mood.attack;
					} else {
						blueTimer--;
					}
					break;
			}
			if(!path.isEmpty()) {
				setNextPosition(path.get(0));
				setDirection(new Direction(getNextPosition().x - getCurrentPosition().x, getNextPosition().y - getCurrentPosition().y));
			}
		}
		super.update(now);
	}

	/**
	 * draws the player between it's current and previous position
	 * @param graphicsContext the context used to issue draw calls to the canvas
	 * @param now the current time
	 */
	@Override
	public void draw(GraphicsContext graphicsContext, long now) {
		float t = (getMoveTime() -(getLastMove() -now)) /(float) getMoveTime();
		Point2D interpolatedPosition = Utility.interpolateLinear(getPreviousPosition(), getCurrentPosition(), t);

		graphicsContext.save();
		Level.Field previous = getCurrentPosition();
		for (Level.Field current : path) {
			graphicsContext.strokeLine(previous.x+0.5, previous.y+0.5, current.x+0.5, current.y+0.5);
			previous = current;
		}
		graphicsContext.translate(interpolatedPosition.getX(), interpolatedPosition.getY());
		//body
		if(mood == Mood.blue)
			graphicsContext.setFill(Color.BLUE);
		else
			graphicsContext.setFill(getColor());

		graphicsContext.fillOval(0.2,0.1,0.6,0.6);
		graphicsContext.fillRect(0.2,0.4,0.6,0.4);
		graphicsContext.fillOval(0.2,0.7,0.2,0.2);
		graphicsContext.fillOval(0.4,0.7,0.2,0.2);
		graphicsContext.fillOval(0.6,0.7,0.2,0.2);

		if(mood != Mood.blue) {
			//eye whites
			graphicsContext.setFill(Color.WHITESMOKE);
			graphicsContext.fillOval(0.3, 0.2, 0.2, 0.3);
			graphicsContext.fillOval(0.5, 0.2, 0.2, 0.3);
		}

		//pupils
		graphicsContext.setFill(Color.BLACK);
		final double dx, dy;
		if(getDirection()==null) {
			dx = 0;
			dy = 0;
		} else {
			dx = getDirection().x * 0.05;
			dy = getDirection().y * 0.05;
		}
		graphicsContext.fillOval(0.35+dx,0.25+dy,0.1,0.2);
		graphicsContext.fillOval(0.55+dx,0.25+dy,0.1,0.2);

		graphicsContext.restore();
	}

	public void setPathfinder(PathFinder pathfinder) {
		this.pathfinder = pathfinder;
	}
}
