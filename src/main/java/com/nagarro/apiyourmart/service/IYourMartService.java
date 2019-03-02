package com.nagarro.apiyourmart.service;

import com.nagarro.apiyourmart.dto.PaginationData;
import com.nagarro.apiyourmart.dto.ResponseDataCredentialsDto;

/**
 * Interface for mart service.
 * 
 * @author ayushsaxena
 *
 */
public interface IYourMartService {
	/**
	 * Method to update entity.
	 * 
	 * @param id
	 * @param entity
	 * @return
	 */
	String updateEntity(int id, Object entity);

	/**
	 * Method to delete entity.
	 * 
	 * @param id
	 * @return
	 */
	String deleteEntity(int id);

	/**
	 * Method to fetch entity.
	 * 
	 * @param id
	 * @return
	 */
	Object getEntity(int id);

	/**
	 * Method to fetch entities.
	 * 
	 * @return
	 */
	Object getAllEntities();

	/**
	 * Method to add entity.
	 * 
	 * @param entity
	 * @return
	 */
	Object addEntity(Object entity);

	/**
	 * Method to fetch entities under entities.
	 * 
	 * @return
	 */
	Object getEntitiesUnderEntity();

	/**
	 * Method to add entity under entity.
	 * 
	 * @param id
	 * @param entity
	 * @return
	 */
	String addEntityUnderEntity(int id, Object entity);

	/**
	 * Method to set pagination data.
	 * 
	 * @return
	 */
	PaginationData getPaginationInfo();
}
