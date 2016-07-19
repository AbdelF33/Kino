package adminModels;

import java.sql.SQLException;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import adminControllers.Tools;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class MovieModel extends DbConnection {
	
	private Tools tool = new Tools();


	public MovieModel() {
		// TODO Auto-generated constructor stub
	}
	
	public void addMovieDB(String sqlQuery){
		try {
			Connection cn = connect();
			//Création d'un statement
			setSt((Statement) cn.createStatement());
			//Execution de la requête
			 getSt().executeUpdate(sqlQuery);
			 
		} catch (SQLException e) {
			e.printStackTrace();
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					tool.alertDialog(AlertType.ERROR, "Erreur", "Erreur d'insertion !", null);
				}
			});
			return;
		}finally{
			try {
				cn.close();
				getSt().close();
			} catch (SQLException e) {
				e.printStackTrace();
				return;
			}
		}
	}
	
	public void updateMovieDB(String sqlQuery){
		try {
			Connection cn = connect();
			//Création d'un statement
			setSt((Statement) cn.createStatement());

			//Execution de la requête
			 getSt().executeUpdate(sqlQuery);
			
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
			}
		}
	}
	/**
	 * 
	 * @param table
	 * @param colIndex
	 * @param movieId
	 * @param id
	 * @param choice
	 */
	public void deleteMovieDB(String table, String colIndex, int movieId, int id, String choice){
		String sqlQuery = null;
		try {
			Connection cn = connect();
			//Création d'un statement
			setSt((Statement) cn.createStatement());
			if (choice == "movie" && id == 0) {
				sqlQuery = "DELETE FROM "+table+" WHERE "+colIndex+" = " + movieId;
			}else if(choice == "cast"){
				sqlQuery = "DELETE FROM "+table+" WHERE "+colIndex+" = " + movieId
						+ " AND fkMovieId = "+ id
						+ " AND fkFunctionId = 1";
			}else if(choice == "director"){
				sqlQuery = "DELETE FROM "+table+" WHERE "+colIndex+" = " + movieId
						+ " AND fkMovieId = "+ id
						+ " AND fkFunctionId = 2";
			}else{
				sqlQuery = "DELETE FROM "+table+" WHERE "+colIndex+" = " + movieId
						+ " AND fkMovieId = "+ id;
			}

			//Execution de la requête
			 getSt().executeUpdate(sqlQuery);
			 
		} catch (SQLException e) {
			e.printStackTrace();
			tool.alertDialog(AlertType.ERROR, "Erreur", "Erreur de suppression !", null);
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
				//return;
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
		//ArrayList<> nation = new ArrayList<NationalityInfo>();
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
			//return;
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
			//return;
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
			//return;
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
		

