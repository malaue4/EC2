package MazeLogic;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by martin on 3/9/17.
 */
public class Level extends Dimension{
	private boolean[][] fields;

	public Level(int width, int height){
		super(width, height); // Skal man sikre sig at width & height ikke er < 1?
		fields = new boolean[width][height];
		Random random = new Random();
		for(int x=0; x<width; x++){
			for (int y = 0; y < height; y++) {
				fields[x][y] = random.nextBoolean();
			}
		}
	}

	public boolean isWall(int x, int y){
		return fields[x][y];
	}

	Point[] getNeighbours(int x, int y){
		ArrayList<Point> neighbours = new ArrayList<>(4);
		int dx=-1;
		int dy=0;
		for (int i = 0; i < 4; i++) {
			if(!isWall(x+dx, y+dy)){
				neighbours.add(
						new Point(x+dx, y+dy)
				);
			}
			int dn = dx;
			dx = dy;
			dy = -dn;
		}
		return neighbours.toArray(new Point[neighbours.size()]);
	}
}
