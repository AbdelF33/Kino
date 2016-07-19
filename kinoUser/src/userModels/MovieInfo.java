package userModels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MovieInfo {
	
	private final SimpleIntegerProperty movieIdCol;
	private final SimpleStringProperty movieNameCol;
	private final SimpleStringProperty movieRlseCol;
	private final SimpleStringProperty movieDuraCol;
	private final SimpleStringProperty moviePostCol;
	private final SimpleStringProperty movieOverCol;
	private final SimpleStringProperty movieRateCol;
	private final SimpleStringProperty movieTraiCol;
	private final SimpleStringProperty movieWebsCol;

	public MovieInfo(Integer movieId, String movieName, String movieReleaseDate, String movieDuration,
			String moviePoster,String movieOverview, String movieRate,String movieTrailer, String movieWebsite) {
		
		// TODO Auto-generated constructor stub.
		this.movieIdCol = new SimpleIntegerProperty(movieId);
		this.movieNameCol = new SimpleStringProperty(movieName);
		this.movieRlseCol = new SimpleStringProperty(movieReleaseDate);
		this.movieDuraCol = new SimpleStringProperty(movieDuration);
		this.moviePostCol = new SimpleStringProperty(moviePoster);
		this.movieOverCol = new SimpleStringProperty(movieOverview);
		this.movieRateCol = new SimpleStringProperty(movieRate);
		this.movieTraiCol = new SimpleStringProperty(movieTrailer);
		this.movieWebsCol = new SimpleStringProperty(movieWebsite);
		
	}

	public Integer getMovieIdCol() {
		return movieIdCol.get();
	}
	
	public void setmovieIdCol(Integer movieId){
		movieIdCol.set(movieId);
	}

	public String getMovieNameCol() {
		return movieNameCol.get();
	}
	
	public void setmovieNameCol(String movieName){
		movieNameCol.set(movieName);
	}

	public String getMovieRlseCol() {
		return movieRlseCol.get();
	}
	
	public void setmovieRlseColCol(String movieReleaseDate){
		movieRlseCol.set(movieReleaseDate);
	}

	public String getMovieDuraCol() {
		return movieDuraCol.get();
	}
	
	public void setmovieDuraCol(String movieDuration){
		movieDuraCol.set(movieDuration);
	}

	public String getMoviePostCol() {
		return moviePostCol.get();
	}
	
	public void setmoviePostCol(String moviePoster){
		moviePostCol.set(moviePoster);
	}
	public String getMovieOverCol() {
		return movieOverCol.get();
	}
	
	public void setmovieOverCol(String movieOverview){
		movieOverCol.set(movieOverview);
	}

	public String getMovieRateCol() {
		return movieRateCol.get();
	}
	
	public void setmovieRateCol(String movieRate){
		movieRateCol.set(movieRate);
	}

	public String getMovieTraiCol() {
		return movieTraiCol.get();
	}
	
	public void setmovieTraiCol(String movieTrailer){
		movieTraiCol.set(movieTrailer);
	}

	public String getMovieWebsCol() {
		return movieWebsCol.get();
	}
	
	public void setmovieWebsCol(String movieWebsite){
		movieWebsCol.set(movieWebsite);
	}
}
