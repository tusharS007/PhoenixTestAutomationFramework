package com.api.services;

import static com.api.utils.SpecUtil.requestSpecificationWithAuth;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constant.Role;

import io.restassured.response.Response;

public class JobService {
	
	
	private static final String CREATE_JOB_ENDPOINT = "/job/create";
	private static final String SEARCH_ENDPOINT = "/job/search";
	private static final Logger LOGGER = LogManager.getLogger(JobService.class);

	public Response create(Role role, Object payLoad) {
		LOGGER.info("Making request to the {} for the role {} and payload{}",CREATE_JOB_ENDPOINT,role, payLoad );
		
		return given()
	    .spec(requestSpecificationWithAuth(role, payLoad))
	    .when()
	    .post(CREATE_JOB_ENDPOINT);
	}
	
	public Response search(Role role, Object payLoad) {
		LOGGER.info("Making request to the {} for the role {} and payload{}",SEARCH_ENDPOINT,role, payLoad );
		
		return given()
	    .spec(requestSpecificationWithAuth(role, payLoad))
	    .when()
	    .post(SEARCH_ENDPOINT);
	}

}
