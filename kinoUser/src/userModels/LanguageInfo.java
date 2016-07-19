package userModels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class LanguageInfo {

	private final SimpleIntegerProperty languageIdCol;
	private final SimpleStringProperty lblLanguageCol;
	private final SimpleStringProperty codeLanguageCol;
	
	public LanguageInfo(Integer languageIdCol,
			String lblLanguageCol,
			String codeLanguageCol) {
		// TODO Auto-generated constructor stub.
		super();
		this.languageIdCol = new SimpleIntegerProperty(languageIdCol);
		this.lblLanguageCol = new SimpleStringProperty(lblLanguageCol);
		this.codeLanguageCol = new SimpleStringProperty(codeLanguageCol);
	}
	
	public Integer getLanguageIdCol() {
		return languageIdCol.get();
	}
	
	public String getLblLanguageCol() {
		return lblLanguageCol.get();
	}
	
	public String getCodeLanguageCol() {
		return codeLanguageCol.get();
	}


}
