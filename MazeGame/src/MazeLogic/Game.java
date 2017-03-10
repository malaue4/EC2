package MazeLogic;

import com.sun.javafx.tk.Toolkit;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

/**
 * Created by martin on 3/9/17.
 */
public class Game {
	private static Game instance = new Game();


	private BooleanProperty playing = new SimpleBooleanProperty(false);
	private Level level;

	public static Game getInstance() {
		return instance;
	}

	private Game(){
		level = new Level(10, 10);
	}

	public void update(long now) {

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

	public void draw(GraphicsContext graphicsContext) {

	}

	public Level getStageInfo() {
		return level;
	}

	public void newGame() {
		level = new Level(10, 10);
	}

	public void keyPressed(KeyCode code) {

	}
}
