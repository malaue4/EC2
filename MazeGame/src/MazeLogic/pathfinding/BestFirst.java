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
		System.out.print("Best First ");
		List<Level.Field> discovered = new ArrayList<>();
		Map<Level.Field, Level.Field> cameFrom = new HashMap<>();




		discovered.add(start);
		while(!discovered.isEmpty()){
			discovered.sort(Comparator.comparingInt(o -> getManhattanDistance(o, goal)));

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

		seen.clear();
		seen.addAll(discovered);

		return constructPath(goal, cameFrom);
	}
}
