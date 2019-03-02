package com.nagarro.apiyourmart.dto;

import com.nagarro.apiyourmart.entity.ProductImagesInformation;

/**
 * Class which is Data Transfer Object which holds details of product images.
 * 
 * @author ayushsaxena
 *
 */
public class ProductImagesCredentialsDto {
	private int imageId;

	private String imageName;

	private String image;

	public String getImage() {
		return image;
	}

	public int getImageId() {
		return imageId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public ProductImagesInformation convertToEntity() {
		// TODO Auto-generated method stub
		ProductImagesInformation entity=new ProductImagesInformation();
		entity.setImage(this.getImage());
		entity.setImageName(this.getImageName());
		entity.setImageId(this.getImageId());
		return entity;
	}

}
