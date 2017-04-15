package entities;

import MazeLogic.Level;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import utility.Direction;
import utility.Utility;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * Created by Martin on 09-04-2017.
 */
public class PackMan extends Player {

	public PackMan(Level.Field start) {
		super(start);

	}

	@Override
	public void update() {
		setPreviousPosition(getCurrentPosition());
		if(getNextPosition() !=null){
			setCurrentPosition(getNextPosition());
			setNextPosition(null);

			// Check if we can keep going in the same direction
			if(getDirection() !=null){
				Point point = new Point(getCurrentPosition());
				point.translate(getDirection().x, getDirection().y);
				for (Level.Field field : getCurrentPosition().getLinkedFields()) {
					if(field.getLocation().equals(point)) {
						setNextPosition(field);
						break;
					}
				}

				if(getNextPosition()==null){
					setDirection(null);
				}
			}
		}
	}

	public void draw(GraphicsContext graphicsContext, long now) {
		float t = (getMoveTime() -(getLastMove() -now)) /(float) getMoveTime();
		Point2D interpolatedPosition = Utility.interpolateLinear(getPreviousPosition(), getCurrentPosition(), t);

		graphicsContext.save();
		graphicsContext.translate(interpolatedPosition.getX(), interpolatedPosition.getY());
		graphicsContext.setFill(getColor());
		graphicsContext.fillOval(0.1,0.1,.8,.8);
		graphicsContext.setFill(Color.BLACK);
		if(getDirection()==Direction.left) {
			graphicsContext.fillOval(0.25,0.2,0.1,0.2);
		} else if(getDirection()==Direction.up) {
		} else if(getDirection()==Direction.right) {
			graphicsContext.fillOval(0.65,0.2,0.1,0.2);
		} else {
			graphicsContext.fillOval(0.35,0.2,0.1,0.2);
			graphicsContext.fillOval(0.55,0.2,0.1,0.2);
		}
		graphicsContext.restore();
	}
}
