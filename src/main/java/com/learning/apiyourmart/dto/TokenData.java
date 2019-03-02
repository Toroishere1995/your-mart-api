package com.learning.apiyourmart.dto;

import java.util.Date;

/**
 * Class which is Data Transfer Object which holds token. 
 * 
 * @author ayushsaxena
 *
 */
public class TokenData {
	private String token;
	private Date expiryDate;

	public Date getExpiryDate() {
		return expiryDate;
	}

	public String getToken() {
		return token;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
