package userControllers;

import java.util.HashMap;



import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class ScreenController extends StackPane{

	private HashMap<String, Node> screens = new HashMap<>();

	public ScreenController() {
		super();
	}
	
	//Add the screen to the collection
	public void addScreen(String name,Node screen){
		screens.put(name, screen);
	}
	
	//Return the node <ith the appropriate name
	public Node getScreen(String name){
		return screens.get(name);
	}
	
	/*
	 * Load the fxml file, add the screen to the screens collection
	 * and finally injects the screenPane ti the controller.
	*/
	public boolean loadScreen(String name, String resource){
		FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
		Parent loadScreen;
		try {
			loadScreen = (Parent) myLoader.load();
			ControlledScreen myScreenController = ((ControlledScreen) myLoader.getController());
			myScreenController.setScreenParent(this);
			addScreen(name, loadScreen);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean setScreen(final String name){
		if (screens.get(name) != null) { //screen loaded
			final DoubleProperty opacity = opacityProperty();
			
			if (!getChildren().isEmpty()) { //if there more than one screen
				Timeline fade = new Timeline(
						new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
						new KeyFrame(new Duration(150), new EventHandler<ActionEvent>(){
							@Override
							public void handle(ActionEvent t){
								getChildren().remove(0);					//remove the displayed screen
								getChildren().add(0, screens.get(name));	//add the screen
								Timeline fadeIn = new Timeline(
										new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
										new KeyFrame(new Duration(100), new KeyValue(opacity, 1.0)));
								fadeIn.play();
							}
						}, new KeyValue(opacity, 0.0)));
				fade.play();
						
			}else{
				setOpacity(0.0);
				getChildren().add(screens.get(name));						//no one else been displayed, the just show
				Timeline fadeIn = new Timeline(
						new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
						new KeyFrame(new Duration(150), new KeyValue(opacity, 1.0)));
				fadeIn.play();
			}
			return true;
		}else{
			System.out.println("l'écran n'a pas pu être chargé !!!");
			return false;
		}
	}
	
	//remove the screen with the given name from the collection of screens
	public boolean unloadScreen(String name){
		if (screens.remove(name) == null) {
			System.out.println("L'écran n'existe pas !");
			return false;
		}else{
			return true;
		}
	}
	
	
}
