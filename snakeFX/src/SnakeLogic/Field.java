package SnakeLogic;

import java.awt.*;

/**
 *
 */
public class Field extends Point {
	private GameObject contents;
	Field north, south, east, west;


	boolean isFree(){
		return contents == null;
	}

	public void setContents(GameObject contents) {
		this.contents = contents;
	}

	public GameObject getContents() {
		return contents;
	}

	public void setEast(Field east) {
		this.east = east;
		east.west = this;
	}

	public void setSouth(Field south) {
		this.south = south;
		south.north = this;
	}
}
