package com.api.services;

import static com.api.utils.SpecUtil.requestSpecificationWithAuth;
import static io.restassured.RestAssured.given;

import com.api.constant.Role;

import io.restassured.response.Response;

public class JobService {
	
	
	private static final String CREATEJOBENDPOINT = "/job/create";
	private static final String SEARCH_ENDPOINT = "/job/search";
	
	public Response create(Role role, Object payLoad) {
	
		return given()
	    .spec(requestSpecificationWithAuth(role, payLoad))
	    .when()
	    .post(CREATEJOBENDPOINT);
	}
	
	public Response search(Role role, Object payLoad) {
		
		return given()
	    .spec(requestSpecificationWithAuth(role, payLoad))
	    .when()
	    .post(SEARCH_ENDPOINT);
	}

}
