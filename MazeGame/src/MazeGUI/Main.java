package MazeGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
		Parent root = loader.load();
		primaryStage.setTitle("JakeGame");

		Controller controller = loader.getController();

		Scene scene = new Scene(root, 610, 450);
		scene.setOnKeyPressed(event -> controller.game.keyPressed(event.getCode()));
		primaryStage.setScene(scene);
		primaryStage.getScene().getStylesheets().add("MazeGUI/stylesheet.css");
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
