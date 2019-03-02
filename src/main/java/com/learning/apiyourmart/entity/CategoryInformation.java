package com.learning.apiyourmart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.learning.apiyourmart.dto.CategoryInformationCredentialsDto;

/**
 * Entity for Category.
 * @author ayushsaxena
 *
 */
@Entity
@Table(name = "categorydb")
public class CategoryInformation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CATEGORY_ID")
	private int categoryId;

	@Column(name = "CATEGORY_NAME")
	private String categoryName;

	@Column(name = "CATEGORY_PRODUCTS")
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

	public CategoryInformationCredentialsDto convertToDto() {

		// TODO Auto-generated method stub
		CategoryInformationCredentialsDto categoryInformation = new CategoryInformationCredentialsDto();
		categoryInformation.setCategoryId(this.getCategoryId());
		categoryInformation.setCategoryName(this.getCategoryName());
		categoryInformation.setCategoryProductsCount(this.getCategoryProductsCount());
		return categoryInformation;
	}
}
