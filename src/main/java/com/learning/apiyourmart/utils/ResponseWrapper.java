package com.learning.apiyourmart.utils;

import com.learning.apiyourmart.constants.Constants;
import com.learning.apiyourmart.dto.PaginationData;
import com.learning.apiyourmart.dto.ResponseDataCredentialsDto;
import com.learning.apiyourmart.dto.StatusData;

/**
 * Class that wraps the objects into a single response class.
 * 
 * @author ayushsaxena
 *
 */
public class ResponseWrapper {

	/**
	 * Method to wrap
	 * 
	 * @param data
	 * @param statusMessage
	 * @param code
	 * @param pageInfo
	 * @return
	 */
	public static ResponseDataCredentialsDto wrapObjectToResponse(Object data, String statusMessage, String code,
			PaginationData pageInfo) {
		ResponseDataCredentialsDto response = new ResponseDataCredentialsDto();
		response.setData(data);
		StatusData statusData = new StatusData();
		statusData.setStatusMessage(statusMessage);
		statusData.setStatusCode(code);
		statusData.setErrorMessage(Constants.CODES_HASH.get(code));
		statusData.setDescription(Constants.CODES_MSG_HASH.get(statusData.getErrorMessage()));
		response.setStatus(statusData);
		response.setPaginationDetails(pageInfo);
		return response;
	}
}
