package com.learning.apiyourmart.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.learning.apiyourmart.dto.PaginationData;
import com.learning.apiyourmart.entity.CategoryInformation;
import com.learning.apiyourmart.entity.ProductImagesInformation;
import com.learning.apiyourmart.entity.ProductInformation;
import com.learning.apiyourmart.entity.SellerInformation;
import com.learning.apiyourmart.service.impl.EmailService;
import com.learning.apiyourmart.utils.HibernateUtil;

import jersey.repackaged.com.google.common.collect.Lists;

/**
 * Class which handles queries and interact with database. This class handles
 * all product related work.
 * 
 * @author ayushsaxena
 *
 */
public class ProductInformationDao {

	/**
	 * Method to add product under seller.
	 * 
	 * @param sellerId
	 * @param product
	 * @return
	 */
	public boolean addProductUnderSeller(int sellerId, ProductInformation product) {
		// TODO Auto-generated method stub
		boolean sellerExist = false;

		SellerInformation sellerInformation = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		sellerInformation = session.get(SellerInformation.class, new Integer(sellerId));
		if (sellerInformation != null) {
			sellerInformation.getProducts().add(product);
			product.setSellerInformation(sellerInformation);
			product.setProductRegisteredDate(new Date());
			List<ProductImagesInformation> images = (List<ProductImagesInformation>) product.getProductImages();
			Iterator<ProductImagesInformation> iterator = images.iterator();
			while (iterator.hasNext()) {
				ProductImagesInformation productImage = iterator.next();
				productImage.setProductInformation(product);
				
				session.save(productImage);
			}
			session.save(product);
			sellerExist = true;
			String categoryQuery="from CategoryInformation c where c.categoryName =:cName";
			CategoryInformation categoryInformation = (CategoryInformation) session.createQuery(categoryQuery)
					.setParameter("cName", product.getProductCategory()).getSingleResult();
			categoryInformation.setCategoryProductsCount(categoryInformation.getCategoryProductsCount()+1);
		}
		tx.commit();
		session.close();
		return sellerExist;
	}

	/**
	 * Method to fetch products.
	 * 
	 * @param sellerId
	 * @return
	 */
	public List<ProductInformation> getProducts(int sellerId) {
		// TODO Auto-generated method stub
		if (sellerId <= 0) {
			return null;
		}
		List<ProductInformation> products = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		String sql = " Select * from  productdb p where p.SELLER_ID=:sId";
		SQLQuery query = session.createSQLQuery(sql);
	    query.addEntity(ProductInformation.class);
	    query.setParameter("sId", sellerId);
	    products=query.list();
		tx.commit();
		session.close();
		return products;
	}

