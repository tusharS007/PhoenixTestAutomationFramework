package com.api.services;

import static com.api.utils.SpecUtil.*;
import static io.restassured.RestAssured.given;

import com.api.constant.Role;

import io.restassured.response.Response;

public class MasterService {
	
	private static final String MASTER_ENDPOINT = "master";
	
	public Response master(Role role) {
	return given()
	.spec(requestSpecificationWithAuth(role))
	.when()
	.post(MASTER_ENDPOINT);
	}
	
	public Response master() {
		return given()
		.spec(requestSpec())
		.when()
		.post(MASTER_ENDPOINT);
		}
}
