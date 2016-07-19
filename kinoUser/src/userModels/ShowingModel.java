package userModels;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import userModels.CastInfo;
import userModels.GenreInfo;
import userModels.MovieInfo;
import userModels.NationalityInfo;
import userControllers.Tools;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class ShowingModel extends DbConnection {
	
	private Tools tool = new Tools();
	

	public ShowingModel() {
		// TODO Auto-generated constructor stub
	}
	
	public void queryExe(String sqlQuery){
		try {
			Connection cn = connect();
			//Création d'un statement
			setSt((Statement) cn.createStatement());
			//Execution de la requête
			 getSt().executeUpdate(sqlQuery);
			 
		} catch (SQLException e) {
			e.printStackTrace();
			tool.alertDialog(AlertType.ERROR, "Erreur", "Erreur requête SQL !", null);
			return;
		}finally{
			try {
				cn.close();
				getSt().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void doubleQuExe(String sqlQuery, int nbResa){
		String sqlQuery2 = null;
		Date dateJour = new Date();
		Integer lastId = null;
		int chiffreRdm = (int)(Math.random() * (10000000-1)) + 0;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String ticketRef = String.valueOf(chiffreRdm)+"TICKET"+formatter.format(dateJour);
		try {
			Connection cn = connect();
			//Création d'un statement
			setSt((Statement) cn.createStatement());
			
			//Execution de la requête
			getSt().executeUpdate(sqlQuery);
			
			//get the last insert id
			String sqlQuery3 = "SELECT LAST_INSERT_ID()";
			setRs(getSt().executeQuery(sqlQuery3));
			while (getRs().next()) {
				lastId = getRs().getInt("LAST_INSERT_ID()");
			}
			
			for (int i = 0; i < nbResa; i++) {
				sqlQuery2 = "INSERT INTO ticket(ticketRef, ticketDate, fkBookingId) VALUES ('"+ticketRef+"','"+formatter.format(dateJour)+"', "+lastId+")";				
				getSt().executeUpdate(sqlQuery2);
			}
			
			 
		} catch (SQLException e) {
			e.printStackTrace();
			tool.alertDialog(AlertType.ERROR, "Erreur", "Erreur requête SQL !", null);
			return;
		}finally{
			try {
				cn.close();
				getSt().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ObservableList<ShowingInfo> movieShowing(){
		
		String sqlQuery;
		ObservableList<ShowingInfo> myMovieSession = FXCollections.observableArrayList();
	
		try {
			
			Connection cn = connect();
			//Création d'un statement
			setSt((Statement) cn.createStatement());
			
			 sqlQuery = "SELECT * FROM movie, moviesession, languagetbl ,room"
						+ " WHERE movie.movieId = moviesession.fkMovieId"
						+ " AND languagetbl.languageId = moviesession.fkLanguageId"
						+ " AND room.roomNumber = moviesession.fkRoomId"
						+ " AND CURDATE() BETWEEN (moviesession.startTime) AND (moviesession.endDate)"
						+ " AND showing = 1";
			
			//Execution de la requête
			 setRs(getSt().executeQuery(sqlQuery));
			 
				 //parcours du resultats
				 while(getRs().next()){
					 
					 Integer movieId = getRs().getInt("movieId");
					 Integer sessionId = getRs().getInt("sessionId");
					 String movieName = getRs().getString("moviename");
					 String movieReleaseDate = getRs().getString("moviereleasedate");
					 String movieDuration = getRs().getString("movieduration");
					 String moviePoster = getRs().getString("movieposter");
					 String movieOverview = getRs().getString("movieoverview");
					 String movieRate = getRs().getString("movierate");
					 String codeLanguage = getRs().getString("codeLanguage");
					 String roomNameCol = getRs().getString("roomName");
					 String room3DCol = getRs().getString("room3d");
					 String startTime = getRs().getString("startTime");
					 String endDate = getRs().getString("endDate");
					
					 myMovieSession.add(new ShowingInfo(movieId, sessionId, moviePoster, movieName, movieReleaseDate, movieDuration, movieOverview, movieRate, codeLanguage,
							  roomNameCol, room3DCol, startTime, endDate));
				 }
					 
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					cn.close();
					getSt().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			return myMovieSession;
	
			}
	
	public ObservableList<MovieInfo> listOfMovie(){
		
		String sqlQuery;
		ObservableList<MovieInfo> myMovie = FXCollections.observableArrayList();
	
		try {
			
			Connection cn = connect();
			//Création d'un statement
			setSt((Statement) cn.createStatement());
			
			 sqlQuery = "SELECT * FROM movie";
			
			//Execution de la requête
			 setRs(getSt().executeQuery(sqlQuery));
			 
				 //parcours du resultats
				 while(getRs().next()){
					 Integer movieId = getRs().getInt("movieid");
					 String movieName = getRs().getString("moviename");
					 String movieReleaseDate = getRs().getString("moviereleasedate");
					 String movieDuration = getRs().getString("movieduration");
					 String moviePoster = getRs().getString("movieposter");
					 String movieOverview = getRs().getString("movieoverview");
					 String movieRate = getRs().getString("movierate");
					 String movieTrailer = getRs().getString("movietrailer");
					 String movieWebSite = getRs().getString("moviewebsite");
					 
					 myMovie.add(new MovieInfo(movieId, movieName, movieReleaseDate, 
							 					movieDuration, moviePoster,
					 							movieOverview, movieRate, movieTrailer, movieWebSite));
				 }
					 
			} catch (SQLException e) {
				e.printStackTrace();
				tool.alertDialog(AlertType.ERROR, "Erreur", "Une erreur s'est produite !", null);
				return null;
			}finally{
				try {
					cn.close();
					getSt().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			return myMovie;
	
			}
	
	
	// Récupération de mes nationalités
	public ObservableList<NationalityInfo> getNationality(int id){
		ObservableList<NationalityInfo> nation = FXCollections.observableArrayList();
		String sqlQuery = null;
		
	try{
		
		Connection cn = connect();
		//Création d'un statement
		setSt((Statement) cn.createStatement());
		if (id == 0) {
			sqlQuery = "SELECT * FROM nationality";	
		}else{
			sqlQuery = "SELECT * FROM nationality, movienationality, movie"
					+ " WHERE nationality.nationalityId = movienationality.fkNationalityId"
					+ " AND movie.movieId = movienationality.fkMovieId"
					+ " AND movie.movieId = " + id;
		}
		
		//Execution de la requête
		setRs(getSt().executeQuery(sqlQuery)); 
		
		while(getRs().next())
		{
			Integer nationalityId = getRs().getInt("nationalityid");
			String lblNationalityFr = getRs().getString("lblnationalityfr");
			nation.addAll(new NationalityInfo(nationalityId, lblNationalityFr));	
		 }
			 
		} catch (SQLException e) {
			e.printStackTrace();
			tool.alertDialog(AlertType.ERROR, "Erreur", "Une erreur s'est produite !", null);
			return null;
		}finally{
			try {
				cn.close();
				getSt().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			return nation;
	
	}
	
	// Récupération de mes genres
	public ObservableList<GenreInfo> getGenre(int id){
		ObservableList<GenreInfo> myGenre = FXCollections.observableArrayList();
		String sqlQuery = null;
		
		try{
			
			Connection cn = connect();
			//Création d'un statement
			setSt((Statement) cn.createStatement());
			
			if (id == 0) {
				sqlQuery = "SELECT * FROM genre";
			}else{
				sqlQuery = "SELECT * FROM genre, moviegenre, movie"
						+ " WHERE genre.genreId = moviegenre.fkGenreId"
						+ " AND movie.movieId = moviegenre.fkMovieId"
						+ " AND movie.movieId = " + id;
			}
			
			//Execution de la requête
			setRs(getSt().executeQuery(sqlQuery));
			
			while(getRs().next())
			{
				Integer genreId = getRs().getInt("genreid");
				String lblGenre = getRs().getString("lblgenre");
				myGenre.add(new GenreInfo(genreId, lblGenre));	
			}
			 
		} catch (SQLException e) {
			e.printStackTrace();
			tool.alertDialog(AlertType.ERROR, "Erreur", "Une erreur s'est produite !", null);
			return null;
		}finally{
			try {
				cn.close();
				getSt().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return myGenre;
	}
	
	// Récupération de mes cast
	public ObservableList<CastInfo> listOfCast(int id, int cast){
		ObservableList<CastInfo> myCast = FXCollections.observableArrayList();
		String sqlQuery = null;
		
		try{
			
			Connection cn = connect();
			//Création d'un statement
			setSt((Statement) cn.createStatement());
			
			if (id == 0 && cast == 0) {
				sqlQuery = "SELECT * FROM cast_tbl";
			}else{
				// cast=1 correspond à un acteur 
				// cast=2 correspond à un réalisateur
				sqlQuery = "SELECT * FROM cast_tbl, moviecast, movie, functiontbl"
						+ " WHERE cast_tbl.castId = moviecast.fkCastId"
						+ " AND movie.movieId = moviecast.fkMovieId"
						+ " AND functiontbl.functionId = movieCast.fkFunctionId"
						+ " AND movie.movieId = " + id 
						+ " AND functiontbl.functionId = " + cast;
			}
			
			
			//Execution de la requête
			setRs(getSt().executeQuery(sqlQuery));
			
			while(getRs().next())
			{
				Integer castId = getRs().getInt("castId");
				String firstnameCast = getRs().getString("firstnameCast");
				String lastnameCast = getRs().getString("lastnameCast");
				myCast.add(new CastInfo(castId, firstnameCast, lastnameCast));	
			}
			 
		} catch (SQLException e) {
			e.printStackTrace();
			tool.alertDialog(AlertType.ERROR, "Erreur", "Une erreur s'est produite !", null);
			return null;
		}finally{
			try {
				cn.close();
				getSt().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return myCast;
	}
	

}
