package userModels;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ShowingInfo {
	
	private final SimpleIntegerProperty movieIdCol;
	private final SimpleIntegerProperty sessionIdCol;
	private final SimpleStringProperty moviePosterlstview;
	private final SimpleStringProperty movieNamelbl;
	private final SimpleStringProperty movieRlseDatelbl;;
	private final SimpleStringProperty movieDuralbl;
	private final SimpleStringProperty movieOverlbl;
	private final SimpleStringProperty movieRatelbl;
	private final SimpleStringProperty langagelbl;
	private final SimpleStringProperty roomlbl;
	private final SimpleStringProperty roomtypelbl;
	private final SimpleStringProperty startdatemslbl;
	private final SimpleStringProperty enddatemslbl;
	
	
	
	
		
	public ShowingInfo(Integer movieId,Integer sessionId, String moviePoster, String movieName, String movieReleaseDate, String movieDuration, 
			String movieOverview, String movieRate,  String codeLanguage, String roomNameCol, String room3DCol, String startTime, String endDate ) {
		
		this.movieIdCol = new SimpleIntegerProperty(movieId);
		this.sessionIdCol = new SimpleIntegerProperty(sessionId);
		this.moviePosterlstview = new SimpleStringProperty(moviePoster);
		this.movieNamelbl = new SimpleStringProperty(movieName);
		this.movieRlseDatelbl = new SimpleStringProperty(movieReleaseDate);
		this.movieDuralbl = new SimpleStringProperty(movieDuration);
		this.movieOverlbl = new SimpleStringProperty(movieOverview);
		this.movieRatelbl = new SimpleStringProperty(movieRate);
		this.langagelbl = new SimpleStringProperty(codeLanguage);
		this.roomlbl = new SimpleStringProperty(roomNameCol);
		this.roomtypelbl = new SimpleStringProperty(room3DCol);
		this.startdatemslbl = new SimpleStringProperty(startTime);
		this.enddatemslbl = new SimpleStringProperty(endDate);
		

	}



	public Integer getSessionIdCol() {
		return sessionIdCol.get();
	}



	public Integer getMovieIdCol() {
		return movieIdCol.get();
	}



	/**
	 * @return the moviePosterImg
	 */
	public String getMoviePosterlstview() {
		return moviePosterlstview.get();
	}



	/**
	 * @param moviePosterImg the moviePosterImg to set
	 */
	public void setMoviePosterImg(String moviePoster) {
		moviePosterlstview.set(moviePoster);
	}



	/**
	 * @return the movieNamelbl
	 */
	public String getMovieNamelbl() {
		return movieNamelbl.get();
	}



	/**
	 * @param movieNamelbl the movieNamelbl to set
	 */
	public void setMovieNamelbl(String movieName) {
		movieNamelbl.set(movieName);
	}



	/**
	 * @return the movieRlseDatelbl
	 */
	public String getMovieRlseDatelbl() {
		return movieRlseDatelbl.get();
	}



	/**
	 * @param movieRlseDatelbl the movieRlseDatelbl to set
	 */
	public void setMovieRlseDatelbl(String movieReleaseDate) {
		movieRlseDatelbl.set(movieReleaseDate);
	}



	/**
	 * @return the movieDuralbl
	 */
	public String getMovieDuralbl() {
		return movieDuralbl.get();
	}



	/**
	 * @param movieDuralbl the movieDuralbl to set
	 */
	public void setMovieDuralbl(String movieDuration) {
		movieDuralbl.set(movieDuration);
	}
	
	
	/**
	 * @return the movieOverlbl
	 */
	public String getMovieOverlbl() {
		return movieOverlbl.get();
		
	}
	
	/**
	 * @param movieOverlbl the movieOverlbl to set
	 */
	public void setMovieOverlbl(String movieOverview) {
		movieOverlbl.set(movieOverview);
	}



	/**
	 * @return the movieRatelbl
	 */
	public String getMovieRatelbl() {
		return movieRatelbl.get();
	}


	/**
	 * @param movieRatelbl the movieRatelbl to set
	 */
	public void setMovieRatelbl(String movieRate) {
		movieRatelbl.set(movieRate);
	}


	/**
	 * @return the roomlbl
	 */
	public String getRoomlbl() {
		return roomlbl.get();
	}
	
	public void setRoomlbl(String roomNameCol) {
		roomlbl.set(roomNameCol);
	}


	/**
	 * @return the roomtypelbl
	 */
	public String getRoomtypelbl() {
		return roomtypelbl.get();
	}
	
	public void setRoomtypelbl(String room3DCol) {
		roomtypelbl.set(room3DCol);
	}


	/**
	 * @return the startdatemslbl
	 */
	public String getStartdatemslbl() {
		return startdatemslbl.get();
	}
	
	public void setStartdatemslbl(String startTime) {
		startdatemslbl.set(startTime);
	}


	/**
	 * @return the enddatemslbl
	 */
	public String getEnddatemslbl() {
		return enddatemslbl.get();
	}
	
	public void setEnddatemslbl(String endDate) {
		enddatemslbl.set(endDate);
	}


	/**
	 * @return the langagelbl
	 */
	public String getLangagelbl() {
		return langagelbl.get();
	}
	
	public void setLangagelbl(String codeLanguage) {
		langagelbl.set(codeLanguage);
	}


}
