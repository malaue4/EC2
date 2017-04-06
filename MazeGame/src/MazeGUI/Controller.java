package MazeGUI;

import MazeLogic.Game;
import MazeLogic.Level;
import com.sun.javafx.tk.Toolkit;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.*;

public class Controller {
	@FXML
	Label labelStatus;
	@FXML
	Canvas canvas;

	private double fieldHeight;
	private double fieldWidth;
	private float refreshRate = 16 * 1000000;

	Game game = Game.getInstance();

	/**
	 * Executed when JavaFX is initialized. Used to setup the Snake game
	 */
	public void initialize() {
		game.loadTitleLevel();



		calculateFields();

		// Start and control game loop
		new AnimationTimer() {
			long lastUpdate = Toolkit.getToolkit().getMasterTimer().nanos();

			public void handle(long now) {
				if (now > lastUpdate + refreshRate) {
					lastUpdate += refreshRate;
					game.update(now);
					drawCanvas(now);
				}
			}
		}.start();
	}

	/**
	 * Calculate height and width of each field
	 */
	private void calculateFields() {
		fieldHeight = canvas.getHeight() / game.getLevel().height;
		fieldWidth = canvas.getWidth() / game.getLevel().width;
	}

	/**
	 * Draw the canvas - used in the gameloop
	 * @param now the current time in nanoseconds
	 */
	private void drawCanvas(long now) {
		GraphicsContext g = canvas.getGraphicsContext2D();
		g.save();
		g.scale(fieldWidth, fieldHeight);

		game.draw(g, now);
		g.restore();
	}

	/**
	 * Starts a new game, and recalculates field size
	 */
	public void handleStart() {
		//labelStatus.setText("playing");
		game.newGame();
		calculateFields();
	}

	/**
	 * Opens a file chooser to pick a file to save the current level to
	 *
	 */
	public void handleSave(){
		Level level = game.getLevel();

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Level Files", "*.ser"));
		fileChooser.setTitle("Save As");
		File file = fileChooser.showSaveDialog(null);

		if(file==null) return;

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(level);
		} catch (FileNotFoundException e) {
			System.err.println("The file wasn't found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("An IO exception occurred");
			e.printStackTrace();
		}
	}

	/**
	 * Opens a file chooser to pick a level to load
	 *
	 */
	public void handleLoad(){

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Level Files", "*.ser"));
		fileChooser.setTitle("Load Level");
		File file = fileChooser.showOpenDialog(null);

		if(file==null) return;


		Level level = game.loadLevel(file);

		if(level != null) {
			game.setLevel(level);
			calculateFields();
		}
	}

	public void handleMousePressed(MouseEvent mouseEvent) {
		System.out.printf("mouse pressed: (%s, %s)%n", mouseEvent.getX(), mouseEvent.getY());
	}
	public void handleMouseReleased(MouseEvent mouseEvent) {
		System.out.printf("mouse released: (%s, %s)%n", mouseEvent.getX(), mouseEvent.getY());
	}
	public void handleMouseDragged(MouseEvent mouseEvent) {
		System.out.printf("mouse dragged: (%s, %s)%n", mouseEvent.getX(), mouseEvent.getY());
	}
}
