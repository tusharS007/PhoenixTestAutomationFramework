package com.api.services;

import static com.api.utils.SpecUtil.requestSpec;
import static io.restassured.RestAssured.given;

import com.api.request.model.UserCredetials;

import io.restassured.response.Response;

public class AuthService {

	private static final String LOGIN_ENDPOINT = "login";
	
	public Response login(UserCredetials userCredetials) {
		
		Response response =given()
		.spec(requestSpec(userCredetials))
		.when()
		.post(LOGIN_ENDPOINT);
		
		return response;
		
	}
	
}
