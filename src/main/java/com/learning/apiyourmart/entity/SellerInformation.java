package com.learning.apiyourmart.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learning.apiyourmart.dto.SellerInformationCredentialsDto;

/**
 * Entity for seller information.
 * 
 * @author ayushsaxena
 *
 */
@Entity
@Table(name = "sellerdb")
public class SellerInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SELLER_ID")
	private int sellerId;
	@Column(name = "COMPANY_NAME")
	private String companyName;

	@Column(name = "REGISTRATION_DATE")
	private Date registrationDate;

	@Column(name = "OWNER_NAME")
	private String ownerName;
	@Column(name = "COMPANY_ADDRESS")
	private String companyAddress;
	@Column(name = "SELLER_EMAIL")
	private String sellerEmail;
	@Column(name = "SELLER_TELEPHONE")
	private String sellerTelephone;
	@Column(name = "COMPANY_GST")
	private String sellerGst;
	@Column(name = "SELLER_PASSWORD")
	private String sellerPassword;
	@Column(name = "SELLER_STATUS")
	private String sellerStatus;

	@Column(name = "SELLER_UUID")
	private String sellerUid;

	public String getSellerUid() {
		return sellerUid;
	}

	public void setSellerUid(String sellerUid) {
		this.sellerUid = sellerUid;
	}

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "sellerInformation")
	private Collection<ProductInformation> products = new ArrayList<>();

	public String getCompanyAddress() {
		return companyAddress;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public Collection<ProductInformation> getProducts() {
		return products;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public String getSellerGst() {
		return sellerGst;
	}

	public int getSellerId() {
		return sellerId;
	}

	public String getSellerPassword() {
		return sellerPassword;
	}

	public String getSellerStatus() {
		return sellerStatus;
	}

	public String getSellerTelephone() {
		return sellerTelephone;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public void setProducts(Collection<ProductInformation> products) {
		this.products = products;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public void setSellerGst(String sellerGst) {
		this.sellerGst = sellerGst;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public void setSellerPassword(String sellerPassword) {
		this.sellerPassword = sellerPassword;
	}

	public void setSellerStatus(String sellerStatus) {
		this.sellerStatus = sellerStatus;
	}

	public void setSellerTelephone(String sellerTelephone) {
		this.sellerTelephone = sellerTelephone;
	}

	public SellerInformationCredentialsDto convertToDto() {
		SellerInformationCredentialsDto credentialsDto = new SellerInformationCredentialsDto();
		credentialsDto.setCompanyAddress(this.getCompanyAddress());
		credentialsDto.setCompanyName(this.getCompanyName());
		credentialsDto.setOwnerName(this.getOwnerName());
		credentialsDto.setRegistrationDate(this.getRegistrationDate());
		credentialsDto.setSellerEmail(this.getSellerEmail());
		credentialsDto.setSellerGst(this.getSellerGst());
		credentialsDto.setSellerId(this.getSellerId());
		credentialsDto.setSellerStatus(this.getSellerStatus());
		credentialsDto.setSellerTelephone(this.getSellerTelephone());
		credentialsDto.setSellerUid(this.getSellerUid());
		return credentialsDto;
	}
}
