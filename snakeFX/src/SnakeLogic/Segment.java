package SnakeLogic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 */
public class Segment {
	private Color color;
	private int x;
	private int y;
	private int direction;

	private Ses shape = Ses.body;

	enum Ses {
		head,
		body,
		tail
	}

	public Segment(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	public Segment(Segment segment) {
		this.setX(segment.getX());
		this.setY(segment.getY());
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void setShape(Ses shape) {
		this.shape = shape;
	}

	public void draw(GraphicsContext g, double fieldWidth, double fieldHeight) {
		g.setFill(color);
		if (shape == Ses.head){
			g.fillRoundRect(x * fieldWidth, y * fieldHeight, fieldWidth, fieldHeight, 10, 10);
			if(direction == 0){
				g.setFill(Color.BLACK);
				g.fillOval(x * fieldWidth + fieldWidth*0.75, y*fieldHeight+fieldHeight*0.30, 3, 3);
				g.fillOval(x * fieldWidth + fieldWidth*0.75, y*fieldHeight+fieldHeight*0.60, 3, 3);
			} else if(direction == 1){
				g.setFill(Color.BLACK);
				g.fillOval(x * fieldWidth + fieldWidth*0.30, y*fieldHeight+fieldHeight*0.75, 3, 3);
				g.fillOval(x * fieldWidth + fieldWidth*0.60, y*fieldHeight+fieldHeight*0.75, 3, 3);
			} else if(direction == 2){
				g.setFill(Color.BLACK);
				g.fillOval(x * fieldWidth + fieldWidth*0.25, y*fieldHeight+fieldHeight*0.30, 3, 3);
				g.fillOval(x * fieldWidth + fieldWidth*0.25, y*fieldHeight+fieldHeight*0.60, 3, 3);
			} else if(direction == 3){
				g.setFill(Color.BLACK);
				g.fillOval(x * fieldWidth + fieldWidth*0.30, y*fieldHeight+fieldHeight*0.25, 3, 3);
				g.fillOval(x * fieldWidth + fieldWidth*0.60, y*fieldHeight+fieldHeight*0.25, 3, 3);
			}
		} else if (shape == Ses.body) {
			g.fillRoundRect(x * fieldWidth, y * fieldHeight, fieldWidth, fieldHeight, 10, 10);
		} else if (shape == Ses.tail){
			g.fillRoundRect(x * fieldWidth+fieldWidth/4, y * fieldHeight+fieldHeight/4, fieldWidth/2, fieldHeight/2, 10, 10);
		}
	}
}
