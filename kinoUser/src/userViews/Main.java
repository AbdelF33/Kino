package userViews;
	
import userControllers.ScreenController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;



public class Main extends Application {
	
	/*public static String scrMovieShowingID = "movieShowing";
	public static String scrMovieShowingFile = "/userViews/MovieShowing.fxml";
	*/
	public static String scrMovieBookingID = "movieBooking";
	public static String scrMovieBookingFile = "/userViews/MovieBooking.fxml";
	
	@Override
	public void start(Stage primaryStage) {
		
		ScreenController mainContainer = new ScreenController();
		
		mainContainer.loadScreen(Main.scrMovieBookingID, Main.scrMovieBookingFile);
		/*mainContainer.loadScreen(Main.scrMovieShowingID, Main.scrMovieShowingFile);*/
		
		mainContainer.setScreen(Main.scrMovieBookingID);
		
		
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
