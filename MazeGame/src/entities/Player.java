package entities;

import MazeLogic.Game;
import MazeLogic.Level;
import com.sun.javafx.tk.Toolkit;
import entities.GameObject;
import utility.Direction;
import utility.Utility;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * The player class
 */
public abstract class Player implements GameObject {
	private Level.Field currentPosition;
	private Level.Field previousPosition;
	private Level.Field nextPosition;
	private Direction direction;
	/**
	 * the time it takes for the player to move from one field to the next. Measured in nanoseconds
	 */
	private int moveTime = 430*1000000;
	/**
	 * the last time the player moved
	 */
	private long lastMove;
	private Color color = Color.MEDIUMAQUAMARINE;

	public Player(Level.Field start) {
		currentPosition = start;
		previousPosition = currentPosition;
		lastMove = Toolkit.getToolkit().getMasterTimer().nanos();
	}

	/**
	 * check if the player should move based on current time
	 * @param now the current time
	 */
	@Override
	public void update(long now){
		if(now >= lastMove){
			if(nextPosition==null)lastMove=now;
			else lastMove += moveTime;
			update();
		}
	}

	/**
	 * updates the player's position. if nextPosition isn't null: previous -> current, current -> next, next -> null
	 */
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


	public Point getPosition() {
		return getCurrentPosition();
	}


	/**
	 * draws the player between it's current and previous position
	 * @param graphicsContext the context used to issue draw calls to the canvas
	 * @param now the current time
	 */
	@Override
	public void draw(GraphicsContext graphicsContext, long now) {
		float t = (moveTime-(lastMove-now)) /(float)moveTime;
		Point2D interpolatedPosition = Utility.interpolateLinear(previousPosition, currentPosition, t);

		graphicsContext.save();
		/*graphicsContext.setFill(Color.RED);
		for(Level.Field field : currentPosition.getLinkedFields()){
			graphicsContext.fillOval(0.45+field.getX(),0.45+field.getY(),0.1,0.1);
		}*/
		graphicsContext.translate(interpolatedPosition.getX(), interpolatedPosition.getY());
		graphicsContext.setFill(color);
		graphicsContext.fillOval(0,0,1,1);
		graphicsContext.setFill(Color.BLACK);
		graphicsContext.fillOval(0.35,0.2,0.1,0.2);
		graphicsContext.fillOval(0.55,0.2,0.1,0.2);
		graphicsContext.restore();
	}

	/**
	 * Check if the field at the relative position dx, dy and the current field is linked, if they are the field is
	 * marked as the next position, and the player will move there when it next updates/moves.
	 * @param direction coordinate relative to current position
	 */
	public void move(Direction direction) {
		this.direction = direction;
		for(Level.Field field : currentPosition.getLinkedFields()){
			if(field.getX()==currentPosition.getX()+direction.x && field.getY()==currentPosition.getY()+direction.y){
				nextPosition = field;
				break;
			}
		}
	}

	public void setMoveTime(int moveTime) {
		double relativeMove = (Game.getNow()-getLastMove())/(double)this.moveTime;
		setLastMove(Game.getNow()-(long)(moveTime*relativeMove));
		this.moveTime = moveTime;
	}

	public int getMoveTime() {
		return moveTime;
	}

	public void setCurrentPosition(Level.Field currentPosition) {
		this.currentPosition = currentPosition;
	}

	public void setPreviousPosition(Level.Field previousPosition) {
		this.previousPosition = previousPosition;
	}

	public long getLastMove() {
		return lastMove;
	}

	public void setLastMove(long lastMove) {
		this.lastMove = lastMove;
	}

	public Color getColor() {
		return color;
	}

	public Level.Field getCurrentPosition() {
		return currentPosition;
	}

	public Level.Field getPreviousPosition() {
		return previousPosition;
	}

	public Level.Field getNextPosition() {
		return nextPosition;
	}

	public void setNextPosition(Level.Field nextPosition) {
		this.nextPosition = nextPosition;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}
