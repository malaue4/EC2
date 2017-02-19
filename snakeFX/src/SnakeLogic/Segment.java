package SnakeLogic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.geom.Point2D;

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


	/**
	 * Draws the segment at an interpolated position and rotation, between its previous position and its current position
	 * @param g - the graphicscontext to draw it with
	 * @param fieldWidth - the width of a field of the game board, used to determine the size and position of the segment
	 * @param fieldHeight - the height of a field of the game board, used to determine the size and position of the segment
	 * @param girth - the thickness of the segment, 1 is same size as the field
	 * @param progress - how far along is the segment from it's previous position to its current position [0-1]
	 */
	public void draw(GraphicsContext g, double fieldWidth, double fieldHeight, double girth, double progress) {
		g.setFill(colorBase);
		g.save();

		Point2D interpolatedPosition = interpolatedPoint(previousPosition, position, progress);
		g.translate(interpolatedPosition.getX() * fieldWidth + fieldWidth / 2, interpolatedPosition.getY() * fieldHeight + fieldHeight / 2);

		Point2D heading = interpolatedPoint(previousDirection, direction, progress);
		double angrad = Math.atan2(heading.getY(), heading.getX()) - Math.atan2(0, 1);
		g.scale(fieldWidth/25.0, fieldWidth/25.0);
		g.rotate(Math.toDegrees(angrad));
		if (shape == Ses.head) {
			g.fillRoundRect(-21.25, -12.5, 21.25, 25, 10, 10);
			g.fillOval(-12.5, -12.5, 25, 25);
			g.setFill(colorBase.interpolate(colorHighlight, 0.5));
			g.fillRoundRect(-21.25, -6.25, 21.25, 12.5, 5, 5);
			g.setFill(Color.WHITESMOKE);
			g.fillOval(0, -8.0, 6, 5);
			g.fillOval(0, 3.0, 6, 5);
			g.setFill(Color.BLACK);
			Point2D looking = new Point2D.Double(
					(player.getDirection().getX()-heading.getX())*cos(-angrad) - (player.getDirection().getY()-heading.getY())*sin(-angrad),
					(player.getDirection().getX()-heading.getX())*sin(-angrad) + (player.getDirection().getY()-heading.getY())*cos(-angrad));
			g.fillOval(3+looking.getX(), -7.0 + looking.getY(), 3, 3);
			g.fillOval(3+looking.getX(), 4.0 + looking.getY(), 3, 3);
		} else if (shape == Ses.body) {
			g.fillRoundRect(-25, -12.5 *girth, 30.0, 25 *girth, 10, 10);
			g.setFill(colorBase.interpolate(colorHighlight, 0.5));
			g.fillRoundRect(-25, -6.25 * girth, 30.0, 12.5 * girth, 5, 5);
		} else if (shape == Ses.tail) {
			g.fillRoundRect(-12.5, -6.25, 12.5, 12.5, 10, 10);
		}
		g.restore();
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

	Point2D interpolatedPoint(Point p1, Point p2, double t){
		return new Point2D.Double(p1.x*t+p2.x*(1-t), p1.y*t+p2.y*(1-t));
	}
}
