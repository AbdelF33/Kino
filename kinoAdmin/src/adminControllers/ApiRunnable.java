package adminControllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import adminModels.MovieModel;

public class ApiRunnable implements Runnable{
	
	/**
	 * Example link request : (https://api.themoviedb.org/3/movie/upcoming?api_key=048970521277f0867b866d0beff1837f&language=fr)
	 * url + features + apiKey + language
	 */
	private static String url = "https://api.themoviedb.org/3/movie/";
	private static final String apiKey = "?api_key=048970521277f0867b866d0beff1837f";
	private static final String frLanguage = "&language=fr";
	private String features = "upcoming"; //now_playing / upcoming
	
	/**
	 * Example link poster : (http://image.tmdb.org/t/p/w500/ft43pouxItAGYR3ZbbQEtEl91JQ.jpg)
	 * Url + size + name
	 * 	private static final String posterUrl = "http://image.tmdb.org/t/p";
	 *	private static final String posterSmallSize = "/w300";
	 *	private static final String posterMediumSize = "/w500";
	 *	private String posterName;
	 */

	
    private JSONObject jsonObj;
    private JSONObject jsonObj2;
    private JSONArray jsArray;
    private JSONArray jsArray2;
    private String jsonStr;
    private String jsonStr2;
	private int count = 0;
	private String[] parts ;
	private LinkedHashMap<String, String> movieMap = new LinkedHashMap<>();
	private LinkedHashMap<String, String> genreMap = new LinkedHashMap<>();
	private LinkedHashMap<String, String> castMap = new LinkedHashMap<>();
	private LinkedHashMap<String, String> assosMap = new LinkedHashMap<>();
	private ArrayList<String> delList = new ArrayList<String>();
	
	
	private Tools tool = new Tools();
	private MovieModel movieMdl = new MovieModel();
	
