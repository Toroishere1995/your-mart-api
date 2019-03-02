package com.learning.apiyourmart.entity;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.learning.apiyourmart.dto.ProductImagesCredentialsDto;

/**
 * Entity for product images.
 * 
 * @author ayushsaxena
 *
 */
@Entity
@Table(name = "productimages")
public class ProductImagesInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IMAGE_ID")
	private int imageId;

	@Column(name = "IMAGE_NAME")
	private String imageName;

	@Column(name = "IMAGE")
	private String image;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PRODUCT_ID")
	@JsonBackReference
	private ProductInformation productInformation;

	public String getImage() {
		return image;
	}

	public int getImageId() {
		return imageId;
	}

	public String getImageName() {
		return imageName;
	}

	public ProductInformation getProductInformation() {
		return productInformation;
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

	public void setProductInformation(ProductInformation productInformation) {
		this.productInformation = productInformation;
	}

	public ProductImagesCredentialsDto convertToDTO() {
		// TODO Auto-generated method stub
		ProductImagesCredentialsDto imageDto = new ProductImagesCredentialsDto();
		imageDto.setImage(this.getImage());
		imageDto.setImageName(this.getImageName());
		imageDto.setImageId(this.getImageId());
		return imageDto;
	}
}
