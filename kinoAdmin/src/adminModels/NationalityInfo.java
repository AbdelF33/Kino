package adminModels;

public class NationalityInfo {
	private final Integer nationIdCol;
	private final String nationlblFrCol;
		

	public NationalityInfo(int nationalityId, String lblNationalityFr) {
		// TODO Auto-generated constructor stub
		this.nationIdCol = new Integer(nationalityId);
		this.nationlblFrCol = new String(lblNationalityFr);
		
	}


	public Integer getNationIdCol() {
		return nationIdCol;
	}


	public String getNationlblFrCol() {
		return nationlblFrCol;
	}

}
	

