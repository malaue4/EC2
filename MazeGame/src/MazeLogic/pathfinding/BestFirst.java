package MazeLogic.pathfinding;

import MazeLogic.Level;

import java.util.*;

import static java.lang.Math.abs;

/**
 * Created by Martin on 15-04-2017.
 */
public class BestFirst extends PathFinder {

	@Override
	public List<Level.Field> calculatePath(Level.Field start, Level.Field goal) {
		visited = new HashSet<>();
		List<Level.Field> discovered = new ArrayList<>();
		Map<Level.Field, Level.Field> cameFrom = new HashMap<>();




		discovered.add(start);
		while(!discovered.isEmpty()){
			discovered.sort(Comparator.comparingInt(o -> getHeuristicCost(o, goal)));

			Level.Field field = discovered.remove(0);
			if(field.equals(goal)) break;
			visited.add(field);

			for(Level.Field neighbour : field.getLinkedFields()){
				if(visited.contains(neighbour) || discovered.contains(neighbour) || field == previousStart)
					continue;

				discovered.add(neighbour);
				cameFrom.put(neighbour, field);
			}
		}

		previousStart = start;

		List<Level.Field> path = new LinkedList<>();
		Level.Field field = goal;
		while(cameFrom.containsKey(field)){
			path.add(0, field);
			field = cameFrom.get(field);
		}
		return path;
	}

	int getHeuristicCost(Level.Field node1, Level.Field node2){
		return (int)(abs(node1.getX()-node2.getX()) + abs(node1.getY()-node2.getY()));
	}
}
