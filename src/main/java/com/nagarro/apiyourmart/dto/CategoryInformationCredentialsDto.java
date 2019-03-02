package com.nagarro.apiyourmart.dto;

import com.nagarro.apiyourmart.entity.CategoryInformation;

/**
 * Class which is Data Transfer Object which holds details of category.
 * 
 * @author ayushsaxena
 *
 */
public class CategoryInformationCredentialsDto {
	private int categoryId;

	private String categoryName;

	private int categoryProductsCount;

	public int getCategoryId() {
		return categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getCategoryProductsCount() {
		return categoryProductsCount;
	}

	public void setCategoryProductsCount(int categoryProductsCount) {
		this.categoryProductsCount = categoryProductsCount;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public CategoryInformation convertToEntity() {
		
		// TODO Auto-generated method stub
		CategoryInformation categoryInformation=new CategoryInformation();
		categoryInformation.setCategoryId(this.getCategoryId());
		categoryInformation.setCategoryName(this.getCategoryName());
		categoryInformation.setCategoryProductsCount(this.getCategoryProductsCount());
		return categoryInformation;
	}

}
