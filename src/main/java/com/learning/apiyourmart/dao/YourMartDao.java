package com.learning.apiyourmart.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.learning.apiyourmart.entity.CategoryInformation;
import com.learning.apiyourmart.entity.LoginCredentials;
import com.learning.apiyourmart.entity.ProductInformation;
import com.learning.apiyourmart.entity.SellerInformation;
import com.learning.apiyourmart.utils.HibernateUtil;

import jersey.repackaged.com.google.common.collect.Lists;

public class YourMartDao {

	public boolean checkUserCredentials(LoginCredentials credentials) {
		// TODO Auto-generated method stub
		boolean adminCredentialsExist = false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		adminCredentialsExist = (checkEmailAndPassword(session, credentials.getAdminUsername(),
				credentials.getAdminPassword()));
		tx.commit();
		session.close();
		return adminCredentialsExist;
	}

	private boolean checkEmailAndPassword(Session session, String adminUsername, String adminPassword) {
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

	public boolean addSeller(SellerInformation sellerData) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.save(sellerData);
		tx.commit();
		session.close();
		if (sellerData != null) {
			return true;
		}
		return false;
	}

	public List<SellerInformation> getSellers(String keyword, String searchAlias, String sort, int ref) {
		// TODO Auto-generated method stub

		List<SellerInformation> sellers = new ArrayList();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		int firstIndex = 3 * (ref - 1);
		long totalPages = countTotalPages(session, true);
		System.out.println(searchAlias);
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
		Query query = session.createQuery(hql);
		query.setFirstResult(firstIndex);
		query.setMaxResults(3);
		
		sellers = query.getResultList();
		tx.commit();
		session.close();

		return sellers;
	}

	public SellerInformation getSellerInformation(int sellerId) {
		// TODO Auto-generated method stub
		SellerInformation sellerInformation = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		sellerInformation = session.get(SellerInformation.class, new Integer(sellerId));
		tx.commit();
		session.close();
		return sellerInformation;

	}

	public boolean addProductUnderSeller(int sellerId, ProductInformation productInformation) {
		// TODO Auto-generated method stub
		boolean sellerExist = false;
		SellerInformation sellerInformation = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		sellerInformation = session.get(SellerInformation.class, new Integer(sellerId));
		if (sellerInformation != null) {
			sellerInformation.getProducts().add(productInformation);
			productInformation.setSellerInformation(sellerInformation);
			session.save(productInformation);
			sellerExist = true;
		}
		tx.commit();
		session.close();
		return sellerExist;
	}

	public List<ProductInformation> getProducts(int sellerId) {
		// TODO Auto-generated method stub
		List<ProductInformation> products = new ArrayList<>();
		//
		// SellerInformation sellerInformation = getSellerInformation(sellerId);
		// products = (List<ProductInformation>)
		// sellerInformation.getProducts();
		// System.out.println(products);

		SellerInformation sellerInformation = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		sellerInformation = session.get(SellerInformation.class, new Integer(sellerId));
		System.out.println(sellerInformation.getProducts());
		products = (List<ProductInformation>) sellerInformation.getProducts();
		tx.commit();
		session.close();
		return products;
	}

	public ProductInformation getProductInformation(int sellerId, int productId) {
		// TODO Auto-generated method stub

		ProductInformation productInformation = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		productInformation = (ProductInformation) session
				.createQuery("from ProductInformation p where p.productId = :productId")
				.setParameter("productId", productId).getSingleResult();
		tx.commit();
		session.close();
		return productInformation;
	}

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

	private long countTotalPages(Session session, boolean isSellerInformation) {
		String countQ = "";
		if (isSellerInformation) {
			countQ = "Select count (f.sellerId) from SellerInformation f";
		} else {
			countQ = "Select count (f.productId) from ProductInformation f ";
		}
		Query countQuery = session.createQuery(countQ);
		Long countResults = (Long) countQuery.uniqueResult();
		return countResults;
	}

	public List<ProductInformation> getUniversalProductList(String keyword, String searchAliasType,
			String searchAliasValue, String sort, int ref) {
		// TODO Auto-generated method stub
		List<ProductInformation> productInformations = Lists.newArrayList();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		int firstIndex = 3 * (ref - 1);
		long totalPages = countTotalPages(session, false);

		String hql = " from ProductInformation p ";

		if (keyword != null || (searchAliasType != null && searchAliasValue!=null)) {
			hql += " where ";
			if (keyword != null) {
				hql += " (p.sellerProductCode like concat('%','" + keyword + "','%') or  "
						+ " p.productName like concat('%','" + keyword + "','%') or " + " p.productId like concat('%','"
						+ keyword + "','%')) ";
			}
			if (searchAliasType != null && searchAliasValue!=null) {
				hql += " and p."+searchAliasType+" = '" + searchAliasValue + "'  ";
			}
		}
		if (sort != null) {
			hql += " order by p." + sort;
		}

		Query query = session.createQuery(hql);
		query.setFirstResult(firstIndex);
		query.setMaxResults(3);
		productInformations = query.getResultList();

		tx.commit();
		session.close();
		return productInformations;
	}

	public int updateProductStatus(int productId, ProductInformation productInformation) {
		// TODO Auto-generated method stub
		if (productId < 0) {
			return 0;
		}
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		ProductInformation product = session.get(ProductInformation.class, new Integer(productId));
		product.setProductComment(productInformation.getProductComment());
		product.setProductStatus(productInformation.getProductStatus());
		session.update(product);
		tx.commit();
		session.close();
		return 1;
	}
	
	
	public List<CategoryInformation> getCategorizedProducts(){
		List<CategoryInformation> categoryInformations=new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		String hql="from CategoryInformation";
		categoryInformations=session.createQuery(hql).getResultList();
		//categoryInformations.forEach(categoryInformation->categoryInformation.setProductsUnderCategory(categoryInformation.getProductsUnderCategory()));
		tx.commit();
		session.close();
		return categoryInformations;
	}

	public int removeCategory(int categoryId) {
		if(categoryId<1){
			return 0;
		}
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		CategoryInformation categoryInformation=session.get(CategoryInformation.class, new Integer(categoryId));
		session.remove(categoryInformation);
		tx.commit();
		session.close();
		return 1;
	}

	public int editCategoryInformation(int categoryId, CategoryInformation categoryInformation) {
		// TODO Auto-generated method stub
		if(categoryId<1){
			return 0;
		}
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		CategoryInformation category=session.get(CategoryInformation.class, new Integer(categoryId));
		category.setCategoryName(categoryInformation.getCategoryName());
		session.update(category);
		tx.commit();
		session.close();
		return 1;
	}
	
	
	public boolean addCategory(CategoryInformation categoryInformation) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.save(categoryInformation);
		tx.commit();
		session.close();
		if (categoryInformation != null) {
			return true;
		}
		return false;
	} 
}
