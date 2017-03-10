package MazeGUI;

import MazeLogic.Game;
import com.sun.javafx.tk.Toolkit;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;

import java.awt.*;

public class Controller {

	@FXML
	Label labelGameSpeed;
	@FXML
	ProgressBar progressItems;
	@FXML
	Label labelStatus;
	@FXML
	Canvas canvas;

	private double fieldHeight;
	private double fieldWidth;
	private float refreshRate = 16 * 1000000;
	private float drawRate = 16 * 1000000;

	Game game = Game.getInstance();

	/**
	 * Executed when JavaFX is initialized. Used to setup the Snake game
	 */
	public void initialize() {
		calculateFields();
		//game.newGame();

		// Start and control game loop
		new AnimationTimer() {
			long lastUpdate = Toolkit.getToolkit().getMasterTimer().nanos();
			long lastDraw = Toolkit.getToolkit().getMasterTimer().nanos();

			public void handle(long now) {
				if (now > lastUpdate + refreshRate) {
					lastUpdate += refreshRate;
					game.update(now);
				}

				if (now > lastDraw + drawRate) {
					lastDraw += drawRate;
					drawCanvas(now);
				}
			}
		}.start();
	}

	/**
	 * Calculate height and width of each field
	 */
	private void calculateFields() {
		fieldHeight = canvas.getHeight() / game.getStageInfo().height;
		fieldWidth = canvas.getWidth() / game.getStageInfo().width;
	}

	/**
	 * Draw the canvas - used in the gameloop
	 */
	private void drawCanvas(long now) {
		GraphicsContext g = canvas.getGraphicsContext2D();
		game.draw(g);

		// draw a background color
		g.setFill(Color.GOLDENROD);
		g.fillRect(0, 0, game.getStageInfo().width * fieldWidth, game.getStageInfo().height * fieldHeight);

		for(int x=0; x<game.getStageInfo().width; x++){
			for (int y = 0; y < game.getStageInfo().height; y++) {
				if(game.getStageInfo().isWall(x, y))g.setFill(Color.RED);
				else g.setFill(Color.WHEAT);
				g.fillRect(x*fieldWidth, y*fieldHeight, fieldWidth, fieldHeight);
			}
		}

		g.save();
		g.scale(fieldWidth, fieldHeight);


		// draw 'player'
		/*if(game.player != null)
			game.player.draw(g, now);*/
		g.restore();
	}

	public void btnStartAction(ActionEvent event) {
		System.out.println("btn clicked");
		labelStatus.setText("playing");
		game.newGame();
		calculateFields();
	}
}
