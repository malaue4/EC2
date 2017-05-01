package MazeLogic.pathfinding;

import MazeLogic.Level;

import java.util.*;

import static java.lang.Math.abs;

/**
 * Created by Martin on 15-04-2017.
 */
public class AStar implements PathFinder {

	@Override
	public List<Level.Field> getPath(Level.Field start, Level.Field goal) {
		Set<Level.Field> visited = new HashSet<>();
		List<Level.Field> discovered = new ArrayList<>();
		Map<Level.Field, Level.Field> cameFrom = new HashMap<>();

		Map<Level.Field, Double> cost = new HashMap<>();

		cost.put(start, 0.0);

		discovered.add(start);

		while(!discovered.isEmpty()){
			discovered.sort((node1, node2) -> (int)( (cost.get(node1)+getHeuristicCost(node1, goal))-(cost.get(node2)+getHeuristicCost(node2, goal))));

			Level.Field field = discovered.remove(0);
			if(field == goal) break;

			visited.add(field);
			List<Level.Field> neighbours = field.getLinkedFields();
			for(Level.Field neighbour : neighbours){
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
