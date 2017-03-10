package MazeLogic;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class Level extends Dimension implements Serializable{
	private Field[][] fields;

	Field goal, start;

	Level(int width, int height){
		super(width, height); // Skal man sikre sig at width & height ikke er < 1?
		fields = new Field[width][height];
		for(int x=0; x<width; x++){
			for (int y = 0; y < height; y++) {
				fields[x][y] = new Field(x, y);
			}
		}
	}

	public Field getField(int x, int y){
		if(x<0 || y<0 || x >= width || y >= height) return null;
		return fields[x][y];
	}

	ArrayList<Field> getNeighbours(int x, int y){
		ArrayList<Field> neighbours = new ArrayList<>(4);
		int dx=-1;
		int dy=0;
		Field center = getField(x, y);
		for (int i = 0; i < 4; i++) {
			Field other = getField(x+dx, y+dy);
			if(other != null && center.isLinked(other))
				neighbours.add(other);

			int dn = dx;
			dx = dy;
			dy = -dn;
		}
		return neighbours;
	}

	public class Field extends Point{
		ArrayList<Field> linkedFields = new ArrayList<>();
		Field(int x, int y) {
			super(x, y);
		}

		public void link(Field other){
			if(abs(other.x-x)+abs(other.y-y)==1 && !linkedFields.contains(other)){
				linkedFields.add(other);
				other.link(this);
			}
		}

		public void unlink(Field other){
			if(linkedFields.contains(other)){
				linkedFields.remove(other);
				other.unlink(this);
			}
		}

		boolean isLinked(Field other){
			return linkedFields.contains(other);
		}

		public ArrayList<Field> getLinkedFields() {
			return linkedFields;
		}
	}
}
