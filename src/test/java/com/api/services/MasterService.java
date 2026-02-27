package com.api.services;

import static com.api.utils.SpecUtil.*;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constant.Role;

import io.restassured.response.Response;

public class MasterService {

	private static final String MASTER_ENDPOINT = "master";
	private static final Logger LOGGER = LogManager.getLogger(MasterService.class);

	public Response master(Role role) {
		LOGGER.info("Making request to the {} for the role {} ", MASTER_ENDPOINT, role);

		return given().spec(requestSpecificationWithAuth(role)).when().post(MASTER_ENDPOINT);
	}

	public Response master() {

		LOGGER.info("Making request to the {} ", MASTER_ENDPOINT);

		return given().spec(requestSpec()).when().post(MASTER_ENDPOINT);
	}
}
