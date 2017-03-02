package SnakeLogic;

import com.sun.javafx.tk.Toolkit;
import javafx.beans.property.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class for the game logic
 */
public class Game {

	private static Game instance = new Game();
	public static Game getInstance() {
		return instance;
	}

	private BooleanProperty paused = new SimpleBooleanProperty(true);
	private BooleanProperty playing = new SimpleBooleanProperty(false);
	private IntegerProperty gameSpeed = new SimpleIntegerProperty(1);
	private DoubleProperty levelProgress = new SimpleDoubleProperty(0);

	private int level = 1;
	private int itemGoal = 10;
	private int itemEaten = 0;
	private int itemSpawnRate = 60;
	public ArrayList<Item> items = new ArrayList<Item>();
	public Snake player;

	private StageInfo stageInfo;

	public KeyCode keyPressed = KeyCode.BACK_SPACE;

	/**
	 * Get a random position
	 */
	public void spawnPlayerAtRandomPosition() {
		Point pos = stageInfo.getRandomPoint();
		player = new Snake(pos.x, pos.y, stageInfo.width, stageInfo.height);
		player.time = Toolkit.getToolkit().getMasterTimer().nanos();
	}

	public void keyPressed(KeyCode keyCode) {
		keyPressed = keyCode;
	}

	/**
	 * Game loop - executed continuously during the game
	 *
	 * @param now game time in nano seconds
	 */
	public void update(long now) {

		if(!isPlaying()) return;

		switch (keyPressed) {
			case DOWN:
				player.move(0, 1);
				break;
			case LEFT:
				player.move(-1, 0);
				break;
			case RIGHT:
				player.move(1, 0);
				break;
			case UP:
				player.move(0, -1);
				break;
		}


		if(!isPaused())itemSpawnRate--;
		if (itemSpawnRate < 0) {
			stageInfo.addItems(1);
			itemSpawnRate = 60 + 5*items.size();
		}

		if(isPaused())player.time = now;
		while(player.time < now) {
			player.time += player.timePerField;
			player.update();

			ArrayList<Item> deadItems = new ArrayList<>();
			for (Item item : items) {
				item.update();
				if (item.getX() == player.getX() && item.getY() == player.getY()) {
					player.eatItem(item);
					item.die();
					itemEaten++;
					levelProgress.setValue((double) itemEaten/itemGoal);
				}
				if(item.isDead()) deadItems.add(item);
			}
			items.removeAll(deadItems);
			for(Segment segment : player.getSegments().subList(2, player.getSegments().size())){
				if (segment.getX() == player.getX() && segment.getY() == player.getY()) {
					gameOver(now);
				}
			}

			if(itemEaten >= itemGoal){
				setLevel(level+1);
			}
		}

	}

	public void newGame() {
		stageInfo = new StageInfo(5);
		setLevel(1);
		setPaused(false);
		setPlaying(true);
	}

	public void setLevel(int level){
		spawnPlayerAtRandomPosition();
		this.level = level;
		itemGoal = 5+5*level;
		itemEaten = 0;
		items.clear();
		levelProgress.setValue(0);
		stageInfo.addItems(2);
		setGameSpeed(level);
	}

	public void gameOver(long now){
		setPlaying(false);
		player.die(now);
	}



	public boolean isPaused() {
		return paused.get();
	}

	public BooleanProperty pausedProperty() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused.set(paused);
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

	public int getGameSpeed() {
		return gameSpeed.get();
	}

	public IntegerProperty gameSpeedProperty() {
		return gameSpeed;
	}

	public void setGameSpeed(int gameSpeed) {
		if(gameSpeed > 0){
			this.gameSpeed.set(gameSpeed);
			player.setSpeed(gameSpeed);
		}
	}

	public DoubleProperty levelProgressProperty() {
		return levelProgress;
	}

	public StageInfo getStageInfo() {
		return stageInfo;
	}
}
