package SnakeLogic;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class StageInfo {
	public int width;
	public int height;
	private Color[] colors = {Color.BLUE, Color.BLUEVIOLET, Color.VIOLET, Color.MEDIUMVIOLETRED, Color.RED};
	private Random random = new Random();

	Queue<GameObject> gameObjects = new PriorityQueue<>();
	Map<Point, Field> fieldMap = new HashMap<>();

	public StageInfo(int levelSize) {
		width = 3*levelSize;
		height = 2*levelSize;

		// Creates the fields
		for (int i = 0; i < width * height; i++) {
			Point point = new Point(i%width, i/width);
			fieldMap.put(point, new Field());
		}

		// Makes their east-west north-south match up
		for (int i = 0; i < width * height; i++) {
			int x = i % width;
			int y = i / width;
			Point point = new Point(x, y);
			Field field = fieldMap.get(point);
			point.setLocation((x+1)%width, y);
			field.setEast(fieldMap.get(point));
			point.setLocation(x, (y+1)%height);
			field.setSouth(fieldMap.get(point));
		}
	}

	public boolean addGameObject(GameObject gameObject, Point point){
		Field field = fieldMap.get(point);
		if(field.isFree()){
			field.setContents(gameObject);
			return true;
		}
		return false;
	}

	public void addItems(int amount) {
		for (int i = 0; i < amount; i++) {
			Point point = getRandomPoint();
			if(point == null) continue;
			Color color = getRandomColor();
			addGameObject(new DisappearingItem(color, point.x, point.y), point);
		}
	}

	private Color getRandomColor() {
		return colors[random.nextInt(colors.length)];
	}

	public Point getRandomPoint() {
		List<Map.Entry<Point, Field>> freeFields = fieldMap.entrySet().stream()
				.filter(entry -> entry.getValue().isFree())
				.collect(Collectors.toList());

		if(freeFields.size()>0)
			return freeFields.get(random.nextInt(freeFields.size())).getKey();
		else
			return null;
	}
}
