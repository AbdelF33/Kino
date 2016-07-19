package userControllers;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import userModels.CastInfo;
import userModels.GenreInfo;
import userModels.NationalityInfo;
import userModels.ShowingInfo;
import userModels.ShowingModel;
import userViews.Main;

public class ShowingController implements Initializable, ControlledScreen{
	
	private ScreenController myCtrl;
	/**
	 * Example link poster : (http://image.tmdb.org/t/p/w500/ft43pouxItAGYR3ZbbQEtEl91JQ.jpg)
	 * Url + size + name
	 */
	 private static final String posterUrl = "http://image.tmdb.org/t/p";
	 private static final String posterSmallSize = "/w300";
	 private static final String posterMediumSize = "/w500";
	 private String posterName;

	@FXML
	private ListView<ShowingInfo> listView_moviePoster;
	@FXML
	private Label lbl_movieName;
	@FXML
	private Label lbl_Langage;
	@FXML
	private Label lbl_Nationality;
	@FXML
	private Label lbl_Genre;
	@FXML
	private Label lbl_cast;
	@FXML
	private Label lbl_dir;
	@FXML
	private Label lbl_movieReleaseDate;
	@FXML
	private Label lbl_movieDuration;
	@FXML
	private Label lbl_movieOverview;
	@FXML
	private Label lbl_movieRate;
	@FXML
	private Label lbl_Room;
	@FXML
	private Label lbl_RoomType;
	@FXML
	private Label lbl_startDateMS;
	@FXML
	private Label lbl_endDateMS;
	@FXML
	private TextField tf_nbResa;
	@FXML
	private Button plus;
	@FXML
	private Button moin;
	
	Label label = new Label("text");

	private int count = 0;
	private LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
	private ShowingModel showingMdl = new ShowingModel();
	private ImageView imgView = new ImageView();
	private ShowingInfo si;
	private Tools tool = new Tools();

	public ShowingController() {
		
	}

	@Override
	public void setScreenParent(ScreenController screenPage) {
		myCtrl = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resource) {
		popMoviePosterListView();
	}
	
	
	@FXML
	public void gotoHomeView(ActionEvent event){
		myCtrl.setScreen(Main.scrMovieBookingID);
	}
	
	
	
	// Affichage du poster du film dans ListeView poster
	public void popMoviePosterListView(){
		
		listView_moviePoster.setItems(showingMdl.movieShowing());	
		listView_moviePoster.setCellFactory(new Callback<ListView<ShowingInfo>, ListCell<ShowingInfo>>() {
	
			@Override
			public ListCell<ShowingInfo> call(ListView<ShowingInfo> param) {
				ListCell<ShowingInfo> cell = new ListCell<ShowingInfo>(){
					
					@Override
					protected void updateItem(ShowingInfo t, boolean bln) {
	                    super.updateItem(t, bln);
	                    if (t != null) {
	                        setText(t.getMovieNamelbl());
	                    }
					}
					
				};
				return cell;
			}	
		
		});
	
	}
	
	// Affichage du détail si film sélectionné
	@FXML
	public void popMovieDetailslbl(){
		
		si = listView_moviePoster.getSelectionModel().getSelectedItem();
		String nat = "";
		String genre = "";
		String cast = "";
		String dir = "";
		
		if (si != null) {
			lbl_movieName.setText(si.getMovieNamelbl());
			lbl_startDateMS.setText(si.getStartdatemslbl());
			lbl_endDateMS.setText(si.getEnddatemslbl());
			lbl_movieDuration.setText(si.getMovieDuralbl());
			lbl_movieOverview.setText(si.getMovieOverlbl());
			lbl_movieRate.setText(si.getMovieRatelbl());
			lbl_Langage.setText(si.getLangagelbl());
			lbl_Room.setText(si.getRoomlbl());
			lbl_RoomType.setText(si.getRoomtypelbl());
			
			//get all nationalities
			for (NationalityInfo natData : showingMdl.getNationality(si.getMovieIdCol())) {
				nat += natData.getNationlblFrCol()+", ";
			}
			lbl_Nationality.setText(nat);
			
			//get all genres
			for (GenreInfo genreData : showingMdl.getGenre(si.getMovieIdCol())) {
				genre += genreData.getGenrelblCol()+", ";
			}
			lbl_Genre.setText(genre);
			
			//get all casts
			for (CastInfo castData : showingMdl.listOfCast(si.getMovieIdCol(), 1)) {
				cast += castData.getCastFirstnameCol()+" "+castData.getCastLastnameCol()+", ";
			}
			lbl_cast.setText(cast);
			
			//get all directors
			for (CastInfo castData : showingMdl.listOfCast(si.getMovieIdCol(), 2)) {
				dir += castData.getCastFirstnameCol()+" "+castData.getCastLastnameCol()+", ";
			}
			lbl_dir.setText(dir);
		}
	}
	
	@FXML
	private void booking(){
		si = listView_moviePoster.getSelectionModel().getSelectedItem();
		if (si != null) {
			
			int chiffreRdm = (int)(Math.random() * (10000000-1)) + 0;
			Date dateJour = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyy");
			String bookingRef = String.valueOf(chiffreRdm)+"KINO"+formatter.format(dateJour);
			String ticketRef = String.valueOf(chiffreRdm)+"TICKET"+formatter.format(dateJour);
			
			//Enregistrer les reservations et les tickets
			dataMap.put("bookingRef", bookingRef);
			dataMap.put("bookingNb", tf_nbResa.getText());
			dataMap.put("fkMSessionId", si.getSessionIdCol().toString());
			showingMdl.doubleQuExe(tool.insertQuery("booking", dataMap), Integer.parseInt(tf_nbResa.getText()));
			
			//Créer le fichier
			Folder folder = new Folder("Kino"+File.separator+"tickets"+File.separator, "V:");
			int nbresa = Integer.parseInt(tf_nbResa.getText());
			String[] info = { bookingRef, si.getMovieNamelbl(), si.getRoomlbl(), dateJour.toString()};
			for (int i = 0; i < nbresa; i++) {
				folder.createFile(i+ticketRef+".txt");
				folder.writeFile(i+ticketRef+".txt", info);
				//folder.readFile(i+ticketRef+".txt");
			}
			tool.alertDialog(AlertType.CONFIRMATION, "Réservation", "Le film sera diffusé dans la salle "+si.getRoomlbl()+"\nNombre de billet : "+nbresa, "Votre réservation pour le film "+si.getMovieNamelbl());
		}else{
			tool.alertDialog(AlertType.WARNING, "Réservations", "Veuillez d'abord séléctionner un film !", null);
		}
		
	}

	@FXML
	private void incrementResa(ActionEvent event){
		count++;
		String nbResa = String.valueOf(count);
		tf_nbResa.setText(nbResa);
	}
	
	@FXML
	private void decrementResa(ActionEvent event){
		if (count > 1) {
			count--;
			String nbResa = String.valueOf(count);
			tf_nbResa.setText(nbResa);
		}
	}
			
}


