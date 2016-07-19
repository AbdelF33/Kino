package userModels;

public class GenreInfo {
	private final Integer genreIdCol;
	private final String genrelblCol;
		

	public GenreInfo(int genreId, String lblGenre) {
		// TODO Auto-generated constructor stub..
		this.genreIdCol = new Integer(genreId);
		this.genrelblCol = new String(lblGenre);
		
	}


	public Integer getGenreIdCol() {
		return genreIdCol;
	}


	public String getGenrelblCol() {
		return genrelblCol;
	}



}
