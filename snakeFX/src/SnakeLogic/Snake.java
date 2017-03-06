package SnakeLogic;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
	private boolean dead;
	private List<Segment> segments = new LinkedList<>();
	private Segment head, tail;

	public Snake(int x, int y, int width, int height) {
		this.width = width;
		this.height = height;
		Segment segment = new Segment(x, y, this);
		segment.setColorBase(colorBase);
		segment.setColorHighlight(colorHighlight);
		segment.setShape(Segment.SegmentShape.head);
		head = segment;
		segments.add(segment);
		segments.add(segment.duplicate());
	}

	@Override
	public void update() {
		Segment head = getHead();
		head.move(directionToMove);

		Game game = Game.getInstance();
		StageInfo stageInfo = game.getStageInfo();
		Map<Point, Field> fieldMap = stageInfo.getFieldMap();
		Field field = fieldMap.get(head.getCurrentPosition());
		if(field!=null){
			GameObject gameObject = field.getContents();
			if (gameObject != null) {
				if (gameObject instanceof Item) {
					Item item = (Item) gameObject;
					eatItem(item);
					item.die();
					game.setItemEaten(game.getItemEaten());
				}
			}
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

	public void draw(GraphicsContext g, long now) {
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
			g.translate(interpolatedPosition.getX() + 0.5, interpolatedPosition.getY() + 0.5);
			g.rotate(Math.toDegrees(angle));


			if (segment.getShape() == Segment.SegmentShape.head) {
				g.setFill(segment.getColorBase());
				g.fillRoundRect(-0.85, -0.5, 0.85, 1, 0.4, 0.4);
				g.fillOval(-0.5, -0.5, 1, 1);
				g.setFill(segment.getColorBase().interpolate(segment.getColorHighlight(), 0.5));
				g.fillRoundRect(-0.85, -0.25, 0.85, 0.5, 0.2, 0.2);
				g.setFill(Color.WHITESMOKE);
				g.fillOval(0, -0.32, 0.24, 0.2);
				g.fillOval(0, 0.12, 0.24, 0.2);
				g.setFill(Color.BLACK);
				if(isAlive) {
					Point2D looking = new Point2D.Double(
							0.04 *(getDirection().getX() - heading.getX()) * cos(-angle) - 0.04 *(getDirection().getY() - heading.getY()) * sin(-angle),
							0.04 *(getDirection().getX() - heading.getX()) * sin(-angle) + 0.04 *(getDirection().getY() - heading.getY()) * cos(-angle));
					g.fillOval(0.12 + looking.getX(), -0.28 + looking.getY(), 0.12, 0.12);
					g.fillOval(0.12 + looking.getX(), 0.16 + looking.getY(), 0.12, 0.12);
				} else {
					g.save();
					g.translate(0.12, -0.28);
					g.rotate(45);
					g.fillRoundRect(-0.18, -0.04, 0.36, 0.08, 0.12, 0.12);
					g.rotate(90);
					g.fillRoundRect(-0.18, -0.04, 0.36, 0.08, 0.12, 0.12);
					g.restore();
					g.save();
					g.translate(0.12, 0.16);
					g.rotate(45);
					g.fillRoundRect(-0.18, -0.04, 0.36, 0.08, 0.12, 0.12);
					g.rotate(90);
					g.fillRoundRect(-0.18, -0.04, 0.36, 0.08, 0.12, 0.12);
					g.restore();
				}
			} else if (segment.getShape() == Segment.SegmentShape.body) {
				g.setFill(segment.getColorBase());
				g.fillRoundRect(-1, -0.5 *girth, 1.2, 1 *girth, 0.4, 0.4);
				g.setFill(segment.getColorBase().interpolate(segment.getColorHighlight(), 0.5));
				g.fillRoundRect(-1, -0.25 * girth, 1.2, 0.5 * girth, 0.2, 0.2);
			} else if (segment.getShape() == Segment.SegmentShape.tail) {
				g.setFill(segment.getColorBase());
				g.fillRoundRect(-0.8, -0.25, 0.8, 0.5, 0.4, 0.4);
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

		Segment current = head;

		List<Segment> segments = new ArrayList<>();

		while(current!=null){
			segments.add(current);
			current = current.getNext();
		}

		return segments;
	}

	public void die(long now) {
		isAlive = false;
		timeOfDeath = now;
	}

	@Override
	public Point getPosition() {
		return head.getCurrentPosition();
	}
}
