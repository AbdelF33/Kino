package adminControllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;










import adminModels.CastInfo;
import adminModels.GenreInfo;
import adminModels.MovieInfo;
import adminModels.MovieModel;
import adminModels.NationalityInfo;
import adminModels.SessionModel;
import adminViews.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.Pair;

public class MovieController implements Initializable, ControlledScreen {
	
	private ScreenController myCtrl;
	
	@FXML
	private Button btn_addM;
	@FXML
	private Button btn_allM;
	@FXML
	private Button btn_updateM;
	@FXML
	private Button btn_cast;
	@FXML
	private Button btn_genre;	
	@FXML
	private Button btn_nat;	
	@FXML
	private Label lbl_succesMsg;
	@FXML
	private Label lbl_erMsg;
	@FXML
	private Label lbl_id;
	@FXML
	private DatePicker dp_ReleaseDate;
	@FXML
	private TextField tf_moviePoster;
	@FXML
	private ComboBox<NationalityInfo> cb_Nationality;
	@FXML
	private ComboBox<CastInfo> cb_Cast;
	@FXML
	private ComboBox<GenreInfo> cb_Genre;
	@FXML
	private TextField tf_movieRate;
	@FXML
	private TextField tf_movieWebsite;
	@FXML
	private TextField tf_movieName;
	@FXML
	private TextField tf_movieDuration;
	@FXML
	private TextField tf_movieTrailer;
	@FXML
	private TextArea ta_movieOverview;
	@FXML
	private TableColumn<MovieInfo, Integer> movieId;
	@FXML
	private TableColumn<MovieInfo, String> movieName;
	@FXML
	private TableView<MovieInfo> tblView_movie;
	@FXML
	private TableView<GenreInfo> tblView_genre;
	@FXML
	private TableColumn<GenreInfo, String> genre;
	@FXML
	private TableView<CastInfo> tblView_dir;
	@FXML
	private TableColumn<CastInfo, String> dir_lastname;
	@FXML
	private TableColumn<CastInfo, String> dir_firstname;
	@FXML
	private TableView<CastInfo> tblView_cast;
	@FXML
	private TableColumn<CastInfo, String> cast_lastname;
	@FXML
	private TableColumn<CastInfo, String> cast_firstname;
	@FXML
	private TableView<NationalityInfo> tblView_nat;
	@FXML
	private TableColumn<NationalityInfo, String> nationality;
	
	private LinkedHashMap<String, String> movieMap = new LinkedHashMap<>();
	private LinkedHashMap<String, String> genreMap = new LinkedHashMap<>();
	private LinkedHashMap<String, String> nationalityMap = new LinkedHashMap<>();
	private LinkedHashMap<String, String> castMap = new LinkedHashMap<>();
	private LinkedHashMap<String, String> sessionMap = new LinkedHashMap<>();
	
	private MovieModel movieMdl = new MovieModel();
	private SessionModel ssMdl = new SessionModel();
	private Tools tool = new Tools();
	

	public MovieController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setScreenParent(ScreenController screenPage) {
		myCtrl = screenPage;
		
	}

	 @Override
	 public void initialize(URL location, ResourceBundle resources) {
	 	popMovieTableView();
	 }

	@FXML
	public void gotoHomeView(ActionEvent event){
		myCtrl.setScreen(Main.scrHomeID);
	}
	
	@FXML
	public void gotolistMovie(ActionEvent event){
		myCtrl.setScreen(Main.scrListOfMovieID);
	}
		
	@FXML
	public void cancelButton(ActionEvent event){
		emptyDataCreateForm();
	}
	
