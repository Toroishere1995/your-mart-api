package com.learning.apiyourmart;

import java.security.SecureRandom;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.learning.apiyourmart.constants.Constants;
import com.learning.apiyourmart.dto.AuthenticationCredentialsDto;
import com.learning.apiyourmart.dto.CategoryInformationCredentialsDto;
import com.learning.apiyourmart.dto.PaginationData;
import com.learning.apiyourmart.dto.ProductInformationCredentialsDto;
import com.learning.apiyourmart.dto.ResponseDataCredentialsDto;
import com.learning.apiyourmart.dto.SellerInformationCredentialsDto;
import com.learning.apiyourmart.dto.SellerRegistrationCredentialsDto;
import com.learning.apiyourmart.service.IYourMartService;
import com.learning.apiyourmart.service.impl.AuthenticationService;
import com.learning.apiyourmart.service.impl.CategoryService;
import com.learning.apiyourmart.service.impl.ProductService;
import com.learning.apiyourmart.service.impl.SellerService;
import com.learning.apiyourmart.utils.ResponseWrapper;

/**
 * Class that acts as a controller for handling api hit.
 * 
 * @author ayushsaxena
 *
 */
@Path("yourmartservice")
public class YourMartRestService {

	private IYourMartService martService;

	/**
	 * Dummy Method
	 * 
	 * @return
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@RolesAllowed(value = "ALLOW")
	public String getIt() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		String token = bytes.toString();
		return token;
	}

	/**
	 * Authentication API.
	 */

	/**
	 * Method to authenticate user.
	 * 
	 * @param credentials
	 * @return
	 */
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response adminLoginValid(AuthenticationCredentialsDto credentials) {

		AuthenticationService service = new AuthenticationService();
		ResponseDataCredentialsDto responseDataCredentialsDto = service.getResponse(credentials);
		return Response.ok().entity(responseDataCredentialsDto).build();

	}

