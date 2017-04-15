package MazeLogic;

import java.util.*;

import static java.lang.Math.abs;

/**
 * Created by Martin on 15-04-2017.
 */
public class BreadthFirst implements PathFinder {

	@Override
	public List<Level.Field> getPath(Level.Field start, Level.Field goal) {
		Set<Level.Field> visited = new HashSet<>();
		List<Level.Field> discovered = new ArrayList<>();

		Map<Level.Field, Level.Field> cameFrom = new HashMap<>();

		Map<Level.Field, Integer> cost = new HashMap<>();

		cost.put(start, 0);
		discovered.add(start);
		while (!discovered.isEmpty()) {
			discovered.sort(Comparator.comparingInt(cost::get));
			Level.Field field = discovered.remove(0);
			if (field.equals(goal)) break;
			visited.add(field);

			for (Level.Field neighbour : field.getLinkedFields()) {
				if (visited.contains(neighbour) || discovered.contains(neighbour))
					continue;

				cost.put(neighbour, cost.get(field)+1);
				discovered.add(neighbour);
				cameFrom.put(neighbour, field);
			}
		}

		List<Level.Field> path = new ArrayList<>();
		Level.Field field = goal;
		while (cameFrom.containsKey(field)) {
			path.add(0, field);
			field = cameFrom.get(field);
		}
		return path;
	}
}
