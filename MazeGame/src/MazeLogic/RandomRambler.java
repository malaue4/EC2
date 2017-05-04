package MazeLogic;

import MazeLogic.pathfinding.PathFinder;

import java.util.*;

/**
 * Created by Martin on 15-04-2017.
 */
public class RandomRambler extends PathFinder {

	@Override
	public List<Level.Field> calculatePath(Level.Field start, Level.Field goal) {
		List<Level.Field> fieldList = new ArrayList<>(start.getLinkedFields());
		if(fieldList.size()>1){
			fieldList.remove(previousStart);
		}

		List<Level.Field> path = new ArrayList<>();
		if(fieldList.size()>0) {
			path.add(fieldList.get(new Random().nextInt(fieldList.size())));
		}
		previousStart = start;
		return path;
	}

	@Override
	public Set<Level.Field> getVisited() {
		return Collections.emptySet();
	}
}
