package MazeLogic.pathfinding;

import MazeLogic.Level;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by Martin on 15-04-2017.
 */
public abstract class PathFinder {

	protected Set<Level.Field> visited;
	protected Level.Field previousStart;

	public List<Level.Field> findPath(Level.Field start, Level.Field goal) {
		if (start==goal) return Collections.emptyList();
		if (start.getLinkedFields().contains(goal)) return Collections.singletonList(goal);

		return calculatePath(start, goal);
	}

	public abstract List<Level.Field> calculatePath(Level.Field start, Level.Field goal);

	public Set<Level.Field> getVisited() {
		return visited;
	}
}
