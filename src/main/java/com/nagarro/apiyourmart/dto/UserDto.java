package com.nagarro.apiyourmart.dto;

/**
 * Class which is Data Transfer Object which holds details of token and user
 * info.
 * 
 * @author ayushsaxena
 *
 */
public class UserDto {
	private String token;
	private Object userInfo;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Object getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(Object userInfo) {
		this.userInfo = userInfo;
	}
}
