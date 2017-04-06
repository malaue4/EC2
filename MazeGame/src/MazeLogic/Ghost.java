package MazeLogic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import utility.Utility;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by martin on 3/30/17.
 */
public class Ghost extends Player {
	Level.Field previousField;

	public Ghost(Level.Field start) {
		super(start);
		setMoveTime(300 * 1000000);
	}

	/**
	 * check if the player should move based on current time
	 * @param now the current time
	 */
	public void update(long now){
		if(getNextPosition() == null){
			previousField = getCurrentPosition();
			List<Level.Field> fieldList = new ArrayList<>(getCurrentPosition().getLinkedFields());
			if(fieldList.size()>1){
				fieldList.remove(previousField);
			}
			setNextPosition(fieldList.get(new Random().nextInt(fieldList.size())));
		}
		super.update(now);
	}



	/**
	 * draws the player between it's current and previous position
	 * @param graphicsContext the context used to issue draw calls to the canvas
	 * @param now the current time
	 */
	@Override
	public void draw(GraphicsContext graphicsContext, long now) {
		float t = (getMoveTime() -(getLastMove() -now)) /(float) getMoveTime();
		Point2D interpolatedPosition = Utility.interpolateLinear(getPreviousPosition(), getCurrentPosition(), t);

		graphicsContext.save();
		graphicsContext.setFill(Color.RED);
		for(Level.Field field : getCurrentPosition().getLinkedFields()){
			graphicsContext.fillOval(0.45+field.getX(),0.45+field.getY(),0.1,0.1);
		}
		graphicsContext.translate(interpolatedPosition.getX(), interpolatedPosition.getY());
		graphicsContext.setFill(getColor());
		graphicsContext.fillOval(0,0,1,1);
		graphicsContext.setFill(Color.WHITESMOKE);
		graphicsContext.fillOval(0.3,0.2,0.2,0.3);
		graphicsContext.fillOval(0.5,0.2,0.2,0.3);
		graphicsContext.setFill(Color.BLACK);
		graphicsContext.fillOval(0.35,0.2,0.1,0.2);
		graphicsContext.fillOval(0.55,0.2,0.1,0.2);
		graphicsContext.restore();
	}
}
