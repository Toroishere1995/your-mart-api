package com.learning.apiyourmart.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.learning.apiyourmart.dao.CategoryInformationDao;
import com.learning.apiyourmart.dto.CategoryInformationCredentialsDto;
import com.learning.apiyourmart.dto.PaginationData;
import com.learning.apiyourmart.dto.ResponseDataCredentialsDto;
import com.learning.apiyourmart.entity.CategoryInformation;
import com.learning.apiyourmart.service.IYourMartService;

/**
 * Service Class to handle category related business.
 * @author ayushsaxena
 *
 */
public class CategoryService implements IYourMartService {

	private CategoryInformationDao categoryInformationDao;

	public CategoryService() {
		// TODO Auto-generated constructor stub
		categoryInformationDao = new CategoryInformationDao();
	}

	@Override
	public String updateEntity(int id, Object entity) {
		// TODO Auto-generated method stub
		CategoryInformationCredentialsDto categoryDto=(CategoryInformationCredentialsDto)entity;
		CategoryInformation updatedCategory=categoryDto.convertToEntity();
		int val=categoryInformationDao.editCategoryInformation(id, updatedCategory);
		return val!=0?"updated":"error";
	}

	@Override
	public String deleteEntity(int id) {
		// TODO Auto-generated method stub
		int val=categoryInformationDao.removeCategory(id);
		return val!=0?"deleted":"error";
	}

	@Override
	public Object getEntity(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAllEntities() {
		// TODO Auto-generated method stub
		List<CategoryInformation> result= categoryInformationDao.getCategorizedProducts();
		List<CategoryInformationCredentialsDto> categories=new ArrayList<>();
		Iterator<CategoryInformation> iterator=result.iterator();
		while(iterator.hasNext()){
			CategoryInformation category=iterator.next();
			categories.add(category.convertToDto());
		}
		return categories;
	}

	@Override
	public Object addEntity(Object entity) {
		// TODO Auto-generated method stub
		CategoryInformationCredentialsDto categoryDto=(CategoryInformationCredentialsDto)entity;
		CategoryInformation addedCategory=categoryDto.convertToEntity();
		boolean isAdded=categoryInformationDao.addCategory(addedCategory);
		return isAdded;
	}

	@Override
	public Object getEntitiesUnderEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addEntityUnderEntity(int id, Object entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationData getPaginationInfo() {
		// TODO Auto-generated method stub
		return null;
	}


}
