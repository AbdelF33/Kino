package adminControllers;

import java.net.URL;
import java.util.ResourceBundle;

import adminViews.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class HomeController  implements Initializable, ControlledScreen{

	private ScreenController myCtrl;
	//private ApiController apiCtrl = new ApiController();
	private ApiRunnable apiRun = new ApiRunnable();
	
	public HomeController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setScreenParent(ScreenController screenPage) {
		myCtrl = screenPage;
	}

	@Override
	 public void initialize(URL location, ResourceBundle resources) {
	 	
	 }
	
	@FXML
	public void gotolistUser(ActionEvent event){
		myCtrl.setScreen(Main.scrListOfAdminID);
	}
	
	@FXML
	public void gotolistMovie(ActionEvent event){
		myCtrl.setScreen(Main.scrListOfMovieID);
	}
	
	@FXML
	public void gotoLoginScreen(ActionEvent event){
		myCtrl.setScreen(Main.scrLoginID);
	}
	
	@FXML
	public void gotoSessionScreen(ActionEvent event){
		myCtrl.setScreen(Main.scrSessionID);
	}
	
	@FXML
	public void generateMovie(){
		
		//apiCtrl.getMovies("upcoming");
		//apiCtrl.getMovies("now_playing");
		Thread threadApi = new Thread(apiRun);
		threadApi.start();
	}

	
}
