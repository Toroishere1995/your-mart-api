package com.nagarro.apiyourmart.dto;

/**
 * Class which is Data Transfer Object which is wrapper class, it holds status,
 * with data , pagination.
 * 
 * @author ayushsaxena
 *
 */
public class ResponseDataCredentialsDto {

	private Object data;

	private StatusData status;

	private PaginationData paginationDetails;

	public Object getData() {
		return data;
	}

	public StatusData getStatus() {
		return status;
	}

	public PaginationData getPaginationDetails() {
		return paginationDetails;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setStatus(StatusData status) {
		this.status = status;
	}

	public void setPaginationDetails(PaginationData paginationDetails) {
		this.paginationDetails = paginationDetails;
	}

}
