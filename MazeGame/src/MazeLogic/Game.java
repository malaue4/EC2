package MazeLogic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.io.*;

/**
 * The main game class
 */
public class Game {
	private static Game instance = new Game();

	private Level level;
	private Player player;
	private Ghost rambler;

	public static Game getInstance() {
		return instance;
	}

	private Game(){}

	/**
	 * updates the current game state
	 * @param now the current time
	 */
	public void update(long now) {
		player.update(now);
		rambler.update(now);
	}


	/**
	 * draws the current game state
	 * @param graphicsContext the context used to issue draw calls to the canvas
	 * @param now the current time
	 */
	public void draw(GraphicsContext graphicsContext, long now) {
		double width = level.getWidth();
		double height = level.getHeight();
		graphicsContext.setLineWidth(0.1);

		// draw a background color
		graphicsContext.setFill(Color.BEIGE);
		graphicsContext.setStroke(Color.BURLYWOOD);
		graphicsContext.fillRect(0, 0, width, height);
		graphicsContext.strokeRect(0, 0, width, height);

		// draw the player
		player.draw(graphicsContext, now);
		rambler.draw(graphicsContext, now);

		// draw the walls
		for(int x=0; x<level.width; x++){
			for (int y = 0; y < level.height; y++) {
				Level.Field field = level.getField(x, y);
				if(field ==null){
					graphicsContext.setFill(Color.RED);
					graphicsContext.fillRect(x, y, 1, 1);
				} else {
					Level.Field right = level.getField(x+1, y);
					Level.Field down = level.getField(x, y+1);
					if(!field.isLinked(right)) graphicsContext.strokeLine(x+1, y, x+1, y+1);
					if(!field.isLinked(down)) graphicsContext.strokeLine(x, y+1, x+1, y+1);
				}
			}
		}
	}

	/**
	 * start a new level
	 */
	public void newGame() {
		/*
		MazeGenerator generator = new RecursiveBacktracker();
		generator.setSize(30, 20);
		setLevel(generator.generateMaze());
		player = new Player(getLevel().getField(0,0));
		goal = new Goal(getLevel().getField(30-1, 20-1));
		*/
		player = new Player(level.getField(0,0));
	}

	/**
	 * Handles keypresses, tells the player where to move
	 * @param code - the code of the key pressed
	 */
	public void keyPressed(KeyCode code) {
		switch (code){
			case LEFT:
				player.move(-1, 0);
				break;
			case UP:
				player.move(0, -1);
				break;
			case RIGHT:
				player.move(1, 0);
				break;
			case DOWN:
				player.move(0, 1);
				break;
		}
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Level getLevel() {
		return level;
	}

	/**
	 * Load a level from a file
	 * @param file - the file to load the level from
	 * @return the loaded level, or null if an error occurred
	 */
	public Level loadLevel(File file) {
		Level level = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			level = (Level) objectInputStream.readObject();
		} catch (FileNotFoundException e) {
			System.err.println("File was not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("File failed to load");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("The corresponding class could not be found");
			e.printStackTrace();
		}
		return level;
	}

	/**
	 * Load the title level
	 */
	public void loadTitleLevel() {
		level = loadLevel(new File(getClass().getResource("/default.ser").getPath()));
		player = new Player(level.getField(0,0));
		rambler = new Ghost(level.getField(level.width-1, level.height-1));
		rambler.setColor(Color.DARKRED);
	}
}
