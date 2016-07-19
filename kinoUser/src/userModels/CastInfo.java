package userModels;

public class CastInfo {
	private final Integer castIdCol;
	private final String castFirstnameCol;
	private final String castLastnameCol;

		

	public CastInfo(int castId, String castFirstname, String castLastname) {
		// TODO Auto-generated constructor stub.
		this.castIdCol = new Integer(castId);
		this.castFirstnameCol = new String(castFirstname);		
		this.castLastnameCol = new String(castLastname);
	}



	public Integer getCastIdCol() {
		return castIdCol;
	}



	public String getCastFirstnameCol() {
		return castFirstnameCol.toString();
	}



	public String getCastLastnameCol() {
		return castLastnameCol;
	}



}