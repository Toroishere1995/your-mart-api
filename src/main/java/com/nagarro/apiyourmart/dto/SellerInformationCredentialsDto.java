package com.nagarro.apiyourmart.dto;

import java.sql.Date;

import com.nagarro.apiyourmart.entity.SellerInformation;

/**
 * Class which is Data Transfer Object which holds details of seller. 
 * 
 * @author ayushsaxena
 *
 */
public class SellerInformationCredentialsDto {

	private String sellerUid;

	private int sellerId;

	private String companyName;

	private Date registrationDate;

	private String ownerName;

	private String companyAddress;

	private String sellerEmail;

	private String sellerTelephone;

	private String sellerGst;

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

	public int getSellerId() {
		return sellerId;
	}

	public String getSellerStatus() {
		return sellerStatus;
	}

	public String getSellerTelephone() {
		return sellerTelephone;
	}

	public String getSellerUid() {
		return sellerUid;
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

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public void setSellerStatus(String sellerStatus) {
		this.sellerStatus = sellerStatus;
	}

	public void setSellerTelephone(String sellerTelephone) {
		this.sellerTelephone = sellerTelephone;
	}

	public void setSellerUid(String sellerUid) {
		this.sellerUid = sellerUid;
	}

	public SellerInformation convertToEntity() {
		SellerInformation credentials = new SellerInformation();
		credentials.setCompanyAddress(this.getCompanyAddress());
		credentials.setCompanyName(this.getCompanyName());
		credentials.setOwnerName(this.getOwnerName());
		credentials.setRegistrationDate(this.getRegistrationDate());
		credentials.setSellerEmail(this.getSellerEmail());
		credentials.setSellerGst(this.getSellerGst());
		credentials.setSellerId(this.getSellerId());
		credentials.setSellerStatus(this.getSellerStatus());
		credentials.setSellerTelephone(this.getSellerTelephone());
		credentials.setSellerUid(this.getSellerUid());
		return credentials;
	}
}
