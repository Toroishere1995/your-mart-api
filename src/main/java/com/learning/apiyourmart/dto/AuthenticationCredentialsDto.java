package com.learning.apiyourmart.dto;

/**
 * Class which is Data Transfer Object which holds details of user trying to log
 * in.
 * 
 * @author ayushsaxena
 *
 */
public class AuthenticationCredentialsDto {

	private String userName;

	private String userPassword;

	//If admin set as true or false.
	private String admin;

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
