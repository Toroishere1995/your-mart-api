package com.nagarro.apiyourmart.entity;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nagarro.apiyourmart.dto.ProductImagesCredentialsDto;
import com.nagarro.apiyourmart.dto.ProductInformationCredentialsDto;

/**
 * Entity for product information.
 * 
 * @author ayushsaxena
 *
 */
@Entity
@Table(name = "productdb")
public class ProductInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRODUCT_ID")
	private int productId;

	@Column(name = "SELLER_PRODUCT_CODE")
	private String sellerProductCode;

	@Column(name = "PRODUCT_NAME")
	private String productName;

	@Column(name = "PRODUCT_SHORT_DESCRIPTION")
	private String productShortDescription;

	@Column(name = "PRODUCT_LONG_DESCRIPTION")
	private String productLongDescription;

	@Column(name = "PRODUCT_DIMENSIONS")
	private String productDimensions;

	@Column(name = "PRODUCT_CATEGORY")
	private String productCategory;

	@Column(name = "PRODUCT_MRP")
	private double productMrp;

	@Column(name = "PRODUCT_SSP")
	private double productSsp;

	@Column(name = "PRODUCT_YMP")
	private double productYmp;

	@Column(name = "PRODUCT_PRIMARY_IMAGE")
	private String productPrimaryImage;

	@Column(name = "PRODUCT_STATUS")
	private String productStatus;

	@Column(name = "PRODUCT_COMMENT")
	private String productComment;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "productInformation")
	private Collection<ProductImagesInformation> productImages = new ArrayList<>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SELLER_ID")
	private SellerInformation sellerInformation;

	@Column(name = "REGISTRATION_DATE_PRODUCT")
	private Date productRegisteredDate;

	public ProductInformationCredentialsDto convertToDto() {
		// TODO Auto-generated method stub

		ProductInformationCredentialsDto productInformatioDto = new ProductInformationCredentialsDto();
		List<ProductImagesCredentialsDto> imageInformationDto = new ArrayList<>();
		Iterator<ProductImagesInformation> iterator = this.getProductImages().iterator();
		while (iterator.hasNext()) {
			imageInformationDto.add(iterator.next().convertToDTO());
		}
		productInformatioDto.setSellerProductCode(this.getSellerProductCode());
		productInformatioDto.setProductCategory(this.getProductCategory());
		productInformatioDto.setProductComment(this.getProductComment());
		productInformatioDto.setProductDimensions(this.getProductDimensions());
		productInformatioDto.setProductId(this.getProductId());

		productInformatioDto.setProductImages(imageInformationDto);
		productInformatioDto.setProductLongDescription(this.getProductLongDescription());
		productInformatioDto.setProductShortDescription(this.getProductShortDescription());
		productInformatioDto.setProductMrp(this.getProductMrp());
		productInformatioDto.setProductSsp(this.getProductSsp());
		productInformatioDto.setProductYmp(this.getProductYmp());
		productInformatioDto.setProductName(this.getProductName());
		productInformatioDto.setProductPrimaryImage(this.getProductPrimaryImage());
		productInformatioDto.setProductStatus(this.getProductStatus());
		return productInformatioDto;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public String getProductComment() {
		return productComment;
	}

	public String getProductDimensions() {
		return productDimensions;
	}

	public int getProductId() {
		return productId;
	}

	public Collection<ProductImagesInformation> getProductImages() {
		return productImages;
	}

	public String getProductLongDescription() {
		return productLongDescription;
	}

	public double getProductMrp() {
		return productMrp;
	}

	public String getProductName() {
		return productName;
	}

	public String getProductPrimaryImage() {
		return productPrimaryImage;
	}

	public Date getProductRegisteredDate() {
		return productRegisteredDate;
	}

	public String getProductShortDescription() {
		return productShortDescription;
	}

	public double getProductSsp() {
		return productSsp;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public double getProductYmp() {
		return productYmp;
	}

	@JsonIgnore
	public SellerInformation getSellerInformation() {
		return sellerInformation;
	}

	public String getSellerProductCode() {
		return sellerProductCode;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public void setProductComment(String productComment) {
		this.productComment = productComment;
	}

	public void setProductDimensions(String productDimensions) {
		this.productDimensions = productDimensions;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public void setProductImages(Collection<ProductImagesInformation> productImages) {
		this.productImages = productImages;
	}

	public void setProductLongDescription(String productLongDescription) {
		this.productLongDescription = productLongDescription;
	}

	public void setProductMrp(double productMrp) {
		this.productMrp = productMrp;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setProductPrimaryImage(String productPrimaryImage) {
		this.productPrimaryImage = productPrimaryImage;
	}

	public void setProductRegisteredDate(Date productRegisteredDate) {
		this.productRegisteredDate = productRegisteredDate;
	}

	public void setProductShortDescription(String productShortDescription) {
		this.productShortDescription = productShortDescription;
	}

	public void setProductSsp(double productSsp) {
		this.productSsp = productSsp;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public void setProductYmp(double productYmp) {
		this.productYmp = productYmp;
	}

	public void setSellerInformation(SellerInformation sellerInformation) {
		this.sellerInformation = sellerInformation;
	}

	public void setSellerProductCode(String sellerProductCode) {
		this.sellerProductCode = sellerProductCode;
	}
}
