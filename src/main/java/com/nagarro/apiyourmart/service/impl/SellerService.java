package com.nagarro.apiyourmart.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.nagarro.apiyourmart.dao.SellerInformationDao;
import com.nagarro.apiyourmart.dto.PaginationData;
import com.nagarro.apiyourmart.dto.ResponseDataCredentialsDto;
import com.nagarro.apiyourmart.dto.SellerInformationCredentialsDto;
import com.nagarro.apiyourmart.dto.SellerRegistrationCredentialsDto;
import com.nagarro.apiyourmart.entity.SellerInformation;
import com.nagarro.apiyourmart.service.IYourMartService;
import com.nagarro.apiyourmart.utils.ResponseWrapper;

/**
 * Service Class to handle seller related business.
 * @author ayushsaxena
 *
 */
public class SellerService implements IYourMartService {
	private String keyword;
	private String searchAlias;
	private String sort;
	private int ref;
	private SellerInformationDao sellerDao;

	public SellerService() {
		// TODO Auto-generated constructor stub
		sellerDao = new SellerInformationDao();
	}

	public SellerService(String keyword, String searchAlias, String sort, int ref) {
		// TODO Auto-generated constructor stub
		this.keyword = keyword;
		this.ref = ref;
		this.searchAlias = searchAlias;
		this.sort = sort;
		sellerDao = new SellerInformationDao();
	}

	@Override
	public String updateEntity(int id, Object entity) {
		// TODO Auto-generated method stub
		SellerInformationCredentialsDto updatedSellerCredentialsDto=(SellerInformationCredentialsDto)entity;
		SellerInformation updatedSellerInformation=updatedSellerCredentialsDto.convertToEntity();
		int val=sellerDao.updateSellerStatus(id,updatedSellerInformation);
		return val!=0?"updated":"error";
	}

	@Override	
	public String deleteEntity(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getEntity(int id) {
		// TODO Auto-generated method stub
		System.out.println("Here:52");
		SellerInformation sellerInformation=sellerDao.getSellerInformation(id);
		if(sellerInformation==null){
			return null;
		}
		SellerInformationCredentialsDto sellerDto=sellerInformation.convertToDto();
		return sellerDto;
	}

	@Override
	public Object getAllEntities() {
		// TODO Auto-generated method stub
		System.out.println("Here");
		List<SellerInformation> result=sellerDao.getSellers(keyword, searchAlias, sort, ref);
		System.out.println(keyword+","+ searchAlias+","+ sort+","+ ref);
		Iterator<SellerInformation> iterator = result.iterator();
		List<SellerInformationCredentialsDto> sellers = new ArrayList<>();
		while (iterator.hasNext()) {
			SellerInformation seller = iterator.next();
			sellers.add(seller.convertToDto());
		}
		return sellers;
	}

	@Override
	public Object addEntity(Object entity) {
		// TODO Auto-generated method stub
		SellerRegistrationCredentialsDto registrationDto = (SellerRegistrationCredentialsDto) entity;
		SellerInformation sellerEntity = registrationDto.convertToSellerInformation();
		SellerInformation addedSeller = sellerDao.addSeller(sellerEntity);
		SellerInformationCredentialsDto seller = addedSeller.convertToDto();
		return seller;

	}

	@Override
	public Object getEntitiesUnderEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addEntityUnderEntity(int id, Object entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationData getPaginationInfo() {
		// TODO Auto-generated method stub
		PaginationData page=sellerDao.getPageInformation(keyword, searchAlias, sort, ref);
		return page;
	}

}
