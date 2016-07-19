package adminModels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RoomInfo {

	private final SimpleIntegerProperty roomNumberCol; 
	private final SimpleStringProperty roomNameCol;	
	private final SimpleIntegerProperty room3DCol;
	private final SimpleIntegerProperty roomCapacityCol;
	
	public RoomInfo(Integer roomNumberCol,
			String roomNameCol, Integer room3dCol,
			Integer roomCapacityCol) {
		super();
		this.roomNumberCol = new SimpleIntegerProperty(roomNumberCol);
		this.roomNameCol = new SimpleStringProperty(roomNameCol);
		room3DCol = new SimpleIntegerProperty(room3dCol);
		this.roomCapacityCol = new SimpleIntegerProperty(roomCapacityCol);
	}
	
	public Integer getRoomNumberCol() {
		return roomNumberCol.get();
	}
	
	public String getRoomNameCol() {
		return roomNameCol.get();
	}
	
	public Integer getRoom3DCol() {
		return room3DCol.get();
	}
	
	public Integer getRoomCapacityCol() {
		return roomCapacityCol.get();
	}

	
}
