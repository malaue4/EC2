package utility;

import MazeLogic.Level;

import java.awt.Point;

/**
 * Created by Martin on 11-03-2017.
 */
public class LevelEditor {
	Point marker;
	Level level;

	private void placeMarker(int x, int y) {
		marker.setLocation(x, y);
	}

	private void placeWall(int x, int y) {
		if(marker.x!=x || marker.y!=y) {
			System.out.printf("field: (%s, %s) -> (%s, %s)%n", marker.x, marker.y, x, y);
			level.getField(x, y).unlink(level.getField(marker.x, marker.y));
		}
		placeMarker(x, y);

	}

	private void carveWall(int x, int y) {
		if(marker.x!=x || marker.y!=y) {
			System.out.printf("field: (%s, %s) -> (%s, %s)%n", marker.x, marker.y, x, y);
			level.getField(x, y).link(level.getField(marker.x, marker.y));
		}
		placeMarker(x, y);
	}

	enum EditMode{
		freehand,
		line,
		rectangle
	}
}
