package com.learning.apiyourmart.service.impl;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.learning.apiyourmart.dao.AuthenticationDao;
import com.learning.apiyourmart.dto.AuthenticationCredentialsDto;
import com.learning.apiyourmart.dto.ResponseDataCredentialsDto;
import com.learning.apiyourmart.dto.SellerInformationCredentialsDto;
import com.learning.apiyourmart.dto.UserDto;
import com.learning.apiyourmart.entity.SellerInformation;
import com.learning.apiyourmart.utils.ResponseWrapper;

import sun.misc.BASE64Encoder;

/**
 * Service Class to handle authentication related business.
 * 
 * @author ayushsaxena
 *
 */
public class AuthenticationService {

	private AuthenticationDao authenticationDao;

	/**
	 * Constructor
	 */
	public AuthenticationService() {
		// TODO Auto-generated constructor stub
		authenticationDao = new AuthenticationDao();
	}

	/**
	 * Method to verify login details
	 * 
	 * @param authDetails
	 * @return
	 */
	public String verifyLoginDetails(AuthenticationCredentialsDto authDetails) {
		boolean isValid;
		String code;

		if (authDetails.getAdmin().equalsIgnoreCase("true")) {
			isValid = authenticationDao.checkAdminCredentials(authDetails);
			code = "-a-";
		} else {
			isValid = authenticationDao.checkSellerCredentials(authDetails);
			code = "-s-";
		}

		if (isValid) {
			String authString = authDetails.getUserName() + ":" + authDetails.getUserPassword();
			String token = new BASE64Encoder().encode(authString.getBytes());
			System.out.println("Base64 encoded auth string: " + token);
			return token;
		}
		return "error";
	}

	/**
	 * Method to authenticate if the authorization header value is valid.
	 * 
	 * @param authCredentials
	 * @return
	 */
	public boolean authenticate(String authCredentials) {
		// TODO Auto-generated method stub

		if (null == authCredentials)
			return false;
		System.out.println("auth : " + authCredentials);
		final String encodedUserPassword = authCredentials.replaceFirst("" + "", "");
		String usernameAndPassword = null;
		try {
			System.out.println("encode : " + encodedUserPassword);
			byte[] decodedBytes = Base64.getDecoder().decode(encodedUserPassword);
			usernameAndPassword = new String(decodedBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();
		AuthenticationCredentialsDto credentials = new AuthenticationCredentialsDto();
		credentials.setUserName(username);
		credentials.setUserPassword(password);
		// authenticationDao.checkAdminCredentials(credentials);
		boolean authenticationStatus = authenticationDao.checkAdminCredentials(credentials);
		boolean autheticationStatusSeller = authenticationDao.checkSellerCredentials(credentials);
		return authenticationStatus | autheticationStatusSeller;
	}

	/**
	 * Method that sends token and user info.
	 * @param credentials
	 * @return
	 */
	public ResponseDataCredentialsDto getResponse(AuthenticationCredentialsDto credentials) {
		// TODO Auto-generated method stub

		String token = this.verifyLoginDetails(credentials);
		ResponseDataCredentialsDto responseDto;
		if (token.equalsIgnoreCase("error")) {
			responseDto = ResponseWrapper.wrapObjectToResponse(null, token, "401", null);
		} else {
			UserDto userDto = new UserDto();
			if (!credentials.getAdmin().equalsIgnoreCase("true")) {
				SellerInformationCredentialsDto sellerDto = authenticationDao.getSellerInformation(credentials)
						.convertToDto();
				userDto.setUserInfo(sellerDto);
			}
			userDto.setToken(token);
			responseDto = ResponseWrapper.wrapObjectToResponse(userDto, "Success", "201", null);
		}

		return responseDto;
	}
	/**
	 * Method that sends token and user info.
	 * @param authCredentials
	 * @return
	 */
	public ResponseDataCredentialsDto getLoggedUser(String authCredentials) {
		System.out.println("cred" + authCredentials);
		if (authCredentials == null) {
			return ResponseWrapper.wrapObjectToResponse(null, "Null Value", "400", null);
		}
		final String encodedUserPassword = authCredentials.replaceFirst("Basic" + " ", "");
		String usernameAndPassword = null;
		try {
			byte[] decodedBytes = Base64.getDecoder().decode(encodedUserPassword);
			usernameAndPassword = new String(decodedBytes, "UTF-8");

		} catch (IOException e) {
			e.printStackTrace();
		}
		final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();
		AuthenticationCredentialsDto credentials = new AuthenticationCredentialsDto();
		credentials.setUserName(username);
		credentials.setUserPassword(password);
		credentials.setAdmin("false");

		return getResponse(credentials);

	}

}