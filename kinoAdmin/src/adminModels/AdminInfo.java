package adminModels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AdminInfo {
	
	private final SimpleIntegerProperty idCol;
	private final SimpleIntegerProperty privIdCol;
	private final SimpleStringProperty firstnCol;
	private final SimpleStringProperty lastnCol;
	private final SimpleStringProperty emailCol;
	private final SimpleStringProperty telCol;
	private final SimpleStringProperty adressCol;
	private final SimpleStringProperty usernameCol;
	private final SimpleStringProperty passwordCol;
	
	public AdminInfo(Integer accountId, String firstname,
			String lastname, Integer superAdmin, String email,String tel, String adress, String username, String password) {
		this.idCol = new SimpleIntegerProperty(accountId);
		this.privIdCol = new SimpleIntegerProperty(superAdmin);
		this.firstnCol =new SimpleStringProperty(firstname);
		this.lastnCol = new SimpleStringProperty(lastname);
		this.emailCol = new SimpleStringProperty(email);
		this.telCol = new SimpleStringProperty(tel);
		this.adressCol = new SimpleStringProperty(adress);
		this.usernameCol = new SimpleStringProperty(username);
		this.passwordCol = new SimpleStringProperty(password);
	}

	public Integer getIdCol() {
		return idCol.get();
	}
	
	public void setIdCol(Integer accountId){
		idCol.set(accountId);
	}

	public Integer getPrivIdCol() {
		return privIdCol.get();
	}
	
	public void setPrivIdCol(Integer superAdmin){
		privIdCol.set(superAdmin);
	}

	public String getFirstnCol() {
		return firstnCol.get();
	}
	
	public void setFirstnCol(String firstname){
		firstnCol.set(firstname);
	}

	public String getLastnCol() {
		return lastnCol.get();
	}
	
	public void setLastnCol(String lastname){
		lastnCol.set(lastname);
	}

	public String getEmailCol() {
		return emailCol.get();
	}
	
	public void setEmailCol(String email){
		emailCol.set(email);
	}

	public String getTelCol() {
		return telCol.get();
	}
	
	public void setTelCol(String tel){
		telCol.set(tel);
	}

	public String getAdressCol() {
		return adressCol.get();
	}
	
	public void setAdressCol(String adress){
		adressCol.set(adress);
	}
	
	public String getUsernameCol() {
		return usernameCol.get();
	}
	
	public void setUsernameCol(String username){
		usernameCol.set(username);
	}

	public String getPasswordCol() {
		return passwordCol.get();
	}
	
	public void setPasswordCol(String password){
		passwordCol.set(password);
	}

	//Méthode 2
	/*
	public SimpleIntegerProperty accountId = new SimpleIntegerProperty();
	public SimpleStringProperty firstname = new SimpleStringProperty();
	public SimpleStringProperty lastname = new SimpleStringProperty();
	public SimpleIntegerProperty superAdmin = new SimpleIntegerProperty();
	public SimpleStringProperty email = new SimpleStringProperty();
	public SimpleStringProperty tel = new SimpleStringProperty();
	public SimpleStringProperty adress = new SimpleStringProperty();
	
	public AdminInfo(){
		
	}
	public Integer getIdCol() {
		return accountId.get();
	}

	public Integer getPrivIdCol() {
		return superAdmin.get();
	}

	public String getFirstnCol() {
		return firstname.get();
	}

	public String getLastnCol() {
		return lastname.get();
	}

	public String getEmailCol() {
		return email.get();
	}
	
	public String getTelCol() {
		return tel.get();
	}

	public String getAdressCol() {
		return adress.get();
	}*/
	
}
