package SnakeLogic;


import SnakeGUI.Item;
import javafx.scene.paint.Color;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Snek implements GameObject{
	private int width;
	private int height;
	private Point direction = new Point(0, 0);
	private Color color = Color.LAWNGREEN;

	private List<Segment> segments = new ArrayList<>();

	public Snek(int x, int y, int width, int height) {
		this.width = width;
		this.height = height;
		Segment segment = new Segment(x, y);
		segment.setColor(color);
		segment.setShape(Segment.Ses.head);
		getSegments().add(segment);
	}

	@Override
	public void update() {
		for (int i = segments.size()-1; i > 0; i--) {
			segments.get(i).setX(segments.get(i - 1).getX());
			segments.get(i).setY(segments.get(i - 1).getY());
		}

		Segment head = getHead();
		segments.get(0).setX(segments.get(0).getX() + direction.x);
		segments.get(0).setY(segments.get(0).getY() + direction.y);
		if(head.getX()<0)
			head.setX(width-1);
		if(head.getX()>=width)
			head.setX(0);
		if(head.getY()<0)
			head.setY(height-1);
		if(head.getY()>=height)
			head.setY(0);
	}

	public void eatItem(Item item){
		Segment segment = new Segment(segments.get(0));
		segment.setColor(item.getColor().interpolate(color, 0.75));
		segments.add(1, segment);
		if(segments.size() == 2){
			segment.setShape(Segment.Ses.tail);
		}
	}

	public List<Segment> getSegments() {
		return segments;
	}

	public void move(int x, int y) {
		if((y != 0 && direction.y == 0) || (x != 0 && direction.x == 0)){ // Don't do a 180
			direction.setLocation(x, y);
			getHead().setDirection(x==0?y==1?1:3:x==1?0:2);
		}
	}

	public Segment getHead(){
		return segments.get(0);
	}

	public int getX() {
		return getHead().getX();
	}

	public int getY() {
		return getHead().getY();
	}
}