	@FXML
	public void gotoCast(ActionEvent event){
		MovieInfo mi = tblView_movie.getSelectionModel().getSelectedItem();
		if (mi == null) {
			tool.alertDialog(AlertType.WARNING, "Ajout d'acteurs", "Veuillez d'abord selectionner un film !", null);
		}else{
			List<String> choices = new ArrayList<>();
			for(CastInfo data : movieMdl.listOfCast(0,0)){
				choices.add(data.getCastIdCol() + "-" + data.getCastFirstnameCol() + " " + data.getCastLastnameCol());
			}
	
			ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
			dialog.setTitle("Liste des acteurs");
			dialog.setHeaderText("Veuillez choisir un acteur à ajouter.");
			dialog.setContentText(null);
	
			// Traditional way to get the response value.
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent() && (!result.get().equals(""))){
				String[] parts = result.get().split("-");
			    //System.out.println("Your choice: " + parts[0]);
			    
			    castMap.put("fkMovieId", mi.getMovieIdCol().toString());
			    castMap.put("fkCastId", parts[0]);
			    castMap.put("fkFunctionId", "1");
			    movieMdl.addMovieDB(tool.insertQuery("moviecast", castMap));
				tool.alertDialog(AlertType.INFORMATION, "Ajout d'acteurs", "Acteur ajouté avec succès !", null);
				popMovieTableView();
				emptyDataCreateForm();
			}
			// The Java 8 way to get the response value (with lambda expression).
			//result.ifPresent(letter -> System.out.println("Your choice: " + letter));
		}
	}
	
	@FXML
	public void gotoDir(ActionEvent event){
		MovieInfo mi = tblView_movie.getSelectionModel().getSelectedItem();
		if (mi == null) {
			tool.alertDialog(AlertType.WARNING, "Ajout de réalisateurs", "Veuillez d'abord selectionner un film !", null);
		}else{
			List<String> choices = new ArrayList<>();
			for(CastInfo data : movieMdl.listOfCast(0,0)){
				choices.add(data.getCastIdCol() + "-" + data.getCastFirstnameCol() + " " + data.getCastLastnameCol());
			}
	
			ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
			dialog.setTitle("Liste des réalisateurs");
			dialog.setHeaderText("Veuillez choisir un réalisateur à ajouter.");
			dialog.setContentText(null);
	
			// Traditional way to get the response value.
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent() && (!result.get().equals(""))){
				String[] parts = result.get().split("-");
			    //System.out.println("Your choice: " + parts[0]);
			    
			    castMap.put("fkMovieId", mi.getMovieIdCol().toString());
			    castMap.put("fkCastId", parts[0]);
			    castMap.put("fkFunctionId", "2");
			    movieMdl.addMovieDB(tool.insertQuery("moviecast", castMap));
				tool.alertDialog(AlertType.INFORMATION, "Ajout d'acteurs", "Réalisateur ajouté avec succès !", null);
				popMovieTableView();
				emptyDataCreateForm();
			}
			// The Java 8 way to get the response value (with lambda expression).
			//result.ifPresent(letter -> System.out.println("Your choice: " + letter));
		}
	}
	
	@FXML
	public void gotoGenre(ActionEvent event){
		MovieInfo mi = tblView_movie.getSelectionModel().getSelectedItem();
		
		if (mi == null) {
			tool.alertDialog(AlertType.WARNING, "Ajout d'acteurs", "Veuillez d'abord selectionner un film !", null);
		}else{
			List<String> choices = new ArrayList<>();
			for(GenreInfo data : movieMdl.getGenre(0)){
				choices.add(data.getGenreIdCol() + "-" + data.getGenrelblCol());
			}
	
			ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
			dialog.setTitle("Liste des genres");
			dialog.setHeaderText("Veuillez choisir un genre à ajouter.");
			dialog.setContentText(null);
	
			// Traditional way to get the response value.
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent() && (!result.get().equals(""))){
				String[] parts = result.get().split("-");
			    //System.out.println("Your choice: " + parts[0]);
				
			    genreMap.put("fkMovieId", mi.getMovieIdCol().toString());
			    genreMap.put("fkGenreId", parts[0]);
			    movieMdl.addMovieDB(tool.insertQuery("moviegenre", genreMap));
				tool.alertDialog(AlertType.INFORMATION, "Ajout de genres", "Genre ajouté avec succès !", null);
				popMovieTableView();
				emptyDataCreateForm();
			}
			// The Java 8 way to get the response value (with lambda expression).
			//result.ifPresent(letter -> System.out.println("Your choice: " + letter));
		}
	}
	
	@FXML
	public void gotoNat(ActionEvent event){
		MovieInfo mi = tblView_movie.getSelectionModel().getSelectedItem();
		
		if (mi == null) {
			tool.alertDialog(AlertType.WARNING, "Ajout d'acteurs", "Veuillez d'abord selectionner un film !", null);
		}else{
			List<String> choices = new ArrayList<>();
			for(NationalityInfo data : movieMdl.getNationality(0)){
				choices.add(data.getNationIdCol() + "-" + data.getNationlblFrCol());
			}
	
			ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
			dialog.setTitle("Liste des nationalités");
			dialog.setHeaderText("Veuillez choisir une nationalité à ajouter.");
			dialog.setContentText(null);
	
			// Traditional way to get the response value.
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent() && (!result.get().equals(""))){
				String[] parts = result.get().split("-");
			    //System.out.println("Your choice: " + parts[0]);	
				
			    nationalityMap.put("fkMovieId", mi.getMovieIdCol().toString());
			    nationalityMap.put("fkNationalityId", parts[0]);
			    movieMdl.addMovieDB(tool.insertQuery("movienationality", nationalityMap));
				tool.alertDialog(AlertType.INFORMATION, "Ajout de nationalité", "Nationalité ajouté avec succès !", null);
				popMovieTableView();
				emptyDataCreateForm();
			}
			// The Java 8 way to get the response value (with lambda expression).
			//result.ifPresent(letter -> System.out.println("Your choice: " + letter));
		}
	}
	
	@FXML
	public void createSession(ActionEvent event){
		MovieInfo mi = tblView_movie.getSelectionModel().getSelectedItem();
		
		if(mi != null){
			// Create the custom dialog.
			Dialog<Pair<String, String>> dialog = new Dialog<>();
			dialog.setTitle("Affiche");
			dialog.setHeaderText("Veuillez définir la date de mise à l'affiche,\n en précisant la date de début et la date de fin");
	
			// Set the icon (must be included in the project).
			//dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));
	
			// Set the button types.
			ButtonType createButtonType = new ButtonType("Ajouter", ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);
	
			// Create the username and password labels and fields.
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));
	
			DatePicker startDate = new DatePicker();
			startDate.setPromptText("Date de début");
			DatePicker endTime = new DatePicker();
			endTime.setPromptText("Date de fin");
			endTime.setDisable(true);
	
			grid.add(new Label("Date de début:"), 0, 0);
			grid.add(startDate, 1, 0);
			grid.add(new Label("Date de fin:"), 0, 1);
			grid.add(endTime, 1, 1);
	
			// Enable/Disable login button depending on whether a username was entered.
			Node createButton = dialog.getDialogPane().lookupButton(createButtonType);
			createButton.setDisable(true);
	
			// Do some validation (using the Java 8 lambda syntax).
			startDate.valueProperty().addListener((observable, oldValue, newValue) -> {
			    endTime.setDisable(false);
			});
			endTime.valueProperty().addListener((observable, oldValue, newValue) -> {
				createButton.setDisable(false);
			});
	
			dialog.getDialogPane().setContent(grid);
	
			// Request focus on the username field by default.
			Platform.runLater(() -> startDate.requestFocus());
	
			// Convert the result to a username-password-pair when the login button is clicked.
			dialog.setResultConverter(dialogButton -> {
			    if (dialogButton == createButtonType) {
			        return new Pair<>(startDate.getValue().toString(), endTime.getValue().toString());
			    }
			    return null;
			});
	
			Optional<Pair<String, String>> result = dialog.showAndWait();
	
			result.ifPresent(startEndDate -> {
				//System.out.println("Username=" + startEndDate.getKey() + ", Password=" + startEndDate.getValue());
				sessionMap.put("startTime", startEndDate.getKey());
				sessionMap.put("endDate", startEndDate.getValue());
				sessionMap.put("fkMovieId", mi.getMovieIdCol().toString());
				ssMdl.insertDate(tool.insertQuery("moviesession", sessionMap));
				tool.alertDialog(AlertType.INFORMATION, "Affiche", "Film ajouté avec succès !", null);
			});	
		}else{
			tool.alertDialog(AlertType.WARNING, "Affiche", "Veuillez d'abord selectionner un film !", null);
		}
	}
	
	@FXML
	public void addMovie(ActionEvent event){
		
		LocalDate date = dp_ReleaseDate.getValue();
		if (date != null) {
			movieMap.put("moviereleasedate", dp_ReleaseDate.getValue().toString());
		}
		movieMap.put("moviename", tf_movieName.getText());
		movieMap.put("movieduration", tf_movieDuration.getText());
		movieMap.put("movierate", tf_movieRate.getText());
		movieMap.put("movieoverview", ta_movieOverview.getText());
		movieMap.put("movietrailer", tf_movieTrailer.getText());
		movieMap.put("movieposter", tf_moviePoster.getText());
		movieMap.put("moviewebsite", tf_movieWebsite.getText());
		/*movieInfo.put("cast", cb_Cast.getId());
		movieInfo.put("nationality", cb_Nationality.getId());
		movieInfo.put("genre", cb_Genre.getId());*/
		
		if (!(tf_movieName.getText().equals("") || tf_movieDuration.getText().equals("") || date == null
				|| tf_movieRate.getText().equals("")|| ta_movieOverview.getText().equals("")|| tf_movieTrailer.getText().equals("")
				|| tf_moviePoster.getText().equals("")|| tf_movieWebsite.getText().equals(""))) {
				
				//Méhode de création
				movieMdl.addMovieDB(tool.insertQuery("movie", movieMap));
				tool.alertDialog(AlertType.INFORMATION, "Ajout de films", "Film ajouté avec succès !", null);
				popMovieTableView();
		}else{
			tool.alertDialog(AlertType.ERROR, "Ajout de films", "Veuillez remplir les champs obligatoires !", null);
		}
	}
	
	@FXML
	public void updateMovie(ActionEvent event){
		MovieInfo mi = tblView_movie.getSelectionModel().getSelectedItem();
		/*GenreInfo gi = cb_Genre.getSelectionModel().getSelectedItem();
		NationalityInfo ni = cb_Nationality.getSelectionModel().getSelectedItem();
		CastInfo ci = cb_Cast.getSelectionModel().getSelectedItem();*/

		
		//Remplissage du Hashmap de données
		LocalDate date = dp_ReleaseDate.getValue();
		if (date != null) {
			movieMap.put("moviereleasedate", dp_ReleaseDate.getValue().toString());
		}
		movieMap.put("moviename", tf_movieName.getText());
		movieMap.put("movieduration", tf_movieDuration.getText());
		movieMap.put("movierate", tf_movieRate.getText());
		movieMap.put("movieoverview", ta_movieOverview.getText());
		movieMap.put("movietrailer", tf_movieTrailer.getText());
		movieMap.put("movieposter", tf_moviePoster.getText());
		movieMap.put("moviewebsite", tf_movieWebsite.getText());
		/*movieInfo.put("cast", cb_Cast.getId());
		movieInfo.put("nationality", cb_Nationality.getId());
		movieInfo.put("genre", cb_Genre.getId());*/
		
		if (!(tf_movieName.getText().equals("") || 
				tf_movieDuration.getText().equals("") || 
				date == null ||
				tf_movieRate.getText().equals("") || 
				ta_movieOverview.getText().equals("") || 
				tf_movieTrailer.getText().equals("") || 
				tf_moviePoster.getText().equals("") || 
				tf_movieWebsite.getText().equals(""))) {
				
			try {
				movieMdl.updateMovieDB(
						tool.updateQuery("movie", "movieId", mi.getMovieIdCol(), movieMap)
						);
				tool.alertDialog(AlertType.INFORMATION, "Confirmation", "Le film n°" +mi.getMovieIdCol()+ " a été modifié avec succès !", null);
			} catch (Exception e) {
				tool.alertDialog(AlertType.ERROR, "Erreur", "Erreur de modification !", null);
				e.printStackTrace();
			}
			popMovieTableView();

		}else{
			tool.alertDialog(AlertType.ERROR, "modification des films", "Veuillez remplir les champs obligatoires !", null);
		}
		
	}
	
	public void deleteMovie(ActionEvent event){
		
		MovieInfo mi = tblView_movie.getSelectionModel().getSelectedItem();
		GenreInfo gi = tblView_genre.getSelectionModel().getSelectedItem();
		NationalityInfo ni = tblView_nat.getSelectionModel().getSelectedItem();
		CastInfo ci = tblView_cast.getSelectionModel().getSelectedItem();
		CastInfo di = tblView_dir.getSelectionModel().getSelectedItem();
		
		if(mi != null){
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Suppression");
			alert.setHeaderText(null);
			alert.setContentText("Voulez vous vraiment supprimer ?");
	
			ButtonType buttonTypeOne = new ButtonType("Ok");
			ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);
	
			alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
	
			Optional<ButtonType> result = alert.showAndWait();
			
			if (result.get() == buttonTypeOne){
				try {
					if (gi != null) {
						movieMdl.deleteMovieDB("moviegenre","fkgenreId", gi.getGenreIdCol(), mi.getMovieIdCol(), null);
					}else if (ni != null) {
						movieMdl.deleteMovieDB("movienationality","fkNationalityId", ni.getNationIdCol(), mi.getMovieIdCol(), null);
					}else if (ci != null) {
						movieMdl.deleteMovieDB("moviecast","fkCastId", ci.getCastIdCol(),mi.getMovieIdCol(), "cast");
					}else if (di != null) {
						movieMdl.deleteMovieDB("moviecast","fkCastId", di.getCastIdCol(),mi.getMovieIdCol(), "director");
					}else if (mi != null) {
						movieMdl.deleteMovieDB("movie","movieId", mi.getMovieIdCol(), 0, "movie");					
					}
					//movieMdl.deleteMovieDB("movie","movieId", mi.getMovieIdCol());
				} catch (Exception e) {
					e.printStackTrace();
					tool.alertDialog(AlertType.ERROR, "Suppression", "Une erreur c'est produite !", null);
				}
				this.popMovieTableView();
				this.emptyDataCreateForm();
			}
		}else{
			tool.alertDialog(AlertType.WARNING, "Suppression", "Veuillez selectionner une ligne !", null);
		}
	}
	
	
	public void popMovieTableView(){
		
		movieId.setCellValueFactory(new PropertyValueFactory<MovieInfo, Integer>("movieIdCol"));
		movieName.setCellValueFactory(new PropertyValueFactory<MovieInfo, String>("movieNameCol"));		
		
		tblView_movie.setItems(movieMdl.listOfMovie());
		
	}
	
	public void popNationList(){  
		  
		  cb_Nationality.setItems(movieMdl.getNationality(0));
		  
		  cb_Nationality.setCellFactory(new Callback<ListView<NationalityInfo>, ListCell<NationalityInfo>>() {
		   
		   @Override
		   public ListCell<NationalityInfo> call(ListView<NationalityInfo> param) {
		                ListCell<NationalityInfo> cell = new ListCell<NationalityInfo>(){
		                  
		                    @Override
		                    protected void updateItem(NationalityInfo t, boolean bln) {
		                        super.updateItem(t, bln);
		                        if (t != null) {
		                        	/*final String text = String.format("%s %s", t.getNationIdCol(), t.getNationlblFrCol());
		                        	setText(text);*/
		                        	t.getNationIdCol();
		                            setText(t.getNationlblFrCol());
		                        }
		                    }
		                };
		    return cell;
		   }
		  });
		  
		  //cb_Nationality.setCellFactory(ListView -> new ListCell<NationalityInfo>());
		  
		  
		 }
		 
	 @FXML
	 public void popGenreList(){
		  cb_Genre.setItems(movieMdl.getGenre(0));
		  
		  cb_Genre.setCellFactory(new Callback<ListView<GenreInfo>, ListCell<GenreInfo>>() {
		   
		   @Override
		   public ListCell<GenreInfo> call(ListView<GenreInfo> param) {
		                ListCell<GenreInfo> cell = new ListCell<GenreInfo>(){
		                    
		                    @Override
		                    protected void updateItem(GenreInfo t, boolean bln) {
		                        super.updateItem(t, bln);
		                        if (t != null) {
		                         t.getGenreIdCol();
		                            setText(t.getGenrelblCol());
		                        }
		                    }
		                };
		                return cell;
		   }
		  });
		 }
		 
	 @FXML
	 public void popCastList(){
		  cb_Cast.setItems(movieMdl.listOfCast(0,0));
		  
		  cb_Cast.setCellFactory(new Callback<ListView<CastInfo>, ListCell<CastInfo>>() {
		   
		   @Override
		   public ListCell<CastInfo> call(ListView<CastInfo> param) {
		                ListCell<CastInfo> cell = new ListCell<CastInfo>(){
		                   
		                    @Override
		                    protected void updateItem(CastInfo t, boolean bln) {
		                        super.updateItem(t, bln);
		                        if (t != null) {
		                         t.getCastIdCol();
		                            setText(t.getCastFirstnameCol() + " " + t.getCastLastnameCol());
		                        }
		                    }
		                };
		                return cell;
		   }
		  });
	 }
	
	@FXML
	public void btnAllMovie(ActionEvent event){
		popMovieTableView();
		emptyDataCreateForm();
	}

	@FXML
	public void popUpdateMovie(){
		
		MovieInfo mi = tblView_movie.getSelectionModel().getSelectedItem();
		//DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		if (mi != null) {
			tf_movieName.setText(mi.getMovieNameCol());
			tf_movieDuration.setText(mi.getMovieDuraCol());
			
			String[] parts = mi.getMovieRlseCol().split("-");
			int year = Integer.parseInt(parts[0]);
			int month = Integer.parseInt(parts[1]);
			int day = Integer.parseInt(parts[2]);
			dp_ReleaseDate.setValue(LocalDate.of(year, month, day));
			
			tf_movieRate.setText(mi.getMovieRateCol());
			ta_movieOverview.setText(mi.getMovieOverCol());
			tf_movieTrailer.setText(mi.getMovieTraiCol());
			tf_moviePoster.setText(mi.getMoviePostCol());
			tf_movieWebsite.setText(mi.getMovieWebsCol());
			
			genre.setCellValueFactory(new PropertyValueFactory<GenreInfo, String>("genrelblCol"));
			nationality.setCellValueFactory(new PropertyValueFactory<NationalityInfo, String>("nationlblFrCol"));			
			cast_firstname.setCellValueFactory(new PropertyValueFactory<CastInfo, String>("castFirstnameCol"));
			cast_lastname.setCellValueFactory(new PropertyValueFactory<CastInfo, String>("castLastnameCol"));			
			dir_firstname.setCellValueFactory(new PropertyValueFactory<CastInfo, String>("castFirstnameCol"));
			dir_lastname.setCellValueFactory(new PropertyValueFactory<CastInfo, String>("castLastnameCol"));
			
			tblView_cast.setItems(movieMdl.listOfCast(mi.getMovieIdCol(), 1));
			tblView_dir.setItems(movieMdl.listOfCast(mi.getMovieIdCol(), 2));
			tblView_genre.setItems(movieMdl.getGenre(mi.getMovieIdCol()));
			tblView_nat.setItems(movieMdl.getNationality(mi.getMovieIdCol()));
		}else{
			emptyDataCreateForm();
		}

	}
	
	private void emptyDataCreateForm() {
		tf_movieName.setText("");
		tf_movieDuration.setText("");
		dp_ReleaseDate.setValue(null);
		tf_movieRate.setText("");
		ta_movieOverview.setText("");
		tf_movieTrailer.setText("");
		tf_moviePoster.setText("");
		tf_movieWebsite.setText("");
		dp_ReleaseDate.setPromptText("");
		tblView_cast.setItems(null);
		tblView_dir.setItems(null);
		tblView_genre.setItems(null);
		tblView_nat.setItems(null);
	}


}
