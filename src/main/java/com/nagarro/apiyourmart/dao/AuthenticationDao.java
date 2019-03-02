package com.nagarro.apiyourmart.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mysql.jdbc.log.Log;
import com.nagarro.apiyourmart.dto.AuthenticationCredentialsDto;
import com.nagarro.apiyourmart.entity.LoginCredentials;
import com.nagarro.apiyourmart.entity.SellerInformation;
import com.nagarro.apiyourmart.utils.HibernateUtil;

/**
 * Class which handles queries and interact with database. This class handles
 * all authentication work.
 * 
 * @author ayushsaxena
 *
 */
public class AuthenticationDao {

	/**
	 * Method to check if admin is valid
	 * 
	 * @param credentials
	 * @return
	 */
	public boolean checkAdminCredentials(AuthenticationCredentialsDto credentials) {
		// TODO Auto-generated method stub
		boolean adminCredentialsExist = false;

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		adminCredentialsExist = (checkEmailAndPasswordForAdmin(session, credentials.getUserName(),
				credentials.getUserPassword()));
		tx.commit();
		session.close();
		return adminCredentialsExist;
	}

	/**
	 * Method that interacts with database.
	 * 
	 * @param session
	 * @param adminUsername
	 * @param adminPassword
	 * @return
	 */
	private boolean checkEmailAndPasswordForAdmin(Session session, String adminUsername, String adminPassword) {
		LoginCredentials admin = null;
		String jpqlQuery = "from LoginCredentials e where e.adminUsername = :name and e.adminPassword = :pass";
		try {
			admin = (LoginCredentials) session.createQuery(jpqlQuery).setParameter("name", adminUsername)
					.setParameter("pass", adminPassword).getSingleResult();
		} catch (NoResultException nre) {

		}
		if (admin == null)
			return false;
		return true;

	}

	/**
	 * Method to check if seller is valid.
	 * 
	 * @param credentials
	 * @return
	 */
	public boolean checkSellerCredentials(AuthenticationCredentialsDto credentials) {
		// TODO Auto-generated method stub
		boolean sellerCredentialsExist = false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		sellerCredentialsExist = (checkEmailAndPasswordForSeller(session, credentials.getUserName(),
				credentials.getUserPassword()));
		tx.commit();
		session.close();
		return sellerCredentialsExist;
	}

	/**
	 * Method to check credentials of seller.
	 * 
	 * @param session
	 * @param userId
	 * @param userPassword
	 * @return
	 */
	private boolean checkEmailAndPasswordForSeller(Session session, String userId, String userPassword) {
		// TODO Auto-generated method stub

		SellerInformation seller = null;
		String jpqlQuery = "from SellerInformation e where e.sellerUid = :id and e.sellerPassword = :pass";
		try {
			seller = (SellerInformation) session.createQuery(jpqlQuery).setParameter("id", userId)
					.setParameter("pass", userPassword).getSingleResult();
		} catch (NoResultException nre) {

		}
		if (seller == null)
			return false;
		return true;
	}

	/**
	 * Method to get seller information ,if it's valid.
	 * 
	 * @param credentialsDto
	 * @return
	 */
	public SellerInformation getSellerInformation(AuthenticationCredentialsDto credentialsDto) {
		SellerInformation seller;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		String jpqlQuery = "from SellerInformation e where e.sellerUid = :id and e.sellerPassword = :pass";
		seller = (SellerInformation) session.createQuery(jpqlQuery).setParameter("id", credentialsDto.getUserName())
				.setParameter("pass", credentialsDto.getUserPassword()).getSingleResult();

		tx.commit();
		session.close();
		return seller;
	}

	/**
	 * Method that returns admin emails for background thread.
	 * 
	 * @return
	 */
	public static List<String> getAdminEmails() {
		// TODO Auto-generated method stub
		List<String> adminEmails = new ArrayList<>();
		String jpqlQuery = "from LoginCredentials e";
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		List<LoginCredentials> adminList = session.createQuery(jpqlQuery).getResultList();
		Iterator<LoginCredentials> iterator = adminList.iterator();
		while (iterator.hasNext()) {
			LoginCredentials admin = iterator.next();
			adminEmails.add(admin.getAdminUsername());
		}

		tx.commit();
		session.close();

		return adminEmails;
	}
}
