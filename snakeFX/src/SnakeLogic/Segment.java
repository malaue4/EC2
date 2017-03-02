package SnakeLogic;

import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.geom.Point2D;

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
	private Point currentPosition;
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
	 * The previous segment of the snake, if this is null then this segment is the head
	 */
	private Segment previous;
	/**
	 * The snake this segment belongs to
	 */
	private Snake player;

	private SegmentShape shape = SegmentShape.body;

	public void setPrevious(Segment previous) {
		this.previous = previous;
	}

	enum SegmentShape {
		head,
		body,
		tail
	}

	/**
	 *
	 * @param x - the horizontal currentPosition of the segment
	 * @param y - the vertical currentPosition of the segment
	 * @param player - the snake the segment is attached to
	 */
	Segment(int x, int y, Snake player) {
		currentPosition = new Point(x, y);
		previousPosition = new Point(x,y);
		direction = new Point(1,0);
		previousDirection = new Point(1,0);
		this.player = player;
	}

	/**
	 * Create a copy of the segment and insert it in the snake after this segment
	 *
	 * @return - the created copy of the segment
	 */
	Segment duplicate() {
		Segment segment = new Segment(getX(), getY(), player);

		segment.setNext(next);
		segment.setPrevious(this);
		if(next != null)
			next.setPrevious(segment);
		this.setNext(segment);

		segment.previousPosition.setLocation(previousPosition);
		segment.direction.setLocation(direction);
		segment.previousDirection.setLocation(previousDirection);
		segment.setColorBase(colorBase);
		segment.setColorHighlight(colorHighlight);

		if (segment.next == null) {
			segment.setShape(SegmentShape.tail);
		}
		return segment;
	}

	/**
	 * Moves the segment to a new currentPosition, the direction is relative
	 * The segment attached to this segment is also moved
	 * @param direction - where to move to, compared to the current currentPosition
	 */
	void move(Point direction) {
		setDirection(direction);
		translatePosition(direction);
		if (next != null) {
			if (!next.currentPosition.equals(previousPosition)) {
				Point vec = new Point(previousPosition.x - next.currentPosition.x, previousPosition.y - next.currentPosition.y);
				next.move(vec);
			} else {
				next.pause();
			}
		}
		//translatePosition(direction);
		if(currentPosition.x<-1){
			currentPosition.x = player.width-1;
			previousPosition.x = player.width;
		} else if(currentPosition.x >= player.width+1){
			currentPosition.x = 0;
			previousPosition.x = -1;
		}
		if(currentPosition.y<-1){
			currentPosition.y = player.height-1;
			previousPosition.y = player.height;
		} else if(currentPosition.y >= player.height+1){
			currentPosition.y = 0;
			previousPosition.y = -1;
		}
	}

	/**
	 * wait for a turn
	 */
	private void pause(){
		setCurrentPosition(getCurrentPosition());
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
		return currentPosition.x;
	}

	public void setX(int x) {
		currentPosition.x = x;
	}

	public int getY() {
		return currentPosition.y;
	}

	public void setY(int y) {
		currentPosition.y = y;
	}

	public Point getDirection() {
		return direction;
	}

	public void setDirection(Point direction) {
		this.previousDirection.setLocation(this.direction);
		this.direction.setLocation(direction);
	}

	private void translatePosition(Point direction) {
		previousPosition.setLocation(currentPosition);
		currentPosition.translate(direction.x, direction.y);
	}

	public Point getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Point currentPosition) {
		previousPosition.setLocation(this.currentPosition);
		this.currentPosition = currentPosition;
	}

	public void setShape(SegmentShape shape) {
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

	public SegmentShape getShape() {
		return shape;
	}

	public Point2D interpolateLinear(Point p1, Point p2, double t){
		return new Point2D.Double(p1.x*t+p2.x*(1-t), p1.y*t+p2.y*(1-t));
	}
}
