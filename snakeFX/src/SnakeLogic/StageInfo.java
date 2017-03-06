package SnakeLogic;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class StageInfo {
	public int width;
	public int height;
	private Color[] colors = {Color.BLUE, Color.BLUEVIOLET, Color.VIOLET, Color.MEDIUMVIOLETRED, Color.RED};
	private Random random = new Random();
	private Map<Point, Field> fieldMap = new HashMap<>();

	public StageInfo(int levelSize) {
		width = 3*levelSize;
		height = 2*levelSize;

		// Creates the fields
		for (int i = 0; i < width * height; i++) {
			Point point = new Point(i%width, i/width);
			Field field = new Field();
			field.setLocation(point);
			getFieldMap().put(point, field);
		}

		// Makes their east-west north-south match up
		for (int i = 0; i < width * height; i++) {
			int x = i % width;
			int y = i / width;
			Point point = new Point(x, y);
			Field field = getFieldMap().get(point);
			point.setLocation((x+1)%width, y);
			field.setEast(getFieldMap().get(point));
			point.setLocation(x, (y+1)%height);
			field.setSouth(getFieldMap().get(point));
		}
	}

	Color getRandomColor() {
		return colors[random.nextInt(colors.length)];
	}

	public Point getRandomPoint() {
		List<Map.Entry<Point, Field>> freeFields = getFieldMap().entrySet().stream()
				.filter(entry -> entry.getValue().isFree())
				.collect(Collectors.toList());

		if(freeFields.size()>0)
			return freeFields.get(random.nextInt(freeFields.size())).getKey();
		else
			return null;
	}

	public Map<Point, Field> getFieldMap() {
		return fieldMap;
	}
}
