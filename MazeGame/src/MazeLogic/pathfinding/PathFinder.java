package MazeLogic.pathfinding;

import MazeLogic.Game;
import MazeLogic.Level;

import java.util.*;

import static java.lang.Math.abs;

/**
 * Created by Martin on 15-04-2017.
 */
public abstract class PathFinder {

	Set<Level.Field> visited = new HashSet<>();
	Set<Level.Field> seen = new HashSet<>();
	protected Level.Field previousStart;

	public List<Level.Field> findPath(Level.Field start, Level.Field goal) {
		if (start==goal) return Collections.emptyList();
		if (start.getLinkedFields().contains(goal)) return Collections.singletonList(goal);

		long startTime = Game.getNow();
		visited.clear();
		visited.add(previousStart);
		final List<Level.Field> path = calculatePath(start, goal);
		previousStart = start;
		System.out.printf("time taken = %d ms%n", (Game.getNow() - startTime)/1000);
		return path;
	}

	public abstract List<Level.Field> calculatePath(Level.Field start, Level.Field goal);

	public Set<Level.Field> getVisited() {
		return visited;
	}

	List<Level.Field> constructPath(Level.Field goal,
				Map<Level.Field, Level.Field> cameFrom) {
		List<Level.Field> path = new LinkedList<>();
		Level.Field field = goal;
		while(cameFrom.containsKey(field)){
			path.add(0, field);
			field = cameFrom.get(field);
		}
		return path;
	}

	int getManhattanDistance(Level.Field node1, Level.Field node2){
		return (int) (abs(node1.getX()-node2.getX()) + abs(node1.getY()-node2.getY()));
	}

	public Level.Field getPreviousStart() {
		return previousStart;
	}

	public Set<Level.Field> getSeen() {
		return seen;
	}
}
