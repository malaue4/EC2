package SnakeGUI;


import SnakeLogic.Segment;
import SnakeLogic.Snek;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.*;

public class Controller {

	@FXML
	Label labelStatus;
	@FXML
	Canvas canvas;

	private double fieldHeight;
	private double fieldWidth;
	private int width = 30;
	private int height = 20;
	private Random random = new Random();
	private int gameLoopDelay = 500;
	private float refreshRate = 300;
	private KeyCode keyPressed = KeyCode.BACK_SPACE;

	private ArrayList<Item> items = new ArrayList<Item>();
	private Snek player;

	private int itemSpawnCount = 10;

	Color[] colors = {Color.BLUE, Color.BLUEVIOLET, Color.VIOLET, Color.MEDIUMVIOLETRED, Color.RED};

	public void btnStartAction(ActionEvent event) {
		System.out.println("btn clicked");
		labelStatus.setText("test");
		getRandomPosition();
		drawCanvas();
	}

	/**
	 * Executed when JavaFX is initialized. Used to setup the Snake game
	 */
	public void initialize() {
		AddItems();

		calculateFields();
		getRandomPosition();
		//drawCanvas();

		// Start and control game loop
		new AnimationTimer() {
			long lastUpdate;

			public void handle(long now) {
				if (now > lastUpdate + refreshRate * 1000000) {
					lastUpdate = now;
					update(now);
				}
			}
		}.start();
	}

	private void AddItems() {
		items.add(new Item(colors[random.nextInt(colors.length)], random.nextInt(width), random.nextInt(height)));
		items.add(new Item(colors[random.nextInt(colors.length)], random.nextInt(width), random.nextInt(height)));
	}

	void keyPressed(KeyCode keyCode) {
		this.keyPressed = keyCode;
	}

	/**
	 * Game loop - executed continously during the game
	 *
	 * @param now game time in nano seconds
	 */
	private void update(long now) {

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


		itemSpawnCount--;
		if (itemSpawnCount < 0) {
			AddItems();
			itemSpawnCount = 4 + items.size();
		}
		player.update();

		ArrayList<Item> eatenItems = new ArrayList<>();
		for (Item item : items) {
			if (item.getX() == player.getX() && item.getY() == player.getY()) {
				player.eatItem(item);
				eatenItems.add(item);
			}
		}
		items.removeAll(eatenItems);
		drawCanvas();

	}

	/**
	 * Get a random position
	 */

	private void getRandomPosition() {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		player = new Snek(x, y, width, height);
	}

	/**
	 * Calculate height and width of each field
	 */
	private void calculateFields() {
		this.fieldHeight = canvas.getHeight() / this.height;
		this.fieldWidth = canvas.getWidth() / this.width;
	}

	/**
	 * Draw the canvas - used in the gameloop
	 */
	private void drawCanvas() {
		GraphicsContext g = canvas.getGraphicsContext2D();

		g.clearRect(0, 0, width * fieldWidth, height * fieldHeight);

		// draw all fields
		g.setFill(Color.LIGHTGRAY);
		for (int i = 0; i < width; i++) {
			for (int j = i % 2; j < height; j += 2) {
				g.fillRect(i * fieldWidth, j * fieldHeight, fieldWidth, fieldHeight);
			}
		}

		// draw items
		for (Item item : items) {
			g.setFill(item.getColor());
			g.fillRoundRect(item.getX() * fieldWidth, item.getY() * fieldHeight, fieldWidth, fieldHeight, 5, 5);
		}

		// draw 'player'
		List<Segment> segments = player.getSegments();
		for (int i = segments.size() - 1; i >= 0; i--) {
			Segment segment = segments.get(i);
			segment.draw(g, fieldWidth, fieldHeight);
		}
	}
}
