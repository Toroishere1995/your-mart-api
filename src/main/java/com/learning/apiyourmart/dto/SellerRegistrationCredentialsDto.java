package com.learning.apiyourmart.dto;

import java.sql.Date;

import com.learning.apiyourmart.entity.SellerInformation;

/**
 * Class which is Data Transfer Object which holds details of registration of a
 * seller.
 * 
 * 
 * @author ayushsaxena
 *
 */
public class SellerRegistrationCredentialsDto {

	private String companyName;

	private Date registrationDate;

	private String ownerName;

	private String companyAddress;

	private String sellerEmail;

	private String sellerTelephone;

	private String sellerGst;

	private String sellerPassword;

	private String sellerStatus;

	public String getCompanyAddress() {
		return companyAddress;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getOwnerName() {
		return ownerName;
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

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public void setSellerGst(String sellerGst) {
		this.sellerGst = sellerGst;
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

	public SellerInformation convertToSellerInformation() {
		SellerInformation sellerInformation = new SellerInformation();
		sellerInformation.setCompanyName(this.getCompanyName());
		sellerInformation.setCompanyAddress(this.getCompanyAddress());
		sellerInformation.setOwnerName(this.getOwnerName());
		sellerInformation.setRegistrationDate(this.getRegistrationDate());
		sellerInformation.setSellerEmail(this.getSellerEmail());
		sellerInformation.setSellerGst(this.getSellerGst());
		sellerInformation.setSellerPassword(this.getSellerPassword());
		sellerInformation.setSellerStatus(this.getSellerStatus());
		sellerInformation.setSellerTelephone(this.getSellerTelephone());
		return sellerInformation;
	}
}
