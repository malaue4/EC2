package MazeLogic;

import com.sun.javafx.tk.Toolkit;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.io.*;

/**
 * The main game class
 */
public class Game {
	private static Game instance = new Game();


	private BooleanProperty playing = new SimpleBooleanProperty(false);
	private Level level;
	private Player player;

	public static Game getInstance() {
		return instance;
	}

	private Game(){
		level = loadLevel(new File(getClass().getResource("/default.ser").getPath()));
		player = new Player(level.getField(0,0));
	}

	public void update(long now) {
		player.update(now);
	}

	public boolean isPlaying() {
		return playing.get();
	}

	public BooleanProperty playingProperty() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing.set(playing);
	}

	public void draw(GraphicsContext graphicsContext, long now) {
		double width = level.getWidth();
		double height = level.getHeight();
		graphicsContext.setLineWidth(0.1);

		// draw a background color
		graphicsContext.setFill(Color.BEIGE);
		graphicsContext.setStroke(Color.BURLYWOOD);
		graphicsContext.fillRect(0, 0, width, height);
		graphicsContext.strokeRect(0, 0, width, height);
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

		player.draw(graphicsContext, now);
	}

	public Level getStageInfo() {
		return level;
	}

	public void newGame() {
		level = new Level(10, 10);
	}

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
}
