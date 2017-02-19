package SnakeLogic;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake implements GameObject {
	int width;
	int height;
	private Point directionToMove = new Point(0, 0);
	private Color colorBase = Color.DARKSEAGREEN;
	private Color colorHighlight = Color.LAWNGREEN;
	public long timePerField = 500*1000000;
	public long time;

	private List<Segment> segments = new ArrayList<>();

	public Snake(int x, int y, int width, int height) {
		this.width = width;
		this.height = height;
		Segment segment = new Segment(x, y, this);
		segment.setColorBase(colorBase);
		segment.setColorHighlight(colorHighlight);
		segment.setShape(Segment.Ses.head);
		segments.add(segment);
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
		for (int i = segments.size() - 1; i >= 0; i--) {
			Segment segment = segments.get(i);
			segment.draw(g, fieldWidth, fieldHeight, (segments.size()*2.0-i)/(segments.size()*2.0), (double)(time-now)/timePerField);
		}
	}

	void setSpeed(int speed){
		if(speed > 0)
			timePerField = (500*1000000)/speed;
	}

	public Point getDirection() {
		return directionToMove;
	}
}
