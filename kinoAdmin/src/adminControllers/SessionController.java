package adminControllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import adminModels.LanguageInfo;
import adminModels.RoomInfo;
import adminModels.SessionInfo;
import adminModels.SessionModel;
import adminModels.UpdateSessionInfo;
import adminViews.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;

public class SessionController implements Initializable, ControlledScreen{

	private ScreenController myCtrl;
	private SessionModel sessionMdl = new SessionModel();
	private Tools tool = new Tools();
	@FXML
	private TableView<SessionInfo> tblView_session;
	@FXML
	private TableColumn<SessionInfo, String> sMovieName;
	@FXML
	private TableColumn<SessionInfo, Integer> sessionId;
	@FXML
	private TextField tf_nbResa;
	@FXML
	private DatePicker dp_starttime;
	@FXML
	private DatePicker dp_enddate;
	@FXML
	private RadioButton rb_non;
	@FXML
	private RadioButton rb_oui;
	@FXML
	private ToggleGroup rg_showing;
	@FXML
	private ComboBox<String> cbb_language;
	@FXML
	private ComboBox<String> cbb_room;
	
	private String[] parts;
	private SessionInfo si;

	private LinkedHashMap<String, String> sessionMap = new LinkedHashMap<>();
	
	public SessionController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setScreenParent(ScreenController screenPage) {
		myCtrl = screenPage;
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		popTableView();
		popCbbLang();
		popCbbRoom();
	}

	@FXML
	public void gotoHomeView(ActionEvent event){
		myCtrl.setScreen(Main.scrHomeID);
	}	
	
	public void popTableView(){
		emptyFields();
		//Chargement des données dans la table view
		//assert tblView_admin != null;
		sessionId.setCellValueFactory(new PropertyValueFactory<SessionInfo, Integer>("sessionIdCol"));
		sMovieName.setCellValueFactory(new PropertyValueFactory<SessionInfo, String>("movieNameCol"));
		
		tblView_session.setItems(sessionMdl.listOfSessions());
	}
	
	public void popCbbLang(){
		
		List<String> choices = new ArrayList<>();
		for(LanguageInfo data : sessionMdl.listOfLanguages()){
			choices.add(data.getLanguageIdCol() + "-" + data.getCodeLanguageCol());
		}
		
		cbb_language.setPromptText("Choix de la langue");
		cbb_language.getItems().addAll(choices);
	}
	
	public void popCbbRoom(){
		String room3D = null;
		List<String> choices = new ArrayList<>();
		for(RoomInfo data : sessionMdl.listOfRooms()){
			if (data.getRoom3DCol() == 1) {
				room3D = " (3D)";
			}else{
				room3D = "";
			}
			choices.add(data.getRoomNumberCol() + "-" + data.getRoomNameCol() + room3D);
		}
		
		cbb_room.setPromptText("Choix de la salle");
		cbb_room.getItems().addAll(choices);
	}
	
	@FXML
	public void popFields(){
		si = tblView_session.getSelectionModel().getSelectedItem();
		String room3D = null;
		String codeLang = null;
		String roomName = null;
		int roomId = 0;
		int room3dCtrl = 0;
		int langId = 0;
		int year = 0;
		int month = 0;
		int day = 0;
		
		if (si != null) {
			
			for(UpdateSessionInfo data : sessionMdl.listOfUpSessions(si.getSessionIdCol())){
				roomId = data.getRoomIdCol();
				langId = data.getLanguageIdCol();
				room3dCtrl = data.getRoom3DCol();
				codeLang = data.getCodeLanguageCol();
				roomName = data.getRoomNameCol();
			}
			
			tf_nbResa.setText(si.getNbResaCol().toString());
			
			cbb_language.setValue(langId+"-"+codeLang);
			
			if (room3dCtrl == 1) {
				room3D = " (3D)";
			}else{
				room3D = "";
			}
			cbb_room.setValue(roomId+"-"+roomName+room3D);
			
			if (si.getShowingCol() == 0) {
				rb_non.setSelected(true);
			}else{
				rb_oui.setSelected(true);
			}
			
			parts = si.getStartTimeCol().split("-");
			year = Integer.parseInt(parts[0]);
			month = Integer.parseInt(parts[1]);
			day = Integer.parseInt(parts[2]);
			dp_starttime.setValue(LocalDate.of(year, month, day));
			
			parts = si.getEndDateCol().split("-");
			year = Integer.parseInt(parts[0]);
			month = Integer.parseInt(parts[1]);
			day = Integer.parseInt(parts[2]);
			dp_enddate.setValue(LocalDate.of(year, month, day));
		}
	}
	
	@FXML
	public void editSession(ActionEvent event){
		si = tblView_session.getSelectionModel().getSelectedItem();
		
		if (si != null) {
			if (dp_starttime.getValue() == null || dp_enddate.getValue() ==null || tf_nbResa.getText().equals("") || cbb_language.getValue() == null || cbb_room.getValue() == null) {
				tool.alertDialog(AlertType.WARNING, "Session", "Veuillez remplir tous les champs du formulaire !", null);
			}else{
			sessionMap.put("startTime", dp_starttime.getValue().toString());
			sessionMap.put("endDate", dp_enddate.getValue().toString());
			sessionMap.put("numberRes", tf_nbResa.getText());
			
			int getRbValue = rg_showing.getToggles().indexOf(rg_showing.getSelectedToggle());
			String RB_Value;
			if (getRbValue == 0) {
				RB_Value ="0";
			}else{
				RB_Value ="1";
			}
			sessionMap.put("showing", RB_Value);
			
			parts = cbb_language.getValue().split("-");
			sessionMap.put("fkLanguageId", parts[0]);
			
			parts = cbb_room.getValue().split("-");
			sessionMap.put("fkRoomId", parts[0]);
			
				sessionMdl.insertDate(tool.updateQuery("moviesession", "sessionId", si.getSessionIdCol(), sessionMap));
				tool.alertDialog(AlertType.INFORMATION, "Session modif", "Session modifié avec succès !", null);
				popTableView();
				emptyFields();
			}
		}else{
			tool.alertDialog(AlertType.WARNING, "Ajout d'acteurs", "Veuillez d'abord selectionner un film !", null);
		}
	}
	
	@FXML
	public void deleteSession(ActionEvent event){
		si = tblView_session.getSelectionModel().getSelectedItem();
		if (si != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Suppression");
			alert.setHeaderText(null);
			alert.setContentText("Voulez vous vraiment supprimer ?");
	
			ButtonType buttonTypeOne = new ButtonType("Ok");
			ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);
	
			alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
	
			Optional<ButtonType> result = alert.showAndWait();
			
			if (result.get() == buttonTypeOne){
				sessionMdl.deleteSessionDB(si.getSessionIdCol());
				tool.alertDialog(AlertType.INFORMATION, "Session", "Session supprimé avec succès !", null);
				popTableView();
				emptyFields();
			}
		}else{
			tool.alertDialog(AlertType.WARNING, "Ajout d'acteurs", "Veuillez d'abord selectionner un film !", null);
		}
	}
	
	public void emptyFields(){
		tf_nbResa.setText("");
		dp_enddate.setValue(null);
		dp_starttime.setValue(null);
		rb_non.setSelected(false);
		rb_oui.setSelected(false);
		cbb_language.setPromptText("Choix de la langue");
		cbb_room.setPromptText("Choix de la salle");
	}
	
}
