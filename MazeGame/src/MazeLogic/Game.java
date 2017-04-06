package MazeLogic;

import MazeGUI.LevelEditor;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * The main game class
 */
public class Game {
	private static Game instance = new Game();

	public SimpleObjectProperty<Level> levelProperty = new SimpleObjectProperty<>(this, "levelProperty");

	private Player player;
	private ArrayList<Ghost> mGhosts = new ArrayList<>();
	private LevelEditor editor;

	public Game.modes mode = Game.modes.title;

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
		for(Ghost ghost : mGhosts){
			ghost.update(now);
		}
	}


	/**
	 * draws the current game state
	 * @param graphicsContext the context used to issue draw calls to the canvas
	 * @param now the current time
	 */
	public void draw(GraphicsContext graphicsContext, long now) {
		double width = levelProperty.get().getWidth();
		double height = levelProperty.get().getHeight();
		graphicsContext.setLineWidth(0.1);

		// draw a background color
		graphicsContext.setFill(Color.BEIGE);
		graphicsContext.setStroke(Color.BURLYWOOD);
		graphicsContext.fillRect(0, 0, width, height);
		graphicsContext.strokeRect(0, 0, width, height);

		// draw the player
		player.draw(graphicsContext, now);
		for(Ghost ghost : mGhosts){
			ghost.draw(graphicsContext, now);
		}

		// draw the walls
		for(int x = 0; x< levelProperty.get().width; x++){
			for (int y = 0; y < levelProperty.get().height; y++) {
				Level.Field field = levelProperty.get().getField(x, y);
				if(field == null){
					graphicsContext.setFill(Color.RED);
					graphicsContext.fillRect(x, y, 1, 1);
				} else {
					Level.Field right = levelProperty.get().getField(x+1, y);
					Level.Field down = levelProperty.get().getField(x, y+1);
					if(!field.isLinked(right)) graphicsContext.strokeLine(x+1, y, x+1, y+1);
					if(!field.isLinked(down)) graphicsContext.strokeLine(x, y+1, x+1, y+1);
					if(mode == modes.edit){
						Point marker = getEditor().getMarker();
						if(field.getLocation().equals(marker)){
							graphicsContext.setFill(Color.rgb(234,23,234, 0.4));
							graphicsContext.fillRect(x, y, 1, 1);
						}
					}
				}
			}
		}
	}

	/**
	 * start a new levelProperty
	 */
	public void newGame() {
		/*
		MazeGenerator generator = new RecursiveBacktracker();
		generator.setSize(30, 20);
		setLevel(generator.generateMaze());
		player = new Player(getLevel().getField(0,0));
		goal = new Goal(getLevel().getField(30-1, 20-1));
		*/
		player = new Player(levelProperty.get().getField(0,0));
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
		this.levelProperty.set(level);
	}

	public Level getLevel() {
		return levelProperty.get();
	}

	/**
	 * Load a levelProperty from a file
	 * @param file - the file to load the levelProperty from
	 * @return the loaded levelProperty, or null if an error occurred
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
		if(level == null){
			level = new Level(15, 10);
		}
		return level;
	}

	/**
	 * Load the title level
	 */
	public void loadTitleLevel() {
		levelProperty.set(loadLevel(new File(getClass().getResource("/default").getPath())));
		Level level = levelProperty.get();
		player = new Player(level.getField(7,5));
		Ghost rambler = new Ghost(level.getField(0, 0));
		Ghost scrambler = new Ghost(level.getField(0, level.height-1));
		Ghost ambler = new Ghost(level.getField(level.width-1, 0));
		Ghost johhny = new Ghost(level.getField(level.width-1, level.height-1));
		rambler.setColor(Color.DARKRED);
		scrambler.setColor(Color.DARKBLUE);
		ambler.setColor(Color.DARKGOLDENROD);
		johhny.setColor(Color.DARKORANGE);
		mGhosts.add(rambler);
		mGhosts.add(scrambler);
		mGhosts.add(ambler);
		mGhosts.add(johhny);
		mode = modes.title;
	}

	public LevelEditor getEditor() {
		if(editor == null){
			editor = new LevelEditor(this);
		}
		return editor;
	}

	public enum modes {
		title,
		play,
		edit
	}
}
