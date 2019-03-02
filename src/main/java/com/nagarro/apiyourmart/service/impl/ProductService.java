package com.nagarro.apiyourmart.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.nagarro.apiyourmart.dao.ProductInformationDao;
import com.nagarro.apiyourmart.dto.PaginationData;
import com.nagarro.apiyourmart.dto.ProductImagesCredentialsDto;
import com.nagarro.apiyourmart.dto.ProductInformationCredentialsDto;
import com.nagarro.apiyourmart.dto.ResponseDataCredentialsDto;
import com.nagarro.apiyourmart.entity.ProductInformation;
import com.nagarro.apiyourmart.service.IYourMartService;

/**
 * Service Class to handle product related business.
 * @author ayushsaxena
 *
 */
public class ProductService implements IYourMartService {
	private String keyword;
	private String searchAliasType;
	private String searchAliasValue;
	private String sort;
	private int ref;
	private int sellerId;
	ProductInformationDao productDao;

	public ProductService() {
		// TODO Auto-generated constructor stub
		productDao = new ProductInformationDao();
		this.sellerId = -1;
	}

	public ProductService(String keyword, String searchAliasType, String searchAliasValue, String sort, int ref) {
		this.keyword = keyword;
		this.searchAliasType = searchAliasType;
		this.searchAliasValue = searchAliasValue;
		this.ref = ref;
		this.sort = sort;
		productDao = new ProductInformationDao();
		this.sellerId = -1;
	}

	public ProductService(int sellerId) {
		this.sellerId = sellerId;
		productDao = new ProductInformationDao();
	}

	@Override
	public String updateEntity(int id, Object entity) {
		ProductInformationCredentialsDto productInformationCredentialsDto = (ProductInformationCredentialsDto) entity;
		ProductInformation productInformation = productInformationCredentialsDto.convertToEntity();
		int val = productDao.updateProductStatus(id, productInformation);

		return val != 0 ? "Updated" : "Error";
	}

	@Override
	public String deleteEntity(int id) {

		return null;
	}

	@Override
	public Object getEntity(int id) {
		// TODO Auto-generated method stub

		ProductInformation productInformation = productDao.getProductInformation(sellerId, id);
		if (productInformation == null) {
			return null;
		}
		ProductInformationCredentialsDto product = productInformation.convertToDto();
		return product;
	}

	@Override
	public Object getAllEntities() {
		// TODO Auto-generated method stub
		List<ProductInformation> result = productDao.getUniversalProductList(keyword, searchAliasType, searchAliasValue,
				sort, ref);
		Iterator<ProductInformation> iterator = result.iterator();
		List<ProductInformationCredentialsDto> products = new ArrayList<>();
		while (iterator.hasNext()) {
			ProductInformation product = (ProductInformation)iterator.next();
			products.add(product.convertToDto());
		}
		return products;
	}

	@Override
	public String addEntity(Object entity) {
		// TODO Auto-generated method stub
		ProductInformation product = ((ProductInformationCredentialsDto) entity).convertToEntity();
		boolean created = productDao.addProductUnderSeller(sellerId, (product));
		return created ? "created" : "error";
	}

	@Override
	public Object getEntitiesUnderEntity() {
		// TODO Auto-generated method stub
		List<ProductInformation> result = productDao.getProducts(sellerId);
		if (result == null) {
			return null;
		}
		Iterator<ProductInformation> iterator = result.iterator();
		List<ProductInformationCredentialsDto> products = new ArrayList<>();
		while (iterator.hasNext()) {
			ProductInformation product = iterator.next();
			System.out.println(product);
			products.add(product.convertToDto());
		}
		return products;
	}

	@Override
	public String addEntityUnderEntity(int id, Object entity) {
		// TODO Auto-generated method stub
		ProductInformation product = ((ProductInformationCredentialsDto) entity).convertToEntity();
		boolean created = productDao.addProductUnderSeller(id, (product));
		return created ? "created" : "error";
	}
	
	@Override
	public PaginationData getPaginationInfo() {
		// TODO Auto-generated method stub
		PaginationData page=productDao.getPageInformation(keyword, searchAliasType,searchAliasValue, sort, ref);
		return page;
	}

}