	/**
	 * Method to get product under particular seller.
	 * 
	 * @param sellerId
	 * @param productId
	 * @return
	 */
	public ProductInformation getProductInformation(int sellerId, int productId) {
		// TODO Auto-generated method stub

		if (productId <= 0) {
			return null;
		}
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

	/**
	 * Method to get product list with parameters.
	 * 
	 * @param keyword
	 * @param searchAliasType
	 * @param searchAliasValue
	 * @param sort
	 * @param ref
	 * @return
	 */
	public List<ProductInformation> getUniversalProductList(String keyword, String searchAliasType,
			String searchAliasValue, String sort, int ref) {
		// TODO Auto-generated method stub
		List<ProductInformation> productInformations = Lists.newArrayList();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		int firstIndex = 3 * (ref - 1);

		Query query = session.createQuery(setQuery(keyword, searchAliasType, searchAliasValue, sort, ref));
		System.out.println(setQuery(keyword, searchAliasType, searchAliasValue, sort, ref));
		query.setFirstResult(firstIndex);
		query.setMaxResults(3);
		productInformations = (List<ProductInformation>)query.list();
		tx.commit();
		session.close();
		return productInformations;
	}

	/**
	 * Method to update product status.
	 * 
	 * @param productId
	 * @param productInformation
	 * @return
	 */
	public int updateProductStatus(int productId, ProductInformation productInformation) {
		// TODO Auto-generated method stub
		if (productId < 0) {
			return 0;
		}
		productInformation.setProductId(productId);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		ProductInformation product = session.get(ProductInformation.class, new Integer(productId));
		if (productInformation.getProductComment()!=null) {
			EmailService emailService = new EmailService();
			emailService.sendMail(product.getSellerInformation().getSellerEmail(), "Comment Added", productInformation.getProductComment());
		}
		product.setProductRegisteredDate(new Date());
		product.setProductComment(productInformation.getProductComment());
		product.setProductStatus(productInformation.getProductStatus());
		product.setProductRegisteredDate(new Date());
		if (productInformation.getProductCategory() != null
				&& !productInformation.getProductCategory().equalsIgnoreCase("null")) {
			product.setProductCategory(productInformation.getProductCategory());
			product.setProductDimensions(productInformation.getProductDimensions());
			product.setProductImages(productInformation.getProductImages());
			product.setProductLongDescription(productInformation.getProductLongDescription());
			product.setProductShortDescription(productInformation.getProductShortDescription());
			product.setProductMrp(productInformation.getProductMrp());
			product.setProductSsp(productInformation.getProductSsp());
			product.setProductYmp(productInformation.getProductYmp());
			product.setProductName(productInformation.getProductName());
			product.setProductPrimaryImage(productInformation.getProductPrimaryImage());
			product.setSellerProductCode(productInformation.getSellerProductCode());
		}

		session.update(product);
		tx.commit();
		session.close();
		return 1;
	}

	/**
	 * Method to fetch product list for Background Service.
	 * 
	 * @return
	 */
	public static String getProductNamesForBackgroundService() {
		Date today = new Date();
		long ltime = today.getTime() - 5 * 24 * 60 * 60 * 1000;
		Date fiveBehind = new Date(ltime);

		System.out.println(fiveBehind);
		List<ProductInformation> productInformations = Lists.newArrayList();
		String productList = "";
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		productInformations = session
				.createQuery("from ProductInformation p where p.productStatus = 'NEW'"
						+ " or p.productStatus = 'REVIEW' and p.productRegisteredDate <= :date")
				.setTimestamp("date", fiveBehind).getResultList();
		Iterator<ProductInformation> iterator = productInformations.iterator();
		while (iterator.hasNext()) {
			ProductInformation product = iterator.next();
			productList += " " + product.getProductId() + ". " + product.getProductName() + "\n";
		}
		tx.commit();
		session.close();
		return productList;

	}

	/**
	 * Method to set query.
	 * 
	 * @param keyword
	 * @param searchAliasType
	 * @param searchAliasValue
	 * @param sort
	 * @param ref
	 * @return
	 */
	private String setQuery(String keyword, String searchAliasType, String searchAliasValue, String sort, int ref) {
		String hql = " from ProductInformation p ";

		if (keyword != null || (searchAliasType != null && searchAliasValue != null)) {
			
			if (searchAliasType != null && searchAliasValue != null) {
				switch(searchAliasType){
				case "sellerId":
					hql += " where p.sellerInformation = " + searchAliasValue + "  ";
					break;
				default :
					hql += " where p." + searchAliasType + " = '" + searchAliasValue + "'  ";
					break;
				
				}
				
			}
			if (keyword != null) {
				hql += " and (p.sellerProductCode like concat('%','" + keyword + "','%') or  "
						+ " p.productName like concat('%','" + keyword + "','%') or " + " p.productId like concat('%','"
						+ keyword + "','%')) ";
			}
			
		}
		if (sort != null) {
			hql += " order by p." + sort;
		}
		return hql;
	}

	/**
	 * Method to set pagination data.
	 * 
	 * @param keyword
	 * @param searchAliasType
	 * @param searchAliasValue
	 * @param sort
	 * @param ref
	 * @return
	 */
	public PaginationData getPageInformation(String keyword, String searchAliasType, String searchAliasValue,
			String sort, int ref) {
		PaginationData paginationData = new PaginationData();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery(
				" Select count (p.productId)" + setQuery(keyword, searchAliasType, searchAliasValue, sort, ref));

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
