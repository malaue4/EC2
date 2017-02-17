package SnakeLogic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 *
 */
public class Segment {
	private Color colorBase;
	private Color colorHighlight;
	private Point position;
	private Point previousPosition;
	private Point direction;
	private Point previousDirection;
	private Segment next;

	private Snek player;

	private Ses shape = Ses.body;

	enum Ses {
		head,
		body,
		tail
	}

	Segment(int x, int y, Snek player) {
		position = new Point(x, y);
		previousPosition = new Point(x,y);
		direction = new Point(1,0);
		previousDirection = new Point(1,0);
		this.player = player;
	}

	Segment(Segment segment) {
		this(segment.getX(), segment.getY(), segment.player);

		setNext(segment.getNext());
		segment.setNext(this);

		previousPosition.setLocation(segment.previousPosition);
		direction.setLocation(segment.direction);
		previousDirection.setLocation(segment.previousDirection);

		if (next==null) {
			setShape(Segment.Ses.tail);
		}
	}

	void move(Point direction) {
		setDirection(direction);
		if (next != null) {
			if (!next.position.equals(position)) {
				Point dir = new Point(position.x - next.position.x, position.y - next.position.y);
				next.move(dir);
			} else {
				next.pause();
			}
		}
		translatePosition(direction);
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

	void pause(){
		setPosition(getPosition());
		setDirection(getDirection());
		if(next!=null){
			next.pause();
		}
	}

	public void draw(GraphicsContext g, double fieldWidth, double fieldHeight, double girth, double progress) {
		g.setFill(colorBase);
		g.save();

		Point2D interpolatedPosition = interpolatedPoint(previousPosition, position, progress);
		g.translate(interpolatedPosition.getX() * fieldWidth + fieldWidth / 2, interpolatedPosition.getY() * fieldHeight + fieldHeight / 2);

		Point2D heading = interpolatedPoint(previousDirection, direction, progress);
		g.rotate(Math.toDegrees(Math.atan2(heading.getY(), heading.getX()) - Math.atan2(0, 1)));
		if (shape == Ses.head) {
			g.fillRoundRect(-fieldWidth*0.85, -fieldHeight/2, fieldWidth*0.85, fieldHeight, 10, 10);
			g.fillOval(-fieldWidth/2, -fieldHeight/2, fieldWidth, fieldHeight);
			g.setFill(colorBase.interpolate(colorHighlight, 0.5));
			g.fillRoundRect(-fieldWidth*0.85, -fieldHeight/2*0.5, fieldWidth*0.85, fieldHeight*0.5, 5, 5);
			g.setFill(Color.BLACK);
			g.fillOval(2, -fieldHeight * 0.20-1, 3, 2);
			g.fillOval(2, fieldHeight * 0.20, 3, 2);
		} else if (shape == Ses.body) {
			g.fillRoundRect(-fieldWidth, -fieldHeight/2*girth, fieldWidth*1.2, fieldHeight*girth, 10, 10);
			g.setFill(colorBase.interpolate(colorHighlight, 0.5));
			g.fillRoundRect(-fieldWidth, -fieldHeight/2*0.5*girth, fieldWidth*1.2, fieldHeight*0.5*girth, 5, 5);
		} else if (shape == Ses.tail) {
			g.fillRoundRect(-fieldWidth/2, -fieldHeight / 4, fieldWidth/2, fieldHeight / 2, 10, 10);
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
