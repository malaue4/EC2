package SnakeLogic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static java.lang.Math.PI;
import static java.lang.Math.sin;

/**
 * Created by Martin on 28-02-2017.
 *
 */
public class DisappearingItem extends Item {

	private int turnsLeft = 30;

	public DisappearingItem(Color color, int x, int y) {
		super(color, x, y);
	}

	public void update(){
		turnsLeft--;
		if(turnsLeft<0){
			die();
		}
	}

	public void draw(GraphicsContext g, double fieldWidth, double fieldHeight, long now) {
		g.save();
		float t = (float)((now+phaseOffset) % phase)/phase;
		g.translate(getPosition().x * fieldWidth+fieldWidth/2, getPosition().y * fieldHeight+fieldHeight/2);
		g.scale(fieldWidth/25.0, fieldWidth/25.0);

		g.setFill(Color.BLACK);
		g.fillText(String.valueOf(turnsLeft), 0, 0);
		g.setGlobalAlpha(turnsLeft/30.0);

		g.setFill(Color.DARKSLATEGRAY);
		double sSize = 3+sin(t*PI*2);
		g.fillOval(-sSize*1.1, -sSize, sSize*2.2, sSize*2);

		g.setFill(getColor());
		g.fillOval(-5, -10+sin(t*PI*2)*2, 10, 10);
		g.restore();
	}

}
