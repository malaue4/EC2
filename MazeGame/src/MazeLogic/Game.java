package MazeLogic;

import MazeGUI.LevelEditor;
import MazeLogic.pathfinding.AStar;
import MazeLogic.pathfinding.BidirectionalSearch;
import MazeLogic.pathfinding.BreadthFirst;
import MazeLogic.pathfinding.BestFirst;
import entities.*;
import entities.effects.Effect;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import utility.Direction;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * The main game class
 */
public class Game {
	private static Game instance = new Game();

	public SimpleObjectProperty<Level> levelProperty = new SimpleObjectProperty<>(this, "levelProperty");

	private PackMan player;
	private ArrayList<Ghost> ghosts = new ArrayList<>();
	private ArrayList<Pellet> pellets = new ArrayList<>();
	private ArrayList<Effect> effects = new ArrayList<>();;
	private LevelEditor editor;

	public Game.modes mode = Game.modes.title;
	private Ghost rambler;
	private Ghost scrambler;
	private Ghost ambler;
	private Ghost johnny;

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
		for(Ghost ghost : ghosts){
			ghost.update(now);
			if(player.getCurrentPosition().equals(ghost.getCurrentPosition())){
				if(ghost.getMood() == Ghost.Mood.blue){
					ghost.setMood(Ghost.Mood.retreat);
				} else if(ghost.getMood() != Ghost.Mood.retreat){
					System.out.println("player was caught");
				}
			}
		}

		ArrayList<GameObject> deathrow = new ArrayList<>();
		for(Pellet pellet : pellets){
			if(player.getPosition().equals(pellet.getPosition())){
				pellet.die();
				deathrow.add(pellet);
				if(pellet instanceof PowerPellet){
					for(Ghost ghost :ghosts){
						ghost.setMood(Ghost.Mood.blue);
					}
				}
			}
		}

		for(Effect effect : effects){
			effect.update(now);
			if(effect.isDead()) deathrow.add(effect);
		}

		for(GameObject gameObject : deathrow){
			if(pellets.contains(gameObject)) pellets.remove(gameObject);
			else if(effects.contains(gameObject)) effects.remove(gameObject);
		}
	}


	/**
	 * draws the current game state
	 * @param graphicsContext the context used to issue draw calls to the canvas
	 * @param now the current time
	 */
	public void draw(GraphicsContext graphicsContext, long now) {
		Level level = getLevel();
		double width = level.getWidth();
		double height = level.getHeight();
		graphicsContext.setLineWidth(0.1);

		// draw a background color
		graphicsContext.setFill(Color.BEIGE);
		graphicsContext.setStroke(Color.BURLYWOOD);
		graphicsContext.fillRect(0, 0, width, height);
		graphicsContext.strokeRect(0, 0, width, height);

		// draw the walls
		for(int x = 0; x< level.width; x++){
			for (int y = 0; y < level.height; y++) {
				Level.Field field = level.getField(x, y);
				if(field == null){
					graphicsContext.setFill(Color.RED);
					graphicsContext.fillRect(x, y, 1, 1);
				} else {
					Level.Field right = level.getField(x+1, y);
					Level.Field down = level.getField(x, y+1);
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

		// draw the pellets
		for(Pellet pellet : pellets){
			pellet.draw(graphicsContext, now);
		}
		// draw effects
		for(Effect effect : effects){
			effect.draw(graphicsContext, now);
		}
		// draw the player
		player.draw(graphicsContext, now);
		// and then the ghosts
		for(Ghost ghost : ghosts){
			ghost.draw(graphicsContext, now);
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
		final Level level = getLevel();
		player = new PackMan(level.getField(level.width/2, level.height*2/3));
		pellets.clear();
		ghosts.clear();

		rambler = new Ghost(level.getField(6, 4));
		rambler.setColor(Color.BLUE);
		rambler.setPathfinder(new BidirectionalSearch());
		rambler.setCorner(getLevel().getField(0,0));
		ghosts.add(rambler);

		scrambler = new Ghost(level.getField(6, 5));
		scrambler.setColor(Color.ORANGE);
		scrambler.setPathfinder(new BestFirst());
		scrambler.setCorner(getLevel().getField(0,getLevel().height-1));
		ghosts.add(scrambler);

		ambler = new Ghost(level.getField(8, 4));
		ambler.setColor(Color.PINK);
		ambler.setPathfinder(new BreadthFirst());
		ambler.setCorner(getLevel().getField(getLevel().width-1,0));
		ghosts.add(ambler);

		johnny = new Ghost(level.getField(8, 5));
		johnny.setColor(Color.RED);
		johnny.setPathfinder(new AStar());
		johnny.setCorner(getLevel().getField(getLevel().width-1,getLevel().height-1));
		ghosts.add(johnny);

		for(int i = 0; i< level.width * level.height; i++){
			final int x = i % level.width;
			final int y = i / level.width;
			if(((x ==0 || x == level.width-1) && (y == 0 || y == level.height-1))){
				pellets.add(new PowerPellet(Color.YELLOWGREEN, x, y));
			} else if (x < 6 || x > 8 || y < 4 || y > 6) {
				pellets.add(new Pellet(Color.YELLOWGREEN, x, y));
			}
		}
	}

	/**
	 * Handles keypresses, tells the player where to move
	 * @param code - the code of the key pressed
	 */
	public void keyPressed(KeyCode code) {
		switch (code){
			case LEFT:
				player.move(Direction.left);
				break;
			case UP:
				player.move(Direction.up);
				break;
			case RIGHT:
				player.move(Direction.right);
				break;
			case DOWN:
				player.move(Direction.down);
				break;
		}
	}

	public void setLevel(Level level) {
		this.levelProperty.set(level);
		newGame();
	}

	public Level getLevel() {
		return levelProperty.get();
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
		if(level == null){
			level = new Level(15, 10);
		}
		return level;
	}

	/**
	 * Load the title level
	 */
	public void loadTitleLevel() {
		levelProperty.set(loadLevel(new File(getClass().getResource("/default.ser").getPath())));
		Level level = levelProperty.get();
		player = new PackMan(level.getField(7,5));
		Ghost rambler = new Ghost(level.getField(0, 0));
		Ghost scrambler = new Ghost(level.getField(0, level.height-1));
		Ghost ambler = new Ghost(level.getField(level.width-1, 0));
		Ghost johhny = new Ghost(level.getField(level.width-1, level.height-1));
		rambler.setColor(Color.BLUE);
		scrambler.setColor(Color.ORANGE);
		ambler.setColor(Color.PINK);
		johhny.setColor(Color.RED);
		ghosts.add(rambler);
		ghosts.add(scrambler);
		ghosts.add(ambler);
		ghosts.add(johhny);
		mode = modes.title;
	}

	public LevelEditor getEditor() {
		if(editor == null){
			editor = new LevelEditor(this);
		}
		return editor;
	}

	public PackMan getPlayer() {
		return player;
	}

	public static long getNow() {
		return com.sun.javafx.tk.Toolkit.getToolkit().getMasterTimer().nanos();
	}

	public void spawnEffect(Point position, Effect effect) {
		effect.setPosition(position);
		effects.add(effect);
	}

	public Ghost getScrambler() {
		return scrambler;
	}

	public Ghost getRambler() {
		return rambler;
	}

	public Ghost getAmbler() {
		return ambler;
	}

	public Ghost getJohnny() {
		return johnny;
	}

	public enum modes {
		title,
		play,
		edit
	}
}
