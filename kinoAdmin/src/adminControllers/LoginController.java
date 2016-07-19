package adminControllers;

import java.net.URL;
import java.util.ResourceBundle;

import adminModels.loginModel;
import adminViews.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController implements Initializable, ControlledScreen{

	private ScreenController myCtrl;
	
	@FXML
	private Button btn_connection;
	@FXML
	private Label lbl_successMsg;
	@FXML
	private TextField tb_admin;
	@FXML
	private TextField pf_admin;
	@FXML
	private Label lbl_erMessage;
	
	//Instancier la classe loginModel
	loginModel lgnModel = new loginModel();
	
	public LoginController() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setScreenParent(ScreenController screenPage) {
		myCtrl = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	
	@FXML
	private void goToMainPage(ActionEvent event){
		lbl_erMessage.setText("");
		if (tb_admin.getText().equals(null) || pf_admin.getText().equals(null)) {
			lbl_erMessage.setText("Veuillez renseigner tous les champs !");
		}else if (tb_admin.getText().equals("")|| pf_admin.getText().equals("")) {
			lbl_erMessage.setText("Veuillez renseigner tous les champs !");
		}else{
			if (lgnModel.userAuth(tb_admin.getText(), pf_admin.getText()) == true) {
				lbl_successMsg.setText("Authentification réussie !");
				myCtrl.setScreen(Main.scrHomeID);
			}else{
				lbl_erMessage.setText("Erreur d'authentification ! Identifiant ou mot de passe incorrect.");
			}
		}
	}

	
}
