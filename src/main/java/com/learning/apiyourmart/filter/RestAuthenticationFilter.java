package com.learning.apiyourmart.filter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.learning.apiyourmart.service.impl.AuthenticationService;

/**
 * Filter class that authenticates if user trying to hit queries is allowed or
 * not.
 * 
 * @author ayushsaxena
 *
 */
@Provider
public class RestAuthenticationFilter implements ContainerRequestFilter {
	@Context
	private ResourceInfo resourceInfo;

	private static final String AUTHORIZATION_PROPERTY = "Authorization";

	/**
	 * Method that provides filtering.
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// TODO Auto-generated method stub
		Method method = resourceInfo.getResourceMethod();
		// Access allowed for all
		if (!method.isAnnotationPresent(PermitAll.class)) {
			// Access denied for all
			if (method.isAnnotationPresent(DenyAll.class)) {
				requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
						.entity("You cannot access this resource").build());
				return;
			}

			// Get request headers
			final MultivaluedMap<String, String> headers = requestContext.getHeaders();

			// Fetch authorization header
			final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

			RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
			if (rolesAnnotation == null) {
				return;
			}
			Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

			if (authorization == null || authorization.isEmpty()) {
				if (rolesSet.contains("DENY")) {
					requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
							.entity("Access blocked for all users !!").build());
					return;
				} else {
					return;
				}
			}

			AuthenticationService authenticationService = new AuthenticationService();

			if (method.isAnnotationPresent(RolesAllowed.class)) {
				requestContext.setProperty("token", authorization.get(0));
				boolean authenticationStatus = authenticationService.authenticate(authorization.get(0));
				// Is user valid?
				if (!authenticationStatus) {
					requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
							.entity("You cannot access this resource").build());
					return;
				}
			}

		}
	}
}