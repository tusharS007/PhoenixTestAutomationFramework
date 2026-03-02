package com.api.services;

import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constant.Role;
import com.api.utils.SpecUtil;

import io.restassured.response.Response;

public class UserDetailsService {

	private static final String USERDETAILS_ENDPOINT = "userdetails";
	private static final Logger LOGGER = LogManager.getLogger(UserDetailsService.class);

	public Response userDetails(Role role) {
		LOGGER.info("Making request to the {} for the role {} ", USERDETAILS_ENDPOINT, role);

		Response response =given()
				  .spec(SpecUtil.requestSpecificationWithAuth(role))
				  .when()
				  .get(USERDETAILS_ENDPOINT);
		
		return response;
		
	}
	
}
