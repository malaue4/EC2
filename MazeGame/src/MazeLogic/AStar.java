package MazeLogic;

import java.util.*;
import java.util.stream.Collectors;

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

		Map<Level.Field, Double> gCost = new HashMap<>();
		Map<Level.Field, Double> fCost = new HashMap<>();

		gCost.put(start, 0.0);
		fCost.put(start, getHeuristicCost(start, goal));

		discovered.add(start);

		while(!discovered.isEmpty()){
			discovered.sort((o1, o2) -> (int)(fCost.get(o1)-fCost.get(o2)));

			Level.Field field = discovered.remove(0);
			if(field == goal) break;

			visited.add(field);
			List<Level.Field> neighbours = field.getLinkedFields();
			for(Level.Field neighbour : neighbours){
				if(visited.contains(neighbour))
					continue;



				if(!discovered.contains(neighbour)) {
					discovered.add(neighbour);
				} else if(gCost.get(field)+1 >= gCost.get(neighbour)){
					continue;
				}

				cameFrom.put(neighbour, field);
				gCost.put(neighbour, gCost.get(field)+1);
				fCost.put(neighbour, gCost.get(neighbour)+getHeuristicCost(neighbour, goal));
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
