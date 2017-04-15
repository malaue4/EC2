package utility;

/**
 * Created by Martin on 09-04-2017.
 *
 */
public class Direction {
	public int x;
	public int y;

	public final static Direction up = new Direction(0, -1);
	public final static Direction down = new Direction(0, 1);
	public final static Direction left = new Direction(-1, 0);
	public final static Direction right = new Direction(1, 0);
	static Direction[] directions = {
			up,
			down,
			left,
			right
	};

	public Direction(int x, int y){
		this.x = x;
		this.y = y;
	}

	public static Direction[] getDirections() {
		return directions;
	}
}
