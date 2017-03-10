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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.*;

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
		g.save();
		g.scale(fieldWidth, fieldHeight);

		game.draw(g, now);
		g.restore();
	}

	public void btnStartAction(ActionEvent event) {
		System.out.println("btn clicked");
		labelStatus.setText("playing");
		game.newGame();
		calculateFields();
	}

	public void handleSave(ActionEvent actionEvent){
		Level level = game.getStageInfo();

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



	public void handleLoad(ActionEvent actionEvent){

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
		X = (int) (mouseEvent.getX()/fieldWidth);
		Y = (int) (mouseEvent.getY()/fieldHeight);

	}

	public void handleMouseReleased(MouseEvent mouseEvent) {
		System.out.printf("mouse released: (%s, %s)%n", mouseEvent.getX(), mouseEvent.getY());
	}

	private static int X, Y;
	public void handleMouseDragged(MouseEvent mouseEvent) {
		//System.out.printf("mouse dragged: (%s, %s)%n", mouseEvent.getX(), mouseEvent.getY());
		int x = (int) (mouseEvent.getX()/fieldWidth);
		int y = (int) (mouseEvent.getY()/fieldHeight);
		if(X!=x || Y!=y) {
			System.out.printf("field: (%s, %s) -> (%s, %s)%n", X, Y, x, y);
			if(mouseEvent.getButton() == MouseButton.PRIMARY){
				game.getStageInfo().getField(x, y).link(game.getStageInfo().getField(X, Y));
			} else if(mouseEvent.getButton() == MouseButton.SECONDARY){
				game.getStageInfo().getField(x, y).unlink(game.getStageInfo().getField(X, Y));
			}
		}
		X = x;
		Y = y;
	}
}