	public ApiRunnable() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run(){
		String urlMovie = null;
		String id = null;
		String idCast = null;
		String title = null;
		String overview = null;
		String releaseDate = null;
		String poster = null;
		String vote = null;
		String name = null;
		String photo = null;
		String firstname = null;
		String lastname = null;
		
		try {
			
			urlMovie = url+features+apiKey+frLanguage;
			jsonStr = IOUtils.toString(new URL(urlMovie));
			jsonObj = (JSONObject) JSONValue.parseWithException(jsonStr);
			jsArray = (JSONArray) jsonObj.get("results");
			
			for (int i = 0; i < jsArray.size(); i++) {
				JSONObject movies = (JSONObject) jsArray.get(i);
				
				id = (movies.get("id") == null ? "" : movies.get("id").toString());
				title = (movies.get("original_title") == null ? "" : movies.get("original_title").toString());
				overview = (movies.get("overview") == null ? "" : movies.get("overview").toString());
				releaseDate = (movies.get("release_date") == null ? "" : movies.get("release_date").toString());
				poster = (movies.get("poster_path") == null ? "" : movies.get("poster_path").toString());
				vote = (movies.get("vote_average") == null ? "" : movies.get("vote_average").toString());
				
				//insert movies in database
				movieMap.put("movieId", id);
				movieMap.put("movieName", title);
				movieMap.put("movieReleaseDate", releaseDate);
				movieMap.put("moviePoster", poster);
				movieMap.put("movieOverview", overview);
				movieMap.put("movieRate", vote);
				movieMdl.addMovieDB(tool.insertQueryDuplicate("movie", movieMap, "movieName"));
				count++;
				
				//insert associated genre with movies
				String genreId = (movies.get("genre_ids") == null ? "" :  movies.get("genre_ids").toString().replace("[", "").replace("]", ""));
				parts = genreId.split(",");
				for (int j = 0; j < parts.length; j++) {
					if (!parts[j].equals("")) {
						genreMap.put("fkmovieId", movies.get("id").toString());
						genreMap.put("fkgenreId", parts[j]);
						movieMdl.addMovieDB(tool.insertQuery("moviegenre", genreMap));
					}
				}
				
				//insert associated casts & crew with movie

				String credits = id+"/credits";
				String urlCast = url+credits+apiKey;
				jsonStr2 = IOUtils.toString(new URL(urlCast));
				jsonObj2 = (JSONObject) JSONValue.parseWithException(jsonStr2);
				jsArray2 = (JSONArray) jsonObj2.get("cast");
				
				for (int k = 0; k < jsArray2.size(); k++) {
					JSONObject casts = (JSONObject) jsArray2.get(k);
					idCast = (casts.get("id") == null ? "" : casts.get("id").toString());
					name = (casts.get("name") == null ? "" : casts.get("name").toString());
					photo = (casts.get("profile_path") == null ? "" : casts.get("profile_path").toString());
					parts = name.split(" ");
					for (int j = 0; j < parts.length; j++) {
						if (j == 0) {
							firstname = parts[0];
						}
						if (j == 1) {
							lastname = parts[1];
						}
						if (j == 2) {
							lastname += " "+parts[2];
						}
					}
					castMap.put("castId", idCast);
					castMap.put("firstnameCast", firstname);
					castMap.put("lastnameCast", lastname);
					castMap.put("photoCast", photo);
					movieMdl.addMovieDB(tool.insertQueryDuplicate("cast_tbl", castMap, "lastnameCast"));
					assosMap.put("fkMovieId", id);
					assosMap.put("fkCastId", idCast);
					assosMap.put("fkFunctionId", "1");
					movieMdl.addMovieDB(tool.insertQueryDuplicate("moviecast", assosMap, "fkMovieId"));
				}
				
				jsArray2 = (JSONArray) jsonObj2.get("crew");
				assosMap = new LinkedHashMap<String, String>();
				castMap = new LinkedHashMap<String, String>();
				for (int l = 0; l < jsArray2.size(); l++) {
					JSONObject crews = (JSONObject) jsArray2.get(l);
					if (crews.get("job").equals("Director")) {
						idCast = (crews.get("id") == null ? "" : crews.get("id").toString());
						name = (crews.get("name") == null ? "" : crews.get("name").toString());
						photo = (crews.get("profile_path") == null ? "" : crews.get("profile_path").toString());
						parts = name.split(" ");
						for (int j = 0; j < parts.length; j++) {
							if (j == 0) {
								firstname = parts[0];
							}
							if (j == 1) {
								lastname = parts[1];
							}
							if (j == 2) {
								lastname += " "+parts[2];
							}
						}
						castMap.put("castId", idCast);
						castMap.put("firstnameCast", firstname);
						castMap.put("lastnameCast", lastname);
						castMap.put("photoCast", photo);
						movieMdl.addMovieDB(tool.insertQueryDuplicate("cast_tbl", castMap, "lastnameCast"));
						assosMap.put("fkMovieId", id);
						assosMap.put("fkCastId", idCast);
						assosMap.put("fkFunctionId", "2");
						movieMdl.addMovieDB(tool.insertQueryDuplicate("moviecast", assosMap, "fkMovieId"));						
					}
				}
				
			}
			
			//Supprimer les doublons
			delList.add("fkMovieId");
			delList.add("fkCastId");
			delList.add("fkFunctionId");
			movieMdl.addMovieDB(tool.deleteDouble("moviecast", delList));
			
			delList = new ArrayList<String>();
			delList.add("fkmovieId");
			delList.add("fkGenreId");
			movieMdl.addMovieDB(tool.deleteDouble("moviegenre", delList));
			
			//tool.alertDialog(AlertType.INFORMATION, "API insert", "Données ajoutées avec succès !\n"+ count +" film(s) ajouté(s)", null);
			
		} catch (Exception e) {
			e.printStackTrace();
			//tool.alertDialog(AlertType.ERROR, "API insert", "Une erreur s'est produite lors de la récupération des données !", null);
			return;
		}
		
	}

}
