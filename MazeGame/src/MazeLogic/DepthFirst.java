package MazeLogic;

import java.util.*;

import static java.lang.Math.abs;

/**
 * Created by Martin on 15-04-2017.
 */
public class DepthFirst implements PathFinder {

	@Override
	public List<Level.Field> getPath(Level.Field start, Level.Field goal) {
		Set<Level.Field> visited = new HashSet<>();
		List<Level.Field> discovered = new ArrayList<>();

		Map<Level.Field, Level.Field> cameFrom = new HashMap<>();

		discovered.add(start);
		while(!discovered.isEmpty()){
			discovered.sort((o1, o2) -> (int) (getHeuristicCost(o1, goal)-getHeuristicCost(o2, goal)));
			Level.Field field = discovered.remove(0);
			if(field.equals(goal)) break;
			visited.add(field);

			for(Level.Field neighbour : field.getLinkedFields()){
				if(visited.contains(neighbour) || discovered.contains(neighbour))
					continue;

				discovered.add(neighbour);
				cameFrom.put(neighbour, field);
			}
		}

		List<Level.Field> path = new ArrayList<>();
		Level.Field field = goal;
		while(cameFrom.containsKey(field)){
			path.add(0, field);
			field = cameFrom.get(field);
		}
		return path;
	}

	Double getHeuristicCost(Level.Field node1, Level.Field node2){
		return abs(node1.getX()-node2.getX()) + abs(node1.getY()-node2.getY());
	}
}
