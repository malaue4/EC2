package MazeLogic.pathfinding;

import MazeLogic.Level;

import java.util.*;

import static java.lang.Math.abs;

/**
 * Created by Martin on 15-04-2017.
 */
public class AStar extends PathFinder {

	@Override
	public List<Level.Field> calculatePath(Level.Field start, Level.Field goal) {
		System.out.print("A* ");
		List<Level.Field> discovered = new ArrayList<>();
		Map<Level.Field, Level.Field> cameFrom = new HashMap<>();

		Map<Level.Field, Integer> cost = new HashMap<>();

		cost.put(start, 0);
		discovered.add(start);

		while(!discovered.isEmpty()){
			discovered.sort(Comparator.comparingInt(node -> (cost.get(node) + getManhattanDistance(node, goal))));

			Level.Field field = discovered.remove(0);
			if(field == goal) break;

			visited.add(field);
			for(Level.Field neighbour : field.getLinkedFields()){
				if(visited.contains(neighbour))
					continue;

				if(!discovered.contains(neighbour)) {
					discovered.add(neighbour);
				} else if(cost.get(field)+1 >= cost.get(neighbour)){
					continue;
				}

				cameFrom.put(neighbour, field);
				cost.put(neighbour, cost.get(field)+1);
			}
		}

		seen.clear();
		seen.addAll(discovered);

		return constructPath(goal, cameFrom);
	}

}
