package MazeLogic.pathfinding;

import MazeLogic.Level;

import java.util.List;

/**
 * Created by Martin on 15-04-2017.
 */
public interface PathFinder {
	List<Level.Field> getPath(Level.Field start, Level.Field goal);
}
