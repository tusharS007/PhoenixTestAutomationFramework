package com.api.services;

import static com.api.utils.SpecUtil.requestSpec;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.request.model.UserCredetials;

import io.restassured.response.Response;

public class AuthService {

	private static final String LOGIN_ENDPOINT = "login";
	private static final Logger LOGGER = LogManager.getLogger(AuthService.class);
	public Response login(Object userCredetials) {               // casting done to prevent printing from Password value
		LOGGER.info("Making logging request for the payload {}", ((UserCredetials)userCredetials).username());
		Response response =given()
		.spec(requestSpec(userCredetials))
		.when()
		.post(LOGIN_ENDPOINT);
		
		return response;
		
	}
	
}
