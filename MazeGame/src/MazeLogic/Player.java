package MazeLogic;

import utility.Utility;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * The player class
 */
public class Player implements GameObject{
	private Level.Field currentPosition;
	private Level.Field previousPosition;
	private Level.Field nextPosition;
	private long moveTime = 150*1000000;
	private long lastMove;

	public Player(Level.Field start) {
		currentPosition = start;
		previousPosition = currentPosition;
	}

	public void update(long now){
		while(now > lastMove){
			update();
			lastMove += moveTime;
		}
	}

	@Override
	public void update() {
		previousPosition = currentPosition;
		if(nextPosition !=null){
			currentPosition = nextPosition;
			nextPosition = null;
		}
	}

	@Override
	public void die() {

	}

	@Override
	public boolean isDead() {
		return false;
	}

	@Override
	public Point getPosition() {
		return currentPosition;
	}

	@Override
	public void draw(GraphicsContext graphicsContext, long now) {
		float t = ((float)(now%moveTime))/moveTime;
		Point2D interpolatedPosition = Utility.interpolateLinear(previousPosition, currentPosition, t);

		graphicsContext.save();
		graphicsContext.setFill(Color.RED);
		for(Level.Field field : currentPosition.getLinkedFields()){
			graphicsContext.fillOval(0.45+field.getX(),0.45+field.getY(),0.1,0.1);
		}
		graphicsContext.translate(interpolatedPosition.getX(), interpolatedPosition.getY());
		graphicsContext.setFill(Color.OLDLACE);
		graphicsContext.fillOval(0,0,1,1);
		graphicsContext.setFill(Color.BLACK);
		graphicsContext.fillOval(0.35,0.2,0.1,0.2);
		graphicsContext.fillOval(0.55,0.2,0.1,0.2);
		graphicsContext.restore();
	}

	public void move(int x, int y) {
		for(Level.Field field : currentPosition.getLinkedFields()){
			if(field.getX()==currentPosition.getX()+x && field.getY()==currentPosition.getY()+y){
				nextPosition = field;
				break;
			}
		}
	}
}
