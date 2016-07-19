package adminModels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SessionInfo {

	private final SimpleIntegerProperty sessionIdCol;
	private final SimpleStringProperty movieNameCol;
	private final SimpleStringProperty startTimeCol;
	private final SimpleStringProperty endDateCol;
	private final SimpleIntegerProperty nbResaCol;
	private final SimpleIntegerProperty showingCol;
	private final SimpleIntegerProperty languageIdCol;
	private final SimpleIntegerProperty roomIdCol;
	
	
	public SessionInfo(Integer sessionId, String movieName,
			String startTime, String endDate,
			Integer nbResa, Integer showing, Integer languageId, Integer roomId) {
		
		
		this.sessionIdCol = new SimpleIntegerProperty(sessionId);
		this.movieNameCol = new SimpleStringProperty(movieName);
		this.startTimeCol = new SimpleStringProperty(startTime);
		this.endDateCol = new SimpleStringProperty(endDate);
		this.nbResaCol = new SimpleIntegerProperty(nbResa);
		this.showingCol = new SimpleIntegerProperty(showing);
		this.languageIdCol = new SimpleIntegerProperty(languageId);
		this.roomIdCol = new SimpleIntegerProperty(roomId);
	}


	public Integer getLanguageIdCol() {
		return languageIdCol.get();
	}


	public Integer getRoomIdCol() {
		return roomIdCol.get();
	}


	public String getMovieNameCol() {
		return movieNameCol.get();
	}


	public Integer getSessionIdCol() {
		return sessionIdCol.get();
	}
	

	public String getStartTimeCol() {
		return startTimeCol.get();
	}


	public String getEndDateCol() {
		return endDateCol.get();
	}


	public Integer getNbResaCol() {
		return nbResaCol.get();
	}


	public Integer getShowingCol() {
		return showingCol.get();
	}
	
	
	
	

}
