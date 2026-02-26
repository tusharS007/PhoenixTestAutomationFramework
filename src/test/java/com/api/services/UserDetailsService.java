package com.api.services;

import static io.restassured.RestAssured.given;

import com.api.constant.Role;
import com.api.utils.SpecUtil;

import io.restassured.response.Response;

public class UserDetailsService {

	private static final String USERDETAILS_ENDPOINT = "userdetails";
	
	public Response userDetails(Role role) {
		
		Response response =given()
				  .spec(SpecUtil.requestSpecificationWithAuth(role))
				  .when()
				  .get(USERDETAILS_ENDPOINT);
		
		return response;
		
	}
	
}
