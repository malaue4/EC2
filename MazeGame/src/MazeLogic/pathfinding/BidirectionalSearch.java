package MazeLogic.pathfinding;

import MazeLogic.Level;

import java.util.*;

/**
 * Created by Martin on 5/2/2017.
 */
public class BidirectionalSearch implements PathFinder {

	private Set<Level.Field> visited;
	private Level.Field previousStart;

	@Override
	public List<Level.Field> getPath(Level.Field start, Level.Field goal) {
		if (start==goal) return Collections.emptyList();
		if (start.getLinkedFields().contains(goal)) return Collections.singletonList(goal);

		LinkedList<Level.Field>
				discoveredStart = new LinkedList<>(),
				discoveredGoal = new LinkedList<>();
		visited = new HashSet<>();

		Map<Level.Field, Level.Field> cameFrom = new HashMap<>();
		Level.Field intersectStart = null, intersectGoal = null;

		discoveredStart.add(start);
		discoveredGoal.add(goal);

		while (!discoveredGoal.isEmpty() && !discoveredStart.isEmpty()){
			Level.Field field = discoveredStart.pollFirst();

			visited.add(field);
			for(Level.Field neighbour : field.getLinkedFields()){
				if(visited.contains(neighbour))
					continue;

				if(!discoveredStart.contains(neighbour)){
					discoveredStart.add(neighbour);
				} else {
					continue;
				}

				if(discoveredGoal.contains(neighbour)){
					intersectGoal = neighbour;
					intersectStart = field;
					break;
				}

				cameFrom.put(neighbour, field);
			}
			if(intersectStart != null) break;
			field = discoveredGoal.pollFirst();

			visited.add(field);
			for(Level.Field neighbour : field.getLinkedFields()){
				if(visited.contains(neighbour))
					continue;

				if(!discoveredGoal.contains(neighbour)){
					discoveredGoal.add(neighbour);
				} else {
					continue;
				}

				if(discoveredStart.contains(neighbour)){
					intersectGoal = field;
					intersectStart = neighbour;
					break;
				}

				cameFrom.put(neighbour, field);
			}
			if(intersectGoal != null) break;
		}

		LinkedList<Level.Field> path = new LinkedList<>();

		// Construct path
		for (Level.Field field=intersectStart; field != start; field=cameFrom.get(field)){
			path.addFirst(field);
		}
		for (Level.Field field=intersectGoal; field != null; field=cameFrom.get(field)){
			path.add(field);
		}

		return path;
	}

	@Override
	public Set<Level.Field> getVisited() {
		return visited;
	}
}
