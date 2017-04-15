package MazeLogic;

import utility.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Martin on 15-04-2017.
 */
public class RandomRambler implements PathFinder {

	Level.Field previousField;

	@Override
	public List<Level.Field> getPath(Level.Field start, Level.Field goal) {
		List<Level.Field> fieldList = new ArrayList<>(start.getLinkedFields());
		if(fieldList.size()>1){
			fieldList.remove(previousField);
		}

		List<Level.Field> path = new ArrayList<>();
		if(fieldList.size()>0) {
			path.add(fieldList.get(new Random().nextInt(fieldList.size())));
		}
		previousField = start;
		return path;
	}
}
