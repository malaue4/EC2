package MazeLogic.pathfinding;

import MazeLogic.Level;
import MazeLogic.pathfinding.PathFinder;

import java.util.*;

/**
 * Created by Martin on 15-04-2017.
 */
public class Fleeing extends PathFinder {

	@Override
	public List<Level.Field> calculatePath(Level.Field start, Level.Field goal) {
		System.out.print("Fleeing ");
		List<Level.Field> fieldList = new ArrayList<>(start.getLinkedFields());
		if(fieldList.size()>1){
			fieldList.remove(previousStart);
		}

		List<Level.Field> path = new ArrayList<>();
		if(fieldList.size()>0) {
			// n1 and n2 are reversed, so the node furthest from the goal comes first.
			fieldList.sort((n1,n2) -> getManhattanDistance(n2, goal)-getManhattanDistance(n1,goal));
			path.add(fieldList.get(0));
		}
		return path;
	}

	@Override
	public Set<Level.Field> getVisited() {
		return Collections.emptySet();
	}
}
