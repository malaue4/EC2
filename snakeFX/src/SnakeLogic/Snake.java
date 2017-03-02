package SnakeLogic;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Snake implements GameObject {
	int width;
	int height;
	private Point directionToMove = new Point(0, 0);
	private Color colorBase = Color.DARKSEAGREEN;
	private Color colorHighlight = Color.LAWNGREEN;
	public long timePerField = 500*1000000;
	public long time;

	boolean isAlive = true;
	long timeOfDeath;
	private List<Segment> segments = new LinkedList<>();

	public Snake(int x, int y, int width, int height) {
		this.width = width;
		this.height = height;
		Segment segment = new Segment(x, y, this);
		segment.setColorBase(colorBase);
		segment.setColorHighlight(colorHighlight);
		segment.setShape(Segment.SegmentShape.head);
		segments.add(segment);
		segments.add(segment.duplicate());
	}

	@Override
	public void update() {
		Segment head = getHead();
		head.move(directionToMove);
	}


	/**
	 * Add  a new segment to the snake, with a colorBase based on the eaten item.
	 * @param item - the item that is eaten
	 */
	public void eatItem(Item item) {
		Segment segment = getHead().duplicate();
		segment.setColorBase(colorBase.interpolate(item.getColor(), 0.15));
		segment.setColorHighlight(colorHighlight);
		segments.add(1, segment);
	}

	public void move(int dx, int dy) {
		if ((dy != 0 && getHead().getDirection().y == 0) || (dx != 0 && getHead().getDirection().x == 0)) { // Don't do a 180
			if(getHead().getX()>= 0 && getHead().getX()<width && getHead().getY()>= 0 && getHead().getY()<height) // freeze in the threshold
				directionToMove.setLocation(dx, dy);
		}
	}

	public Segment getHead() {
		return segments.get(0);
	}

	public int getX() {
		return getHead().getX();
	}

	public int getY() {
		return getHead().getY();
	}

	public void draw(GraphicsContext g, double fieldWidth, double fieldHeight, long now) {
		if(!isAlive) now = timeOfDeath;
		double t = (double) (time - now) / timePerField;
		t = t*t;


		for (int i = segments.size() - 1; i >= 0; i--) {
			Segment segment = segments.get(i);
			double girth = (segments.size()*2.0-i)/(segments.size()*2.0);

			Point2D interpolatedPosition = segment.interpolateLinear(segment.getPreviousPosition(), segment.getCurrentPosition(), t);
			Point2D heading = segment.interpolateLinear(segment.getPreviousDirection(), segment.getDirection(), t);
			double angle = Math.atan2(heading.getY(), heading.getX()) - Math.atan2(0, 1);

			g.save();
			g.translate(interpolatedPosition.getX() * fieldWidth + fieldWidth / 2, interpolatedPosition.getY() * fieldHeight + fieldHeight / 2);
			g.scale(fieldWidth /25.0, fieldWidth /25.0);
			g.rotate(Math.toDegrees(angle));


			if (segment.getShape() == Segment.SegmentShape.head) {
				g.setFill(segment.getColorBase());
				g.fillRoundRect(-21.25, -12.5, 21.25, 25, 10, 10);
				g.fillOval(-12.5, -12.5, 25, 25);
				g.setFill(segment.getColorBase().interpolate(segment.getColorHighlight(), 0.5));
				g.fillRoundRect(-21.25, -6.25, 21.25, 12.5, 5, 5);
				g.setFill(Color.WHITESMOKE);
				g.fillOval(0, -8.0, 6, 5);
				g.fillOval(0, 3.0, 6, 5);
				g.setFill(Color.BLACK);
				if(isAlive) {
					Point2D looking = new Point2D.Double(
							(getDirection().getX() - heading.getX()) * cos(-angle) - (getDirection().getY() - heading.getY()) * sin(-angle),
							(getDirection().getX() - heading.getX()) * sin(-angle) + (getDirection().getY() - heading.getY()) * cos(-angle));
					g.fillOval(3 + looking.getX(), -7.0 + looking.getY(), 3, 3);
					g.fillOval(3 + looking.getX(), 4.0 + looking.getY(), 3, 3);
				} else {
					g.save();
					g.translate(3, -7);
					g.rotate(45);
					g.fillRoundRect(-4.5, -1, 9, 2, 3,3);
					g.rotate(90);
					g.fillRoundRect(-4.5, -1, 9, 2, 3,3);
					g.restore();
					g.save();
					g.translate(3, 4);
					g.rotate(45);
					g.fillRoundRect(-4.5, -1, 9, 2, 3, 3);
					g.rotate(90);
					g.fillRoundRect(-4.5, -1, 9, 2, 3, 3);
					g.restore();
				}
			} else if (segment.getShape() == Segment.SegmentShape.body) {
				g.setFill(segment.getColorBase());
				g.fillRoundRect(-25, -12.5 *girth, 30.0, 25 *girth, 10, 10);
				g.setFill(segment.getColorBase().interpolate(segment.getColorHighlight(), 0.5));
				g.fillRoundRect(-25, -6.25 * girth, 30.0, 12.5 * girth, 5, 5);
			} else if (segment.getShape() == Segment.SegmentShape.tail) {
				g.setFill(segment.getColorBase());
				g.fillRoundRect(-20, -6.25, 20, 12.5, 10, 10);
			}
			g.restore();
		}
	}

	void setSpeed(int speed){
		if(speed > 0) {
			timePerField = (2500 / (4+speed)) * 1000000;
		}
	}

	public Point getDirection() {
		return directionToMove;
	}

	public List<Segment> getSegments() {
		return segments;
	}

	public void die(long now) {
		isAlive = false;
		timeOfDeath = now;
	}
}
