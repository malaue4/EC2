package SnakeGUI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
		Parent root = loader.load();
		primaryStage.setTitle("JakeGame");

		Controller controller = loader.getController();

		Scene scene = new Scene(root, 600, 450);
		scene.setOnKeyPressed(event -> controller.game.keyPressed(event.getCode()));
		primaryStage.setScene(scene);
		primaryStage.getScene().getStylesheets().add("SnakeGUI/stylesheet.css");
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
