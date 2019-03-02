package com.learning.apiyourmart.dto;

/**
 * Class which is Data Transfer Object which holds details of api hits,
 * including status, error.
 * 
 * @author ayushsaxena
 *
 */
public class StatusData {
	private String statusMessage;
	private String statusCode;

	private String errorMessage;

	private String description;

	public String getDescription() {
		return description;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public void setStatusMessage(String message) {
		this.statusMessage = message;
	}
}
