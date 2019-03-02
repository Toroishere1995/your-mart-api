package com.learning.apiyourmart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity for Login
 * 
 * @author ayushsaxena
 *
 */
@Entity
@Table(name = "admindb")
public class LoginCredentials {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ADMIN_ID")
	private int adminId;

	@Column(name = "ADMIN_USERNAME")
	private String adminUsername;

	@Column(name = "ADMIN_PASSWORD")
	private String adminPassword;

	public int getAdminId() {
		return adminId;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public String getAdminUsername() {
		return adminUsername;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}
}
