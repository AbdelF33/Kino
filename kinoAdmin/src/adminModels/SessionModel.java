package adminModels;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import adminControllers.Tools;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class SessionModel  extends DbConnection {

	private Tools tool = new Tools();
	
	public SessionModel() {
		// TODO Auto-generated constructor stub
	}

	public ObservableList<SessionInfo> listOfSessions(){
			
		String sqlQuery;
		ObservableList<SessionInfo> ol = FXCollections.observableArrayList();
		
		try {
			
			Connection cn = connect();
			//Création d'un statement
			setSt((Statement) cn.createStatement());
			
			/*sqlQuery = "SELECT * FROM movie, moviesession"
					+ " WHERE movie.movieId = moviesession.fkMovieId"
					+ " AND CURDATE() BETWEEN (moviesession.startTime) AND (moviesession.endDate)";*/
			
			sqlQuery = "SELECT * FROM movie, moviesession"
					+ " WHERE movie.movieId = moviesession.fkMovieId";
			
			//Execution de a requête
			 setRs(getSt().executeQuery(sqlQuery));
			 
				 //parcours du resultats
				 while(getRs().next()){
					 Integer sessionId = getRs().getInt("sessionId");
					 String movieName = getRs().getString("movieName");
					 String startTime = getRs().getString("startTime");
					 String endDate = getRs().getString("endDate");
					 Integer nbResa = getRs().getInt("numberRes");
					 Integer showing = getRs().getInt("showing");
					 Integer languageId = getRs().getInt("fkLanguageId");
					 Integer roomId = getRs().getInt("fkRoomId");
					 
					 ol.add(new SessionInfo(
							 sessionId, 
							 movieName, 
							 startTime, 
							 endDate, 
							 nbResa, 
							 showing, 
							 languageId,
							 roomId
							 )
					 );
				 }
				 
		} catch (SQLException e) {
			e.printStackTrace();
			tool.alertDialog(AlertType.ERROR, "Erreur", "Erreur SQL !", null);
			 return null;
		}finally{
			try {
				cn.close();
				getSt().close();
			} catch (SQLException e) {
				e.printStackTrace();
				tool.alertDialog(AlertType.ERROR, "Erreur", "Erreur SQL !", null);
				return null;
			}
		}
		
		return ol;
	}
	
	public ObservableList<UpdateSessionInfo> listOfUpSessions(int id){
		
		String sqlQuery;
		ObservableList<UpdateSessionInfo> ol = FXCollections.observableArrayList();
		
		try {
			
			Connection cn = connect();
			//Création d'un statement
			setSt((Statement) cn.createStatement());
						
			sqlQuery = "SELECT * FROM movie, moviesession, languagetbl, room "
					+ " WHERE movie.movieId = moviesession.fkMovieId"
					+ " AND moviesession.fkLanguageId = languagetbl.languageId"
					+ " AND moviesession.fkroomId = room.roomNumber"
					+ " AND moviesession.sessionId = "+id;
		
			
			//Execution de a requête
			 setRs(getSt().executeQuery(sqlQuery));
			 
				 //parcours du resultats
				 while(getRs().next()){
					 Integer sessionId = getRs().getInt("sessionId");
					 String movieName = getRs().getString("movieName");
					 String startTime = getRs().getString("startTime");
					 String endDate = getRs().getString("endDate");
					 Integer nbResa = getRs().getInt("numberRes");
					 Integer showing = getRs().getInt("showing");
					 Integer languageId = getRs().getInt("fkLanguageId");
					 String codeLanguage = getRs().getString("codeLanguage");
					 Integer roomId = getRs().getInt("fkRoomId");
					 String roomName = getRs().getString("roomName");
					 int room3D = getRs().getInt("room3D");
					 
					 ol.add(new UpdateSessionInfo(
							 sessionId, 
							 movieName, 
							 startTime, 
							 endDate, 
							 nbResa, 
							 showing, 
							 languageId,
							 codeLanguage,
							 roomId,
							 roomName,
							 room3D
							 )
					 );
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
		
		return ol;
	}
	
	public ObservableList<LanguageInfo> listOfLanguages(){
		
		String sqlQuery;
		ObservableList<LanguageInfo> ol = FXCollections.observableArrayList();
		
		try {
			
			Connection cn = connect();
			//Création d'un statement
			setSt((Statement) cn.createStatement());
			
			sqlQuery = "SELECT * FROM languagetbl";
			
			//Execution de a requête
			 setRs(getSt().executeQuery(sqlQuery));
			 
				 //parcours du resultats
				 while(getRs().next()){
					 Integer languageId = getRs().getInt("languageId");
					 String lblLanguage = getRs().getString("lblLanguage");
					 String codeLanguage = getRs().getString("codeLanguage");
					 
					 ol.add(new LanguageInfo(languageId, lblLanguage, codeLanguage));
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
		
		return ol;
	}
	
	public ObservableList<RoomInfo> listOfRooms(){
		
		String sqlQuery;
		ObservableList<RoomInfo> ol = FXCollections.observableArrayList();
		
		try {
			
			Connection cn = connect();
			//Création d'un statement
			setSt((Statement) cn.createStatement());
			
			sqlQuery = "SELECT * FROM room";
			
			//Execution de a requête
			 setRs(getSt().executeQuery(sqlQuery));
			 
				 //parcours du resultats
				 while(getRs().next()){
					 Integer roomNumber = getRs().getInt("roomNumber");
					 String roomName = getRs().getString("roomName");
					 Integer room3D = getRs().getInt("room3D");
					 Integer roomCapacity = getRs().getInt("roomCapacity");
					 
					 ol.add(new RoomInfo(roomNumber, roomName, room3D, roomCapacity));
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
		
		return ol;
	}
	
	public void insertDate(String query){
		try {
			Connection cn = connect();
			//Création d'un statement
			setSt((Statement) cn.createStatement());
			
			String sqlQuery = query;
			
			//Execution de la requête
			 getSt().executeUpdate(sqlQuery);
			 
		} catch (SQLException e) {
			e.printStackTrace();
			tool.alertDialog(AlertType.ERROR, "Erreur", "Erreur d'insertion !", null);
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
	
	public void deleteSessionDB(int id){
		try {
			Connection cn = connect();
			//Création d'un statement
			setSt((Statement) cn.createStatement());
			
			String sqlQuery = "DELETE FROM moviesession WHERE sessionId = " + id;

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
	
	
}
