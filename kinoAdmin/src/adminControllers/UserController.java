package adminControllers;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import adminModels.AdminInfo;
import adminModels.UserModel;
import adminViews.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserController implements Initializable, ControlledScreen{

	private ScreenController myCtrl;
	
	@FXML
	private TextField tf_tel;
	@FXML
	private TextField tf_login;
	@FXML
	private TextField tf_lastname;
	@FXML
	private TextField tf_firstname;
	@FXML
	private TextField tf_email;
	@FXML
	private TextArea ta_adress;
	@FXML
	private RadioButton rb_spAdmin;
	@FXML
	private RadioButton rb_admin;
	@FXML
	private ToggleGroup rg_admin;
	@FXML
	private PasswordField pf_pass;
	@FXML
	private PasswordField pf_confirmPass;
	@FXML
	private Button btn_create;
	@FXML
	private Button btn_update;
	@FXML
	private Button btn_cancel;
	@FXML
	private Button btn_cancelU;
	@FXML
	private Button link_CreateUser;
	@FXML
	private Button link_listUser;
	@FXML
	private Label lbl_succesMsg;
	@FXML
	private Label lbl_erMsg;
	@FXML
	private Label lbl_passMatch;
	@FXML
	private Label lbl_passError;
	@FXML
	private Label lbl_id;
	@FXML
	private TableView<AdminInfo> tblView_admin;
	@FXML
	private TableColumn<AdminInfo, Integer> adminId;
	@FXML
	private TableColumn<AdminInfo, String> adminLastname;
	@FXML
	private TableColumn<AdminInfo, String> adminFirstname;
	@FXML
	private TableColumn<AdminInfo, Integer> adminPrivilege;
	@FXML
	private TableColumn<AdminInfo, String> adminEmail;
	@FXML
	private TableColumn<AdminInfo, String> adminTel;
	@FXML
	private TableColumn<AdminInfo, String> adminAdress;
	@FXML
	private ListView<AdminInfo> lv_admin;
	
	private LinkedHashMap<String, String> userInfo = new LinkedHashMap<>();
	private LinkedHashMap<String, String> userPrivilege = new LinkedHashMap<>();
	
	private UserModel usrMdl = new UserModel();
	private Tools tool = new Tools();
	
	
	public UserController() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setScreenParent(ScreenController screenPage) {
		myCtrl = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		popTableView(2);
	}
	
	@FXML
	public void gotoHomeView(ActionEvent event){
		myCtrl.setScreen(Main.scrHomeID);
	}	
	
	@FXML
	public void gotolistUser(ActionEvent event){
		myCtrl.setScreen(Main.scrListOfAdminID);
	}
	
	@FXML
	public void cancelButton(ActionEvent event){
		emptyData();
	}

	@FXML
	public void createUser(ActionEvent event){

		//Remplissage du Hashmap de données
		userInfo.put("firstname", tf_firstname.getText());
		userInfo.put("lastname", tf_lastname.getText());
		userInfo.put("email", tf_email.getText());
		userInfo.put("adress", ta_adress.getText());
		userInfo.put("password", pf_confirmPass.getText());
		userInfo.put("username", tf_login.getText());
		userInfo.put("tel", tf_tel.getText());
		
		if (!(tf_firstname.getText().equals("") || tf_lastname.getText().equals("") || tf_login.getText().equals("") || pf_confirmPass.getText().equals(""))) {
			if (pf_pass.getText().equals(pf_confirmPass.getText())) {		
				//Méhode de création
				try {
					usrMdl.createUserDB(tool.insertQuery("account", userInfo), rg_admin.getToggles().indexOf(rg_admin.getSelectedToggle()));				
				} catch (Exception e) {
					tool.alertDialog(AlertType.ERROR, "Erreur", "Une erreur est survenue !", null);
					e.printStackTrace();
				}
				lbl_succesMsg.setText("Utilisateur créé avec succès !");
				popTableView(2);
				emptyData();
			}else{
				lbl_passError.setText("Veuillez saisir un mot de passe identique !");
			}
		}else{
			lbl_erMsg.setText("Veuillez remplir les champs obligatoires !");
		}
	}
	
	@FXML
	public void updateUser(ActionEvent event){
		
		AdminInfo am = tblView_admin.getSelectionModel().getSelectedItem();
		int getRbValue = rg_admin.getToggles().indexOf(rg_admin.getSelectedToggle());
		String RB_Value;
		
		if (!(tf_firstname.getText().equals("") || tf_lastname.getText().equals("") || tf_login.getText().equals("") || pf_confirmPass.getText().equals(""))) {
			if (pf_pass.getText().equals(pf_confirmPass.getText())) {	
				
				if (getRbValue == 0) {
					RB_Value ="0";
				}else{
					RB_Value ="1";
				}
				
				//Remplissage du Hashmap de données
				userInfo.put("firstname", tf_firstname.getText());
				userInfo.put("lastname", tf_lastname.getText());
				userInfo.put("email", tf_email.getText());
				userInfo.put("adress", ta_adress.getText());
				userInfo.put("tel", tf_tel.getText());
				userPrivilege.put("privilegeId", RB_Value);
				
				try {
					usrMdl.updateUserDB(
						tool.updateQuery("account", "accountId", am.getIdCol(), userInfo), 
						tool.updateQuery("privilege", "privilegeId", am.getPrivIdCol(), userPrivilege)
					);
				} catch (Exception e) {
					tool.alertDialog(AlertType.ERROR, "Erreur", "Erreur de modification !", null);
					e.printStackTrace();
				}
				tool.alertDialog(AlertType.INFORMATION, "Confirmation", "L'utilisateur n°" +am.getIdCol()+ " a été modifié avec succès !", null);
				myCtrl.setScreen(Main.scrListOfAdminID);
				popTableView(2);
			}else{
				lbl_passError.setText("Veuillez saisir un mot de passe identique !");
			}
		}else{
			lbl_erMsg.setText("Veuillez remplir les champs obligatoires !");
		}
		
	}
	
	@FXML
	public void deleteUser(ActionEvent event){
		AdminInfo am = tblView_admin.getSelectionModel().getSelectedItem();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Suppression");
		alert.setHeaderText(null);
		alert.setContentText("Voulez vous vraiment supprimer cet utilisateur ?");

		ButtonType buttonTypeOne = new ButtonType("Ok");
		ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne){
			try {
				usrMdl.deleteUserDB(am.getIdCol());	
			} catch (Exception e) {
				tool.alertDialog(AlertType.WARNING, "Suppression", "Veuillez selectionner une ligne !", null);
				e.printStackTrace();
			}
			popTableView(2);
			emptyData();
		}
	
	}
	
	@FXML
	public void popAllListView(){
		
		lv_admin.setItems(usrMdl.listOfUsers(0));
		//lv_admin.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		/*try {
			popUpdateUser(am.getIdCol());
		} catch (Exception e) {
			tool.alertDialog(AlertType.WARNING, "Select row", "Veuillez sélectionner une ligne", null);
			myCtrl.setScreen(Main.scrListOfAdminID);
		}*/
	}
	
	public void popTableView(int param){
		emptyData();
		//Chargement des données dans la table view
		//assert tblView_admin != null;
		adminId.setCellValueFactory(new PropertyValueFactory<AdminInfo, Integer>("idCol"));
		adminFirstname.setCellValueFactory(new PropertyValueFactory<AdminInfo, String>("lastnCol"));
		adminLastname.setCellValueFactory(new PropertyValueFactory<AdminInfo, String>("firstnCol"));
		/*adminPrivilege.setCellValueFactory(new PropertyValueFactory<AdminInfo, Integer>("privIdCol"));
		adminEmail.setCellValueFactory(new PropertyValueFactory<AdminInfo, String>("emailCol"));
		adminTel.setCellValueFactory(new PropertyValueFactory<AdminInfo, String>("telCol"));
		adminAdress.setCellValueFactory(new PropertyValueFactory<AdminInfo, String>("adressCol"));*/
		
		tblView_admin.setItems(usrMdl.listOfUsers(param));
	}
	
	@FXML
	public void btnAllTv(ActionEvent event){
		popTableView(2);
	}
	
	@FXML
	public void btnAdminTv(ActionEvent event){
		popTableView(0);
	}
	
	@FXML
	public void btnSpAdminTv(ActionEvent event){
		popTableView(1);
	}
	
	@FXML
	public void popUpdateUser(/*int userId*/){
		emptyData();
		AdminInfo am = tblView_admin.getSelectionModel().getSelectedItem();
		
		if (am != null) {
			lbl_id.setText(am.getIdCol().toString());
			tf_firstname.setText(am.getFirstnCol());
			tf_lastname.setText(am.getLastnCol());
			tf_email.setText(am.getEmailCol());
			ta_adress.setText(am.getAdressCol());
			tf_tel.setText(am.getTelCol());
			tf_login.setText(am.getUsernameCol());
			pf_pass.setText(am.getPasswordCol());
			pf_confirmPass.setText(am.getPasswordCol());
			if (am.getPrivIdCol() == 0) {
				rb_admin.setSelected(true);
			}else{
				rb_spAdmin.setSelected(true);
			}
		}else{
			emptyData();
		}

		/*for(AdminInfo data : usrMdl.listOfUsers(userId)){
			id = adminId.getCellData(data);
			fName = adminFirstname.getCellData(data);
			lName = adminLastname.getCellData(data);
			priv = adminPrivilege.getCellData(data);
			email = adminEmail.getCellData(data);
			tel = adminTel.getCellData(data);
			adress = adminAdress.getCellData(data);
		}*/
	}
	
	//Vider tous les champs de la vue createUser
	public void emptyData(){
		lbl_succesMsg.setText("");
		lbl_erMsg.setText("");
		lbl_passError.setText("");
		lbl_passMatch.setText("");
		tf_firstname.setText("");
		tf_lastname.setText("");
		tf_email.setText("");
		ta_adress.setText("");
		pf_confirmPass.setText("");
		pf_pass.setText("");
		tf_login.setText("");
		tf_tel.setText("");
	}
	
	public void passMatch(){
		if (pf_pass.getText().equals(pf_confirmPass.getText())) {
			lbl_passError.setText("");
			lbl_passMatch.setText("Mot de passe identique !");
		}else{
			lbl_passMatch.setText("");
			lbl_passError.setText("Mot de passe différent !");
		}
	}
	
}