	/**
	 * Method to register for new seller.
	 * 
	 * @param credentials
	 * @return
	 */
	@POST
	@Path("/register")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed(value = "ALLOW")
	public Object addSeller(SellerRegistrationCredentialsDto credentials) {

		martService = new SellerService();
		Object registeredSeller = martService.addEntity(credentials);
		if (registeredSeller == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		return registeredSeller;

	}

	/**
	 * Method that returns logged user.
	 * 
	 * @param containerRequestContext
	 * @return
	 */

	@GET
	@Path("/seller")
	@RolesAllowed(value = "ALLOW")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getLoggedSeller(@Context ContainerRequestContext containerRequestContext) {
		AuthenticationService service = new AuthenticationService();
		String credentials = containerRequestContext.getHeaders().get(Constants.AUTHORIZATION).get(0);

		ResponseDataCredentialsDto responseDataCredentialsDto = service.getLoggedUser(credentials);
		return Response.ok().entity(responseDataCredentialsDto).build();

	}

	/**
	 * Seller API's.
	 */

	/**
	 * Method that returns sellers based on query.
	 * 
	 * @param keyword
	 * @param searchAlias
	 * @param sort
	 * @param ref
	 * @return
	 */
	@GET
	@Path("/sellers")
	@RolesAllowed(value = "ALLOW")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getSellers(@QueryParam("keyword") String keyword, @QueryParam("search-alias") String searchAlias,
			@QueryParam("sort") String sort, @DefaultValue("1") @QueryParam("ref") int ref) {
		martService = new SellerService(keyword, searchAlias, sort, ref);
		Object sellers = martService.getAllEntities();
		PaginationData page = martService.getPaginationInfo();
		String status = sellers != null ? Constants.SUCCESS : Constants.ERROR;
		String code = sellers != null ? "200" : "400";
		ResponseDataCredentialsDto responseDto = ResponseWrapper.wrapObjectToResponse(sellers, status, code, page);

		return Response.ok().entity(responseDto).build();
	}

	/**
	 * 
	 * Method that gives sellers.
	 * 
	 * @return
	 */
	@GET
	@Path("/seller")
	@RolesAllowed(value = "ALLOW")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getSeller() {
		martService = new SellerService();
		Object sellers = martService.getAllEntities();
		String status = sellers != null ? Constants.SUCCESS : Constants.ERROR;
		String code = sellers != null ? "200" : "400";
		ResponseDataCredentialsDto responseDto = ResponseWrapper.wrapObjectToResponse(sellers, status, code, null);

		return Response.ok().entity(responseDto).build();
	}

	/**
	 * 
	 * Method that gives a perticular seller.
	 * 
	 * @param sellerId
	 * @return
	 */
	@GET
	@Path("/sellers/{sellerId}")
	@RolesAllowed(value = "ALLOW")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getSellerInfo(@PathParam("sellerId") int sellerId) {
		martService = new SellerService();
		Object seller = martService.getEntity(sellerId);
		String status = seller != null ? Constants.SUCCESS : Constants.ERROR;
		String code = seller != null ? "200" : "204";
		ResponseDataCredentialsDto responseDto = ResponseWrapper.wrapObjectToResponse(seller, status, code, null);

		return Response.ok().entity(responseDto).build();
	}

	/**
	 * 
	 * Method that updates seller .
	 * 
	 * @param sellerId
	 * @param sellerInformationCredentialsDto
	 * @return
	 */
	@PUT
	@Path("/sellers/{sellerId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed(value = "DENY")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateSellerStatus(@PathParam("sellerId") int sellerId,
			SellerInformationCredentialsDto sellerInformationCredentialsDto) {
		martService = new SellerService();
		String updationDone = martService.updateEntity(sellerId, sellerInformationCredentialsDto);
		String status = !updationDone.equalsIgnoreCase(Constants.ERROR) ? Constants.SUCCESS : Constants.ERROR;
		String code = !updationDone.equalsIgnoreCase(Constants.ERROR) ? "201" : "400";
		ResponseDataCredentialsDto responseDto = ResponseWrapper.wrapObjectToResponse(updationDone, status, code, null);

		return Response.ok().entity(responseDto).build();
	}

	/**
	 * Product API's
	 */

	/**
	 * Method that returns list of products based on query.
	 * 
	 * @param keyword
	 * @param searchAlias
	 * @param sort
	 * @param ref
	 * @return
	 */
	@GET
	@Path("/products")
	@RolesAllowed(value = "ALLOW")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getProducts(@QueryParam("keyword") String keyword, @QueryParam("search-alias") String searchAlias,
			@QueryParam("sort") String sort, @DefaultValue("1") @QueryParam("ref") int ref) {
		String[] searchAliases = null;
		Object products;
		if (searchAlias != null) {
			searchAliases = searchAlias.split("-");
			martService = new ProductService(keyword, searchAliases[0], searchAliases[1], sort, ref);

		} else {
			martService = new ProductService(keyword, null, null, sort, ref);
		}
		products = martService.getAllEntities();
		PaginationData page = martService.getPaginationInfo();
		String status = products != null ? Constants.SUCCESS : Constants.ERROR;
		String code = products != null ? "200" : "400";
		ResponseDataCredentialsDto responseDto = ResponseWrapper.wrapObjectToResponse(products, status, code, page);

		return Response.ok().entity(responseDto).build();
	}

	/**
	 * Method that gives particular product information.
	 * 
	 * @param productId
	 * @return
	 */
	@GET
	@Path("/products/{productId}")
	@RolesAllowed(value = "ALLOW")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getProductInfo(@PathParam("productId") int productId) {
		martService = new ProductService(0);
		Object product = martService.getEntity(productId);
		String status = product != null ? Constants.SUCCESS : Constants.ERROR;
		String code = product != null ? "200" : "404";
		ResponseDataCredentialsDto responseDto = ResponseWrapper.wrapObjectToResponse(product, status, code, null);

		return Response.ok().entity(responseDto).build();
	}

	/**
	 * Method to update product
	 * 
	 * @param productId
	 * @param product
	 * @return
	 */
	@PUT
	@Path("/products/{productId}")
	@RolesAllowed(value = "DENY")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateProductInfo(@PathParam("productId") int productId, ProductInformationCredentialsDto product) {
		martService = new ProductService(0);
		String val = martService.updateEntity(productId, product);

		String status = !val.equalsIgnoreCase(Constants.ERROR) ? Constants.SUCCESS : Constants.ERROR;
		String code = !val.equalsIgnoreCase(Constants.ERROR) ? "201" : "400";
		ResponseDataCredentialsDto responseDto = ResponseWrapper.wrapObjectToResponse(val, status, code, null);

		return Response.ok().entity(responseDto).build();
	}

	/**
	 * Method to add product under particular seller.
	 * 
	 * @param sellerId
	 * @param product
	 * @return
	 */
	@POST
	@RolesAllowed(value = "DENY")
	@Path("/sellers/{sellerId}/products")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response addProduct(@PathParam("sellerId") int sellerId, ProductInformationCredentialsDto product) {
		martService = new ProductService(sellerId);
		String productAdded = (String) martService.addEntity(product);

		String status = !productAdded.equalsIgnoreCase(Constants.ERROR) ? Constants.SUCCESS : Constants.ERROR;
		String code = !productAdded.equalsIgnoreCase(Constants.ERROR) ? "201" : "400";
		ResponseDataCredentialsDto responseDto = ResponseWrapper.wrapObjectToResponse(productAdded, status, code, null);

		return Response.ok().entity(responseDto).build();

	}

	/**
	 * Method that gives products under particular seller.
	 * 
	 * @param sellerId
	 * @return
	 */
	@GET
	@Path("/sellers/{sellerId}/products")
	@RolesAllowed(value = "ALLOW")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getProductsUnderSeller(@PathParam("sellerId") int sellerId) {
		martService = new ProductService(sellerId);
		Object products = martService.getEntitiesUnderEntity();
		String status = products != null ? Constants.SUCCESS : Constants.ERROR;
		String code = products != null ? "200" : "400";
		ResponseDataCredentialsDto responseDto = ResponseWrapper.wrapObjectToResponse(products, status, code, null);

		return Response.ok().entity(responseDto).build();

	}

	/**
	 * Method to give a particular product under particular seller.
	 * 
	 * @param sellerId
	 * @param productId
	 * @return
	 */
	@GET
	@Path("/sellers/{sellerId}/products/{productId}")
	@RolesAllowed(value = "ALLOW")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getProduct(@PathParam("sellerId") int sellerId, @PathParam("productId") int productId) {

		martService = new ProductService(sellerId);
		Object product = martService.getEntity(productId);
		String status = product != null ? Constants.SUCCESS : Constants.ERROR;
		String code = product != null ? "200" : "400";
		ResponseDataCredentialsDto responseDto = ResponseWrapper.wrapObjectToResponse(product, status, code, null);

		return Response.ok().entity(responseDto).build();

	}

	/**
	 * Categories API's
	 * 
	 */

	/**
	 * Method that returns list of categories.
	 * 
	 * @return
	 */
	@GET
	@Path("/categories")
	@RolesAllowed(value = "ALLOW")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getCategories() {

		martService = new CategoryService();

		Object categories = martService.getAllEntities();
		// ResponseDataCredentialsDto credentialsDto =
		// ResponseWrapper.wrapObjectToResponse(categories);

		String status = categories != null ? Constants.SUCCESS : Constants.ERROR;
		String code = categories != null ? "200" : "400";
		ResponseDataCredentialsDto responseDto = ResponseWrapper.wrapObjectToResponse(categories, status, code, null);

		return Response.ok().entity(responseDto).build();

	}

	/**
	 * Method that deletes category.
	 * 
	 * @param categoryId
	 * @return
	 */
	@DELETE
	@Path("/categories/{categoryId}")
	@RolesAllowed(value = "DENY")
	public Response removeCategory(@PathParam("categoryId") int categoryId) {

		martService = new CategoryService();

		String result = martService.deleteEntity(categoryId);

		String status = !result.equalsIgnoreCase(Constants.ERROR) ? Constants.SUCCESS : Constants.ERROR;
		String code = !result.equalsIgnoreCase(Constants.ERROR) ? "201" : "400";
		ResponseDataCredentialsDto responseDto = ResponseWrapper.wrapObjectToResponse(result, status, code, null);

		return Response.ok().entity(responseDto).build();

	}

	/**
	 * Method to update category.
	 * 
	 * @param categoryId
	 * @param categoryInformationDto
	 * @return
	 */
	@PUT
	@Path("/categories/{categoryId}")
	@RolesAllowed(value = "DENY")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response editCategory(@PathParam("categoryId") int categoryId,
			CategoryInformationCredentialsDto categoryInformationDto) {

		martService = new CategoryService();

		String updationDone = martService.updateEntity(categoryId, categoryInformationDto);

		String status = !updationDone.equalsIgnoreCase(Constants.ERROR) ? Constants.SUCCESS : Constants.ERROR;
		String code = !updationDone.equalsIgnoreCase(Constants.ERROR) ? "201" : "400";
		ResponseDataCredentialsDto responseDto = ResponseWrapper.wrapObjectToResponse(updationDone, status, code, null);

		return Response.ok().entity(responseDto).build();

	}

	/**
	 * Method to add a category.
	 * 
	 * @param categoryInformationDto
	 * @return
	 */
	@POST
	@Path("/categories")
	@RolesAllowed(value = "DENY")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response addCategory(CategoryInformationCredentialsDto categoryInformationDto) {

		martService = new CategoryService();

		boolean categoryAdded = (boolean) martService.addEntity(categoryInformationDto);
		String status = categoryAdded ? Constants.SUCCESS : Constants.ERROR;
		String code = categoryAdded ? "201" : "400";
		ResponseDataCredentialsDto responseDto = ResponseWrapper.wrapObjectToResponse(status, status, code, null);

		return Response.ok().entity(responseDto).build();

	}
}
