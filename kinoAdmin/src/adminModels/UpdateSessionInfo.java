package adminModels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class UpdateSessionInfo {


	private final SimpleIntegerProperty sessionIdCol;
	private final SimpleStringProperty movieNameCol;
	private final SimpleStringProperty startTimeCol;
	private final SimpleStringProperty endDateCol;
	private final SimpleIntegerProperty nbResaCol;
	private final SimpleIntegerProperty showingCol;
	private final SimpleIntegerProperty languageIdCol;
	private final SimpleStringProperty codeLanguageCol;
	private final SimpleIntegerProperty roomIdCol;
	private final SimpleStringProperty roomNameCol;
	private final SimpleIntegerProperty room3DCol;
	
	
	public UpdateSessionInfo(Integer sessionId, String movieName,
			String startTime, String endDate,
			Integer nbResa, Integer showing, Integer languageId, 
			String codeLanguage, Integer roomId, String roomName, Integer room3D) {
		
		
		this.sessionIdCol = new SimpleIntegerProperty(sessionId);
		this.movieNameCol = new SimpleStringProperty(movieName);
		this.startTimeCol = new SimpleStringProperty(startTime);
		this.endDateCol = new SimpleStringProperty(endDate);
		this.nbResaCol = new SimpleIntegerProperty(nbResa);
		this.showingCol = new SimpleIntegerProperty(showing);
		this.languageIdCol = new SimpleIntegerProperty(languageId);
		this.codeLanguageCol = new SimpleStringProperty(codeLanguage);
		this.roomIdCol = new SimpleIntegerProperty(roomId);
		this.roomNameCol = new SimpleStringProperty(roomName);
		this.room3DCol = new SimpleIntegerProperty(room3D);
	}
	
	
	public String getCodeLanguageCol() {
		return codeLanguageCol.get();
	}
	
	
	public String getRoomNameCol() {
		return roomNameCol.get();
	}
	
	
	public Integer getRoom3DCol() {
		return room3DCol.get();
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
