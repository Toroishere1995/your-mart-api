package com.nagarro.apiyourmart.config;

import org.glassfish.jersey.server.ResourceConfig;

import com.nagarro.apiyourmart.filter.CORSFilter;
import com.nagarro.apiyourmart.filter.RestAuthenticationFilter;
/**
 * Custom Configuration Class which registers filters.
 * @author ayushsaxena
 *
 */
public class CustomApplication extends ResourceConfig {
	public CustomApplication() {
		
		// Register Auth Filter here
		register(CORSFilter.class);
		//register(RestAuthenticationFilter.class);
		
	}
}