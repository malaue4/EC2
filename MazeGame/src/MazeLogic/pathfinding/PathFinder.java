package MazeLogic.pathfinding;

import MazeLogic.Level;

import java.util.List;
import java.util.Set;

/**
 * Created by Martin on 15-04-2017.
 */
public interface PathFinder {
	List<Level.Field> getPath(Level.Field start, Level.Field goal);

	Set<Level.Field> getVisited();
}
