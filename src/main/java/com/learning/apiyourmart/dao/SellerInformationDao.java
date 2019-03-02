package com.learning.apiyourmart.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.fasterxml.uuid.Generators;
import com.learning.apiyourmart.dto.PaginationData;
import com.learning.apiyourmart.dto.SellerInformationCredentialsDto;
import com.learning.apiyourmart.dto.SellerRegistrationCredentialsDto;
import com.learning.apiyourmart.entity.SellerInformation;
import com.learning.apiyourmart.utils.HibernateUtil;

/**
 * Class which handles queries and interact with database. This class handles
 * all seller related work.
 * 
 * @author ayushsaxena
 *
 */
public class SellerInformationDao {

	/**
	 * Method to add seller.
	 * 
	 * @param sellerInformation
	 * @return
	 */
	public SellerInformation addSeller(SellerInformation sellerInformation) {
		// TODO Auto-generated method stub
		// SellerInformation seller =
		// sellerRegistrationCredentials.convertToSellerInformation();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		int id = (int) session.save(sellerInformation);
		sellerInformation.setSellerUid(generateUUID(id, sellerInformation.getOwnerName()));
		session.update(sellerInformation);
		tx.commit();
		session.close();

		return sellerInformation;
	}

	/**
	 * Method to generate UUID.
	 * 
	 * @param id
	 * @param ownerName
	 * @return
	 */
	private String generateUUID(int id, String ownerName) {
		// TODO Auto-generated method stub

		String[] name = ownerName.split(" ");
		UUID uuid = Generators.nameBasedGenerator().generate(name[0] + "" + id);
		return uuid.toString();
	}

	/**
	 * Method to get sellers.
	 * 
	 * @param keyword
	 * @param searchAlias
	 * @param sort
	 * @param ref
	 * @return
	 */
	public List<SellerInformation> getSellers(String keyword, String searchAlias, String sort, int ref) {
		// TODO Auto-generated method stub

		List<SellerInformation> sellers = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		int firstIndex = 3 * (ref - 1);

		Query query = session.createQuery(setQuery(keyword, searchAlias, sort, ref));

		query.setFirstResult(firstIndex);
		query.setMaxResults(3);

		sellers = query.getResultList();

		tx.commit();
		session.close();
		return sellers;
	}

	/**
	 * Method to get seller information.
	 * 
	 * @param sellerId
	 * @return
	 */
	public SellerInformation getSellerInformation(int sellerId) {
		// TODO Auto-generated method stub
		if (sellerId <= 0) {
			return null;
		}
		SellerInformation sellerInformation = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		sellerInformation = session.get(SellerInformation.class, new Integer(sellerId));
		tx.commit();
		session.close();
		return sellerInformation;

	}

	/**
	 * Method to update seller status.
	 * @param sellerId
	 * @param sellerInformation
	 * @return
	 */
	public int updateSellerStatus(int sellerId, SellerInformation sellerInformation) {
		// TODO Auto-generated method stub
		if (sellerId < 0) {
			return 0;
		}
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		SellerInformation seller = session.get(SellerInformation.class, new Integer(sellerId));
		seller.setSellerStatus(sellerInformation.getSellerStatus());
		session.update(seller);
		tx.commit();
		session.close();
		return 1;
	}

	/**
	 * Method to set query.
	 * @param keyword
	 * @param searchAlias
	 * @param sort
	 * @param ref
	 * @return
	 */
	private String setQuery(String keyword, String searchAlias, String sort, int ref) {
		String hql = "from SellerInformation s ";

		if (keyword != null || searchAlias != null) {
			hql += " where ";
			if (keyword != null) {
				hql += " (s.companyName like concat('%','" + keyword + "','%') or  " + " s.ownerName like concat('%','"
						+ keyword + "','%') or " + " s.sellerTelephone like concat('%','" + keyword + "','%')) ";
			}
			if (searchAlias != null) {
				hql += " and s.sellerStatus = '" + searchAlias + "'  ";
				System.out.println(hql);
			}
		}
		if (sort != null) {
			hql += " order by s." + sort;
		} else {
			hql += " order by case s.sellerStatus when 'NEED_APPROVAL' then 1 when 'APPROVED' then 2 else 3 end";
		}
		return hql;
	}

	/**
	 * Method to returnF pagination data
	 * @param keyword
	 * @param searchAlias
	 * @param sort
	 * @param ref
	 * @return
	 */
	public PaginationData getPageInformation(String keyword, String searchAlias, String sort, int ref) {
		PaginationData paginationData = new PaginationData();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery(" Select count (s.sellerId)" + setQuery(keyword, searchAlias, sort, ref));

		Long countResults = (Long) query.setMaxResults(1).uniqueResult();
		tx.commit();
		session.close();
		System.out.println(countResults);
		int lastPageItems = (countResults % 3) == 0 ? 0 : 1;
		int totalPages = (int) ((countResults / 3) + lastPageItems);
		boolean nextPage = (ref == (totalPages)) ? true : false;
		paginationData.setCurrentPage(ref);
		if(countResults!=0)
			paginationData.setNextPage(nextPage);
		else
			paginationData.setNextPage(true);
		return paginationData;
	}

}
