package com.learning.apiyourmart.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.learning.apiyourmart.entity.CategoryInformation;
import com.learning.apiyourmart.entity.ProductInformation;
import com.learning.apiyourmart.utils.HibernateUtil;

/**
 * Class which handles queries and interact with database. This class handles
 * all category related work.
 * 
 * @author ayushsaxena
 *
 */
public class CategoryInformationDao {
	
	/**
	 * Method to return categories.
	 * @return
	 */
	public List<CategoryInformation> getCategorizedProducts() {
		List<CategoryInformation> categoryInformations = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from CategoryInformation";
		categoryInformations = session.createQuery(hql).getResultList();
		// categoryInformations.forEach(categoryInformation->categoryInformation.setProductsUnderCategory(categoryInformation.getProductsUnderCategory()));
		tx.commit();
		session.close();
		return categoryInformations;
	}

	/**
	 * Method to remove category.
	 * @param categoryId
	 * @return
	 */
	public int removeCategory(int categoryId) {
		if (categoryId < 1) {
			return 0;
		}
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		CategoryInformation categoryInformation = session.get(CategoryInformation.class, new Integer(categoryId));
		session.remove(categoryInformation);
		tx.commit();
		session.close();
		return 1;
	}

	/**
	 * Method to edit category.
	 * @param categoryId
	 * @param categoryInformation
	 * @return
	 */
	public int editCategoryInformation(int categoryId, CategoryInformation categoryInformation) {
		// TODO Auto-generated method stub
		if (categoryId < 1) {
			return 0;
		}
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		CategoryInformation category = session.get(CategoryInformation.class, new Integer(categoryId));
		Query query = session
				.createQuery(
						"update ProductInformation as p set p.productCategory =:newValue where p.productCategory =:oldValue ")
				.setParameter("oldValue", category.getCategoryName())
				.setParameter("newValue", categoryInformation.getCategoryName());
		System.out.println(category.getCategoryName() + " ; " + categoryInformation.getCategoryName());
		int result = query.executeUpdate();
		category.setCategoryName(categoryInformation.getCategoryName());
		session.update(category);

		System.out.println(result);
		tx.commit();
		session.close();
		return 1;
	}

	/**
	 * Method to add category.
	 * @param categoryInformation
	 * @return
	 */
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
