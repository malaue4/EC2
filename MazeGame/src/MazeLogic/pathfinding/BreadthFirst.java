package MazeLogic.pathfinding;

import MazeLogic.Level;

import java.util.*;

import static java.lang.Math.abs;

/**
 * Created by Martin on 15-04-2017.
 */
public class BreadthFirst extends PathFinder {

	@Override
	public List<Level.Field> calculatePath(Level.Field start, Level.Field goal) {
		visited = new HashSet<>();
		List<Level.Field> discovered = new ArrayList<>();
		Map<Level.Field, Level.Field> cameFrom = new HashMap<>();

		//Map<Level.Field, Integer> cost = new HashMap<>();

		//cost.put(start, 0);
		discovered.add(start);
		while (!discovered.isEmpty()) {
			//discovered.sort(Comparator.comparingInt(cost::get));
			Level.Field field = discovered.remove(0);
			if (field.equals(goal)) break;
			visited.add(field);

			for (Level.Field neighbour : field.getLinkedFields()) {
				if (visited.contains(neighbour) || discovered.contains(neighbour) || field == previousStart)
					continue;

				//cost.put(neighbour, cost.get(field)+1);
				discovered.add(neighbour);
				cameFrom.put(neighbour, field);
			}
		}

		previousStart = start;

		LinkedList<Level.Field> path = new LinkedList<>();
		Level.Field field = goal;
		while (cameFrom.containsKey(field)) {
			path.addFirst(field);
			field = cameFrom.get(field);
		}
		return path;
	}
}
