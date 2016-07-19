package adminViews;

import adminControllers.ScreenController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	
	public static String scrLoginID = "loginView";
	public static String scrLoginFile = "/adminViews/loginView.fxml";
	
	public static String scrHomeID = "homeView";
	public static String scrHomeFile = "/adminViews/homeView.fxml";
	
	public static String scrListOfAdminID = "listOfAdmin";
	public static String scrListOfAdminFile = "/adminViews/ListOfAdmin.fxml";
	
	public static String scrListOfMovieID = "listOfMovie";
	public static String scrListOfMovieFile = "/adminViews/ListOfMovie.fxml";

	public static String scrSessionID = "sessionView";
	public static String scrSessionFile = "/adminViews/sessionView.fxml";
	
	@Override
	public void start(Stage primaryStage) {
		
		ScreenController mainContainer = new ScreenController();
		
		mainContainer.loadScreen(Main.scrLoginID, Main.scrLoginFile);
		mainContainer.loadScreen(Main.scrHomeID, Main.scrHomeFile);
		mainContainer.loadScreen(Main.scrListOfAdminID, Main.scrListOfAdminFile);
		mainContainer.loadScreen(Main.scrListOfMovieID, Main.scrListOfMovieFile);
		mainContainer.loadScreen(Main.scrSessionID, Main.scrSessionFile);
		
		mainContainer.setScreen(Main.scrLoginID);
		//mainContainer.setScreen(Main.scrHomeID);
		
		Group root = new Group();
		root.getChildren().addAll(mainContainer);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
