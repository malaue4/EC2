package MazeLogic;

import java.awt.Point;
import java.awt.Dimension;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Represents a levelProperty/maze, it is made up of many fields which may or may not be linked to one another
 */
public class Level extends Dimension implements Serializable{
	private static final long serialVersionUID = 2483211102882312031L;
	/**
	 * A 2 dimensional array containing all the fields of the levelProperty
	 * @link Field
	 */
	private Field[][] mFields;
	private String mTitle = "";

	/**
	 * Constructs a levelProperty with the given with and height, and initializes all the fields. By default none of the fields
	 * are linked to one another
	 *
	 * @link Field
	 * @param width width of the levelProperty
	 * @param height height of the levelProperty
	 */
	Level(int width, int height){
		super(width, height); // Skal man sikre sig at width & height ikke er < 1?
		mFields = new Field[width][height];
		for(int x=0; x<width; x++){
			for (int y = 0; y < height; y++) {
				mFields[x][y] = new Field(x, y);
			}
		}
	}

	/**
	 * Returns the Field at the given coordinates
	 * @link Field
	 * @param x the horizontal coordinate of the Field
	 * @param y the vertical coordinate of the Field
	 * @return the Field or null if it doesn't exist
	 */
	public Field getField(int x, int y){
		if(x<0 || y<0 || x >= width || y >= height) return null;
		return mFields[x][y];
	}

	/**
	 * Returns the neighbours of the given Field, regardless if they are linked or not.
	 * Only the cardinal directions are fetched.
	 *
	 * @link Field
	 * @param field the Field to check
	 * @return list of neighbouring Fields
	 */
	public ArrayList<Field> getNeighbours(Field field){
		return getNeighbours(field.x, field.y);
	}

	/**
	 * Returns the neighbours of the Field at the given coordinates, regardless if they are linked or not.
	 * Only the cardinal directions are fetched.
	 *
	 * @link Field
	 * @param x the horizontal coordinate of the Field
	 * @param y the vertical coordinate of the Field
	 * @return list of neighbouring Fields
	 */
	public ArrayList<Field> getNeighbours(int x, int y){
		ArrayList<Field> neighbours = new ArrayList<>(4);
		int dx=-1;
		int dy=0;
		for (int i = 0; i < 4; i++) {
			Field other = getField(x+dx, y+dy);
			if(other != null)
				neighbours.add(other);

			int dn = dx;
			dx = dy;
			dy = -dn;
		}
		return neighbours;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public List<Field> getFields() {
		List<Field> fields = new ArrayList<>();
		for(Field[] fieldRow : mFields){
			Collections.addAll(fields, fieldRow);
		}
		return fields;
	}

	/**
	 * A field in the levelProperty, it has position and knows which fields it is linked to
	 */
	public class Field extends Point{
		private static final long serialVersionUID = 4523511842502318683L;
		/**
		 * A list of the Fields this Field is linked to
		 */
		private ArrayList<Field> linkedFields = new ArrayList<>();

		/**
		 * Constructs and initializes a field at the specified (x,y) location in the coordinate space.
		 * @param x the horizontal coordinate
		 * @param y the vertical coordinate
		 */
		Field(int x, int y) {
			super(x, y);
		}

		/**
		 * Links this Field to the other Field, if it isn't already
		 * @param other the field to link to
		 */
		public void link(Field other){
			if(abs(other.x-x)+abs(other.y-y)==1 && !linkedFields.contains(other)){
				linkedFields.add(other);
				other.link(this);
			}
		}

		/**
		 * Unlinks this Field from the other Field, if it isn't already
		 * @param other the field to unlink from
		 */
		public void unlink(Field other){
			if(linkedFields.contains(other)){
				linkedFields.remove(other);
				other.unlink(this);
			}
		}

		/**
		 * Return whether or not this Field and the other Field is linked
		 * @param other the Field to check
		 * @return a boolean
		 */
		boolean isLinked(Field other){
			return linkedFields.contains(other);
		}

		/**
		 * Returns an ArrayList containing references to the Fields this Field is linked to
		 * @return an ArrayList containing Fields
		 */
		public ArrayList<Field> getLinkedFields() {
			return linkedFields;
		}
	}
}
