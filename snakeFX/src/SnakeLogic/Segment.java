package SnakeLogic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.geom.Point2D;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * A segment of the snake
 */
public class Segment {
	/**
	 * The base color of the segment
	 */
	private Color colorBase;
	/**
	 * The highlight color of the segment, it's the stripe on the snakes back
	 */
	private Color colorHighlight;
	/**
	 * The current position of the segment on the board
	 */
	private Point position;
	/**
	 * The previous position of the segment on the board
	 */
	private Point previousPosition;
	/**
	 * The current facing direction of the segment
	 */
	private Point direction;
	/**
	 * The previous facing direction of the segment
	 */
	private Point previousDirection;
	/**
	 * The next segment of the snake, if this is null then this segment is the tip of the tail
	 */
	private Segment next;
	/**
	 * The snake this segment belongs to
	 */
	private Snake player;

	private Ses shape = Ses.body;
	enum Ses {
		head,
		body,
		tail
	}

	/**
	 *
	 * @param x - the horizontal position of the segment
	 * @param y - the vertical position of the segment
	 * @param player - the snake the segment is attached to
	 */
	Segment(int x, int y, Snake player) {
		position = new Point(x, y);
		previousPosition = new Point(x,y);
		direction = new Point(1,0);
		previousDirection = new Point(1,0);
		this.player = player;
	}

	/**
	 * Create a copy of the segment and insert it in the snake after this segment
	 * @return - the created copy of the segment
	 */
	Segment duplicate(){
		Segment segment = new Segment(getX(), getY(), player);

		segment.setNext(next);
		next = segment;

		segment.previousPosition.setLocation(previousPosition);
		segment.direction.setLocation(direction);
		segment.previousDirection.setLocation(previousDirection);
		segment.setColorBase(colorBase);
		segment.setColorHighlight(colorHighlight);

		if (segment.next==null) {
			segment.setShape(Ses.tail);
		}
		return segment;
	}

	/**
	 * Moves the segment to a new position, the direction is relative
	 * The segment attached to this segment is also moved
	 * @param direction - where to move to, compared to the current position
	 */
	void move(Point direction) {
		setDirection(direction);
		translatePosition(direction);
		if (next != null) {
			if (!next.position.equals(previousPosition)) {
				Point vec = new Point(previousPosition.x - next.position.x, previousPosition.y - next.position.y);
				next.move(vec);
			} else {
				next.pause();
			}
		}
		//translatePosition(direction);
		if(position.x<-1){
			position.x = player.width-1;
			previousPosition.x = player.width;
		} else if(position.x >= player.width+1){
			position.x = 0;
			previousPosition.x = -1;
		}
		if(position.y<-1){
			position.y = player.height-1;
			previousPosition.y = player.height;
		} else if(position.y >= player.height+1){
			position.y = 0;
			previousPosition.y = -1;
		}
	}

	/**
	 * wait for a turn
	 */
	private void pause(){
		setPosition(getPosition());
		setDirection(getDirection());
		if(next!=null){
			next.pause();
		}
	}


	public Color getColorBase() {
		return colorBase;
	}

	public void setColorBase(Color colorBase) {
		this.colorBase = colorBase;
	}

	public Color getColorHighlight() {
		return colorHighlight;
	}

	public void setColorHighlight(Color colorHighlight) {
		this.colorHighlight = colorHighlight;
	}

	public int getX() {
		return position.x;
	}

	public void setX(int x) {
		position.x = x;
	}

	public int getY() {
		return position.y;
	}

	public void setY(int y) {
		position.y = y;
	}

	public Point getDirection() {
		return direction;
	}

	public void setDirection(Point direction) {
		this.previousDirection.setLocation(this.direction);
		this.direction.setLocation(direction);
	}

	private void translatePosition(Point direction) {
		previousPosition.setLocation(position);
		position.translate(direction.x, direction.y);
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		previousPosition.setLocation(this.position);
		this.position = position;
	}

	public void setShape(Ses shape) {
		this.shape = shape;
	}

	public Segment getNext() {
		return next;
	}

	public void setNext(Segment next) {
		this.next = next;
	}

	public Point getPreviousPosition() {
		return previousPosition;
	}

	public void setPreviousPosition(Point previousPosition) {
		this.previousPosition = previousPosition;
	}

	public Point getPreviousDirection() {
		return previousDirection;
	}

	public void setPreviousDirection(Point previousDirection) {
		this.previousDirection = previousDirection;
	}

	public Snake getPlayer() {
		return player;
	}

	public void setPlayer(Snake player) {
		this.player = player;
	}

	public Ses getShape() {
		return shape;
	}

	public Point2D interpolateLinear(Point p1, Point p2, double t){
		return new Point2D.Double(p1.x*t+p2.x*(1-t), p1.y*t+p2.y*(1-t));
	}
}
