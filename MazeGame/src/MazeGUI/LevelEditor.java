package MazeGUI;

import MazeLogic.Game;
import MazeLogic.Level;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.awt.Point;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martin on 11-03-2017.
 */
public class LevelEditor implements Initializable{
	private Point marker;
	private Game game;

	tools tool = tools.freehand;
	mirrors mirror = mirrors.none;

	public LevelEditor(Game game) {
		this.game = game;
		marker = new Point();
	}

	void placeMarker(int x, int y) {
		marker.setLocation(x, y);
	}

	void placeWall(int x, int y) {
		if(marker.x!=x || marker.y!=y) {
			System.out.printf("field: (%s, %s) -> (%s, %s)%n", marker.x, marker.y, x, y);
			Level.Field field = game.getLevel().getField(x, y);
			Level.Field other = game.getLevel().getField(marker.x, marker.y);
			field.unlink(other);
		}
		placeMarker(x, y);

	}

	void carveWall(int x, int y) {
		if(marker.x!=x || marker.y!=y) {
			if(game.getLevel().getNeighbours(marker.x, marker.y).contains(game.getLevel().getField(x, y))){
				System.out.printf("field: (%s, %s) -> (%s, %s)%n", marker.x, marker.y, x, y);
				game.getLevel().getField(x, y).link(game.getLevel().getField(marker.x, marker.y));
			} else {
				System.out.println("Fields were not neighbours");
			}
		}
		placeMarker(x, y);
	}

	void clear(boolean open){
		if(open){
			Level level = game.getLevel();
			for(Level.Field field : level.getFields()){
				for(Level.Field neighbour : level.getNeighbours(field)){
					field.link(neighbour);
				}
			}
		} else {
			Level level = game.getLevel();
			for(Level.Field field : level.getFields()){
				for(Level.Field neighbour : level.getNeighbours(field)){
					field.unlink(neighbour);
				}
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public Point getMarker() {
		return marker;
	}

	enum tools{
		freehand,
		line,
		rectangle
	}

	enum mirrors{
		none,
		horizontal,
		vertical,
		both
	}
}
