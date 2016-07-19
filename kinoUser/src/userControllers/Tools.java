package userControllers;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Tools {

	public Tools() {
		// TODO Auto-generated constructor stub
	}
	
	//Requete d'insertion en BDD
    public String insertQuery(String tableName, LinkedHashMap<String, String> dataArray)
    {
    	String sqlQuery = null;
        int cpt = 0;

        sqlQuery = "INSERT INTO " + tableName + " (";
        for (Map.Entry<String, String> entry : dataArray.entrySet())
        {
            sqlQuery += ((cpt == 0 ? "" : ", ")) + entry.getKey();
            cpt = cpt + 1;
        }
        sqlQuery += ") VALUES (";
        cpt = 0;
        for (Map.Entry<String, String> entry : dataArray.entrySet())
        {
            sqlQuery += ((cpt == 0 ? "" : ", ")) + "'" + entry.getValue().replace("'", "\'") + "'";
            cpt = cpt + 1;
        }
        sqlQuery += ")";

        return sqlQuery;
    }
    
	//Requete de modification en BDD
    public String updateQuery(String tableName, String lblIndex, Integer index, LinkedHashMap<String, String> dataArray)
    {
    	String sqlQuery = null;
        int cpt = 0;

        sqlQuery = "UPDATE " + tableName + " SET ";
        for (Map.Entry<String, String> entry : dataArray.entrySet())
        {
            sqlQuery += ((cpt == 0 ? "" : ", ")) + entry.getKey() + " = '" + entry.getValue().replace("'", "\'") + "'";
            cpt = cpt + 1;
        }
        sqlQuery += " WHERE " + lblIndex + " = " + index;

        return sqlQuery;
    }
    
    public void alertDialog(AlertType aType, String title, String content, String headerText){
		Alert alert = new Alert(aType);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(content);

		alert.showAndWait();
    }

}
