package SnakeGUI;


import SnakeLogic.Game;
import SnakeLogic.Item;
import com.sun.javafx.tk.Toolkit;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class Controller {

	@FXML
	Label labelGameSpeed;
	@FXML
	ProgressBar progressItems;
	@FXML
	Button btnPause;
	@FXML
	Label labelStatus;
	@FXML
	Canvas canvas;

	private double fieldHeight;
	private double fieldWidth;
	private float refreshRate = 16 * 1000000;
	private float drawRate = 16 * 1000000;

	Game game = new Game();

	/**
	 * Executed when JavaFX is initialized. Used to setup the Snake game
	 */
	public void initialize() {
		calculateFields();
		//game.newGame();

		btnPause.disableProperty().bind(game.playingProperty().not());
		//spinnerGameSpeed.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
		//spinnerGameSpeed.valueProperty().addListener((observable, oldValue, newValue) -> game.setGameSpeed(newValue));
		labelGameSpeed.textProperty().bind(game.gameSpeedProperty().asString());
		progressItems.progressProperty().bind(game.levelProgressProperty());

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
		fieldHeight = canvas.getHeight() / game.height;
		fieldWidth = canvas.getWidth() / game.width;
	}

	/**
	 * Draw the canvas - used in the gameloop
	 */
	private void drawCanvas(long now) {
		GraphicsContext g = canvas.getGraphicsContext2D();

		// draw a background color
		g.setFill(Color.GOLDENROD);
		g.fillRect(0, 0, game.width * fieldWidth, game.height * fieldHeight);

		// draw every other field in a checkerboard pattern
		g.setFill(Color.DARKGOLDENROD);
		for (int i = 0; i < game.width; i++) {
			for (int j = i % 2; j < game.height; j += 2) {
				g.fillRect(i * fieldWidth, j * fieldHeight, fieldWidth, fieldHeight);
			}
		}

		// draw items
		for (Item item : game.items) {
			item.draw(g, fieldWidth, fieldHeight, now);
		}

		// draw 'player'
		if(game.player != null)
			game.player.draw(g, fieldWidth, fieldHeight, now);
	}

	public void btnStartAction(ActionEvent event) {
		System.out.println("btn clicked");
		labelStatus.setText("playing");

		game.newGame();
	}

	public void btnPauseAction(ActionEvent event) {
		System.out.println("btn clicked");
		game.setPaused(!game.isPaused());
		if(game.isPaused()) {
			labelStatus.setText("paused");
			btnPause.setText("Resume");
		}else {
			labelStatus.setText("playing");
			btnPause.setText("Pause");
		}
	}
}
