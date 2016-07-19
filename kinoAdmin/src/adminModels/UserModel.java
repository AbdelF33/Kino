package adminModels;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import adminControllers.Tools;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class UserModel extends DbConnection {
	
	private Tools tool = new Tools();
	
	public UserModel() {
		// TODO Auto-generated constructor stub
	}


	public void createUserDB(String query1 , Integer isAdmin){
		try {
			Connection cn = connect();
			//Création d'un statement
			setSt((Statement) cn.createStatement());
			
			String sqlQuery1 = query1;
			String sqlQuery2 = "INSERT INTO privilege(superAdmin, fkAccountId) VALUES ("+ isAdmin +", LAST_INSERT_ID())";
			
			//Execution de la requête
			 getSt().executeUpdate(sqlQuery1);
			 getSt().executeUpdate(sqlQuery2);
			 
		} catch (SQLException e) {
			e.printStackTrace();
			tool.alertDialog(AlertType.ERROR, "Erreur", "Erreur SQL !", null);
			return;
		}finally{
			try {
				cn.close();
				getSt().close();
			} catch (SQLException e) {
				e.printStackTrace();
				tool.alertDialog(AlertType.ERROR, "Erreur", "Erreur SQL !", null);
				return;
			}
		}
	}
	
	public void updateUserDB(String query1, String query2){
		try {
			Connection cn = connect();
			//Création d'un statement
			setSt((Statement) cn.createStatement());
			
			String sqlQuery1 = query1;
			String sqlQuery2 = query2;

			//Execution de la requête
			 getSt().executeUpdate(sqlQuery1);
			 getSt().executeUpdate(sqlQuery2);
			 
		} catch (SQLException e) {
			e.printStackTrace();
			tool.alertDialog(AlertType.ERROR, "Erreur", "Erreur de modification !", null);
			return;
		}finally{
			try {
				cn.close();
				getSt().close();
			} catch (SQLException e) {
				e.printStackTrace();
				tool.alertDialog(AlertType.ERROR, "Erreur", "Erreur SQL !", null);
				return;
			}
		}
	}
	
	public void deleteUserDB(int id){
		try {
			Connection cn = connect();
			//Création d'un statement
			setSt((Statement) cn.createStatement());
			
			String sqlQuery = "DELETE FROM account WHERE accountId = " + id;

			//Execution de la requête
			 getSt().executeUpdate(sqlQuery);
			 
		} catch (SQLException e) {
			e.printStackTrace();
			tool.alertDialog(AlertType.ERROR, "Erreur", "Erreur SQL !", null);
			return;
		}finally{
			try {
				cn.close();
				getSt().close();
			} catch (SQLException e) {
				e.printStackTrace();
				tool.alertDialog(AlertType.ERROR, "Erreur", "Erreur SQL !", null);
				return;
			}
		}
	}
	
	public ObservableList<AdminInfo> listOfUsers(int id){
	//public List<AdminInfo> listOfUsers(){
		
		String sqlQuery;
		ObservableList<AdminInfo> ol = FXCollections.observableArrayList();
		//List<AdminInfo> ol = new LinkedList<AdminInfo>();
		
		try {
			
			Connection cn = connect();
			//Création d'un statement
			setSt((Statement) cn.createStatement());
			
			if (id == 2) {
				sqlQuery = "SELECT * FROM account, privilege WHERE account.accountId = privilege.fkAccountId";	
			}else{
				// 0 = Admin / 1 = superAdmin
				sqlQuery = "SELECT * FROM account, privilege WHERE privilege.superAdmin = "+id+" AND account.accountId = privilege.fkAccountId";
			}
			
			//Execution de a requête
			 setRs(getSt().executeQuery(sqlQuery));
			 
				 //parcours du resultats
				 while(getRs().next()){
					 Integer accountId = getRs().getInt("accountId");
					 String lastname = getRs().getString("lastname");
					 String firstname = getRs().getString("firstname");
					 Integer superAdmin = getRs().getInt("superAdmin");
					 String email = getRs().getString("email");
					 String tel = getRs().getString("tel");
					 String adress = getRs().getString("adress");
					 String username = getRs().getString("username");
					 String password = getRs().getString("password");
					 
					 ol.add(new AdminInfo(accountId, firstname, lastname, superAdmin, email, tel, adress, username, password));
					 //System.out.println(accountId+" "+ lastname+" " +firstname+"  "+ superAdmin+" "+ email+" "+ tel+" "+ adress);
					 
					 //Méthode 2
					 /*AdminInfo am = new AdminInfo();
					 am.accountId.set(getRs().getInt("accountId"));
					 am.lastname.set(getRs().getString("lastname"));
					 am.firstname.set(getRs().getString("firstname"));
					 am.superAdmin.set(getRs().getInt("superAdmin"));
					 am.email.set(getRs().getString("email"));
					 am.tel.set(getRs().getString("tel"));
					 am.adress.set(getRs().getString("adress"));
					 ol.add(am);*/
				 }
				 
		} catch (SQLException e) {
			e.printStackTrace();
			tool.alertDialog(AlertType.ERROR, "Erreur", "Erreur SQL !", null);
			//return ol;
		}finally{
			try {
				cn.close();
				getSt().close();
			} catch (SQLException e) {
				e.printStackTrace();
				tool.alertDialog(AlertType.ERROR, "Erreur", "Erreur SQL !", null);
				//return;
			}
		}
		
		return ol;
	}
	

}
