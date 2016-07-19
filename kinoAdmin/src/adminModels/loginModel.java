package adminModels;

import java.sql.SQLException;

import javafx.scene.control.Alert.AlertType;
import adminControllers.Tools;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class loginModel extends DbConnection{
	
	private Tools tool = new Tools();
	
	public loginModel() {
		// TODO Auto-generated constructor stub
	}


	public boolean userAuth(String userName, String userPass){
		
		boolean result = false;
		try {

			Connection cn = connect();
			//Création d'un statement
			setSt((Statement) cn.createStatement());
			
			String sqlQuery = "SELECT username, password "
					+ "FROM account "
					+ "WHERE username='"+userName+"' AND password='"+userPass+"'"
					+ "AND accountId IN (SELECT fkAccountId "
										+ "FROM privilege "
										+ "WHERE superAdmin = 0 "
										+ "OR superAdmin = 1)";
			
			//Execution de a requête
			 setRs(getSt().executeQuery(sqlQuery));
			
			//Verifier si la requete a retourné un resultat
			if(!rs.isBeforeFirst()){
				result = false;			 
			}else{
				result = true;				 
			}
			 
		} catch (SQLException e) {
			e.printStackTrace();
			tool.alertDialog(AlertType.ERROR, "Erreur", "Erreur SQL !", null);
			//return;
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
		return result;
	}

}
